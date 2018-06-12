<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<tmp:common title="Student">
    <jsp:body>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/source/css/student.css" />
	<style type="text/css">
	    .item_title {
	        font-size: 18px;
	        font-weight: 700;
	    }
	    .item_selection {
	        font-size: 15px;
	    }
	    .top-navbar {
		    position: fixed;
		    top: 0px;
		    left: 0px;
		    width: 100%;
		    z-index: 5;
	    }
	</style>
    <nav class="navbar navbar-info top-navbar">
	    <div class="navbar-header">
	        <a class="navbar-brand" onclick="" href="${pageContext.request.contextPath }/page/admin">上机考试系统</a>
	    </div>
	    <ul class="nav navbar-nav navbar-right">
	    	<li><a data-toggle="modal" data-target="#notice" onclick="show()" style="cursor:pointer;">
	    		<i class="glyphicon glyphicon-bell"></i> <span id="msg">通知</span>
	    	</a></li>
	   	 	<li><a>时间：<span id="time"></span></a></li>
	        <li class="dropdown">
	            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="cursor:pointer;">
					当前考生：${student.name }&ensp;<i class="glyphicon glyphicon-chevron-down"></i>
	            </a>
	            <ul class="dropdown-menu">
	                <li><a onclick="submitAnswer()" href="#">提交试卷</a></li>
	            </ul>
	        </li>
	    </ul>
	</nav>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="notice" data-keyboard="false" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                    &times;
	                </button>
	                <h4 class="modal-title">通知详情</h4>
	            </div>
	            <div class="modal-body">
	                <div class="pre-scrollable" style="height:150px;word-break:break-all">
			    		<ul class="list-group" id="announcements"></ul>
					</div>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
    <!-- 动态加载试题 -->
    <div class="container" style="margin:40px auto">
	    <hr>
        <form id="quesForm" class="form-horizontal" role="form">
	        <c:forEach items="${ques }" var="q" varStatus="index">
		        <div class="item">
		            <input type="hidden" name="questions[${q.number }].number" value="${q.number }">
		            <input type="hidden" name="questions[${q.number }].type" value="${q.type }">
		            <div class="item_title">${index.count }、${q.title }</div>
		            <c:if test="${q.type == 'choice'}">
			            <div class="item_body" style="padding: 5px 20px">
			                <c:forTokens var="s" items="${q.selection }" delims="," varStatus="si">
			                    <a href="#" class="list-group-item">${si.count == 1? 'A：':''}${si.count == 2? 'B：':''}${si.count == 3? 'C：':''}${si.count == 4? 'D：':''}${s}</a>
			                </c:forTokens>
			            </div>
		            </c:if>
		            <div class="item_answer">
		                <textarea class="form-control" rows="3" name="questions[${q.number }].answer" placeholder="请输入您的答案"></textarea>
		            </div>
		        </div>
                <hr>
            </c:forEach>
        </form>
    </div> 
    <script type="text/javascript">
    var msg=0;
    noticeCount();
    function noticeCount(){
        $.get("${pageContext.request.contextPath }/student/exam/broadcast/list/${examId }", function(data){
        	var msgs=data.data;
        	var words=msgs.split("<<EOF>>");
        	if(words.length>msg){
        		msg=words.length;
        		$("#msg").html("有新消息");
        		$("#msg").css("color","red");
        		$("#announcements").empty();
        		for(var value in words){
            		var li=document.createElement("li");
	    			$(li).html(words[value]);
	    			$(li).addClass("list-group-item");
	    			$("#announcements").append(li);
	        	}
        	}
        });
        setTimeout("noticeCount()", 60000);
    }
    function show(){
    	$("#msg").html("通知");
		$("#msg").css("color","");
    }
    function submitAnswer() {
        //获取参数信息
        var params = $("#quesForm").serialize();
        var url = "${pageContext.request.contextPath}/student/exam/submit"
        $.post(url, params + "&examId=${examId}&studentId=${student.id}", function(data) {
            if (data.status == 200) {
                alert("上传成功,请安静离开考场！");
                window.location="${pageContext.request.contextPath}"
            } else {
                alert(data.msg);
            }
        })
    }
    </script>
    </jsp:body>
</tmp:common>