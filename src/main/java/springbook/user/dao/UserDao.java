package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
//	private ConnectionMaker connectionMaker;
//	
//	public ConnectionMaker getConnectionMaker() {
//		return connectionMaker;
//	}
//
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}	
	
//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
//	private SimpleConnectionMaker simpleConnectionMaker;
//	
//	public UserDao() {
//		simpleConnectionMaker = new SimpleConnectionMaker();
//	}
	
//	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
//	public Connection getConnection() throws ClassNotFoundException, SQLException {
//		Class.forName("org.h2.Driver");
//		return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
//		
//	}

	public void add(User user) throws SQLException {
//		Connection c = getConnection();
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, passwd) values(?, ?, ?) ");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPasswd());
		
		ps.executeUpdate();
		ps.close();
		c.close();				
	}
	
	public User get(String id) throws SQLException {
		
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ? ");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		
//		rs.next();
//		
//		User user = new User();
//		user.setId(rs.getString("id"));
//		user.setName(rs.getString("name"));
//		user.setPasswd(rs.getString("passwd"));
		
		User user = null;
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPasswd(rs.getString("passwd"));
		}
		
		rs.close();
		ps.clearBatch();
		c.close();
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		return user;		
	}
	
	public void deleteAll() throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("delete from users");
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	
	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select count(*) from users");
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		return count;
	}

}
