<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>

<tmp:common title="Admin">
<jsp:body>
<tmp:pub-admin>
<jsp:body>
<div class="container-fluid">

    <!--工具栏-->
    <div id="toolbar" class="btn-group">
        <button id="btn_add" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </button>
        <button id="btn_edit" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
        </button>
        <button id="btn_delete" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
        </button>
    </div>

    <!--表格-->
    <table id="table"></table>
    <!--新增-->
    <div class="modal fade" data-keyboard="false" id="addTeacherModel" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" >
                        添加教师
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="addTeacherForm" action="#" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input name="id" type="text" class="form-control" placeholder="请输入ID">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input name="name" type="text" class="form-control"  placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group text-center" style="color: red">
                            *密码与ID一致
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <div class="checkbox">
                                    <label>
                                        <input name="isAdmin" type="checkbox" value="1">设为管理员
                                    </label>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        取消
                    </button>
                    <button id="addTeacher" type="button" class="btn btn-primary">
                        保存
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!--修改-->
    <div class="modal fade"  data-keyboard="false" id="updateTeacherModel" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">
                        修改教师信息
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="updateTeacherForm" action="#" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input name="id" type="text" class="form-control" placeholder="请输入ID">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input name="name" type="text" class="form-control"  placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group text-center" style="color: red">
                            <label  class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                                <input name="passwd" type="text" class="form-control"  placeholder="请输入要重置的密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <div class="checkbox">
                                    <label>
                                        <input name="isAdmin" type="checkbox" value="1">设为管理员
                                    </label>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        取消
                    </button>
                    <button id="updateTeacher" type="button" class="btn btn-primary">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>
<script type="text/javascript" >

    $(function () {

        //1.初始化Table
        $('#table').bootstrapTable({
            url: '${pageContext.request.contextPath}/admin/teacher/list',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            /*queryParams: function (params) {
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    limit: params.limit,   //页面大小
                    offset: params.offset,  //页码
                    departmentname: $("#txt_search_departmentname").val(),
                    statu: $("#txt_search_statu").val()
                };
            return temp;
            },*/ //传递参数（*）
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: '教师ID',
                sortable:true
            }, {
                field: 'name',
                title: '教师姓名',
                sortable:true
            }, {
                field: 'isAdmin',
                title: '是否是管理员',
                sortable:true,
                formatter: isAdminFormatter
            } ]
        });

        //2.初始化事件
        function isAdminFormatter(value, row, index) {
            return value == true? "<span class='badge' style='background:red'>是</span>": "<span class='badge'>否</span>";
        }

        $("#btn_delete").click(function () {
        	//获取要删除的教师id 
        	var ids = new Array();
             var selects = getSelectRows();
             // selects.forEach(function (item, index, arr) {
             //     ids.push(item.id);
             // })
             $.each(selects, function(index, item) {
                 ids.push(item.id);
             })
            // 请求服务器删除数据
            var url = "${pageContext.request.contextPath}/admin/teacher/remove/" + ids;
            $.get(url, function(data) {
                if (data.status == 200) {
                    //删除本地表格对应的行
                    removeRows(ids);
                }
            })
        })

        //新增教师
        $("#btn_add").click(function () {
            //开启模态框
            $('#addTeacherModel').modal('toggle');

        })
        //新增教师提交表单
        $("#addTeacher").click(function() {
            //获取数据
            var params = $("#addTeacherForm").serialize();
            //var t = $("#addTeacherForm").serializeArray(); 测试静态数据时使用
            //post请求添加数据
             var url = "${pageContext.request.contextPath}/admin/teacher/add";
             $.post(url, params, function (data) {
                 if (data.status == 200) {
                    // 提交成功，列表项添加该数据
                    //addRow({ id: t[0].value, name:t[1].value, isAdmin:t.length ==2 ? 0:1});
                     addRow({ id: data.data.id, name: data.data.name, isAdmin: data.data.isAdmin });
                    //数据清空
                    clearForm();
                 } else {
                  //alert重新输入
                  alert(data.msg);
                 }
             })
             $('#addTeacherModel').modal('toggle');
            // 提交成功，列表项添加该数据
            //addRow({ id: t[0].value, name:t[1].value, isAdmin:t.length ==2 ? 0:1});
            //数据清空
            //clearForm()
        })

        //清空表单
        function  clearForm() {
            $("form input[name!='isAdmin']").val("");
            $("form input[name='isAdmin']").removeAttr("checked");
        }
        

         /* $('#updateTeacherModel').on('hidden.bs.modal', function () {
          // 执行一些动作...
        	  $("#updateTeacherForm input[name='isAdmin']").removeProp("checked");
        })  */
        
        //修改教师
        $("#btn_edit").click(function () {
            //获取行信息
            var selects = getSelectRows();

            if (selects.length > 0) {
                //数据填充到模态框
                var row = selects[0];
                $("#updateTeacherForm :input[name='id']").val(row.id);
                $("#updateTeacherForm :input[name='name']").val(row.name);
                if (row.isAdmin) {
                	 $("#updateTeacherForm :input[name='isAdmin']").prop("checked", true);
                } else {
                	$("#updateTeacherForm :input[name='isAdmin']").removeAttr("checked");
                }
                //开启模态框
                $('#updateTeacherModel').modal('toggle');
            }

        })
        //修改教师提交表单
        $("#updateTeacher").click(function() {
            //获取数据
            var params = $("#updateTeacherForm").serialize();
            //var t = $("#updateTeacherForm").serializeArray();
            //post请求添加数据
            var url = "${pageContext.request.contextPath}/admin/teacher/update";
            $.post(url, params, function (data) {
                if (data.status == 200) {
		            //提交成功，修改该数据
		            //updateRow({index:getSelectIndex(t[0].value), row:{ id: t[0].value, name:t[1].value, isAdmin:t.length ==2 ? 0:1}});
		            updateRow({index:getSelectIndex(data.data.id), row:{ id: data.data.id, name: data.data.name, isAdmin: data.data.isAdmin }});
		            $('#updateTeacherModel').modal('toggle');
		            //数据清空
		            clearForm();
                } else {
                	alert(data.msg);
                }
            })
        })

    });
</script>
</jsp:body>
</tmp:pub-admin>
</jsp:body>
</tmp:common>