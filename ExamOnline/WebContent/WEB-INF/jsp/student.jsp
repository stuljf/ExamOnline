<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>
<tmp:common title="Student">
    <jsp:body>
        <nav class="navbar navbar-info top-navbar">
		    <div class="navbar-header">
		        <a class="navbar-brand" onclick="" href="${pageContext.request.contextPath }/page/admin">上机考试系统</a>
		    </div>
		    <ul class="nav navbar-nav navbar-right">
		   	 	<li><a>Time: <span id="time"></span></a>
		    	</li>
		        <li class="dropdown">
		            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
						当前用户：${student.name }&ensp;<i class="glyphicon glyphicon-chevron-down"></i>
		            </a>
		            <ul class="dropdown-menu">
		                <li><a href="${pageContext.request.contextPath }/admin/logout">commit</a>
		                </li>
		            </ul>
		        </li>
		    </ul>
		</nav>
		<div class="container">
			<div class="jumbotron text-center">
           		<h2 class="text-info">请确认考生信息</h2>
           		<p class="text-primary">管理员功能：教师管理、系统配置、考试清理</p>
             	<div class="text-primary">
             	<table>
             		<tr>
             		<td>学号</td><td>${student.id }</td>
             		</tr>
             		<tr>
             		<td>姓名</td><td>${student.name }</td>
             		</tr>
             		<tr>
             		<td>班级</td><td>${student.clazz }</td>
             		</tr>
             	</table>
             	</div>
             </div>
             <!--工具栏-->
    <div id="toolbar" class="btn-group">
        <button id="btn_add" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </button>
        <button id="btn_cancel" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量取消考试
        </button>
    </div>
             <table id="table"></table>
		</div>
		<script type="text/javascript">
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
	    });
	  //2.初始化事件
	    function dateFormatter(value, row, index) {
	        var date = new Date();
	        date.setTime(value);
	        return date.format("yyyy-MM-dd HH:mm");
	    }
	    function stateFormatter(value, row, index) {
	        if (value == "created") {
	            return "<span class='badge' style='background: darkgreen'>创建</span>";
	        } else if (value == "begined") {
	            return "<button type='button' class='btn btn-sm btn-info' onclick="start()">开始</button>";
	        } else if (value == "canceled") {
	            return "<span class='badge' style='background: orange'>取消</span>"
	        } else {
	        	return "<span class='badge'>结束</span>"
	        }
	    }
	    function start(){
	    	
	    }
	    
		</script>
    </jsp:body>
</tmp:common>