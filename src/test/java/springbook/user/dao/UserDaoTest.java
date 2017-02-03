package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private UserDaoJdbc dao;
	
	User user1;
	User user2;
	User user3;
	
	@Before
	public void setUp() {
		user1 = new User("wonseok1", "조원석1", "4321", Level.BASIC, 1, 0);
		user2 = new User("wonseok2", "조원석2", "4321", Level.SILVER, 55, 10);
		user3 = new User("wonseok3", "조원석3", "4321", Level.GOLD, 100, 40);
	}
	
	@Test
	public void addAndGet() throws SQLException {
		
//		User user1 = new User("ricemen", "조원석", "1234");
//		User user2 = new User("ricemen2", "조원석2", "1234");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		// add user
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);
		
		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
		
	}

	@Test
	public void count() throws SQLException {

//		User user1 = new User("wonseok1", "조원석1", "4321");
//		User user2 = new User("wonseok2", "조원석2", "4321");
//		User user3 = new User("wonseok3", "조원석3", "4321");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		// add user
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		// add user
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		// add user
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test
	public void update() {
		dao.deleteAll();
		
		dao.add(user1);
		dao.add(user2);
		
		user1.setName("이지은");
		user1.setPasswd("12345");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		dao.update(user1);
		
		User user1update = dao.get(user1.getId());
		checkSameUser(user1, user1update);
		User user2update = dao.get(user2.getId());
		checkSameUser(user2, user2update);
	}
	
//	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
		
//		dao.deleteAll();
//		assertThat(dao.getCount(), is(0));
//		
//		dao.get("unknow_id");
	}
	
	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();
		dao.add(user1); // Id: gyumee
		List<User> users1 = dao.getAll();
		assertThat(users1.size() , is(1));
		checkSameUser(user1 , users1.get(0)); 
		dao.add(user2); // Id: leegw7ÐÐ
		List<User> users2 = dao.getAll();
		assertThat(users2.size() , is(2));
		checkSameUser(user1 , users2 .get(0));
		checkSameUser(user2 , users2.get(1));
		dao.add(user3); // Id: bumjin
		List<User> users3 = dao.getAll();
		assertThat(users3 .size() , is(3));
		checkSameUser(user1 , users3.get(0)); 
		checkSameUser(user2 , users3.get(1));
		checkSameUser(user2, users3.get(2));
	}

	private void checkSameUser(User user1 , User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName() , is(user2.getName()));
		assertThat(user1.getPasswd(), is(user2.getPasswd()));
		assertThat(user1.getLevel() , is(user2.getLevel()));
		assertThat(user1.getLogin() , is(user2.getLogin()));
		assertThat(user1.getRecommend() , is(user2.getRecommend()));
	}

}
