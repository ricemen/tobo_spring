package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class CountingDaoFactory {
	
//	@Bean
//	public UserDao userDao() {
//		return new UserDao(connectionMaker());
//	}
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
//		userDao.setConnectionMaker(connectionMaker());
		userDao.setDataSource(dataSource());
		return userDao;
	}	
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource datasource = new SimpleDriverDataSource();
		
		datasource.setDriverClass(org.h2.Driver.class);
		datasource.setUrl("jdbc:h2:tcp://localhost/~/test");
		datasource.setUsername("sa");
		datasource.setPassword("");
		
		return datasource;
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}
	@Bean
	public ConnectionMaker realConnectionMaker() {
		return new DConnectionMaker();
	}

}
