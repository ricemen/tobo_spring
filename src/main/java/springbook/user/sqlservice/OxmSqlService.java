package springbook.user.sqlservice;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import springbook.user.dao.UserDao;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class OxmSqlService implements SqlService {
	
	private final BaseSqlService baseSqlService = new BaseSqlService();
	
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	public void setSqlmapFile(Resource sqlmapFile) {
		this.oxmSqlReader.sqlMapFile = sqlmapFile;
	}
	

	@PostConstruct
	public void loadSql() {
//		this.oxmSqlReader.read(this.sqlRegistry);
		this.baseSqlService.setSqlReader(this.oxmSqlReader);
		this.baseSqlService.setSqlRegistry(this.sqlRegistry);
		
		this.baseSqlService.loadSql();
	}

	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
//		try {
//			return this.sqlRegistry.findSql(key);
//		} catch(SqlNotFoundException e) {
//			throw new SqlRetrievalFailureException(e);
//		}
		return this.baseSqlService.getSql(key);
	}
	
	private class OxmSqlReader implements SqlReader {
		
		private Unmarshaller unmarshaller;
		private Resource DEFAULT_SQLMAP_FILE = new ClassPathResource("sqlmap.xml");
		private Resource sqlMapFile = DEFAULT_SQLMAP_FILE;

		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}

		public void setSqlMapFile(Resource sqlMapFile) {
			this.sqlMapFile = sqlMapFile;
		}

		@Override
		public void read(SqlRegistry sqlRegistry) {
			try {
				
//				Source source = new StreamSource(UserDao.class.getResourceAsStream(this.sqlMapFile));
				Source source = new StreamSource(sqlMapFile.getInputStream());
				Sqlmap sqlmap = (Sqlmap)this.unmarshaller.unmarshal(source);
				
				for(SqlType sql : sqlmap.getSql()) {
					sqlRegistry.registerSql(sql.getKey(), sql.getValue());
				}
			} catch(IOException e) {
				throw new IllegalArgumentException(this.sqlMapFile.getFilename() + "을 가져올수 없습니다.", e);
			}
		}
		
	}

}
