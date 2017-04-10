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
    <title>用户管理</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;">
        <input id="username" class="easyui-textbox" data-options="label:'用户名'" style="width:160px;"/>
        <input id="mobile" class="easyui-textbox" data-options="label:'电话'" style="width:150px;"/>
        <select id="gender" class="easyui-combobox" panelHeight="auto" name="state" label='性别' style="width:120px">
            <option value="">全部</option>
            <option value="0">男</option>
            <option value="1">女</option>
        </select>
        <a href="javascript:void(0)" onclick="queryUsers()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
    </div>

    <div data-options="region:'center',border:false" style="height:100%">
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
            <a href="javascript:void(0)" onclick="getRoles()" class="easyui-linkbutton" iconCls="icon-user-config"
               plain="true">角色设置</a>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true">剪切</a>--%>

        </div>
    </div>
</div>
<div id="dlg"></div>
<script>
    var datagrid;
    var dialog;
    $(function () {
        datagrid = $('#dg').datagrid({
            method: "get",
            url: '${ctx}/sys/user/page',
            fit: true,
            fitColumns: true,
            border: true,
            idField: 'id',
            striped: true,
            pagination: true,
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
                {field: 'username', title: '用户名', sortable: true, width: 100},
                {field: 'name', title: '姓名', sortable: true, width: 100},
                {
                    field: 'gender', title: '性别', sortable: true,
                    formatter: function (value, row, index) {
                        return value == 0 ? '男' : '女';
                    }
                },
                {field: 'email', title: '邮箱', sortable: true, width: 100},
                {field: 'mobile', title: '电话', sortable: true, width: 100},
                {
                    field: 'operate', title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='remove(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='edit(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        var r = "<a onclick='getRoles(" + row.id + ")' class='button-edit button-info'>角色设置</a>";
                        if (row.isFixed == 1) {//固定的
                            return e + '  ' + r;
                        } else {
                            return e + '  ' + d + '  ' + r;
                        }
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});

                if (data) {
                    $.each(data.rows,
                        function (index, item) {
                            if (item.checked) {
                                $('#dg').datagrid('checkRow', index);
                            }
                        });
                }
            },
            onSelect: function (index, row) {
                if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                    $('#btn-delete').hide();
                } else {
//                    $('#btn-edit').show();
                    $('#btn-delete').show();
                }
            },
            queryParams: {
                username: $('#username').val(),
                mobile: $('#mobile').val(),
                gender: $('#gender').val()
            }
        });
    });

    function queryUsers() {
        $(datagrid).datagrid('load', {
                username: $('#username').val(),
                mobile: $('#mobile').val(),
                gender: $('#gender').val()
            }
        );
    }

    function add() {
        dialog = $("#dlg").dialog({
            title: '添加用户',
            width: 340,
            height: 360,
            href: '${ctx}/admin/user/add',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/user/add",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog.dialog('close');
                                    queryUsers();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('用户已存在');
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

    function edit(id) {
        if (id == null) {
            var row = datagrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择用户');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '编辑用户',
            width: 380,
            height: 300,
            href: '${ctx}/admin/user/edit/' + id,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/user/update",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog.dialog('close');
                                    queryUsers();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 404) {
                                    U.msg('未找到该用户');
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

    function remove(id) {
        if (id == null) {
            var row = datagrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择用户');
                return
            } else {
                id = row.id;
            }
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: "${ctx}/sys/user/delete",
                    loading: true,
                    data: {id: id},
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryUsers();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该用户');
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

    function getRoles(id) {
        if (U.isEmpty(id)) {
            var row = datagrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择用户');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '用户角色管理',
            width: 600,
            height: 400,
            href: '${ctx}/admin/user/' + id + '/role',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    U.post({
                        url: '${ctx}/sys/user/' + id + '/role/modify',
                        loading: true,
                        data: {
                            roles: getSelectedRoles()
                        },
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog.dialog('close');
                                queryUsers();
                            } else if (data.code == 400) {//参数验证失败
                                U.msg('参数验证失败');
                            } else if (data.code == 404) {
                                U.msg('未找到该用户');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    }

    //保存用户角色
    function getSelectedRoles() {

        //所选的角色列表
        var roleIds = [];
        var data = $('#role-dg').datagrid('getSelections');
        for (var i = 0, j = data.length; i < j; i++) {
            roleIds.push(data[i].id);
        }
        return roleIds;
    }

</script>
</body>
</html>