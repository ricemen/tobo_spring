package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserServiceTest {
	
	@Autowired UserService userService;
	@Autowired UserDao userDao;
	@Autowired UserServiceImpl UserServiceImpl;
	@Autowired MailSender mailSender;
	@Autowired PlatformTransactionManager transactionManager;	
	
	List<User> users;

	@Before
	public void setUp() {
		users = Arrays.asList(
			new User("wonseok1", "원석1", "p1", "ricemen@gmail.com", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER-1, 0),
			new User("wonseok2", "원석2", "p2", "ricemen@naver.com", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER, 0),
			new User("wonseok3", "원석3", "p3", "ricemen@nate.com", Level.SILVER, 60, UserServiceImpl.MIN_RECCOMENT_FOR_GOLD-1),
			new User("wonseok4", "원석4", "p4", "wonseok.cho@postvisual.com", Level.SILVER, 60, UserServiceImpl.MIN_RECCOMENT_FOR_GOLD),
			new User("wonseok5", "원석5", "p5", "ricemen@hanmail.com", Level.GOLD, 100, Integer.MAX_VALUE)
		);
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));
		
	}
	
	@Test
	public void upgradeLevels() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl(); 
		
		MockUserDao mockUserDao = new MockUserDao(this.users);  
		userServiceImpl.setUserDao(mockUserDao);

		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();

		List<User> updated = mockUserDao.getUpdated();  
		assertThat(updated.size(), is(2));  
		checkUserAndLevel(updated.get(0), "wonseok2", Level.SILVER);
		checkUserAndLevel(updated.get(1), "wonseok4", Level.GOLD);
		
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(2));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
		
//		UserServiceImpl userServiceImple = new UserServiceImpl();
//		MockUserDao mockUserDao = new MockUserDao(this.users);
//		userServiceImple.setUserDao(mockUserDao);
//		
//		MockMailSender mockMailSender = new MockMailSender();
//		UserServiceImpl.setMailSender(mockMailSender);
//		
//		userServiceImple.upgradeLevels();
//		
//		List<User> updated = mockUserDao.getUpdate();
//		assertThat(updated.size(), is(2));
//		checkUserAndLevel(updated.get(0), "ricemen@gmail.com", Level.SILVER);
//		checkUserAndLevel(updated.get(1), "ricemen@naver.com", Level.GOLD);
//		
//		List<String> request = mockMailSender.getRequests();
//		assertThat(request.size(), is(2));
//		assertThat(request.get(0), is(users.get(1).getEmail()));
//		assertThat(request.get(1), is(users.get(3).getEmail()));
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		TestUserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(userDao);
		testUserService.setMailSender(mailSender);
		
		UserServiceTx txUserService = new UserServiceTx();
		txUserService.setTransactionManager(transactionManager);
		txUserService.setUserService(testUserService);
		
		userDao.deleteAll();
		
		for(User user : users) userDao.add(user);
		
		try {
			txUserService.upgradeLevels();
			fail("TestUserServiceException expected");
			
		}catch(TestUserServiceException e) {
			
		}
		
		checkLevelUpgraded(users.get(1), false);
	}
	
	public void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		System.out.println(updated.toString());
		assertThat(updated.getId(), is(expectedId));
		assertThat(updated.getLevel(), is(expectedLevel));
	}
	
	public void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel() , is(expectedLevel));
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	@Test
	public void bean() {
		assertThat(this.userService, is(notNullValue()));
	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPasswd(), is(user2.getPasswd()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
	}
	static class MockUserDao implements UserDao {
		
		private List<User> users;
		private List<User> update = new ArrayList<User>();
		
		public MockUserDao(List<User> users) {
			this.users = users;
		}

		public List<User> getUpdated() {
			return update;
		}



		@Override
		public void add(User user) {
			throw new UnsupportedOperationException();
		}

		@Override
		public User get(String id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void update(User user) {
			update.add(user);
		}

		@Override
		public List<User> getAll() {
			return this.users;
		}

		@Override
		public void deleteAll() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getCount() {
			throw new UnsupportedOperationException();
		}

	}
	
	static class MockMailSender implements MailSender {
		
		private List<String> requests = new ArrayList<String>();
		
		public List<String> getRequests() {
			return requests;
		}
		@Override
		public void send(SimpleMailMessage mailMessage) throws MailException {
			requests.add(mailMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage[] arg0) throws MailException {
		}
		
	}
	
	
	static class TestUserService extends UserServiceImpl {
		private String id;
		
		private TestUserService(String id) {
			this.id = id;
		}

		@Override
		protected void upgradeLevel(User user) {
			if(user.getId().equals(this.id)) throw new TestUserServiceException();
			super.upgradeLevel(user);
		}
	}
	
	static class TestUserServiceException extends RuntimeException {}
}
