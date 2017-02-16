package springbook.user.sqlservice;

public interface SqlRegistry {

	void registerSql(String key, String sql); // sql을 키와 함꼐 등록한다.
	String findSql(String key) throws SqlNotFoundException;
}
