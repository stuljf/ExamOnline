<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>

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
				        var index = 0;
				
				        //exam id
				        // var e_id = ${e_id};
				
				        $("#paper").on("change", "select", function(event) {
				            //get the question type
				            var $type = $(this).val();
				
				            //if the type == 单选  show
				            var $nextNode = $(this).parent().parent().next();
				            if ($type == "单选") {
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
				            $("#paper").append('<div class="item"> <div class="form-group"> <label class="col-sm-2 control-label">题号</label> <div class="col-sm-9"> <span>No.' + index + '</span> <input type="hidden" class="form-control" name="questions[' + index + '].number" value="1" /> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">题目</label> <div class="col-sm-9"> <input type="text" class="form-control" name="questions[' + index + '].title"/> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">类型</label> <div class="col-sm-9"> <select name="questions[' + index + '].type"> <option value="单选" selected>单选</option> <option value="填空">填空</option> <option value="编程">编程</option> </select> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">选项</label> <div class="col-sm-9"> <input type="hidden" class="form-control" name="questions[' + index + '].selection"/> <div class="input-group"> <span class="input-group-addon">A</span> <input type="text" class="form-control"/> </div> <div class="input-group"> <span class="input-group-addon">B</span> <input type="text" class="form-control"/> </div> <div class="input-group"> <span class="input-group-addon">C</span> <input type="text" class="form-control"/> </div> <div class="input-group"> <span class="input-group-addon">D</span> <input type="text" class="form-control"/> </div> </div> </div> <div class="form-group"> <label class="col-sm-2 control-label">答案</label> <div class="col-sm-9"> <input type="text" class="form-control" name="questions[' + index + '].answer"/> <input type="hidden" class="form-control" name="" + e_id + ""/> </div> </div> <div class="text-center"> </div> </div>');
				
				            index++;
				        });
				
				    });
				</script>
				
			    <div class="container">
			        <form class="form-horizontal" role="form">
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
			                    <button type="submit" class="btn btn-warning .btn-lg" style="font-size: 20px;">导入试题</button>
			                </div>
			            </div>
			        </form>
			    </div>
            </jsp:body>
        </tmp:pub-teacher>
    </jsp:body>
</tmp:common>
