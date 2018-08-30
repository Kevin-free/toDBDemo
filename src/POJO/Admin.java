package POJO;

public class Admin {
	private String account;
	private String password;
	private String team;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Admin () {
		super();
	}
	public Admin(String account,String password) {
		this.account = account;
		this.password = password;
	}
}
