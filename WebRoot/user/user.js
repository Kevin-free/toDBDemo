/**
 * 用于增删改查的脚本
 * 如需传参，参数格式：{key1:value1,key2:value2}
 * 参数类型：JSON对象
 */
//页面加载
$(document).ready(function(){
  $("#btnQuery").click();
});

/* //单击查询
$("#btnQuery").click(function(){
  var url = "/teaching/StudentAction/Get";
  var type = $("#type").val();
  var judge = $("#judge").val();
  var condition = $("#condition").val();
  var pageNo = $("#pageNo").val();
  var param = "{\"type\":\""+type+"\",\"judge\":\""+judge+"\",\"condition\":\""+condition+"\",\"pageNo\":\""+pageNo+"\"}";
  var jsonObj = JSON.parse(param);
  $.post(url,jsonObj,
    function(data){
      createTable2(data);
  });
});
 */
//单击添加
$("#btnAdd").click(function(){
  var url = "/toDBDemo/UserAction/Insert";
  var userName = $("#userName").val();
  var userPhone = $("#userPhone").val();
  var userPassword = $("#userPassword").val();
  var item = $("#item").val();
  var myPhone = $("#myPhone").val();
  var myEmail = $("#myEmail").val();
  var dcmy = $("#dcmy").val();
  
  var param = "{\"userName\":\""+userName+"\",\"userPhone\":\""+userPhone+"\",\"userPassword\":\""+userPassword+"\",\"item\":\""+item+"\",\"myPhone\":\""+myPhone+"\",\"myEmail\":\""+myEmail+"\",\"dcmy\":\""+dcmy+"\"}";
  var jsonObj = JSON.parse(param);
  $.post(url,jsonObj,
    function(data){
      var result = JSON.parse(data);
      if(result.success){$("#btnQuery").click();}
      else{alert(result.msg);}
  });
  
});

/* //单击修改
$("#btnEdit").click(function(){
  url = "/teaching/StudentAction/Update";
  var id = $("#id").val();
  var sequence = $("#sequence").val();
  var name = $("#name").val();
  var userPassword = $("#userPassword").val();
  var birthday = $("#birthday").val();
  var card = $("#card").val();
  var political = $("#political").val();
  var nation = $("#nation").val();
  var nativePlace = $("#nativePlace").val();
  var className = $("#className").val();
  
  var param = "{\"id\":\""+id+"\",\"sequence\":\""+sequence+"\",\"name\":\""+name+"\",\"userPassword\":\""+userPassword+"\",\"birthday\":\""+birthday+"\",\"card\":\""+card+"\",\"political\":\""+political+"\",\"nation\":\""+nation+"\",\"nativePlace\":\""+nativePlace+"\",\"className\":\""+className+"\"}";
  jsonObj = JSON.parse(param);
  $.post(url,jsonObj,
    function(data){
      var result = JSON.parse(data);
      if(result.success){$("#btnQuery").click();}
      else{alert(result.msg);}
  });
});

//单击删除按钮
$("#btnDel").click(function(){
  url = "/teaching/StudentAction/Delete";
  
  var id = $("#id").val();
  var param = "{\"id\":"+id+"}";
  jsonObj = JSON.parse(param);
  $.post(url,jsonObj,
    function(data){
      var result = JSON.parse(data);
      if(result.success){$("#btnQuery").click();}
      else{alert(result.msg);}
  });
});

//AJAX请求
function ajaxRequest(){
  $.post(url,jsonObj,
    function(data){
      createTable(data);
  });
};

//生成动态表格
//方法一：使用循环遍历JSON
function createTable(data){
  var jsonObj = JSON.parse(data);
  var str ="";
  //遍历JSON对象
  str += "<table border='1px' cellspacing=0px style='width:80%;font-size:12px'>";
    for (var i in jsonObj.rows){
      //显示标题
      if(i==0){
        str += "<tr>";
        for(j in jsonObj.rows[i]){
          str += "<td>";
          str += j + "</td>";
        }
        str += "</tr>";
      }
      str += "<tr>";
      //显示内容
      for(var j in jsonObj.rows[i]){
        str += "<td>"+jsonObj.rows[i][j] + "</td>";
      }
      str += "</tr>";
    }
    str += "</table>";
    //显示总记录数
    var cnt = jsonObj.count;//记录数
    var pageSize = 5;//每页显示的行数
    var pages = Math.ceil(cnt/pageSize);//总页数
    str += "<br/>共有"+cnt+"条记录<br/>";
    //按每页显示5条记录,动态生成N个超链接
    str += "<span id='spa'>";
    for(var k=1;k<=pages;k++){
      str +="&nbsp;<a href='javascript:void(0)' id='a"+k+"' onclick=reSend("+k+")>第"+k+"页</a>&nbsp;";
    }
    str += "</span>";
    $("#sp")[0].innerHTML = str;
}

//生成动态表格
//方法二：将遍历写死，直接获取指定名称的数据
//对于初学者，如果觉得以上循环难以理解，可以用这个方法
function createTable2(data){
  var jsonObj = JSON.parse(data);
  var str ="";
  //遍历JSON对象,动态生成表格
  str += "<table border='1px' cellspacing=0px style='width:80%;font-size:12px'>";
    //显示标题
    str += "<tr>";
    str += "<td>序号</td>";
    str += "<td>学号</td>";
    str += "<td>姓名</td>";
    str += "<td>性别</td>";
    str += "<td>出生日期</td>";
    str += "<td>身份证号</td>";
    str += "<td>民族</td>";
    str += "<td>籍贯</td>";
    str += "<td>政治面貌</td>";
    str += "<td>班级</td>";
    str += "</tr>";
    //显示内容
    for (var i in jsonObj.rows){
      str += "<tr>";
        str += "<td>"+jsonObj.rows[i].id + "</td>";
        str += "<td>"+jsonObj.rows[i].sequence + "</td>";
        str += "<td>"+jsonObj.rows[i].name + "</td>";
        str += "<td>"+jsonObj.rows[i].userPassword + "</td>";
        str += "<td>"+jsonObj.rows[i].birthday + "</td>";
        str += "<td>"+jsonObj.rows[i].card + "</td>";
        str += "<td>"+jsonObj.rows[i].nation + "</td>";
        str += "<td>"+jsonObj.rows[i].nativePlace + "</td>";
        str += "<td>"+jsonObj.rows[i].political + "</td>";
        str += "<td>"+jsonObj.rows[i].className + "</td>";
      str += "</tr>";
    }
  str += "</table>";
  //显示总记录数
  var cnt = jsonObj.count;//记录数
  var pageSize = 5;//每页显示的行数
  var pages = Math.ceil(cnt/pageSize);//总页数
  str += "<br/>共有"+cnt+"条记录<br/>";
  //按每页显示5条记录,动态生成N个超链接
  str += "<span id='spa'>";
  for(var k=1;k<=pages;k++){
    str +="&nbsp;<a href='javascript:void(0)' id='a"+k+"' onclick=reSend("+k+")>第"+k+"页</a>&nbsp;";
  }
  str += "</span>";
  $("#sp")[0].innerHTML = str;
}
 */
//单击超链接
function reSend(k){
  $("#pageNo").val(k);
  $("#btnQuery").click();
}