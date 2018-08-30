package POJO;

public class Cr {
	String team;
	String phone;
	String name;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 定义空构造方法
	public Cr() {
		super();
	}
	
	//重写构造方法
	public Cr(String phone) {
		this.phone = phone;
	}
	
	public Cr(String phone,String team) {
		this.phone = phone;
		this.team = team;
	}
	
	public Cr(String phone,String name,String team) {
		this.phone = phone;
		this.name = name;
		this.team = team;
	}
	
	public String toString() {
		return "Add Cr[crName=" + name + ",crPhone=" + phone + ",crTeam=" + team + "]";
	}
	
}
