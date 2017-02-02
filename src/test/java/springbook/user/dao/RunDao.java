package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class RunDao {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
//		ConnectionMaker connectionMaker = new DConnectionMaker();
//		UserDao dao = new UserDao(connectionMaker);
//		UserDao dao = new DaoFactory().userDao();
		
//		// @Configuration ������̼� ���� AnnotationConfigApplicationContext �޼ҵ带 ����Ѵ�.
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		UserDao dao = context.getBean("userDao", UserDao.class);

		// XML ���� ���� �̿�
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		String strCounter = "13";
		
		User user = new User();
		user.setId("wonseok" + strCounter);
		user.setName("������" + strCounter);
		user.setPasswd(strCounter);
		
		// add user
		dao.add(user);
		
		System.out.println(user.toString() + " ��� ����");
		
		User user2 = new User();
		user2 = dao.get(user.getId());
		System.out.println(user2.toString() + " ��ȸ ����");
		
		
		if(!user.getName().equals(user2.getName())) {
			System.out.println("�׽�Ʈ ����(name)");
		} else if(!user.getPasswd().equals(user2.getPasswd())) {
			System.out.println("�׽�Ʈ ����(password)");
		} else {
			System.out.println("��ȸ �׽�Ʈ ����");
		}
	}
}
