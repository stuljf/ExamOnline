$(document).ready(function() {
	$('#table').bootstrapTable({
        url: '/ExamOnline/teacher/exam/list?tId=1510121166&state=created',
        									//请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式 
        /*queryParams: function (params) {
            var temp = {   					//这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   		//页面大小
                offset: params.offset,  	//页码
                departmentname: $("#txt_search_departmentname").val(),
                statu: $("#txt_search_statu").val()
            };
        	return temp;
        },*/ 								//传递参数（*）
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
	timeCount();
	$("#btn_start").click(function start(){
		$("#table").bootstrapTable("checkBy", {field: 'state', values: ["begined"]});
		var row=$("#table").bootstrapTable('getSelections');
		if(row.length==0){
			alert("当前没有进行中的考试");
		}else{
			var id=row[0].id;
			window.location="/ExamOnline/student/exam/start?eId="+id;
		}
	});
});
function timeCount(){
	$("#time").html((new Date()).format("HH:mm:ss"));
	setTimeout("timeCount()", 1000);
}