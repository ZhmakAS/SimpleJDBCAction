package ua.nure.zhmaka.Practice8.dto;

public class User {

	private int id;
	private String login;
	
	public static User createUser(String login) {
		User user = new User();
		user.setLogin(login);
		return user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + "]";
	}
	
	
	
	
	
}
