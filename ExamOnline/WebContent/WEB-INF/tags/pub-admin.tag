<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                <a class="active-menu" href="${pageContext.request.contextPath }/admin/teacher/list"><i class="glyphicon glyphicon-chevron-down"></i> 教师管理</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/admin/setting/list"><i class="glyphicon glyphicon-cog"></i> 全局设置</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/admin/exam/closed"><i class="glyphicon glyphicon-chevron-up"></i> 考试清理</a>
            </li>
        </ul>
    </nav>
    <div class="col-md-10">
        <jsp:doBody />
    </div>
</div>