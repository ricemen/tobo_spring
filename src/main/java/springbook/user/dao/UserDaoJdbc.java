package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

@Repository
public class UserDaoJdbc implements UserDao {
	
	@Autowired
	private SqlService sqlService;
	
	private JdbcTemplate jdbcTemplate;
	
	public void setSqlService(SqlService sqlService) {
		this.sqlService = sqlService;
	}

	public void setUserMapper(RowMapper<User> userMapper) {
		this.userMapper = userMapper;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(User user) throws DuplicateKeyException {
		// use jdbcTemplate
		//this.jdbcTemplate.update(" insert into users(id, name, passwd, email, level, login, recommend) values(?, ?, ?, ?, ?, ?, ?) ", user.getId(), user.getName(), user.getPasswd(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
		this.jdbcTemplate.update(this.sqlService.getSql("userAdd"), user.getId(), user.getName(), user.getPasswd(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
		
	}
	

	public void update(User user) {
//		this.jdbcTemplate.update(
//			" update users set name = ?, passwd= ?, email = ?, level =?, login =?, recommend =? where id= ? ",
//			user.getName(), user.getPasswd(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId() 			
//		);
		this.jdbcTemplate.update(this.sqlService.getSql("userUpdate"), user.getName(), user.getPasswd(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
	}
	
	public User get(String id) {
		//return this.jdbcTemplate.queryForObject(" select * from users where id = ? ", new Object[] {id}, userMapper);
		return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"), new Object[] {id}, userMapper);
	}
	
	public List<User> getAll() {
//		return this.jdbcTemplate.query(" select * from users order by id ", userMapper);
		return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"), userMapper);
	}
	
	public void deleteAll() {
//		this.jdbcTemplate.update("delete from users");
		this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
	}
	
	public int getCount() {
//		return this.jdbcTemplate.queryForInt("select count(*) from users");
		return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPasswd(rs.getString("passwd"));
			user.setEmail(rs.getString("email"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		}
	};
}
