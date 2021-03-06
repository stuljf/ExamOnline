<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Login">
    <jsp:body>
		<link rel="stylesheet" type="text/css"
		    href="${pageContext.request.contextPath }/source/css/login.css" />
		<script type="text/javascript"
		    src="${pageContext.request.contextPath }/source/js/login.js"></script>
        <div class="container">
	        <div class="row content">
	            <div class="col-md-7">
	                <div class="wolcome">
	                    <h1 class="text-info">Welcome to ExamOnline</h1>
	                    <p>Don't believe that winning is really everything. It's more important to stand for something.
	                    If you don't stand for something, what do you win?</p>
	                    <p class="text-right">--Yauto</p>
	                    <img src="${pageContext.request.contextPath }/source/images/fou_04.jpg" width="100%" />
	                </div>
	            </div>
	            <div class="col-md-5">
	                <div class="login">
	                    <div class="header">
	                        <div class="swidth"><a id="student_btn" onclick="swidth(this)" class="swidth_btn">学生登录</a></div>
	                        <div class="swidth"><a id="teacher_btn" onclick="swidth(this)" class="swidth_btn">教师登录</a></div>
	                    </div>
	                    <div id="student_login" class="login_form">
	                        <div id="s_tip" class="tip"></div>
	                        <form id="s_form" onkeydown="if(event.keyCode==13) s_login('${pageContext.request.contextPath}');">
	                            <div class="form-group"><input class="form-control" name="id" type="text" placeholder="请输入学号"/></div>
	                            <div class="form-group"><input class="form-control" name="name" type="text" placeholder="请输入姓名"/></div>
	                            <div class="form-group"><input class="btn btn-info btn-block" type="button" onclick="s_login('${pageContext.request.contextPath}')" value="登录"/></div>
	                        </form>
	                    </div>
	                    <div id="teacher_login" class="login_form">
	                        <div id="t_tip" class="tip"></div>
	                        <form id="t_form" onkeydown="if(event.keyCode==13) t_login('${pageContext.request.contextPath}');">
	                            <div class="form-group"><input class="form-control" name="id" type="text" placeholder="请输入用户名"/></div>
	                            <div class="form-group"><input class="form-control" name="passwd" type="password" placeholder="请输入密码"/></div>
	                            <div class="checkbox"><label><input id="is_admin" name="isAdmin" type="checkbox" />以管理员身份登录</label></div>
	                            <div class="form-group"><input class="btn btn-info btn-block" type="button" onclick="t_login('${pageContext.request.contextPath}')" value="登录"/></div>
	                        </form>
	                    </div>
	                </div>
	            </div>
            </div>
        </div>
    </jsp:body>
</tmp:common>