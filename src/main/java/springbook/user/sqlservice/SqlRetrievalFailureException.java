package springbook.user.sqlservice;

public class SqlRetrievalFailureException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SqlRetrievalFailureException(RuntimeException e) {
		super(e);
	}
	
	public SqlRetrievalFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
