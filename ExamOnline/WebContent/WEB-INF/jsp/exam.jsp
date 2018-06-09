<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
    .item_title {
        font-size: 18px;
        font-weight: 700;
    }
    .item_selection {
        font-size: 15px;
    }
</style>
<tmp:common title="Student">
    <jsp:body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/source/css/student.css" />
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
		                <li><a href="#">提交试卷</a></li>
		            </ul>
		        </li>
		    </ul>
		</nav>
    <!-- 动态加载试题 -->
    <div class="container">
	    <hr>
        <form class="form-horizontal" role="form">
	        <c:forEach items="${ques }" var="q" varStatus="index">
		        <div class="item">
		            <input type="hidden" name="questions[${q.number }]number" value="${q.number }">
		            <div class="item_title">${index.count }、${q.title }</div>
		            <c:if test="${q.type == 'choice'}">
			            <div class="item_body" style="padding: 5px 20px">
			                <c:forTokens var="s" items="${q.selection }" delims="," varStatus="si">
			                    <a href="#" class="list-group-item">${si.count == 1? 'A：':''}${si.count == 2? 'B：':''}${si.count == 3? 'C：':''}${si.count == 4? 'D：':''}${s}</a>
			                </c:forTokens>
			            </div>
		            </c:if>
		            <div class="item_answer">
		                <textarea class="form-control" rows="3" name="questions[${q.number }]answer" placeholder="请输入您的答案"></textarea>
		            </div>
		        </div>
                <hr>
            </c:forEach>
        </form>
    </div> 
    </jsp:body>
</tmp:common>