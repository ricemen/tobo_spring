package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDaoJdbc implements UserDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		int i = 3;
	}
	
	

	public void add(User user) throws DuplicateKeyException {
		// use jdbcTemplate
		this.jdbcTemplate.update(" insert into users(id, name, passwd, level, login, recommend) values(?, ?, ?, ?, ?, ?) ", user.getId(), user.getName(), user.getPasswd(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
	}
	

	public void update(User user) {
		this.jdbcTemplate.update(
			" update users set name = ?, passwd= ?, level =?, login =?, recommend =? where id= ? ", user.getName(), user.getPasswd(),user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId() 			
		);
	}
	
	public User get(String id) {
		return this.jdbcTemplate.queryForObject(" select * from users where id = ? ", new Object[] {id}, userMapper);
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query(" select * from users order by id ", userMapper);
	}
	
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}
	
	public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPasswd(rs.getString("passwd"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		}
	};
}
