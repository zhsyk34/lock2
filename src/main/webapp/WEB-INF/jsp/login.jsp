<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/commons.jsp" %>
    <title>登录页面</title>
    <link rel="stylesheet" href="css/reset.css"/>
    <link rel="stylesheet" href="css/login.css"/>
</head>
<body>
<div class="login_box">
    <div class="welcome"></div>
    <form>
        <div class="login">
            <div class="login_inp">
                <div class="login_txt">
                    <label>账　号</label>
                    <input id="name" name="name" type="text" class="inp_txt"/>
                </div>
                <div class="login_txt">
                    <label>密　码</label>
                    <input id="password" name="password" type="password" class="inp_txt"/>
                </div>
            </div>
            <div class="btn_box">
                <input id="loginBtn" type="button" value="登 录" class="login_btn"/>
            </div>
        </div>
    </form>
</div>
<script src="js/login.js"></script>
</body>
</html>