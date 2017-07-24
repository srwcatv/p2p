<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>P2P平台</title>
    <!-- 包含一个模板文件,模板文件的路径是从当前路径开始找 -->
<#include "common/links-tpl.ftl" />
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/localization/messages_zh.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/account.css"/>

    <script type="text/javascript">
        // 自定义手机号码验证方法
        $.validator.addMethod("isMobile", function (value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");
        //手机号验证表单
        $(function () {
            //手机号校验
            $("#bindPhoneForm").validate({
                rules: {
                    phoneNumber: {
                        required: true,
                        minlength: 11,
                        number: true,
                        isMobile: true
                    },
                    verifyCode: {
                        required: true
                    }
                },
                messages: {
                    phoneNumber: {
                        required: "请输入手机号",
                        minlength: "确认手机不能小于11个字符",
                        number: "请输入合法的数字",
                        isMobile: "请正确填写您的手机号码"
                    },
                    verifyCode: {
                        required: "请输入验证码"
                    }
                },
                errorClass: "text-danger",
                highlight: function (element, errorClass) {
                    //给输入框添加红色外框
                    $(element).closest("div.form-group").addClass("has-error");
                },
                unhighlight: function (element, errorClass) {
                    $(element).closest("div.form-group").removeClass("has-error");
                }
            });
            //邮箱校验
            $("#bindEmailForm").validate({
                rules: {
                    email: {
                        required: true,
                        email: true
                    }
                },
                messages: {
                    email: {
                        required: "请输入你的邮箱地址",
                        email: "请输入正确的邮箱地址"
                    }
                },
                errorClass: "text-danger",
                highlight: function (element, errorClass) {
                    //给输入框添加红色外框
                    $(element).closest("div.form-group").addClass("has-error");
                },
                unhighlight: function (element, errorClass) {
                    $(element).closest("div.form-group").removeClass("has-error");
                }
            });
            //初始化变量
            var bindPhone = $("#showBindPhoneModal");
            //手机验证窗口
            if (bindPhone.size() > 0) {
                bindPhone.click(function () {
                    $("#bindPhoneModal").modal("show");
                });
            }
            //发送验证码
            $("#sendVerifyCode").click(function () {
                //判断手机号填写是否正确
                if ($("#phoneNumber").valid()) {
                    var _this = $(this);
                    $.post("/sendVerifyCode.do", {phoneNumber: $("#phoneNumber").val()}, function (date) {
                        if (date.success) {
                            _this.prop("disabled", true);
                            var sec = 5;
                            var timer = window.setInterval(function () {
                                sec--;
                                if (sec > 0) {
                                    _this.text(sec + "秒后重新发送");
                                } else {
                                    window.clearInterval(timer);
                                    _this.prop("disabled", false);
                                    _this.text("重新发送验证码");
                                }
                            }, 1000);
                        } else {
                            $.messager.popup(date.msg);
                        }
                    }, "json");
                }
            });

            //将绑定手机表单转化为ajax方式提交的表单
            $("#bindPhoneForm").ajaxForm(function (date) {
                if (date.success) {
                    //提交成功刷新页面
                    window.location.reload();
                } else {
                    //弹出错误窗口
                    $.messager.popup(date.msg);
                }
            });
            //绑定手机按钮添加点击事件提交表单
            $("#bindPhone").click(function () {
                $("#bindPhoneForm").submit();
            });

            //邮箱验证窗口,先判断窗口是否存在
            if ($("#showBindEmailModal").size() > 0) {
                $("#showBindEmailModal").click(function () {
                    $("#bindEmailModal").modal("show");
                });
            }

            //将绑邮箱表单转化为ajax方式提交的表单
            $("#bindEmailForm").ajaxForm(function (date) {
                if (date.success) {
                    //提交成功刷新页面
                    window.location.reload();
                } else {
                    //弹出错误窗口
                    $.messager.popup(date.msg);
                }
            });

            //发送邮件按钮绑定点击事件
            $("#bindEmail").click(function () {
                //判断邮箱地址是否填写,并提交表单
                if ($("#bindEmailForm").valid()) {
                    $("#bindEmailForm").submit();
                    $("#bindEmailModal").modal("hide");
                }
            });

        });
    </script>
</head>
<body>
<!-- 网页顶部导航 -->
<#include "common/head-tpl.ftl" />
<!-- 网页导航 -->
<!-- 在当前的freemarker的上下文中,添加一个变量,变量的名字叫nav,变量的值叫personal -->
<#assign currentNav = "personal"/>
<#include "common/navbar-tpl.ftl" />

<div class="container">
    <div class="row">
        <!--导航菜单-->
        <div class="col-sm-3">
        <#assign currentMenu="personal"/>
					<#include "common/leftmenu-tpl.ftl" />
        </div>
        <!-- 功能页面 -->
        <div class="col-sm-9">
            <div class="panel panel-default">
                <div class="panel-body el-account">
                    <div class="el-account-info">
                        <div class="pull-left el-head-img">
                            <img class="icon" src="/images/ms.png"/>
                        </div>
                        <div class="pull-left el-head">
                            <p>用户名：${loginInfo.username}</p>
                            <p>最后登录时间：</p>
                        </div>
                        <div class="pull-left" style="text-align: center;width: 400px;margin:30px auto 0px auto;">
                            <a class="btn btn-primary btn-lg" href="/recharge.do">账户充值</a>
                            <a class="btn btn-danger btn-lg" href="/moneyWithdraw.do">账户提现</a>
                        </div>
                        <div class="clearfix"></div>
                    </div>

                    <div class="row h4 account-info">
                        <div class="col-sm-4">
                            账户总额：<span class="text-primary">${account.totalAmount}</span>
                        </div>
                        <div class="col-sm-4">
                            可用金额：<span class="text-primary">${account.usableAmount}</span>
                        </div>
                        <div class="col-sm-4">
                            冻结金额：<span class="text-primary">${account.freezeAmount}</span>
                        </div>
                    </div>

                    <div class="row h4 account-info">
                        <div class="col-sm-4">
                            待收利息：<span class="text-primary">${account.unReceiveInterest}</span>
                        </div>
                        <div class="col-sm-4">
                            待收本金：<span class="text-primary">${account.unReceivePrincipal}</span>
                        </div>
                        <div class="col-sm-4">
                            待还本息：<span class="text-primary">${account.unReturnAmount}</span>
                        </div>
                    </div>

                    <div class="el-account-info top-margin">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/shiming.png"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>实名认证</h5>
                                    <#if userInfo.isRealAuth>
                                        <p>
                                            已认证
                                            <a href="#">查看</a>
                                        </p>
                                    <#else>
                                        <p>
                                            未认证
                                            <a href="/realAuth.do" id="">立刻绑定</a>
                                        </p>
                                    </#if>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">实名认证之后才能在平台投资</p>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/shouji.jpg"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>手机认证</h5>
                                    <#if userInfo.isBindPhone>
                                        <p>
                                            已认证
                                            <a href="#">查看</a>
                                        </p>
                                    <#else>
                                        <p>
                                            未认证
                                            <a href="javascript:;" id="showBindPhoneModal">立刻绑定</a>
                                        </p>
                                    </#if>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">可以收到系统操作信息,并增加使用安全性</p>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/youxiang.jpg"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>邮箱认证</h5>
                                    <#if userInfo.isBindEmail>
                                        <p>
                                            已绑定
                                            <a href="#">查看</a>
                                        </p>
                                    <#else >
                                        <p>
                                            未绑定
                                            <a href="javascript:;" id="showBindEmailModal">去绑定</a>
                                        </p>
                                    </#if>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">您可以设置邮箱来接收重要信息</p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="el-accoun-auth">
                                    <div class="el-accoun-auth-left">
                                        <img src="images/baozhan.jpg"/>
                                    </div>
                                    <div class="el-accoun-auth-right">
                                        <h5>VIP会员</h5>
                                        <p>
                                            普通用户
                                            <a href="#">查看</a>
                                        </p>
                                    </div>
                                    <div class="clearfix"></div>
                                    <p class="info">VIP会员，让你更快捷的投资</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#if !userInfo.isBindPhone>
<div class="modal fade" id="bindPhoneModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="exampleModalLabel">绑定手机</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="bindPhoneForm" method="post" action="/bindPhone.do">
                    <div class="form-group">
                        <label for="phoneNumber" class="col-sm-2 control-label">手机号:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="verifyCode" class="col-sm-2 control-label">验证码:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="verifyCode" name="verifyCode"/>
                        </div>
                        <button id="sendVerifyCode" class="btn btn-primary" type="button" autocomplate="off">获取验证码
                        </button>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="bindPhone">保存</button>
            </div>
        </div>
    </div>
</div>
</#if>
<#if !userInfo.isBindEmail>
<div class="modal fade" id="bindEmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="exampleModalLabel">绑定邮箱</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="bindEmailForm" method="post" action="/sendEmail.do">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email:</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="email" name="email"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="bindEmail">保存</button>
            </div>
        </div>
    </div>
</div>
</#if>
</body>
</html>