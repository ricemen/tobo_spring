package springbook.user.sqlservice;

import java.util.Map;

public class SimpleSqlService implements SqlService {
	
	private Map<String, String> sqlMap;

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		String sql = this.sqlMap.get(key);
		if(sql == null) throw new SqlRetrievalFailureException(key + " 에 대한 SQL을 찾을수 없습니다.", null);
		else return sql;
	}

}
