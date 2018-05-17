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
        <a class="navbar-brand" onclick="" href="">上机考试管理员后台管理系统</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                <i class="glyphicon glyphicon-user"></i> username <i class="glyphicon glyphicon-chevron-down"></i>
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
                <a href=""><i class="glyphicon glyphicon-cog"></i> UI Elements</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-chevron-up"></i> Charts</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-user"></i> Tabs  Panels</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-picture"></i> Responsive Tables</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-home"></i> Forms</a>
            </li>
            <li>
                <a href=""><i class="glyphicon glyphicon-repeat"></i> Empty Page</a>
            </li>
        </ul>
    </nav>
    <div class="col-md-10">
        <jsp:doBody />
    </div>
</div>