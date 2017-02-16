package springbook.user.sqlservice;

import javax.annotation.PostConstruct;

import org.junit.Test;


public class XmlSqlService {
	
	private String sqlMapFile;
	private SqlRegistry sqlRegistry;
	private SqlReader sqlReader;
	
	@Test
	public void dispPath() {
//		System.out.println(Sqlmap.class.getPackage().getName());
	}
	
	public void setSqlMapFile(String sqlMapFile) {
		this.sqlMapFile = sqlMapFile;
	}
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}

	@PostConstruct
	public void loadSql() {
		this.sqlReader.read(sqlRegistry);
	}

	public XmlSqlService() {

	}

}
