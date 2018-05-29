<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tmp"%>

<tmp:common title="Teacher">
<jsp:body>
<tmp:pub-teacher>
<jsp:body>
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
</jsp:body>
</tmp:pub-teacher>
</jsp:body>
</tmp:common>