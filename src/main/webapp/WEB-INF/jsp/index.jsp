<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/commons.jsp" %>
    <link rel="shortcut icon" href="img/warn.ico" type="image/x-icon">
    <link rel="stylesheet" href="css/index.css"/>
    <title>首页</title>
</head>

<body>
<div class="body">
    <div class="header">
        <a href="javascript:void(0)" id="logo">门锁管理系统</a>
        <div class="right">
            <i></i>
            <div><a id="logout" href="logout">退出</a></div>
        </div>
    </div>
    <div class="content">
        <div class="nav">
            <ul>
                <li class="active"><a href="gateway">网关管理</a></li>
                <li><a href="lock">门锁管理</a></li>
            </ul>
        </div>
        <div id="main">
            <iframe src="gateway"></iframe>
        </div>
    </div>
</div>
<script src="js/index.js"></script>
</body>
</html>
