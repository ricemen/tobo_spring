package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class RunDao {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
//		ConnectionMaker connectionMaker = new DConnectionMaker();
//		UserDao dao = new UserDao(connectionMaker);
//		UserDao dao = new DaoFactory().userDao();
		
//		// @Configuration 어노테이션 사용시 AnnotationConfigApplicationContext 메소드를 사용한다.
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);

		// XML 설정 파일 이용
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		String strCounter = "13";
		
		User user = new User();
		user.setId("wonseok" + strCounter);
		user.setName("조원석" + strCounter);
		user.setPasswd(strCounter);
		
		// add user
		dao.add(user);
		
		System.out.println(user.toString() + " 등록 성공");
		
		User user2 = new User();
		user2 = dao.get(user.getId());
		System.out.println(user2.toString() + " 조회 성공");
		
		
		if(!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패(name)");
		} else if(!user.getPasswd().equals(user2.getPasswd())) {
			System.out.println("테스트 실패(password)");
		} else {
			System.out.println("조회 테스트 성공");
		}
	}
}
