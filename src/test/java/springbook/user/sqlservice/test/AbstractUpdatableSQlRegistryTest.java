package springbook.user.sqlservice.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import springbook.user.sqlservice.SqlNotFoundException;
import springbook.user.sqlservice.SqlUpdateFailureException;
import springbook.user.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.update.ConcurrentHashMapSqlRegistry;

public abstract class AbstractUpdatableSQlRegistryTest {

	UpdatableSqlRegistry sqlRegistry;
	
	abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry();
	
	@Before
	public void setUp() {
		sqlRegistry = createUpdatableSqlRegistry();
		
		sqlRegistry.registerSql("KEY1", "SQL1");
		sqlRegistry.registerSql("KEY2", "SQL2");
		sqlRegistry.registerSql("KEY3", "SQL3");
	}
	
	@Test
	public void find() {
		checkFindResult("SQL1", "SQL2", "SQL3");
	}
	
	@Test(expected=SqlNotFoundException.class)
	public void unknownKey() {
		sqlRegistry.findSql("SAdfas2314");
	}
	
	@Test
	public void updateSingle() {
		sqlRegistry.updateSql("KEY2", "Modifed2");
		checkFindResult("SQL1", "Modifed2", "SQL3");
	}
	
	@Test
	public void updateMulti() {
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modifed1");
		sqlmap.put("KEY3", "Modifed3");
		
		sqlRegistry.updateSql(sqlmap);
		checkFindResult("Modifed1", "SQL2", "Modifed3");
	}
	
	@Test(expected=SqlUpdateFailureException.class)
	public void updateWithNotExistingKey() {
		sqlRegistry.updateSql("sadfasdf", "modify2");
	}
	
	protected void checkFindResult(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
	}
	
	protected void checkFind(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));		
		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));		
		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));		
	}

}
