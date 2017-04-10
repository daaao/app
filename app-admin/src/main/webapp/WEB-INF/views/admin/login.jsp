<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">

    <title>欢迎登录后台管理系统</title>
    <link rel="shortcut icon" href="${ctxstatic}/images/favicon.ico" type="image/x-icon" />

    <link href="${ctxstatic}/css/login-style.css" rel="stylesheet" type="text/css"/>
    <script src="${ctxstatic}/plugin/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${ctxstatic}/js/cloud.js" type="text/javascript"></script>

    <script src="${ctxstatic}/plugin/layer/layer.js" type="text/javascript"></script>
    <script src="${ctxstatic}/js/utils.js" type="text/javascript"></script>

    <script>
        if (window != top) {
            top.location.href = location.href;
        }
    </script>
</head>

<body style="background-color:#1c77ac; background-image:url(${ctxstatic}/images/light.png);
        background-repeat:no-repeat; background-position:center top; overflow:hidden;">
<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>

<div class="logintop">
    <span>欢迎登录后台管理界面平台</span>
    <ul>
        <%--<li><a href="#">回首页</a></li>--%>
        <li><a href="javascript:void(0);">帮助</a></li>
        <li><a href="javascript:void(0);">关于</a></li>
    </ul>
</div>

<div class="loginbody">
    <span class="systemlogo"></span>
    <div class="loginbox loginbox1">
        <ul>
            <li><input id="username" name="" type="text" class="loginuser" placeholder="admin"/></li>
            <li><input id="password" name="" type="password" class="loginpwd" placeholder="密码"/></li>
            <li class="yzm">
                <span><input id="captcha" type="text" placeholder="验证码"/></span>
                <cite>
                    <img alt="验证码" src="${ctx}/static/images/kaptcha.jpg" title="点击更换" id="img_captcha"
                         name="img_captcha" onclick="javascript:refreshCaptcha();"
                         style="width: 100%; height: 100%;cursor: pointer;"/>
                </cite>
            </li>
            <li>
                <input name="" type="button" class="loginbtn" value="登录" onclick="javascript:login();"/>
                <%--<label><input name="" type="checkbox" value="" checked="checked"/>记住密码</label>--%>
                <label><a href="javascript:void(0);">忘记密码？</a></label>
            </li>
        </ul>
    </div>
</div>
<div class="loginbm">Copyright ©2017
    <a href="http://zhijian.io">zhijian.io</a>
</div>

<script language="javascript">
    $(function () {
        $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
        $(window).resize(function () {
            $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
        })
    });

    /**
     * 点击刷新验证码
     */
    function refreshCaptcha() {
        $('#img_captcha').attr('src', '${ctx}/static/images/kaptcha.jpg?t=' + Math.floor(Math.random() * 100));
    }

    /**
     * 登录系统
     */
    function login() {
        if (validate()) {
            U.post({
                url: "${ctx}/account/login",
                loading: true,
                data: {
                    username: $('#username').val().trim(),
                    password: $('#password').val().trim(),
                    captcha: $('#captcha').val().trim()
                },
                success: function (data) {
                    if (data.code == 200) {
                        if (data.body != null) {
                            U.msg('登录成功');
                            window.location.href = "${ctx}/admin/index";
                        }
                    } else if (data.code == 200002) {
                        U.msg('验证码错误');
                    } else if (data.code == 200001) {
                        U.msg('用户名或密码错误');
                    } else if (data.code == 404) {
                        U.msg('找不到该用户');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    }

    function validate() {
        var username = $('#username').val().trim();
        var password = $('#password').val().trim();
        var captcha = $('#captcha').val().trim();

        if (U.isEmpty(username)) {
            U.msg('请输入用户名');
            return false;
        }
        if (U.isEmpty(password) || password == '密码') {
            U.msg('请输入密码');
            return false;
        }
        console.log(captcha);
        if (U.isEmpty(captcha) || captcha == '验证码') {
            U.msg('请输入验证码');
            return false;
        }
        return true;
    }

</script>
</body>
</html>
