<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Admin">
    <jsp:body>
        <tmp:pub-admin>
            <jsp:body>
            	<div class="panel panel-info mainpage">
            		<div class="panel-heading">系统配置</div>
            		<div class="panel-body"><form method="post" action="${pageContext.request.contextPath }/admin/setting/update">
            			<div class="form-group">
            				分页查询时的每页记录数：（条）
            				<input class="form-control" name="pageCount" type="text" value="${pageCount }"/>
            			</div>
	                    <div class="form-group">
	                    	手动开启考试的时间阈值：（分钟）
	                    	<input class="form-control" name="timeLimit" type="text" value="${timeLimit }"/>
	                    </div>
	                    <div class="form-group">
	                    	后台任务的时间周期：（秒）
	                    	<input class="form-control" name="" type="text" value="15" readonly="true"/>
	                    </div>
	                    <div class="form-group"><div class="form-inline">
	                    	学生上传文件大小的有效范围：
	                    	<input class="form-control" name="" type="text" value="15" readonly="true"/> KB
	                    	~
	                    	<input class="form-control" name="" type="text" value="15" readonly="true"/> KB
	                    </div></div>
	                    <div class="form-group">
	                    	允许主考教师清理和删除考试：<input name="" type="checkbox" checked="checked" value="15" disabled="disabled"/>
	                    </div>
	                    <div class="form-group"><input class="btn btn-info btn-block" type="submit" onclick="" value="保存"/></div>
            		</form></div>
            	</div>
            </jsp:body>
        </tmp:pub-admin>
    </jsp:body>
</tmp:common>