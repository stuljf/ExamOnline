function swidth(a){
	$(".swidth_btn").css("color","#999");
	$(a).css("color","#333");
	$(".login_form").hide();
	var i=$(".swidth_btn").index(a);
	$(".login_form").eq(i).css("display","block");
}

function s_login(){
	$("#s_form").submit();
}

function t_login(){
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
}