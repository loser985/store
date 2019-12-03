<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="${pageContext.request.contextPath }/img/logo2.png" />
	</div>
	
	<div class="col-md-5" style="padding-top: 5px;">
		<span class="h1"><font face="楷体" style="font-size: 50px;color: #000;padding-right: 10px;font-weight: normal;padding: 0 10px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传智播客</font></span>
					<font face="黑体" style="font-size:15px;color: #000;padding-right: 10px;font-weight: normal;padding:0px">--黑马程序员</font>
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${not empty user }">
							<a href="javascript:void(0)" id="userName">${user.name }</a> : 欢迎回来!
							<li><a
					href="${pageContext.request.contextPath}/user?method=logOut" class="btn btn-default">退出</a></li>
				<li><a
					href="${pageContext.request.contextPath}/order?method=findAllByPage&currPage=1" class="btn btn-default">我的订单</a></li>
			</c:if>
			<c:if test="${empty user }">
				<li><a
					href="${pageContext.request.contextPath}/user?method=loginUi" class="btn btn-default">登录</a></li>
				<li><a
					href="${pageContext.request.contextPath}/user?method=registUi" class="btn btn-default">注册</a></li>
			</c:if>
			<li><a
				href="${pageContext.request.contextPath}/jsp/cart.jsp" class="btn btn-default">购物车</a></li>
		</ol>
	</div>
</div>
<script>
	$(function(){
		$("#userName").click(function(){
			layer.open({
				type:1,
				title:"用户信息",
				area:['500px','400px'],
				shadeClose: false,
				content:'&nbsp;&nbsp;&nbsp;用户名&nbsp;:&nbsp;${user.username}<br>&nbsp;&nbsp;&nbsp;姓名&nbsp;:&nbsp;${user.name}<br>&nbsp;&nbsp;&nbsp;性别&nbsp;:&nbsp;${user.sex}<br>&nbsp;&nbsp;&nbsp;邮箱&nbsp;:&nbsp;${user.email}<br>&nbsp;&nbsp;&nbsp;电话&nbsp;:&nbsp;${user.telephone}<br>&nbsp;&nbsp;&nbsp;出生日期&nbsp;:&nbsp;${user.birthday}'				
			})
		})
	})
</script>
<!--
            	时间：2015-12-30
            	描述：导航条
            -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }/index.jsp">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="menuId">
					<%-- <c:forEach items="${clist }" var="c">
						<li><a href="#">${c.cname}</a></li>
					</c:forEach> --%>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>

			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>
<script>
	$(function(){
		$.get("${pageContext.request.contextPath}/category?method=findAll",function(data){
			var ul=$("#menuId")
			$(data).each(function(){
				ul.append($("<li><a href='${pageContext.request.contextPath}/product?method=findByPage&cid="+this.cid+"&currPage=1'>"+this.cname+"</a></li>"));
			})
		},"json")
	})
</script>