function swidth(a){
	$(".swidth_btn").css("color","#999");
	$(a).css("color","#333");
	$(".login_form").hide();
	var i=$(".swidth_btn").index(a);
	$(".login_form").eq(i).css("display","block");
}

function s_login(context) {
	//do post
	$.post(context + "/student/login", $("#s_form").serialize(), function(data) {
		/*optional stuff to do after success */
		if (data.status == 200) {
			window.location=context + "/page/student";
		} else {
			$("#s_tip").html(data.msg);
		}
	}, "json");
}

function t_login(context){
	if($("#is_admin").is(":checked")){
		//do post
		$.post(context + "/admin/login", $("#t_form").serialize(), function(data) {
			/*optional stuff to do after success */
			if (data.status == 200) {
				window.location=context + "/page/admin";
			} else {
				$("#t_tip").html(data.msg);
			}
		}, "json");
	}else{
		//do post
		$.post(context + "/teacher/login", $("#t_form").serialize(), function(data) {
			/*optional stuff to do after success */
			if (data.status == 200) {
				window.location=context + "/page/teacher";
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
    });
*/
}