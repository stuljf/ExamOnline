<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../source/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="../source/css/login.css" />
<script type="text/javascript" src="../source/jquery/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="../source/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../source/js/login.js"></script>
</head>
<body>
	<div class="container">
		<div class="row content">
			<div class="col-md-7">
				<div class="wolcome">
					<h1 class="text-info">Wolcome to ExamOnline!</h1>
					<p>谁无聊拿放大镜看风景累不累却忘记了看清楚自己是谁，
					我的宇宙轻飘飘美得摇摇欲坠旁人来来去去像行云流水。</p>
					<p>模糊糊的视线不管天色黑不黑心中没鬼就不用处处防备。</p>
					<img src="source/images/fou_04.jpg" width="100%" />
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
						<form id="s_form" onsubmit="return false">
							<div class="form-group"><input class="form-control" name="username" type="text" placeholder="请输入学号"/></div>
							<div class="form-group"><input class="form-control" name="passwd" type="password" placeholder="请输入密码"/></div>
							<div class="form-group"><input class="btn btn-info btn-block" type="submit" onclick="s_login()" value="登录"/></div>
						</form>
					</div>
					<div id="teacher_login" class="login_form">
						<div id="t_tip" class="tip"></div>
						<form id="t_form" onsubmit="return false">
							<div class="form-group"><input class="form-control" type="text" placeholder="请输入用户名"/></div>
							<div class="form-group"><input class="form-control" type="password" placeholder="请输入密码"/></div>
							<div class="checkbox"><label><input type="checkbox" />以管理员身份登录</label></div>
							<div class="form-group"><input class="btn btn-info btn-block" type="submit" onclick="t_login()" value="登录"/></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>