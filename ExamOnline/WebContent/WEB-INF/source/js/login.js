function swidth(a){
	$(".swidth_btn").css("color","#999");
	$(a).css("color","#333");
	$(".login_form").hide();
	var i=$(".swidth_btn").index(a);
	$(".login_form").eq(i).css("display","block");
}

function s_login() {
	//do post
	$.post("/ExamOnline/student/login", $("#s_form").serialize(), function(data) {
		/*optional stuff to do after success */
		if (data.status == 200) {
			window.location="/ExamOnline/page/admin";
		} else {
			$("#s_tip").html(data.msg);
		}
	}, "json");
	
}

function t_login(){
	
	if($("#is_admin").is(":checked")){
		//do post
		$.post("/ExamOnline/admin/login", $("#t_form").serialize(), function(data) {
			/*optional stuff to do after success */
			if (data.status == 200) {
				window.location="/ExamOnline/page/admin";
			} else {
				$("#t_tip").html(data.msg);
			}
		}, "json");
	}else{
		//do post
		$.post("/ExamOnline/teacher/login", $("#t_form").serialize(), function(data) {
			/*optional stuff to do after success */
			if (data.status == 200) {
				window.location="/ExamOnline/page/teacher";
			} else {
				$("#t_tip").html(data.msg);
			}
		}, "json");
	}
	/*
	$.ajax({
        type: "POST",
        url: "",
        dataType: "text",
        data: $('#t_form').serialize(),
        success: function (result) {
        	if(result=="1"){
        		$(location).attr("href","");
        	}else if(result=="2"){
            	$("#t_tip").html("用户名或密码错误！");
        	}
        },
        error: function() {
            alert("requeat failed");
        }
    });*/
}