package springbook.user.sqlservice;

public class DefaultSqlService extends BaseSqlService {

	protected SqlRegistry sqlRegistry;
	protected SqlReader sqlReader;
	
	public DefaultSqlService() {
		setSqlReader(new JaxbXmlSqlReader());
		setSqlRegistry(new HashMapSqlRegistry());
	}
}
