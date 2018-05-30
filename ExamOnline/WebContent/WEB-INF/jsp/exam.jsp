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
		                <li><a href="${pageContext.request.contextPath }/admin/logout">提交试卷</a></li>
		            </ul>
		        </li>
		    </ul>
		</nav>
    
    </jsp:body>
</tmp:common>