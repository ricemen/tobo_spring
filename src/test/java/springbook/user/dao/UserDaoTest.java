package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {
	
//	@Autowired
//	private ApplicationContext context;
	
	@Autowired
	private UserDao dao;
	
	User user1;
	User user2;
	User user3;
	
	@Before
	public void setUp() {
		
//		System.out.println(this.context);
//		System.out.println(this);
////		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		this.dao = context.getBean("userDao", UserDao.class);
		
		user1 = new User("wonseok1", "조원석1", "4321");
		user2 = new User("wonseok2", "조원석2", "4321");
		user3 = new User("wonseok3", "조원석3", "4321");
	}
	
	@Test
	public void addAndGet() throws SQLException {
		// XML 설정 파일 이용
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user1 = new User("ricemen", "윤서아빠", "1234");
		User user2 = new User("ricemen2", "윤서아빠2", "1234");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		// add user
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPasswd(), is(user1.getPasswd()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPasswd(), is(user2.getPasswd()));
		
	}

	@Test
	public void count() throws SQLException {
		// XML 설정 파일 이용
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);

		User user1 = new User("wonseok1", "조원석1", "4321");
		User user2 = new User("wonseok2", "조원석2", "4321");
		User user3 = new User("wonseok3", "조원석3", "4321");
		
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
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknow_id");
	}

}
