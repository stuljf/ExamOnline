(function ($) {

	Date.prototype.format = function(fmt) {
		var o = {
				"M+" : this.getMonth()+1,                 //月份
				"d+" : this.getDate(),                    //日
				"H+" : this.getHours(),                   //小时
				"m+" : this.getMinutes(),                 //分
				"s+" : this.getSeconds(),                 //秒
				"S"  : this.getMilliseconds()             //毫秒
		};
		if(/(y+)/.test(fmt)) {
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		}
		for(var k in o) {
			if(new RegExp("("+ k +")").test(fmt)){
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
			}
		}
		return fmt;
	}


	
	window.messager = function () {
		var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
		'<div class="modal-dialog modal-sm">' +
		'<div class="modal-content">' +
		'<div class="modal-header">' +
		'<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
		'<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
		'</div>' +
		'<div class="modal-body">' +
		'<p>[Message]</p>' +
		'</div>' +
		'<div class="modal-footer">' +
		'<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
		'<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>';


		var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
		'<div class="modal-dialog">' +
		'<div class="modal-content">' +
		'<div class="modal-header">' +
		'<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
		'<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
		'</div>' +
		'<div class="modal-body">' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>';
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		var generateId = function () {
			var date = new Date();
			return 'mdl' + date.valueOf();
		}
		var init = function (options) {
			options = $.extend({}, {
				title: "操作提示",
				message: "提示内容",
				btnok: "确定",
				btncl: "取消",
				width: 200,
				auto: false
			}, options || {});
			var modalId = generateId();
			var content = html.replace(reg, function (node, key) {
				return {
					Id: modalId,
					Title: options.title,
					Message: options.message,
					BtnOk: options.btnok,
					BtnCancel: options.btncl
				}[key];
			});
			$('body').append(content);
			$('#' + modalId).modal({
				width: options.width,
				backdrop: 'static'
			});
			$('#' + modalId).on('hide.bs.modal', function (e) {
				$('body').find('#' + modalId).remove();
			});
			return modalId;
		}

		return {
			alert: function (options) {
				if (typeof options == 'string') {
					options = {
							message: options
					};
				}
				var id = init(options);
				var modal = $('#' + id);
				modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
				modal.find('.cancel').hide();

				return {
					id: id,
					on: function (callback) {
						if (callback && callback instanceof Function) {
							modal.find('.ok').click(function () { callback(true); });
						}
					},
					hide: function (callback) {
						if (callback && callback instanceof Function) {
							modal.on('hide.bs.modal', function (e) {
								callback(e);
							});
						}
					}
				};
			},
			confirm: function (options) {
				var id = init(options);
				var modal = $('#' + id);
				modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
				modal.find('.cancel').show();
				return {
					id: id,
					on: function (callback) {
						if (callback && callback instanceof Function) {
							modal.find('.ok').click(function () { callback(true); });
							modal.find('.cancel').click(function () { callback(false); });
						}
					},
					hide: function (callback) {
						if (callback && callback instanceof Function) {
							modal.on('hide.bs.modal', function (e) {
								callback(e);
							});
						}
					}
				};
			},
			dialog: function (options) {
				options = $.extend({}, {
					title: 'title',
					url: '',
					width: 800,
					height: 550,
					onReady: function () { },
					onShown: function (e) { }
				}, options || {});
				var modalId = generateId();

				var content = dialogdHtml.replace(reg, function (node, key) {
					return {
						Id: modalId,
						Title: options.title
					}[key];
				});
				$('body').append(content);
				var target = $('#' + modalId);
				target.find('.modal-body').load(options.url);
				if (options.onReady())
					options.onReady.call(target);
				target.modal();
				target.on('shown.bs.modal', function (e) {
					if (options.onReady(e))
						options.onReady.call(target, e);
				});
				target.on('hide.bs.modal', function (e) {
					$('body').find(target).remove();
				});
			}
		}
	}();
})(jQuery);

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
        return "<span class='badge' style='background: red'>进行中...</span>";
    } else if (value == "canceled") {
        return "<span class='badge' style='background: orange'>取消</span>"
    } else {
    	return "<span class='badge'>结束</span>"
    }
}

function getSelectRow(id) {
	return $("#table").bootstrapTable('getRowByUniqueId', id);
}

function getSelectRows() {
	return $("#table").bootstrapTable('getSelections');
}

function removeRow(id) {
	$("#table").bootstrapTable( 'removeByUniqueId', id);
}

function removeRows(ids) {
	$("#table").bootstrapTable('remove', {field:'id', values:ids});
}

function addRow(data) {
	$("#table").bootstrapTable('prepend', data);
}

//获取索引号，坑爹的设计，修改行数据必须要index而不是uniqueId，大坑特坑
function getSelectIndex(id) {
	return $("#table tbody tr[data-uniqueid='" + id + "']").attr("data-index");
}

function refresh() {
	$("#table").bootstrapTable('refresh');
}

function updateRow(data) {
	$("#table").bootstrapTable('updateRow', data);
}