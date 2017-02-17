package springbook.user.sqlservice.updatable.test;

import org.junit.After;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import springbook.user.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.test.AbstractUpdatableSQlRegistryTest;
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

}
