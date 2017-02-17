package springbook.user.sqlservice.test;

import springbook.user.sqlservice.UpdatableSqlRegistry;
import springbook.user.sqlservice.update.ConcurrentHashMapSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSQlRegistryTest {

	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}

}
