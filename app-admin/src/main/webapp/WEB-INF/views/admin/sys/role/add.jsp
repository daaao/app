<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>角色管理</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="height:100%;">
        <form id="form">
            <table style="margin: 0 auto; padding: 10px;">
                <tr>
                    <td>角色名称:</td>
                    <td><input name="name" class="easyui-textbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>角色编码:</td>
                    <td><input name="code" class="easyui-textbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td align="right">描述:</td>
                    <td>
                        <input name="description" class="easyui-textbox" style="height:60px;" data-options="multiline:true">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<style scoped>
    #form tr {
        line-height: 35px;
    }
    #form tr input, select {
        width: 220px;
    }
</style>
</body>
</html>