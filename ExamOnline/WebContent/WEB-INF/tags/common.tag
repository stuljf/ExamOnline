<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath }/source/images/net.ico" />
<title>${title }</title>
<!-- 引入bootstrap样式 -->
<link
	href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<!-- 引入bootstrap-table样式 -->
<link
	href="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.css"
	rel="stylesheet">

<!-- jquery -->
<script src="https://cdn.bootcss.com/jquery/2.2.3/jquery.min.js"></script>
<script	src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- bootstrap-table.min.js -->
<script	src="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
<!-- 引入中文语言包 -->
<script	src="https://cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/source/js/common.js"></script>
<!-- 时间选择组件 -->
<link href="${pageContext.request.contextPath }/source/bootstrap-datepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath }/source/bootstrap-datepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath }/source/bootstrap-datepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<style type="text/css">
#footer {
    position: fixed;
    bottom: 0px;
    left: 0px;
    width: 100%;
}
</style>
</head>
<body>
	<jsp:doBody />
	<div id="footer" class="panel-footer text-center text-primary">
    	版权所有&ensp;&copy;&ensp;第一小组：张亚康、赵俊福、姚亚强、刘冀峰
	</div>
</body>
</html>