package springbook.user.domain;

public class User {

	String id;
	String name;
	String passwd;
	
	public User() {
	}
	
	public User(String id, String name, String passwd) {
		this.id = id;
		this.name = name;
		this.passwd = passwd;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", passwd=" + passwd + ", toString()=" + super.toString() + "]";
	}
}
