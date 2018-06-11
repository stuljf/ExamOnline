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
        <button id="btn_export" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>导出考试成绩
        </button>
        <button id="btn_exportPapers" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>导出学生试卷
        </button>
    </div>

    <!--表格-->
    <table id="table"></table>
    <div style="height: 30px;"></div>

<script type="text/javascript">
    //1.初始化Table
    $(function() {
    	$('#table').bootstrapTable({
            url: '${pageContext.request.contextPath}/teacher/exam/list?tId=${teacher.id }&state=closed',         //请求后台的URL（*）
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
                radio: true,
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
            } ]
        });
    })

    $("#btn_export").click(function () {
    	var selects = getSelectRows();
    	if (selects.length > 0) {
    		var row = selects[0];
	    	messager.confirm({ message: "确认要下载[ " + row.subject + " ]的考试成绩？" }).on(function (e) {
	            if (e) {
	            	var url = "${pageContext.request.contextPath}/teacher/exam/closed/scoer/" + row.id;
	            	$.get(url, function(data) {
	            		
	            		if (data.status == 200) {
	            			//新开窗口下载
	            			window.open(data.data);
	            		} else {
	            			alert(data.msg);
	            		}
	            	})
	            }
	        });
    	}
    })
</script>
</jsp:body>
</tmp:pub-teacher>
</jsp:body>
</tmp:common>