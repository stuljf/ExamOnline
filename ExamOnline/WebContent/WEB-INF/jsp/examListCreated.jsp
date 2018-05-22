<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>

<tmp:common title="Teacher">
	<jsp:body>
        <tmp:pub-teacher>
            <jsp:body>
                <div style="margin: 10px">

    <style type="text/css">
#table  thead {
	background: #5488c4;
}
</style>

    <!--工具栏-->
    <div id="toolbar" class="btn-group">
        <button id="btn_add" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </button>
        <button id="btn_cancel" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量取消考试
        </button>
    </div>

    <!--表格-->
    <table id="table"></table>

    <!--新增-->
    <div class="modal fade" data-keyboard="false" id="addExamModel"
						data-backdrop="static" tabindex="-1" role="dialog"
						aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
										data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">
                        创建考试
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="addExamForm" action="#"
										class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">科目</label>
                            <div class="col-sm-10">
                                <input name="subject" type="text"
													class="form-control" placeholder="请输入考试科目">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-10">
                                <input name="starttime" type="text"
													readonly class="form-control form_datetime">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-10">
                                <input name="endtime" type="text"
													readonly class="form-control form_datetime">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
										data-dismiss="modal">
                        取消
                    </button>
                    <button id="addExam" type="button"
										class="btn btn-primary">
                        创建
                    </button>
                </div>
            </div>
							<!-- /.modal-content -->
        </div>
						<!-- /.modal-dialog -->
    </div>
					<!-- /.modal -->

    <!--修改-->
    <div class="modal fade" data-keyboard="false" id="updateExamModel"
						data-backdrop="static" tabindex="-1" role="dialog"
						aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
										data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">
                        修改考试信息
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="updateExamForm" action="#"
										class="form-horizontal" role="form">
                        <div class="form-group" style="display: none;">
                            <label class="col-sm-2 control-label">ID</label>
                            <div class="col-sm-10">
                                <input name="id" type="text"
													class="form-control" placeholder="请输入考试科目">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">科目</label>
                            <div class="col-sm-10">
                                <input name="subject" type="text"
													class="form-control" placeholder="请输入考试科目">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-10">
                                <input name="starttime" type="text"
													readonly class="form-control form_datetime">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-10">
                                <input name="endtime" type="text"
													readonly class="form-control form_datetime">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
										data-dismiss="modal">
                        取消
                    </button>
                    <button id="updateExam" type="button"
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
</div>

<script type="text/javascript">

    //时间控件初始化
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        minuteStep: 5,
        startDate: new Date()
    });
    //考试结束时间 >= 考试开始时间
    function checkDate() {
        var cDate = $("#addExamForm input[name='starttime']").val();
        $("#addExamForm :input[name='endtime']").datetimepicker('setStartDate', cDate);
        $("#addExamForm :input[name='endtime']").val(cDate);

        var uDate = $("#updateExamForm input[name='starttime']").val();
        $("#updateExamForm :input[name='endtime']").datetimepicker('setStartDate', uDate);
        $("#updateExamForm :input[name='endtime']").val(uDate);
    }
    $("input[name='starttime']").change(function () {
        checkDate();
    })


    //1.初始化Table
    $(function() {
    	$('#table').bootstrapTable({
            url: '${pageContext.request.contextPath}/teacher/exam/list?tId=${teacher.id }&state=created',         //请求后台的URL（*）
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
                valign: 'middle',
                sortable:true
            }, {
                field: 'subject',
                title: '科目',
                valign: 'middle',
                sortable:true
            }, {
                field: 'starttime',
                title: '开始时间  ',
                valign: 'middle',
                sortable:true,
                formatter: dateFormatter
            }, {
                field: 'endtime',
                title: '结束时间  ',
                valign: 'middle',
                sortable:true,
                formatter: dateFormatter
            }, {
                field: 'state',
                title: '当前状态  ',
                valign: 'middle',
                sortable:true,
                formatter: stateFormatter
            },{
                field: 'id',
                title: "操作",
                sortable:true,
                valign: 'middle',
                align: 'center',
                formatter: operationFormatter
            } ]
        });
    //2.初始化事件
    function dateFormatter(value, row, index) {
        var date = new Date();
        date.setTime(value);
        return date.format("yyyy-MM-dd HH:mm");
    }
    })

    function stateFormatter(value, row, index) {
        if (value == "created") {
            return "<span class='badge' style='background: darkgreen'>创建</span>";
        } else if (value == "begined") {
            return "<span class='badge' style='background: red'>进行中...</span>";
        } else if (value == "canceled") {
            return "<span class='badge' style='background: orange'>取消</span>"
        } else {
        	return "<span class='badge'>结束</span>"
        }
    }

    function operationFormatter(value, row, index) {
        var id = value;
        var result = "";
        // result += "<a  class='btn btn-xs green' onclick=\"EditViewById('" + id + "', view='view')\"><span class='glyphicon glyphicon-search'></span></a>";
        // result += "<a  class='btn btn-xs blue' onclick='editExam(" + id + ") ' title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
        // result += "<a  class='btn btn-xs red' onclick='' title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
        result += "<a  class='btn btn-xs red' onclick='editExam(" + id + ", " + index + ")' title='考试信息编辑'><button type='button' class='btn btn-sm btn-info'>编辑</button></a>";
        result += "<a  class='btn btn-xs red' href='${pageContext.request.contextPath}/teacher/question/list?id=" + id + "' title='试题管理'><button type='button' class='btn btn-sm btn-info'>试题</button></a>";
        result += "<a  class='btn btn-xs red' href='#' title='学生管理'><button type='button' class='btn btn-sm btn-info'>学生</button></a>";
        result += "<a  class='btn btn-xs red' onclick='startExam(" + id + ", " + index + ")' href='#' title='学生管理'><button type='button' class='btn btn-sm btn-info'>开始</button></a>";
        return result;
    }

    $("#btn_cancel").click(function () {
    	messager.confirm({ message: "确认要取消选中的考试吗？" }).on(function (e) {
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
    $("#btn_add").click(function () {
        //开启模态框
        $('#addExamModel').modal('toggle');
    })
    //新增教师提交表单
    $("#addExam").click(function() {
        //获取数据
        var params = $("#addExamForm").serialize() + "&t_id=${teacher.id }";
        //var t = $("#addExamForm").serializeArray();
        //post请求添加数据
        var url = "${pageContext.request.contextPath}/teacher/exam/add";
        $.post(url, params, function (data) {
            if (data.status == 200) {
                //添加一行数据到行首，，模态框消失
                addRow({ id: data.data.id, subject: data.data.subject, starttime: data.data.starttime, endtime: data.data.endtime, state: "created"});
            } else {
                alert(data.msg);
            }
        })
        //提交成功，列表项添加该数据
        // addRow({id:"1", name:"test", isAdmin:"1"});
        //var date = new Date(t[1].value);
        //addRow({ id: 1, subject: t[0].value, starttime: date.getTime(), endtime: date.getTime(), state: "created"});
        $('#addExamModel').modal('toggle');
        //数据清空
        clearForm();
    })

    //清空表单
    function  clearForm() {
        $("form input[name!='isAdmin']").val("");
        $("form input[name='isAdmin']").removeAttr("checked");
    }
    var index;
    //修改考试信息
    function editExam(id, i) {
        index = i;

        //数据填充到模态框
        var row = getSelectRow(id);

        //获取行信息
        $("#updateExamForm :input[name='id']").val(row.id);
        $("#updateExamForm :input[name='subject']").val(row.subject);
        var starttime = new Date(row.starttime).format("yyyy-MM-dd HH:mm");
        var endtime = new Date(row.endtime).format("yyyy-MM-dd HH:mm");
        $("#updateExamForm :input[name='starttime']").val(starttime);
        $("#updateExamForm :input[name='endtime']").val(endtime);

        //开启模态框
        $('#updateExamModel').modal('toggle');
        checkDate();
    }
    //修改考试信息提交表单
    $("#updateExam").click(function() {
        //获取数据
        var params = $("#updateExamForm").serialize();
        var t = $("#updateExamForm").serializeArray();
        //post请求添加数据
        var url = "${pageContext.request.contextPath}/teacher/exam/update";
        $.post(url, params, function (data) {
            if (data.status == 200) {
            	//提交成功，修改该数据
                var starttime = new Date(t[2].value);
                var entTime = new Date(t[3].value);
                updateRow({index:index, row:{ id: t[0].id, subject:t[1].value, starttime: starttime.getTime(), endtime: entTime.getTime(), state: "created"}});
                clearForm();
                index = -1;
            } else {
                alert(data.msg);
            }
        })
        $('#updateExamModel').modal('toggle');
        //数据清空
        //clearForm();
        //index = -1;
    })

    //开始考试
    function startExam(id, i) {
    	messager.confirm({ message: "确认要取消选中的考试吗？" }).on(function (e) {
            if (e) {
            	$.get("${pageContext.request.contextPath}/teacher/exam/start/" + id, function(data) {
            		if (data.status == 200) {
            			index = i;
                        var row = getSelectRow(id);
                        row.state = 'begined';
                        updateRow({index: index, row:row});
                        // removeRow(id);
                        index = -1;
            		}
            	});
            }
    	});
    }
</script>
            </jsp:body>
        </tmp:pub-teacher>
    </jsp:body>
</tmp:common>