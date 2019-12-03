<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head></head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
/* 	position:relative;
	 float:left; */
	
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>

</head>
<body>

	<c:if test="${not empty m }">
		<%
			session.setAttribute("m", null);
				session.setAttribute("msg1", null);
		%>
	</c:if>
	<c:if test="${not empty msg1 }">
		<%
			session.setAttribute("m", 1);
		%>
	</c:if>



	<!-- 动态包含 -->
	<jsp:include page="/jsp/head.jsp"></jsp:include>



	<div class="container"
		style="width:100%;background:url('${pageContext.request.contextPath}/image/regist_bg.jpg');">
		<div class="row">

			<div class="col-md-2"></div>

			<div class="col-md-8"
				style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
				<font>会员注册</font>USER REGISTER&nbsp;&nbsp;<font color="red"
					style="color: red">${msg1}</font>
				<form class="form-horizontal" style="margin-top:5px;"
					action="${pageContext.request.contextPath}/user?method=register"
					method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username"
								placeholder="请输入用户名" name="username">
						</div>
						<span id="userspan"></span>
					</div>
					<script type="text/javascript">
						$(function() {
							$("[name='username']").blur(function() {
								var value = $(this).val()
								if (value != "") {
									$.get("${pageContext.request.contextPath}/user?method=checkUsername4Ajax", {
										"value" : value
									}, function(d) {
										if (d == 1) {
											$("#userspan").html("<font style='color: red'>用户名已注册</font>")
											$("#sub").prop("disabled", true)
										} else {
											$("#userspan").html("<font style='color: green'>用户名可以使用</font>")
											$("#sub").prop("disabled", false)
										}
									}, "json")
								} else {
									$("#userspan").html("")
									$("#sub").prop("disabled", true)
								}
							})
						})
					</script>


					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="inputPassword3"
								placeholder="请输入密码" name="password">
						</div>
						<span id="pdspan"></span>
					</div>
					
					<script type="text/javascript">
						$(function(){
							$("#inputPassword3").blur(function(){
								var pd1 = $("[name='password']").val()
								var pd2 = $("#confirmpwd").val()
								if(pd2!=""){
									if(pd1!=pd2){
										$("#pdspan").html("<font style='color: red'>两次密码不相同</font>")
									}else{
										$("#pdspan").html("")
										$("#pdspan2").html("")
									}
								}else{
									$("#pdspan").html("")
								}
							})
						})
					</script>
					
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								placeholder="请输入确认密码">
						</div>
						<span id="pdspan2"></span>
					</div>

					<script type="text/javascript">
						$(function() {
							$("#confirmpwd").blur(function() {
								var pd1 = $("[name='password']").val()
								var pd2 = $("#confirmpwd").val()
								if (pd2 != "") {
									if (pd1 != pd2) {
										$("#pdspan2").html("<font style='color: red'>两次密码不相同</font>")
									}else{
										$("#pdspan").html("")
										$("#pdspan2").html("")
									}
								}else{
									$("#pdspan2").html("")
								}
							})
						})
					</script>

					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3"
								placeholder="Email" name="email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption"
								placeholder="请输入姓名" name="name">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								id="inlineRadio1" name="sex" value="男"> 男
							</label> <label class="radio-inline"> <input type="radio"
								id="inlineRadio2" name="sex" value="女"> 女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkcode" placeholder="请输入验证码" >

						</div>
						<div class="col-sm-2">
							<img alt="验证码"
								src="${pageContext.request.contextPath }/codeServlet"
								title="看不清楚，换一张" onclick="changeImg(this)">
						</div>
						<script type="text/javascript">
							function changeImg(obj) {
								obj.src = "${pageContext.request.contextPath }/codeServlet?" + Math.random();
							}
						</script>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								id="sub" border="0"
								style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>



	<div style="margin-top:50px;">
		<img src="${pageContext.request.contextPath}/image/footer.jpg"
			width="100%" height="78" alt="我们的优势" title="我们的优势" />
	</div>

	<div style="text-align: center;margin-top: 5px;">
		<ul class="list-inline">
			<li><a>关于我们</a></li>
			<li><a>联系我们</a></li>
			<li><a>招贤纳士</a></li>
			<li><a>法律声明</a></li>
			<li><a>友情链接</a></li>
			<li><a target="_blank">支付方式</a></li>
			<li><a target="_blank">配送方式</a></li>
			<li><a>服务声明</a></li>
			<li><a>广告声明</a></li>
		</ul>
	</div>
	<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
		Copyright &copy; 2018-2020 传智商城 版权所有</div>

</body>
</html>




