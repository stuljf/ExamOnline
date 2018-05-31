<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>

<tmp:common title="Teacher">
<jsp:body>
<tmp:pub-teacher>
<jsp:body>
<style type="text/css">
#table  thead {
	background: #5488c4;
}
</style>

    <!--工具栏-->
    <div id="toolbar" class="btn-group">
        <button id="btn_import" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>单个导入
        </button>
        <button id="btn_importAll" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>批量导入
        </button>
        <button id="btn_update" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>修改学生信息
        </button>
         <button id="btn_remove" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量删除
        </button>
    </div>

    <!--表格-->
    <table id="table"></table>
    <div style="height: 30px;"></div>
    <!--新增-->
    <div class="modal fade" data-keyboard="false" id="importStudentModel"
						data-backdrop="static" tabindex="-1" role="dialog"
						aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
										data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">
                        导入学生
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="importStudentForm" action="#"
										class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学号</label>
                            <div class="col-sm-10">
                                <input name="id" type="text"
													class="form-control" placeholder="请输入学号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input name="name" type="text" class="form-control"  placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">班级</label>
                            <div class="col-sm-10">
                                <input name="clazz" type="text" class="form-control"  placeholder="请输入班级">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
										data-dismiss="modal">
                        取消
                    </button>
                    <button id="importStudent" type="button"
										class="btn btn-primary">
                        添加
                    </button>
                </div>
            </div>
							<!-- /.modal-content -->
        </div>
						<!-- /.modal-dialog -->
    </div>
					<!-- /.modal -->

    <!--修改-->
    <div class="modal fade" data-keyboard="false" id="updateStudentModel"
						data-backdrop="static" tabindex="-1" role="dialog"
						aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
										data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">
                        修改学生信息
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="updateStudentForm" action="#"
										class="form-horizontal" role="form">
                        <div class="form-group" style="display: none;">
                            <label class="col-sm-2 control-label">学号</label>
                            <div class="col-sm-10">
                                <input name="id" type="text"
													class="form-control" placeholder="请输入学号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input name="name" type="text"
													class="form-control" placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">班级</label>
                            <div class="col-sm-10">
                                <input name="clazz" type="text"
                                                    class="form-control" placeholder="请输入班级">
                            </div>
                        </div>
                        
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
										data-dismiss="modal">
                        取消
                    </button>
                    <button id="updateStudent" type="button"
										class="btn btn-primary">
                        提交更改
                    </button>
                </div>
            </div>
							<!-- /.modal-content -->
        </div>
						<!-- /.modal-dialog -->
    </div>
					<!-- /.modal -->
					
    <!--批量导入-->
    <div class="modal fade" data-keyboard="false" id="importAllStudentModel"
                        data-backdrop="static" tabindex="-1" role="dialog"
                        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                                        data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">
                        批量导入学生信息
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="importAllStudentForm" action="#" enctype="multipart/form-data"
                                        class="form-horizontal" role="form">
                        <div class="text-center badge" style="background: darkgreen">文件格式：第一行必须是列名，且列名必须为"学号","姓名","班级"(可乱序)</div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Excel文件</label>
                            <div class="col-sm-10">
                                <input name="file" type="file">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                                        data-dismiss="modal">
                        取消
                    </button>
                    <button id="importAllStudent" type="button"
                                        class="btn btn-primary">
                        提交更改
                    </button>
                </div>
            </div>
                            <!-- /.modal-content -->
        </div>
                        <!-- /.modal-dialog -->
    </div>
                    <!-- /.modal -->
	
<script type="text/javascript">
    //1.初始化Table
    $(function() {
    	$('#table').bootstrapTable({
            url: '${pageContext.request.contextPath}/teacher/exam/created/student/list?examId=${examId}',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: function (params) {
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit,   //页面大小
                    offset: params.offset,  //页码
                    //departmentname: $("#txt_search_departmentname").val(),
                    sort: params.sort, //排序列的field
                    order: params.order 
                };
            return temp;
            }, //传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true,
                valign: 'middle'
            }, {
                field: 'id',
                title: 'ID',
                valign: 'middle'
            }, {
                field: 'name',
                title: '姓名',
                valign: 'middle',
                sortable:true
            }, {
                field: 'clazz',
                title: '班级',
                valign: 'middle',
                sortable:true
            }, {
                field: 'ip',
                title: '登录IP',
                valign: 'middle',
                formatter: ipFormatter
            }]
        });
    
    })
    
    function ipFormatter(value, row, index) {
         if (value == "0.0.0.0" || value == "" || value == undefined) {
            return "<span class='badge' style='background: red'>未登录</span>";
        } else {
            return "<span class='badge' style='background: darkgreen'>" + value  + "</span>";
        } 
    }

   

    $("#btn_remove").click(function () {
    	messager.confirm({ message: "确认要删除考生信息吗？" }).on(function (e) {
            if (e) {
            	//本地表格删除
                var ids = new Array();
                var selects = getSelectRows();
                // selects.forEach(function (item, index, arr) {
                //     ids.push(item.id);
                // })
                $.each(selects, function(index, item) {
                    ids.push(item.id);
                })

                // 请求服务器删除数据
                var url = "${pageContext.request.contextPath}/teacher/exam/cancel/" + ids;
                $.get(url, function(data) {
                    if (data.status == 200) {
                        //删除本地表格对应的行
                        //removeRows(ids);
                        $.each(ids, function(index, item) {
	                        var row = getSelectRow(item);
	                        row.state = 'canceled';
	                        updateRow({index: getSelectIndex(item), row:row});
                        })
                    }
                })
            }
        });
    })

    //新增
    $("#btn_import").click(function () {
        //开启模态框
        $('#importStudentModel').modal('toggle');
    })
    //新增教师提交表单
    $("#importStudent").click(function() {
        //获取数据
        var params = $("#importStudentForm").serialize();
        //var params2 = new FormData($("#importStudentForm")[0])
        //post请求添加数据
        var url = "${pageContext.request.contextPath}/teacher/exam/created/student/import";
        $.post(url, params + "&examId=${examId}", function (data) {
            if (data.status == 200) {
                //添加一行数据到行首，，模态框消失
                addRow({ id: data.data.id, name: data.data.name, clazz: data.data.clazz, ip: data.data.ip});
            } else {
                alert(data.msg);
            }
        })
        //提交成功，列表项添加该数据
        $('#importStudentModel').modal('toggle');
        //数据清空
        clearForm();
    })

    //清空表单
    function  clearForm() {
        $("form input").val("");
    }
    
    var index;
    //修改考试信息
    $("#btn_update").click(function () {
    	//获取行信息
        var row = getSelectRows()[0];
    	index = getSelectIndex(row.id);
    	//数据填充到模态框
        $("#updateStudentForm :input[name='id']").val(row.id);
        $("#updateStudentForm :input[name='name']").val(row.name);
        $("#updateStudentForm :input[name='clazz']").val(row.clazz);
        //开启模态框
        $('#updateStudentModel').modal('toggle');
    })
    //修改考试信息提交表单
    $("#updateStudent").click(function() {
        //获取数据
        var params = $("#updateStudentForm").serialize();
        //post请求添加数据
        var url = "${pageContext.request.contextPath}/teacher/exam/created/student/update";
        $.post(url, params + "&examId=${examId}", function (data) {
            if (data.status == 200) {
            	//提交成功，修改该数据
                updateRow({index:index, row:{ id: data.data.id, name: data.data.name, clazz: data.data.clazz, ip: data.data.ip}});
                $('#updateStudentModel').modal('toggle');
            	clearForm();
                index = -1;
            } else {
                alert(data.msg);
            }
        })
       
    })

    //批量导入
    $("#btn_importAll").click(function () {
        //开启模态框
        $('#importAllStudentModel').modal('toggle');
    })
    //新增教师提交表单
    $("#importAllStudent").click(function() {
        //获取数据
        examId = ${examId};
        var params = new FormData($("#importAllStudentForm")[0]);
        params.append("examId", examId);
        //post请求添加数据
        var url = "${pageContext.request.contextPath}/teacher/exam/created/student/importAll";
        $.ajax({
            url: url,
            type: "POST",
            data: params,           
            processData: false,       //必不可缺
            contentType: false,       //必不可缺
            success: function (e) {
                if (e.status == 200) {
                	 refresh();
                     $('#importAllStudentModel').modal('toggle');
                } else {
                    alert(data.msg);
                }
            }
        })
    })
</script>
</jsp:body>
</tmp:pub-teacher>
</jsp:body>
</tmp:common>