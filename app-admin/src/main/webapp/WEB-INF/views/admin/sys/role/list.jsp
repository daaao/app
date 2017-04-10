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
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'west',border:false" style="height:100%; width: 50%;">
        <table id="dg" style="width:100%;height:100%;">
        </table>
        <div id="tb" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-edit" href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-delete" href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
            <a href="javascript:void(0)" onclick="getUsers()" class="easyui-linkbutton"
               iconCls="icon-user-config"
               plain="true">查看用户</a>
        </div>
    </div>
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="rdg" style="width:100%;height:100%;">
        </table>
        <div id="rtb" style="height: 30px;">
        </div>
        <div id="rft" style="padding:2px 5px;text-align: right;">
            <a href="javascript:void(0)" onclick="saveRolePermission()" class="easyui-linkbutton" iconCls="icon-save" plain="false">保存授权</a>
        </div>
    </div>
</div>
<div id="dlg"></div>
<script>
    var roleDataGrid, resourceDataGrid, dialog;
    $(function () {
        roleDataGrid = $('#dg').datagrid({
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
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#tb',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'name', title: '角色名称', sortable: false, width: 20},
                {field: 'code', title: '角色编码', sortable: false, width: 20},
                {field: 'description', title: '描述', sortable: false, width: 25},
                {
                    field: 'operate', title: '操作', width: 35,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        var r = "<a onclick='getUsers(" + row.id + ")' class='button-edit button-info'>查看用户</a>";
                        if (row.isFixed == 1) {//固定的
                            return r + '  ' + e;
                        } else {
                            return r + '  ' + e + '  ' + d;
                        }
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});

                if (data != null && data.rows != null && data.rows.length > 0) {
                    $('#dg').datagrid('checkRow', 0);
                }
            },
            onSelect: function (index, row) {
                queryResourceByRole(row.id);

                if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                    $('#btn-delete').hide();
                } else {
//                    $('#btn-edit').show();
                    $('#btn-delete').show();
                }
            }
        });

        resourceDataGrid = $('#rdg').treegrid({
            method: "get",
            url: '${ctx}/sys/resource/all',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            treeField: 'name',
            iconCls: 'icon',
            animate: true,
            rownumbers: true,
            striped: true,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar: '#rtb',
            footer: '#rft',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true, width: 100},
                {field: 'name', title: '资源/权限名称', width: 100},
                {field: 'description', title: '描述', width: 100, tooltip: true}
            ]],
            onClickRow: function (row) {
                //级联选择
                $(this).treegrid('cascadeCheck', {
                    id: row.id, //节点ID
                    deepCascade: true //深度级联
                });
            },
            onLoadSuccess: function (data) {
            }
        });
    });

    /**
     * 获取角色
     */
    function queryRoles() {
        $(roleDataGrid).datagrid('load', {});
    }

    /**
     * 添加角色
     */
    function add() {
        dialog = $("#dlg").dialog({
            title: '添加角色',
            width: 340,
            height: 260,
            href: '${ctx}/admin/role/add',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/role/add",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog.dialog('close');
                                    queryRoles();
                                } else if (data.code == 400) {
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('角色名称已存在');
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
     * 编辑角色
     */
    function edit(id) {
        if (id == null) {
            var row = roleDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择用户');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '编辑角色',
            width: 340,
            height: 260,
            href: '${ctx}/admin/role/edit/' + id,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/role/update",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog.dialog('close');
                                    queryRoles();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 404) {
                                    U.msg('未找到该角色');
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
     * 删除角色
     */
    function remove(id) {
        if (id == null) {
            var row = roleDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择角色');
                return
            } else {
                id = row.id;
            }
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: "${ctx}/sys/role/delete",
                    loading: true,
                    data: {id: id},
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryRoles();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该角色');
                        } else if (data.code == 424) {
                            U.msg('该角色已被使用，无法删除');
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

    /**
     * 查看用户
     * @param id
     */
    function getUsers(id) {
        if (U.isEmpty(id)) {
            var row = roleDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择角色');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '用户管理',
            width: 650,
            height: 450,
            href: '${ctx}/admin/role/' + id + '/user',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '取消',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    }

    /**
     * 获取角色拥有的权限
     * @param roleId
     */
    function queryResourceByRole(roleId) {
        resourceDataGrid.treegrid('unselectAll');

        U.get({
            url: "${ctx}/sys/resource/role/" + roleId,
            loading: true,
            success: function (data) {
                if (data.code == 200) {
                    var result = data.body;
                    for (var i = 0, j = result.length; i < j; i++) {
                        resourceDataGrid.treegrid('select', result[i].id);
                        resourceDataGrid.treegrid('checkRow', result[i].id);
                    }
                } else {
                    U.msg('服务器异常');
                }
            }
        });
    }

    /**
     * 保存修改权限
     */
    function saveRolePermission() {
        var roleRow = roleDataGrid.datagrid('getSelected');
        if (roleRow == null || roleRow.id == null) {
            U.msg('请先选择角色');
            return
        }

        parent.$.messager.confirm('提示', '确认要保存授权？', function (data) {
            if (data) {
                U.post({
                    url: '${ctx}/sys/role/' + roleRow.id + '/resource/modify',
                    loading: true,
                    data: {
                        resources: getSelectedResources()
                    },
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('修改成功');
                            queryRoles();
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取所选的权限
     * @returns {Array}
     */
    function getSelectedResources() {
        var resources = [];
        var data = resourceDataGrid.datagrid('getSelections');
        for (var i = 0, j = data.length; i < j; i++) {
            resources.push(data[i].id);
        }
        return resources;
    }
</script>
</body>
</html>