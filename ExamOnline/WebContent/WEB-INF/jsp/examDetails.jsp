<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>

<tmp:common title="Teacher">
<jsp:body>
<tmp:pub-teacher>
<jsp:body>
<script type="text/javascript">
	function addAnnouncement() {
		var value = $("#announcement").val(); 
		if(value != ""){
			var li=document.createElement("li");
			$(li).html(value);
			$(li).addClass("list-group-item");
			$("#announcements").append(li);
		}
	    $("#div").scrollTop($("#div")[0].scrollHeight);
	}
</script>
<div>
	<br/>
	<div class="text-info panel panel-default">
		<div class="panel-heading">
			<h4 class="text-info" style="text-align: center;">考试详情</h4>
		</div>
			<table class="table">
				<tbody>
  					<tr>
  					<td style="text-align: center;">总人数：64</td>
  					<td style="text-align: center;">在线人数：43</td>
  					<td style="text-align: center;">缺考人数：24</td>
  					<td style="text-align: center;">提交人数：24</td>
  					</tr>
  				</tbody>
			</table>
	</div>
	<div class="text-info panel panel-default">
		<div class="panel-heading">
			<h4 style="text-align: center;" class="text-info">考试公告</h4>
		</div>
		<div class="pre-scrollable" style=" height: 280px" id="div">
    		<ul class="list-group" id="announcements">
        		<li class="list-group-item">准备充分，忙中有序</li>
        		<li class="list-group-item">倾听说明，填写卷头</li>
        		<li class="list-group-item">通读试卷，统观全局</li>
        		<li class="list-group-item">认真审题，明确要求</li>
        		<li class="list-group-item">先易后难，掌握顺序</li>
        		<li class="list-group-item">仔细检查，文卷勿慌</li>
    		</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-md-10">
			<input class="form-control" type="text" id="announcement"/>
		</div>
		<div class="col-md-2" style="text-align: left;">
			<input class="form-control btn btn-default" type="button" value="发布公告" onclick="addAnnouncement()"/>
		</div>	
	</div>
</div>
</jsp:body>
</tmp:pub-teacher>
</jsp:body>
</tmp:common>