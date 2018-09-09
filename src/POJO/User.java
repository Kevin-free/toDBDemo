package POJO;

public class User extends Basis {
	String userName;
	String userPhone;
	String userPassword;
	String imageUrl;
	String item;
	String room;
	String addTxt;
	String myPhone;
	String myName;
	String team;
	String dcmy;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMyPhone() {
		return myPhone;
	}

	public void setMyPhone(String myPhone) {
		this.myPhone = myPhone;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getDcmy() {
		return dcmy;
	}

	public void setDcmy(String dcmy) {
		this.dcmy = dcmy;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getAddTxt() {
		return addTxt;
	}

	public void setAddTxt(String addTxt) {
		this.addTxt = addTxt;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String toString() {
		return "User[userName=" + userName + ",userPhone=" + userPhone + ",userPassword=" + userPassword + ",item="
				+ item + ",room=" + room + ",addTxt=" + addTxt + ",myPhone=" + myPhone + ",myName=" + myName + ",team=" + team
				+  ",dcmy=" + dcmy + "]";
	}

}
