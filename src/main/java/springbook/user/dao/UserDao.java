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
	
	private JdbcContext jdbcContext;	

	public void setDataSource(DataSource dataSource) {
		this.jdbcContext = new JdbcContext();
		this.jdbcContext.setDataSource(dataSource);
		this.dataSource = dataSource;
	}

//	public void setJdbcContext(JdbcContext jdbcContext) {
//		this.jdbcContext = jdbcContext;
//	}

	public void add(User user) throws SQLException {
		
		// AddStatement 를 내부클래스로 정의
		jdbcContextWithStatementStrategy(
				new StatementStrategy() {
					@Override
					public PreparedStatement makepreparedStatement(Connection c) throws SQLException {
						PreparedStatement ps = c.prepareStatement("insert into users(id, name, passwd) values(?, ?, ?) ");
						ps.setString(1, user.getId());
						ps.setString(2, user.getName());
						ps.setString(3, user.getPasswd());
						
						return ps;
					}
				}
		);
//		this.jdbcContext.workWithStatementStrategy(
//			new StatementStrategy() {
//				@Override
//				public PreparedStatement makepreparedStatement(Connection c) throws SQLException {
//					PreparedStatement ps = c.prepareStatement("insert into users(id, name, passwd) values(?, ?, ?) ");
//					ps.setString(1, user.getId());
//					ps.setString(2, user.getName());
//					ps.setString(3, user.getPasswd());
//					
//					return ps;
//				}
//			}
//		);
	}
	
	public User get(String id) throws SQLException {
		
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ? ");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		
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
//		jdbcContextWithStatementStrategy(
//				new StatementStrategy() {
//					@Override
//					public PreparedStatement makepreparedStatement(Connection c) throws SQLException {
//						PreparedStatement ps = c.prepareStatement("delete from users");
//						return ps;
//					}
//				}
//		);
		
		System.out.println("jdbcContext : " + jdbcContext);
		this.jdbcContext.workWithStatementStrategy(
			new StatementStrategy() {  
				public PreparedStatement makepreparedStatement(Connection c) throws SQLException {
					return c.prepareStatement("delete from users");
				}
			}				
		);
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
		
//		jdbcContextWithStatementStrategy(
//			new StatementStrategy() {
//				@Override
//				public PreparedStatement makepreparedStatement(Connection c) throws SQLException {
//					PreparedStatement ps = c.prepareStatement("select count(*) from users");
//					ResultSet rs = ps.executeQuery();					
//				}
//			};
//		);
	}
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			
			c = dataSource.getConnection();
			ps = stmt.makepreparedStatement(c);
			ps.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			try { if(ps != null) ps.close(); } catch (Exception e) {} 
			try { if(c != null) c.close(); } catch (Exception e) {} 
		}
	}

}
