<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Teacher">
    <jsp:body>
        <tmp:pub-teacher>
            <jsp:body>
            	<div class="panel panel-info mainpage">
            		<div class="panel-heading">IP解绑</div>
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
            	<script>
            	function unbind() {
            		//do post
            		$.post("${pageContext.request.contextPath }/student/login", $("#unbind_form").serialize(), function(data) {
            			/*optional stuff to do after success */
            			alert(data.msg);
            		}, "json");
            	}
            	</script>
            </jsp:body>
        </tmp:pub-teacher>
    </jsp:body>
</tmp:common>