<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>蓝源Eloan-P2P平台</title>
<#include "common/links-tpl.ftl" />
    <link type="text/css" rel="stylesheet" href="/css/account.css"/>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
    <script type="text/javascript" src="/js/plugins-override.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            /**
             * 添加页码插件
             */
            $('#pagination').twbsPagination({
                totalPages: ${result.totalPage} || 1,
                visiblePages: 5,
                    startPage:${result.currentPage}||1,
                onPageClick: function (event, page) {
                    $('#currentPage').val(page);
                    $("#searchForm").submit();
                }
            });
            /**
             * 添加日期插件
             */
            $(".beginDate,.endDate").click(function () {
                WdatePicker();
            });

            /**
             * 查询按钮绑定点击事件
             */
            $("#query").click(function () {
                $('#currentPage').val(1);
                $("#searchForm").submit();
            });

            /**
             * 状态回显
             */
            $("[name=state] option[value=${qo.state}]").attr("selected","selected");
        });
    </script>
</head>
<body>

<!-- 网页顶部导航 -->
<#include "common/head-tpl.ftl" />
<!-- 网页导航 -->
<#assign currentNav = "personal"/>
<#include "common/navbar-tpl.ftl" />

<div class="container">
    <div class="row">
        <!--导航菜单-->
        <div class="col-sm-3">
        <#assign currentMenu = "ipLog"/>
		<#include "common/leftmenu-tpl.ftl" />
        </div>
        <!-- 功能页面 -->
        <div class="col-sm-9">
            <form action="/ipLog.do" name="searchForm" id="searchForm" class="form-inline" method="post">
                <input type="hidden" id="currentPage" name="currentPage" value=""/>
                <div class="form-group">
                    <label>时间范围</label>
                    <input type="text" class="form-control beginDate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd hh:mm:ss"))!""}'/>
                </div>
                <div class="form-group">
                    <label></label>
                    <input type="text" class="form-control endDate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd hh:mm:ss"))!""}'/>
                </div>
                <div class="form-group">
                    <label>状态</label>
                    <select class="form-control" name="state" id="state">
                        <option value="-1">全部</option>
                        <option value="0">登录失败</option>
                        <option value="1">登录成功</option>
                    </select>

                </div>
                <div class="form-group">
                    <button type="button" id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
                </div>
            </form>

            <div class="panel panel-default" style="margin-top: 20px;">
                <div class="panel-heading">
                    登录日志
                </div>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>用户</th>
                        <th>登录时间</th>
                        <th>登录ip</th>
                        <th>登录状态</th>
                        <th>登陆类型</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list result.result as item>
                    <tr>
                        <td>${item.username}</td>
                        <td>${item.loginTime?string("yyyy-MM-dd hh:mm:ss")}</td>
                        <td>${item.ip}</td>
                        <td>${item.stateDisplay}</td>
                        <td>${item.loginTypeDisplay}</td>
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

<#include "common/footer-tpl.ftl" />
</body>
</html>