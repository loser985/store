<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'home.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>

<body>
	<iframe src="${pageContext.request.contextPath}/admin/top.jsp" name="topFrame" height="100px" width="1900px" scrolling="No" ></iframe>
	<iframe src="${pageContext.request.contextPath}/admin/left.jsp"  name="leftFrame" height="750px" width="200px" scrolling="yes"></iframe>
	<iframe src="${pageContext.request.contextPath}/admin/welcome.jsp" name="mainFrame" height="750px" width="1650px"></iframe>
	<iframe src="${pageContext.request.contextPath}/admin/bottom.jsp" name="bottomFrame" height="40px" width="1900px" scrolling="no"></iframe>
	
</body>  

<%-- <frameset rows="103,*,43" frameborder=0 border="0" framespacing="0">
  <frame src="${pageContext.request.contextPath}/admin/top.jsp" name="topFrame" scrolling="NO" noresize>
  <frameset cols="159,*" frameborder="0" border="0" framespacing="0">
		<frame src="${pageContext.request.contextPath}/admin/left.jsp" name="leftFrame" noresize scrolling="YES">
		<frame src="${pageContext.request.contextPath}/admin/welcome.jsp" name="mainFrame">
  </frameset>
  <frame src="${pageContext.request.contextPath}/admin/bottom.jsp" name="bottomFrame" scrolling="NO"  noresize>
</frameset> --%>
</html>
