<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tmp:common title="ImportQuestion">
    <jsp:body>
        <tmp:pub-teacher>
            <jsp:body>
	            <style type="text/css"> 
				    form div .form-group > div > select {
				        width: 80px;
				    }
				    div .form-group > div > span {
				        color: black;
				        font-size: 20px;
				    }
				
				    .item {
				        border: 1px solid #efefef;
				        margin: 20px auto;
				        background: #fff;
				        box-shadow: 0 1px 4px rgba(0, 0, 0, 0.27), 0 0 40px rgba(0, 0, 0, 0.06) inset;
				    }
				</style>
				
				<script type="text/javascript">
				    $(function() { 
				        
				        //index
				        var index = $("#paper .item").length;
				        //exam id
				        // var e_id = ${e_id};
				
				        $("#paper").on("change", "select", function(event) {
				            //get the question type
				            var $type = $(this).val();
				
				            //if the type == 单选  show
				            var $nextNode = $(this).parent().parent().next();
				            if ($type == "choice") {
				                $nextNode.show();
				            } else {
				                //hidden
				                $nextNode.hide();
				            }
				        });
				
				        // remove a questioni 
				        $("#removeQuestion").click(function(event) {
				            var $item = $("#paper").children(":last-child");
				            //remove it
				            $item.remove();
				            
				            if (index > 0) index--;
				        });
				
				        // add a question
				        $("#addQuestion").click(function(event) {
				            $("#paper").append('<div class="item"> <div class="form-group"> <label class="col-sm-2 control-label">题号</label> <div class="col-sm-9"> <span>No.' + index + '</span> <input type="hidden" class="form-control" name="questions[' + index + '].number" value="' + (index) + '" /> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">题目</label> <div class="col-sm-9"> <input type="text" class="form-control" name="questions[' + index + '].title"/> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">类型</label> <div class="col-sm-9"> <select class="ques_type" name="questions[' + index + '].type"> <option value="choice" selected>选择</option> <option value="fill">填空</option> <option value="program">编程</option> </select> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">选项</label> <div class="col-sm-9"> <input type="hidden" class="ques_selection form-control" name="questions[' + index + '].selection"/> <div class="input-group"> <span class="input-group-addon">A</span> <input type="text" class="ques_A form-control"/> </div> <div class="input-group"> <span class="input-group-addon">B</span> <input type="text" class="ques_B form-control"/> </div> <div class="input-group"> <span class="input-group-addon">C</span> <input type="text" class="ques_C form-control"/> </div> <div class="input-group"> <span class="input-group-addon">D</span> <input type="text" class="ques_D form-control"/> </div> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">答案</label> <div class="col-sm-9"> <input type="text" class="form-control" name="questions[' + index + '].answer"/> <input type="hidden" class="form-control" name="questions[' + index + '].e_id" value="${examId}"/> </div> </div> <div class="text-center"> </div> </div>');
				
				            index++;
				        });
				
				        $("#importQues").click(function() {
				        	
				        	
				        	var types = $(".ques_type");
				        	var selections = $(".ques_selection");
				        	var As = $(".ques_A");
				        	var Bs = $(".ques_B");
				        	var Cs = $(".ques_C");
				        	var Ds = $(".ques_D");
				        	//如果题型是choice将选项ABCD的值放进selection中
				        	$.each(types, function(index, item) {
				        		if ($(item).val() == "choice") {
					        		var selection = new Array();
					        		selection.push($(As[index]).val());
					        		selection.push($(Bs[index]).val());
					        		selection.push($(Cs[index]).val());
					        		selection.push($(Ds[index]).val());
					        		$(selections[index]).val(selection);
				        		}
				        	})
				        	
				        	//post请求提交
				        	var url = "${pageContext.request.contextPath}/teacher/exam/question/import";
                            var params = $("#importQuesForm").serialize();
				        	$.post(url, params, function(data) {
				        		if (data.status == 200) {
				        			alert("添加试题成功！");
				        			window.location="${pageContext.request.contextPath}/page/examListCreated";
				        		} else {
				        			alert(data.msg);
				        		}
				        	})
				        })
				        
				    });
				</script>
				
			    <div style="padding: auto 10px 30px 10px">
			        <form id="importQuesForm" class="form-horizontal" role="form">
			            <div id="paper">
			                <!-- 动态生成试题 -->
			            </div>
			            <div class="text-center">
			                <button type="button" id="removeQuestion" class="btn btn-danger" style="width: 30%; margin: 0px auto 10px auto">移除最后一个试题</button>
			            </div>
			            <div class="text-center">
			                <button type="button" id="addQuestion" class="btn btn-info" style="width: 100%; margin: 0px auto 10px auto">添加一道题试题</button>
			            </div>
			            <div class="form-group">
			                <div class="text-center">
			                    <button id="importQues" type="button" class="btn btn-warning .btn-lg" style="font-size: 20px;">导入试题</button>
			                </div>
			            </div>
			        </form>
			    </div>
            <div style="height: 30px;"></div>
            </jsp:body>
        </tmp:pub-teacher>
    </jsp:body>
</tmp:common>
