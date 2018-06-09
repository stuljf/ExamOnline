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
        <a class="navbar-brand" onclick="" href="${pageContext.request.contextPath}/page/teacher">上机考试教师后台管理系统</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="cursor:pointer;">
				当前用户：${teacher.name }&ensp;<i class="glyphicon glyphicon-chevron-down"></i>
            </a>
            <ul class="dropdown-menu">
                <li>
                	<a href="#" data-toggle="modal" data-target="#changePwdModal">修改密码</a>
                </li>
                <li>
                	<a href="${pageContext.request.contextPath}/teacher/logout/${teacher.id }">退出登录</a>
                </li>
            </ul>
        </li>
    </ul>
    <!-- 模态框（Modal） -->
	<div class="modal fade" id="changePwdModal"  data-keyboard="false" data-backdrop="static"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                    &times;
	                </button>
	                <h4 class="modal-title">
	                   修改密码
	                </h4>
	            </div>
	            <div class="modal-body">
	               <form id="changePwdForm" action="#" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">原密码</label>
                            <div class="col-sm-8">
                                <input name="id" type="hidden" value="${teacher.id }">
                                <input name="passwd" type="password"
                                                    class="form-control" placeholder="请输入原密码">
                            </div>
                            <div class="col-sm-2" style="color:red">*</div>
                        </div>	 
                        <div class="form-group">
                            <label class="col-sm-2 control-label">新密码</label>
                            <div class="col-sm-8">
                                <input name="newPasswd" type="password"
                                                    class="form-control" placeholder="请输入新密码">
                            </div>
                             <div class="col-sm-2" style="color:red">*</div>
                        </div>   
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确认密码</label>
                            <div class="col-sm-8">
                                <input name="ackNewPasswd" type="password"
                                                    class="form-control" placeholder="请输入确认密码 ">
                            </div>
                             <div class="col-sm-2" style="color:red">*</div>
                        </div>                     
	               </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
	                </button>
	                <button type="button" id="changePwd" class="btn btn-primary">
	                    提交更改
	                </button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
    <script type="text/javascript">
        $(function() {
        	$("#changePwd").click(function() {
        		if ($(":input[name='newPasswd']").val() == $(":input[name='ackNewPasswd']").val()) {
        			//post提交
        			var url = "${pageContext.request.contextPath}/teacher/changePwd";
        			var params = $("#changePwdForm").serialize();
        			$.post(url, params, function(data) {
        				if (data.status == 200) {
        					alert("修改成功，请重新登录！");
        					window.location = "${pageContext.request.contextPath}/page/login";
        				} else {
        					alert(data.msg);
        				}
        			})
        		} else {
        			alert("两次密码不一致，请重新输入！");
        			$(":input[name='newPasswd']").val("");
        			$(":input[name='ackNewPasswd']").val("");
        		}
        	})
        })
    </script>
</nav>
<div class="row content">
    <nav class="col-md-2 navbar-info side-navbar">
        <ul class="nav">
            <li>
                <a href="${pageContext.request.contextPath }/page/examList"><i class="glyphicon glyphicon-home"></i> 考试概览</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/examListCreated"><i class="glyphicon glyphicon-cloud"></i> 考前管理</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/examListBegined"><i class="glyphicon glyphicon-tasks"></i> 考中管理</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/examListEnded"><i class="glyphicon glyphicon-saved"></i> 考后管理</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath }/page/unbindIp"><i class="glyphicon glyphicon-paperclip"></i> IP解绑</a>
            </li>
        </ul>
    </nav>
    <div class="col-md-10">
        <div style="margin:20px 0px 50px;">
        <jsp:doBody />
        </div>
    </div>
</div>