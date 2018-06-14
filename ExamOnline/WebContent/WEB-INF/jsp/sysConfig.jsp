<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Admin">
    <jsp:body>
        <tmp:pub-admin>
            <jsp:body>
            	<div class="mainpage" style="width:50%;">
	            	<div style="margin-bottom:20px;">
		            	<h2><img src="${pageContext.request.contextPath }/source/images/config.png" width="60px" /> 系统配置</h2>
	            	</div>
	            	<div class="panel panel-info">
	            		<div class="panel-body">
	            		<form method="post" action="${pageContext.request.contextPath }/admin/setting/update">
	            			<div class="form-group">
	            				分页查询时的每页记录数：（条）
	            				<input class="form-control" name="pageCount" type="text" value="${pageCount }"/>
	            			</div>
		                    <div class="form-group">
		                    	手动开启考试的时间阈值：（分钟）
		                    	<input class="form-control" name="timeLimit" type="text" value="${timeLimit }"/>
		                    </div>
		                    <div class="form-group">
		                    	后台任务扫描周期，重启服务器方可生效：（秒）
		                    	<input class="form-control" name="interval" type="text" value="${interval }"/>
		                    </div>
		                    <div class="form-group">
		                    	学生上传文件大小的有效范围：
			                    <div class="form-inline">
			                    	<input class="form-control" name="" type="text" value="15" readonly="true"/> KB ~
			                    	<input class="form-control" name="" type="text" value="15" readonly="true"/> KB
			                    </div>
		                    </div>
		                    <div class="form-group">
		                    	允许主考教师清理和删除考试：<input name="" type="checkbox" checked="checked" value="15" disabled="disabled"/>
		                    </div>
		                    <div class="form-group"><input class="btn btn-info btn-block" type="submit" onclick="" value="保存"/></div>
	            		</form>
	            		</div>
	            	</div>
            	</div>
            </jsp:body>
        </tmp:pub-admin>
    </jsp:body>
</tmp:common>