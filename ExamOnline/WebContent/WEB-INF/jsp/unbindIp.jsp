<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Teacher">
    <jsp:body>
        <tmp:pub-teacher>
            <jsp:body>
            <div class="mainpage" style="width:50%;">
            <div style="margin-bottom:20px;">
            <h2><img src="${pageContext.request.contextPath }/source/images/unbind.png" width="60px" /> IP解绑</h2>
            </div>
            	<div class="panel panel-info ">
            		<div class="panel-body">
            		<form id="unbind_form">
            			<div class="form-group">
            				<input class="form-control" name="id" type="text" placeholder="请输入学号"/>
            			</div>
	                    <div class="form-group">
	                    	<input class="form-control" name="name" type="text" placeholder="请输入姓名"/>
	                    </div>
	                    <div class="form-group">
	                    	<input class="btn btn-info btn-block" type="button" onclick="unbind()" value="解绑"/>
	                    </div>
            		</form>
            		</div>
            	</div>
            	</div>
            	<script>
            	function unbind() {
            		//do post
            		$.post("${pageContext.request.contextPath }/teacher/unbindIP", $("#unbind_form").serialize(), function(data) {
            			/*optional stuff to do after success */
            			if (data.status == 200) {
            				alert("IP解绑成功！");
            			} else {
            			    alert(data.msg);
            			}
            		}, "json");
            	}
            	</script>
            </jsp:body>
        </tmp:pub-teacher>
    </jsp:body>
</tmp:common>