<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Student">
    <jsp:body>
        <nav class="navbar navbar-info top-navbar">
		    <div class="navbar-header">
		        <a class="navbar-brand" onclick="" href="${pageContext.request.contextPath }/page/admin">上机考试系统</a>
		    </div>
		    <ul class="nav navbar-nav navbar-right">
		   	 	<li>Time: <span id="time"></span>
		    	</li>
		        <li class="dropdown">
		            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
						当前用户：${student.name }&ensp;<i class="glyphicon glyphicon-chevron-down"></i>
		            </a>
		            <ul class="dropdown-menu">
		                <li><a href="${pageContext.request.contextPath }/admin/logout">commit</a>
		                </li>
		            </ul>
		        </li>
		    </ul>
		</nav>
    </jsp:body>
</tmp:common>