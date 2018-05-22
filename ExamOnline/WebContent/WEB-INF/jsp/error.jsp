<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:common title="Error">
<jsp:body>
 <!--GOOGLE FONT -->
    <link href="${pageContext.request.contextPath }/source/css/errorPage.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath }/source/js/errorPage.js"></script>
    
    <div class="container">
        <div class="row pad-top text-center">
            <div class="col-md-6 col-md-offset-3 text-center">
              <h1>  What have you done? </h1>
              <h5> Now Go Back Using Below LInk</h5> 
              <span id="error-link">404</span>
              <h2>! ERROR DECETED !</h2>
            </div>
        </div>

        <div class="row text-center">
            <div class="col-md-8 col-md-offset-2">
                <button type="button" id="back" class="btn btn-primary">Backup to previous</button> 
                <script type="text/javascript">
                   $("#back").click(function() {
                        window.history.back();
                        location.reload();
                   })
                </script>
            </div>
        </div>
     </div>
</jsp:body>
</t:common>