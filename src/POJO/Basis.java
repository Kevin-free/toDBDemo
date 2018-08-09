package POJO;

public class Basis {
	String pageNo;
  String condition;
  
  public String getPageNo() {
    return pageNo;
  }
  public void setPageNo(String pageNo) {
    this.pageNo = pageNo;
  }
  public String getCondition() {
    return condition;
  }
  public void setCondition(String condition) {
    this.condition = condition;
  }
  
  public String toString() {
    return "Basis [pageNo=" + pageNo + ", condition=" + condition + "]";
  }
  
}
