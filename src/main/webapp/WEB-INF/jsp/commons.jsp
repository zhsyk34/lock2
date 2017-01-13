<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<%@ page trimDirectiveWhitespaces="true" %>
<base href="<%=basePath%>"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<link rel="stylesheet" href="css/normalize.css"/>
<link rel="stylesheet" href="css/common.css"/>

<script src="lib/jquery.js"></script>
<script>$.basePath = "<%=basePath%>";</script>