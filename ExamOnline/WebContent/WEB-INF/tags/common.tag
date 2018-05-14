<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="title" required="true" rtexprvalue="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${title }</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/source/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/source/jquery/jquery-2.1.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/source/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:doBody />
</body>
</html>