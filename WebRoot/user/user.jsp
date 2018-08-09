<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="UTF-8"%>
<%@ page import="ToExcel.toExcelUser"%>
<%@ page import="ToExcel.TestEmail"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <title>橙人交单表</title>
    <script type="text/javascript" src="/teaching/js/jquery.min.js"></script>
  </head>
  <body>
  <label>欢迎您：${sessionScope.name}[${sessionScope.type}]</label>
  
  <hr/>
  <table style="font-size:12px">
    <tr><td colspan="10">
      <select id="type" name="type">
        <option value="userName">姓名</option>
        <option value="userPhone">电话</option>
        <option value="item">项目</option>
      </select>
      <select id="judge" name="judge">
        <option value=">">大于</option>
        <option value="<">小于</option>
        <option value="=">等于</option>
      </select>
      <input type="text" id="condition" name="condition"/>
      <input type="hidden" id="pageNo" name="pageNo" value="1"/>
      <input type="button" id="btnQuery" value="查询" />
    </td></tr>
    
    <tr>
      <td>姓名：</td><td><input type="text" id="userName" name="userName"></td>
      <td>电话：</td><td><input type="text" id="userPhone" name="userPhone"></td>
      <td>密码：</td><td><input type="text" id="userPassword" name="userPassword"></td>
    </tr>
    <tr>
      <td>项目：</td><td><input type="text" id="item" name="item"></td>
    </tr>
    <tr>
      <td>橙人电话：</td><td><input type="text" id="myPhone" name="myPhone"></td>
      <td>橙人邮箱：</td><td><input type="text" id="myEmail" name="myEmail"></td> 
      <td>满意度：</td><td><input type="text" id="dcmy" name="dcmy"></td> 
    </tr>
    <tr>
      <td colspan="10">
        <input type="button" id="btnAdd" value="添加" />
      </td>
    </tr>
  </table>
  <hr/>
  <span id="sp"></span>
  <script type="text/javascript" src="user/user.js"></script>
</body>
</html>