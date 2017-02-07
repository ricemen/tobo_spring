package springbook.user.domain;

public class User {

	Level level;
	String id;
	String name;
	String passwd;
	int login;
	int recommend;
	
	public User() {
	}
	
	public User(String id, String name, String passwd, Level level, int login, int recommend) {
		super();
		this.level = level;
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.login = login;
		this.recommend = recommend;
	}

	public User(String id, String name, String passwd) {
		this.id = id;
		this.name = name;
		this.passwd = passwd;
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
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
	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	public void upgradLevel() {
		Level nextLevel = this.level.nextLevel();
		if(nextLevel == null) {
			throw new IllegalArgumentException(this.level + " 은 업그레이드가 불가능합니다.");
		} else {
			this.level = nextLevel;
		}
		
	}
	
	@Override
	public String toString() {
		return "User [level=" + level + ", id=" + id + ", name=" + name + ", passwd=" + passwd + ", login=" + login
				+ ", recommend=" + recommend + ", toString()=" + super.toString() + "]";
	}
}
