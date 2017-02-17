package springbook.user.sqlservice.test;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import springbook.user.sqlservice.SqlUpdateFailureException;
import springbook.user.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.update.EmbeddedDbSqlRegistry;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSQlRegistryTest {
	
	EmbeddedDatabase db;

	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		db = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
			.build();
		
		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(db);
		
		return embeddedDbSqlRegistry;
	}
	
	@After
	public void tearDown() {
		db.shutdown();
	}
	
	@Test
	public void transactionUpdate() {
		checkFindResult("SQL1", "SQL2", "SQL3");
		
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY112341234", "Modified9999");
		
		try {
			sqlRegistry.updateSql(sqlmap);
			fail();
		} catch(SqlUpdateFailureException e) {}
		
		checkFindResult("SQL1", "SQL2", "SQL3");
	}

}
