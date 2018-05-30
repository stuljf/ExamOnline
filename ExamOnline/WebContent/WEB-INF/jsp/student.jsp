<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Student">
    <jsp:body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/source/css/student.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath }/source/js/student.js"></script>
        <nav class="navbar navbar-info top-navbar">
		    <div class="navbar-header">
		        <a class="navbar-brand" onclick="" href="${pageContext.request.contextPath }/page/admin">上机考试系统</a>
		    </div>
		    <ul class="nav navbar-nav navbar-right">
		   	 	<li><a>时间：<span id="time"></span></a></li>
		        <li class="dropdown">
		            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
						当前考生：${student.name }&ensp;<i class="glyphicon glyphicon-chevron-down"></i>
		            </a>
		            <ul class="dropdown-menu">
		                <li><a href="${pageContext.request.contextPath }/student/logout">退出登录</a></li>
		            </ul>
		        </li>
		    </ul>
		</nav>
		<div class="container">
			<div class="jumbotron text-center info">
           		<h2 class="text-info">请确认考生信息</h2>
             	<table class="text-primary" style="width: 100%">
             		<tr>
             		<td>学号：${student.id }</td>
             		
             		<td>姓名：${student.name }</td>
             		
             		<td>班级：${student.clazz }</td>
             		</tr>
             	</table>
            </div>
            <div class="text-info">确认信息无误后，点击开始考试按钮，开始进行中的考试</div>
            <!--工具栏-->
		    <div id="toolbar" class="btn-group">
		        <button id="btn_start" type="button" class="btn btn-default">
		            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>开始考试
		        </button>
		    </div>
			<table id="table"></table>
		</div>
        <div style="height: 50px;"></div>
    </jsp:body>
</tmp:common>