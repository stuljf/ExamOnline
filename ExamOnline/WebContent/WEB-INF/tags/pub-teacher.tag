<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/source/css/manager.css" />
<script type="text/javascript" >
$(document).ready(function(){
var h=$(window).height();
$(".side-navbar").css("min-height", h);
});
</script>
<nav class="navbar navbar-info top-navbar" style="margin: 0">
    <div class="navbar-header">
        <a class="navbar-brand" onclick="" href="${pageContext.request.contentType }/page/teacher">上机考试管理员后台管理系统</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <i class="glyphicon glyphicon-user"></i>${teacher.name }<i class="glyphicon glyphicon-chevron-down"></i>
            </a>
            <ul class="dropdown-menu">
                <li><a href="#">passwd</a>
                </li>
                <li><a href="#">Logout</a>
                </li>
            </ul>
        </li>
    </ul>
</nav>
<div class="row content">
    <nav class="col-md-2 navbar-info side-navbar">
        <ul class="nav">
            <li>
                <a class="active-menu" href=""><i class="glyphicon glyphicon-chevron-down"></i> Dashboard</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/examListCreated"><i class="glyphicon glyphicon-cog"></i>考前管理</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-chevron-up"></i>考中管理</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-user"></i>考后管理</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/examList"><i class="glyphicon glyphicon-picture"></i>考试概览</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-home"></i>IP解绑</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-repeat"></i>待添加...</a>
            </li>
        </ul>
    </nav>
    <div class="col-md-10">
        <jsp:doBody />
    </div>
</div>