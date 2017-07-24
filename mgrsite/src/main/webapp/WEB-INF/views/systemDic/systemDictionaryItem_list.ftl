<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- html <head>标签部分  -->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台(系统管理平台)</title>
<#include "../common/header.ftl"/>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>

    <script type="text/javascript">
        $(function () {
            //分页条
            $('#pagination').twbsPagination({
                totalPages: ${pageResult.totalPage},
                startPage: ${pageResult.currentPage},
                visiblePages: 5,
                first: "首页",
                prev: "上一页",
                next: "下一页",
                last: "最后一页",
                onPageClick: function (event, page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });

            //选择分组,并显示当前分组明细
            $(".group_item").click(function () {
                $("#currentPage").val(1);
                $("#parentId").val($(this).data("dataid"));
                $("#searchForm").submit();
            });

            //页面刷新时候根据parentId选中分组
            var parentIdVal = $("#parentId").val();
            if (parentIdVal) {
                $(".group_item[data-dataid=" + parentIdVal + "]").closest('li').addClass("active");
            }

            //添加字典明细
            $("#addSystemDictionaryItemBtn").click(function () {
                if (parentIdVal) {
                    document.getElementById("editForm").reset();
                    $("#editFormParentId").val(parentIdVal);
                    $("#systemDictionaryItemModal").modal("show");
                } else {
                    $.messager.popup("请选择一个分组进行添加");
                }
            });

            //字典明细编辑框表单校验
            $("#editForm").validate({
                rules: {
                    title: {
                        required: true
                    },
                    sequence: {
                        required: true,
                        number:true
                    }
                },
                messages: {
                    title: {
                        required: "请填写标题"
                    },
                    sequence: {
                        required: "请填写显示序列",
                        number:"请填写数字"
                    }
                }
            });

            //添加明细表单转化为ajax方式提交的表单
            $("#editForm").ajaxForm(function (date) {
                if (date.success) {
                    //提交成功刷新页面
                    $.messager.popup(date.msg);
                    window.location.reload();
                } else {
                    //弹出错误窗口
                    $.messager.popup(date.msg);
                }
            });

            //保存明细按钮点击事件
            $("#saveBtn").click(function () {
                //判断校验是否成功
                if ($("#title").valid() & $("#sequence").valid()) {
                    $("#editForm").submit();
                }
            });

            //点击修改按钮
            $(".edit_Btn").click(function () {
                var json = $(this).data("json");
                document.getElementById("editForm").reset();
                $("#systemDictionaryItemId").val(json.id);
                $("#editFormParentId").val(json.parentId);
                $("#title").val(json.title);
                $("#sequence").val(json.sequence);
                $("#intro").val(json.intro);
                $("#systemDictionaryItemModal").modal("show");
            });
            
            //删除字典明细
            $(".del_Btn").click(function () {
                $.get("/systemDictionaryItem_delete.do",{id:$(this).data("id")},function (data) {
                    if (data.success) {
                        $.messager.popup(data.msg);
                        window.location.reload();
                    } else {
                        $.messager.popup(data.msg);
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container">
<#include "../common/top.ftl"/>
    <div class="row">
        <div class="col-sm-3">
        <#assign currentMenu="systemDictionaryItem" />
				<#include "../common/menu.ftl" />
        </div>
        <div class="col-sm-9">
            <div class="page-header">
                <h3>数据字典明细管理</h3>
            </div>
            <div class="col-sm-12">
                <!-- 提交分页的表单 -->
                <form id="searchForm" class="form-inline" method="post" action="/systemDictionaryItem_list.do">
                    <input type="hidden" id="currentPage" name="currentPage" value=""/>
                    <input type="hidden" id="parentId" name="parentId" value='${qo.parentId!""}'/>
                    <div class="form-group">
                        <label>关键字</label>
                        <input class="form-control" type="text" name="keyword" value="${(qo.keyword!'')}">
                    </div>
                    <div class="form-group">
                        <button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                        <a href="javascript:void(-1);" class="btn btn-success"
                           id="addSystemDictionaryItemBtn">添加数据字典明细</a>
                    </div>
                </form>
                <div class="row" style="margin-top:20px;">
                    <div class="col-sm-3">
                        <ul id="menu" class="list-group">
                            <li class="list-group-item">
                                <a href="#" data-toggle="collapse" data-target="#systemDictionary_group_detail"><span>数据字典分组</span></a>
                                <ul class="in" id="systemDictionary_group_detail">
                                    <#list systemDictionaryGroups as vo><!-- id="pg_${vo.id}" -->
                                    <li><a class="group_item" data-dataid="${vo.id}"
                                           href="javascript:;"><span>${vo.title}</span></a></li>
                                </#list>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <div class="col-sm-9">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>序列</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list pageResult.result as vo>
                            <tr>
                                <td>${vo.title}</td>
                                <td>${vo.sequence!""}</td>
                                <td>
                                    <a href="javascript:void(-1);" class="edit_Btn" data-json='${vo.jsonString}'>修改</a>&nbsp;
                                    <a href="javascript:void(-1);" class="del_Btn" data-id='${vo.id}'>删除</a>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>

                        <div style="text-align: center;">
                            <ul id="pagination" class="pagination"></ul>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="systemDictionaryItemModal" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑/增加</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" class="form-horizontal" method="post" action="systemDictionaryItem_update.do"
                      style="margin: -3px 118px">
                    <input id="systemDictionaryItemId" type="hidden" name="id" value=""/>
                    <input type="hidden" id="editFormParentId" name="parentId" value=""/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">名称</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title" placeholder="字典值名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">顺序</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="sequence" placeholder="字典值显示顺序">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">描述</label>
                        <div class="col-sm-6">
                            <textarea id="introId" rows="4" cols="24"  style="margin-top: 5px;" placeholder="字典描述" name="intro"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="btn btn-success" id="saveBtn" aria-hidden="true">保存</a>
                <a href="javascript:void(0);" class="btn" data-dismiss="modal" aria-hidden="true">关闭</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>