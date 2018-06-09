<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tmp:common title="Teacher">
	<jsp:body>
<tmp:pub-teacher>
<jsp:body>
<script type="text/javascript">
	function addAnnouncement() {
		var value = $("#announcement").val();
		if (value != "") {

			//给服务器
			var url = "${pageContext.request.contextPath}/teacher/exam/begined/publish";
			var params = "examId=${examId}&item=" + value;
			$.post(url, params, function(data) {
				if (data.status == 200) {
					var li = document.createElement("li");
					$(li).html(value);
					$(li).addClass("list-group-item");
					$("#announcements").append(li);
					$(":input:text").val("");
				} else {
					alert(data.msg);
				}
			});

		}
		$("#div").scrollTop($("#div")[0].scrollHeight);
	}
</script>
<div>
	<br />
	<div class="text-info panel panel-default">
		<div class="panel-heading">
			<h4 class="text-info" style="text-align: center;">考试详情</h4>
		</div>
			<table class="table">
				<tbody>
  					<tr>
  					<td style="text-align: center;">总人数：<span class='badge'
										style='background: gray'>${total }</span></td>
  					<td style="text-align: center;">在线人数：<span class='badge'
										style='background: green'>${online }</span></td>
  					<td style="text-align: center;">缺考人数：<span class='badge'
										style='background: red'>${absent }</span></td>
  					</tr>
  				</tbody>
			</table>
	</div>
	<div class="text-info panel panel-default">
		<div class="panel-heading">
			<h4 style="text-align: center;" class="text-info">考试公告</h4>
		</div>
		<div class="pre-scrollable" style="height: 250px" id="div">
    		<ul class="list-group" id="announcements">
    		      <c:forEach items="${publishs}" var="item">
    		          <li class="list-group-item">${item }</li>
    		      </c:forEach>
    		</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-md-10">
			<input class="form-control" type="text" id="announcement" />
		</div>
		<div class="col-md-2" style="text-align: left;">
			<input class="form-control btn btn-warning" type="button"
								value="发布公告" onclick="addAnnouncement()" />
		</div>	
	</div>
</div>
</jsp:body>
</tmp:pub-teacher>
</jsp:body>
</tmp:common>