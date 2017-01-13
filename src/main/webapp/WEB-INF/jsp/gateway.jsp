<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/commons.jsp" %>
    <%@ include file="/WEB-INF/jsp/easyui.jsp" %>
    <title>gateway</title>
    <script src="js/gateway.js"></script>
</head>
<body>
<div id="wrap">
    <div id="nav">
        <table>
            <tbody>
            <tr>
                <td>网关序列号:</td>
                <td><input id="search-sn" class="easyui-textbox"></td>
                <td>网关名称:</td>
                <td><input id="search-name" class="easyui-textbox"></td>
                <td><a id="search" class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
                <td><a id="reset" class="easyui-linkbutton" iconCls="icon-clear">重置</a></td>
            </tr>
            </tbody>
        </table>
    </div>

    <div id="data"></div>

    <div id="editor">
        <form id="form" method="post">
            <table>
                <tr>
                    <td>序列号:<input type="hidden" name="id"></td>
                    <td><input id="sn" class="easyui-textbox" type="text" name="sn" data-options="required:true,validType:'length[1,40]'"></td>
                </tr>
                <tr>
                    <td>名 称:</td>
                    <td><input id="name" class="easyui-textbox" type="text" name="name" data-options="required:true,validType:'length[1,20]'"></td>
                </tr>
                <tr>
                    <td>版本:</td>
                    <td><input id="version" class="easyui-textbox" type="text" name="version"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>