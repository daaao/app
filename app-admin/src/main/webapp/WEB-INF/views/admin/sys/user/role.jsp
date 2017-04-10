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
    <title>用户角色</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="role-dg" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<div id="dlg"></div>
<script>
    var roleDataGrid;
    $(function () {
        roleDataGrid = $('#role-dg').datagrid({
            method: "get",
            url: '${ctx}/sys/role/list',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: false,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 50, 100],
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'name', title: '角色名称', sortable: false, width: 25},
                {field: 'code', title: '角色编码', sortable: false, width: 25},
                {field: 'description', title: '描述', sortable: false, width: 50}
            ]],
            onLoadSuccess: function (data) {
                U.get({
                    url: '${ctx}/sys/role/user/' +${id},
                    loading: true,
                    success: function (data) {
                        if (data.code == 200) {
                            if (data.body) {
                                for (var i = 0, j = data.body.length; i < j; i++) {
                                    roleDataGrid.datagrid('selectRecord', data.body[i].id);
                                }
                            }
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>