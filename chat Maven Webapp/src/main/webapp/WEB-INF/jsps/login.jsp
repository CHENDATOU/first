<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
    <title>用户登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/login.css" />
    <script src="${pageContext.request.contextPath}/Js/jquery-1.8.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/Js/login.js"></script>
</head>
<body>
    <div id="login">
        <div id="login-t">用户登录</div>
       <form action="${pageContext.request.contextPath}/user/login" method="post">
        <div id="login-main">
            <div id="login-con">
                <div class="login-item">
                    用户：<input id="txtName"  name="name" type="text" />
                </div>
                <div class="login-item">
                    密码：<input id="txtPwd" name="password" type="password" />
                </div>
                <div id="login-btn">
                    <input id="btnLogin" type="submit" value="登录" />&nbsp;
                    <input id="btnCancel" type="reset" value="取消" />
                </div>
            </div>         
        </div>
        </form>
		快速登陆，无需注册
    </div>
    <div id="login-msg"></div>
</body>
</html>
