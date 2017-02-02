package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
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
