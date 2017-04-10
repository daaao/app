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
    <title>菜单管理</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'center',border:false" style="height:100%">
        <div id="tb" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-edit" href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-delete" href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
        </div>
        <table id="rdg" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<div id="dlg"></div>
<script>
    var resourceDataGrid, dialog;
    $(function () {
        resourceDataGrid = $('#rdg').treegrid({
            method: "get",
            url: '${ctx}/sys/resource/menus',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            treeField: 'name',
            iconCls: 'icon',
            animate: true,
            rownumbers: true,
            striped: true,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#tb',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true, width: 100},
                {field: 'name', title: '菜单名称', width: 100},
                {field: 'url', title: '路径', width: 100},
                {field: 'sequence', title: '排序', width: 100},
                {field: 'description', title: '描述', width: 100, tooltip: true}
            ]],
            onLoadSuccess: function (data) {
            },
            onSelect: function (row) {
                if (row.isFixed && row.isFixed == 1) {//固定的
                    $('#btn-delete').hide();
                } else {
                    $('#btn-delete').show();
                }
            },
            queryParams: {
                type: '1'
            }
        });
    });

    /**
     * 获取菜单
     */
    function queryResources() {
        $(resourceDataGrid).treegrid('load', {
            type: '1'
        });
    }

    /**
     * 添加菜单
     */
    function add() {
        dialog = $("#dlg").dialog({
            title: '添加菜单',
            width: 360,
            height: 340,
            href: '${ctx}/admin/menu/add',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/resource/add",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog.dialog('close');
                                    queryResources();
                                } else if (data.code == 400) {
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('菜单名称已存在');
                                } else {
                                    U.msg('服务器异常');
                                }
                            }
                        });
                    }
                }
            }, {
                text: '取消',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    }

    /**
     * 编辑菜单
     */
    function edit(id) {
        if (id == null) {
            var row = resourceDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择菜单');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '编辑菜单',
            width: 360,
            height: 340,
            href: '${ctx}/admin/menu/edit/' + id,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/resource/update",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog.dialog('close');
                                    queryResources();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 404) {
                                    U.msg('未找到该菜单');
                                } else {
                                    U.msg('服务器异常');
                                }
                            }
                        });
                    }
                }
            }, {
                text: '取消',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    }

    /**
     * 删除菜单
     */
    function remove(id) {
        if (id == null) {
            var row = resourceDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择菜单');
                return
            } else {
                id = row.id;
            }
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: "${ctx}/sys/resource/"+id+"/delete",
                    loading: true,
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryResources();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该菜单');
                        } else if (data.code == 424) {
                            U.msg('该菜单已被使用，无法删除');
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

</script>
</body>
</html>