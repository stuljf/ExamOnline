<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp" %>

<tmp:common title="Admin">
    <jsp:body>
        <tmp:pub-admin>
            <jsp:body>
                <!-- 引入bootstrap-table样式 -->
                    <script type="text/javascript" >
					    $(function () {
					
					        //初始化bootstrap-table的内容
					        $('#table').bootstrapTable({
					            url: '${pageContext.request.contextPath}/admin/exam/closed',                      //请求后台的URL（*）
					            method: 'GET',                      //请求方式（*）
					            //toolbar: '#toolbar',              //工具按钮用哪个容器
					            striped: true,                      //是否显示行间隔色
					            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					            pagination: true,                   //是否显示分页（*）
					            sortable: true,                     //是否启用排序
					            sortOrder: "asc",                   //排序方式
					            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
					            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
					            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
					            search: true,                      //是否显示表格搜索
					            strictSearch: true,
					            showColumns: true,                  //是否显示所有的列（选择显示的列）
					            showRefresh: true,                  //是否显示刷新按钮
					            clickToSelect: true,                //是否启用点击选中行
					            //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
					            showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
					            cardView: false,                    //是否显示详细视图
					            detailView: false,                  //是否显示父子表
					            columns: [
					            	{
					            		checkbox: true  
					            	}, {
					                    field: 'id',
					                    title: 'ID',
					                    sortable: true
					                }, {
					                    field: 'subject',
					                    title: '科目',
					                    sortable: true
					                }, {
					                    field: 'starttime',
					                    title: '开始时间',
					                    sortable: true,
					                    formatter: dateFormatter
					                    
					                }, {
					                    field: 'endtime',
					                    title: '结束时间',
					                    sortable: true,
					                    formatter: dateFormatter
					                }, {
					                    field: 'state',
					                    title: '状态'
					                }, {
					                    field: 't_id',
					                    title: '教师ID',
					                    sortable: true
					                }
					            ]
					        });
					
					        function dateFormatter(value, row, index) {
					            var date = new Date(value);
					            return date.toLocaleString();
					        }
					
					    })
					    
					    //一键清理
                        function oneKeyClean() {
					    	
					    	/* var ids = new Array();
                            
                            var selectRows = $("#table").bootstrapTable('getSelections');
                            
                            selectRows.forEach(function(item, index, arr){
                                ids.push(item.id);
                            }) 
                            console.log(ids)
                            console.log(ids.join()) */
                           
					    	messager.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
                            	if (e) {
                            		//...
                            		var ids = new Array();
                            		
                                    var selectRows = $("#table").bootstrapTable('getSelections');
                                    
                                    selectRows.forEach(function(item, index, arr){
                                        ids.push(item.id);
                                    }) 
                                    
                                    var url = "${pageContext.request.contextPath}/admin/exam/clean?ids=" + ids;
                                    //请求服务器，清空考试
                                    $.post(url, function(data) {
                                        if (data.status == 200) {
                                        	/* $("#table").bootstrapTable('refresh'); */
                                        	 $("#table").bootstrapTable('remove', {field: 'id', values: ids});
                                        } else {
                                        	alert(data.msg);
                                        }
                                    }) 
                            	}
                            });
                            /* var selectRows = $("#table").bootstrapTable('getSelections', function (row) {
                                return row;
                                }); */
                        }
					    
					</script>
				<div class="text-center">
				    <button class="btn btn-info" style="width: 100%;" type="button" onclick="oneKeyClean()">一键清除</button>
				    <table id="table"></table>
				</div>
            </jsp:body>
        </tmp:pub-admin>
    </jsp:body>
</tmp:common>