<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/source/css/manager.css" />
<script type="text/javascript" >
$(document).ready(function(){
var h=$(window).height();
$(".side-navbar").css("min-height", h);
});
</script>
<nav class="navbar navbar-info top-navbar">
    <div class="navbar-header">
        <a class="navbar-brand" onclick="" href="">上机考试管理员后台管理系统</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
				当前用户：${sessionScope.admin.name }&ensp;<i class="glyphicon glyphicon-chevron-down"></i>
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
                <a class="active-menu" href="${pageContext.request.contextPath }/page/teacherManager"><i class="glyphicon glyphicon-user"></i> 教师管理</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/settings"><i class="glyphicon glyphicon-cog"></i> 全局设置</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/examClean"><i class="glyphicon glyphicon-chevron-up"></i> 考试清理</a>
            </li>
        </ul>
    </nav>
    <div class="col-md-10">
        <div style="padding:10px;">
        <jsp:doBody />
        </div>
    </div>
</div>
<div class="panel-footer text-center text-primary">
	版权所有&ensp;&copy;&ensp;第一小组：张亚康、赵俊福、姚亚强、刘冀峰
</div>