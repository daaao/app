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
    <title>字典管理</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:560">
    <div data-options="region:'west',border:false" style="height:100%; width: 30%;">
        <table id="dg" style="width:100%;height:100%;">
        </table>
        <div id="tb" style="padding:2px 5px;">
            <a href="javascript:void(0)" onclick="addDictType()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加</a>
            <a id="btn-edit" href="javascript:void(0)" onclick="editDictType()" class="easyui-linkbutton"
               iconCls="icon-edit"
               plain="true">编辑</a>
            <a id="btn-delete" href="javascript:void(0)" onclick="removeDictType()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>
        </div>
    </div>
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="rdg" style="width:100%;height:100%;">
        </table>
        <div id="rtb" style="padding:2px 5px; text-align: right;">
            <a href="javascript:void(0)" onclick="addDict()" class="easyui-linkbutton" iconCls="icon-add"
               plain="true">添加字典数据</a>
            <%--<a href="javascript:void(0)" onclick="edit()" class="easyui-linkbutton" iconCls="icon-edit"
               plain="true">编辑</a>
            <a href="javascript:void(0)" onclick="remove()" class="easyui-linkbutton"
               iconCls="icon-remove"
               plain="true">删除</a>--%>
        </div>
    </div>
</div>
<div id="dlg"></div>
<script>
    var dictTypeDataGrid, dictDataGrid, dialog;
    $(function () {
        dictTypeDataGrid = $('#dg').datagrid({
            title: "字典类型",
            method: "get",
            url: '${ctx}/sys/dict/type/list',
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
                {field: 'name', title: '名称', sortable: false, width: 20},
                {field: 'code', title: '类型编码', sortable: false, width: 20},
                {field: 'description', title: '描述', sortable: false, width: 25}
            ]],
            onLoadSuccess: function (data) {
                if (data != null && data.rows != null && data.rows.length > 0) {
                    $(dictTypeDataGrid).datagrid('checkRow', 0);
                }
            },
            onSelect: function (index, row) {
                queryDictByType();

                if (row.isFixed == 1) {//固定的
                    $('#btn-delete').hide();
                } else {
                    $('#btn-delete').show();
                }

                var gridPanel = $(dictDataGrid).datagrid("getPanel");//先获取panel对象
                gridPanel.panel('setTitle', row.name + '-字典数据');//再通过panel对象去修改title
            }
        });

        dictDataGrid = $('#rdg').datagrid({
            title: "字典数据",
            method: "get",
            url: '${ctx}/sys/dict/list',
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
            toolbar: '#rtb',
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'id', hidden: true},
                {field: 'name', title: '名称', sortable: false, width: 20},
                {field: 'code', title: '编码', sortable: false, width: 20},
                {field: 'sequence', title: '排序', sortable: false, width: 20},
                {field: 'description', title: '描述', sortable: false, width: 25},
                {
                    field: 'operate', title: '操作', width: 35,
                    formatter: function (value, row, index) {
                        var d = "<a onclick='removeDict(" + row.id + ")' class='button-delete button-red'>删除</a>";
                        var e = "<a onclick='editDict(" + row.id + ")' class='button-edit button-blue'>编辑</a>";
                        if (row.isFixed == 1) {//固定的
                            return e;
                        } else {
                            return e + '  ' + d;
                        }
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.button-delete').linkbutton({});
                $('.button-edit').linkbutton({});

                if (data != null && data.rows != null && data.rows.length > 0) {
                    $(dictDataGrid).datagrid('checkRow', 0);
                }
            },
            queryParams: {
                type: '1'
            }
        });
    });

    function getSelectedDictType() {
        var row = dictTypeDataGrid.datagrid('getSelected');
        if (row == null) {
            return null;
        } else {
            return row.code;
        }
    }

    /**
     * 获取字典类型
     */
    function queryDictTypes() {
        $(dictTypeDataGrid).datagrid('load', {});
    }

    /**
     * 添加字典类型
     */
    function addDictType() {
        dialog = $("#dlg").dialog({
            title: '添加字典类型',
            width: 340,
            height: 260,
            href: '${ctx}/admin/dict/type/add',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/dict/type/add",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog.dialog('close');
                                    queryDictTypes();
                                } else if (data.code == 400) {
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('字典类型编码已存在');
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
     * 编辑字典类型
     */
    function editDictType(id) {
        if (id == null) {
            var row = dictTypeDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择字典类型');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '编辑字典类型',
            width: 340,
            height: 260,
            href: '${ctx}/admin/dict/type/edit/' + id,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/dict/type/update",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog.dialog('close');
                                    queryDictTypes();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 404) {
                                    U.msg('未找到该字典类型');
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
     * 删除字典类型
     */
    function removeDictType(id) {
        if (id == null) {
            var row = dictTypeDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择字典类型');
                return
            } else {
                id = row.id;
            }
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: "${ctx}/sys/dict/type/" + id + "/delete",
                    loading: true,
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryDictTypes();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该字典类型');
                        } else if (data.code == 424) {
                            U.msg('该字典类型已被使用，无法删除');
                        } else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取字典类型拥有的字典数据
     * @param type
     */
    function queryDictByType() {
        $(dictDataGrid).datagrid('load', {
            type: getSelectedDictType()
        });
    }

    /**
     * 添加字典数据
     */
    function addDict() {
        var dictType = getSelectedDictType();
        if(U.isEmpty(dictType)){
            U.msg('请先选择字典类型');
            return;
        }

        dialog = $("#dlg").dialog({
            title: '添加字典数据',
            width: 340,
            height: 300,
            href: '${ctx}/admin/dict/add/'+dictType,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/dict/add",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('添加成功');
                                    dialog.dialog('close');
                                    queryDictByType();
                                } else if (data.code == 400) {
                                    U.msg('参数验证失败');
                                } else if (data.code == 409) {
                                    U.msg('字典数据编码已存在');
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
     * 编辑字典
     */
    function editDict(id) {
        if (id == null) {
            var row = dictDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择字典数据');
                return
            } else {
                id = row.id;
            }
        }

        dialog = $("#dlg").dialog({
            title: '编辑字典类型',
            width: 340,
            height: 300,
            href: '${ctx}/admin/dict/edit/' + id,
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/dict/update",
                            loading: true,
                            data: $('#form').serialize(),
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功');
                                    dialog.dialog('close');
                                    queryDictByType();
                                } else if (data.code == 400) {//参数验证失败
                                    U.msg('参数验证失败');
                                } else if (data.code == 404) {
                                    U.msg('未找到该字典数据');
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
     * 删除字典数据
     */
    function removeDict(id) {
        if (id == null) {
            var row = dictDataGrid.datagrid('getSelected');
            if (row == null) {
                U.msg('请先选择字典数据');
                return
            } else {
                id = row.id;
            }
        }

        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                U.post({
                    url: "${ctx}/sys/dict/" + id + "/delete",
                    loading: true,
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryDictByType();
                        } else if (data.code == 400) {//参数验证失败
                            U.msg('参数验证失败');
                        } else if (data.code == 404) {
                            U.msg('未找到该字典数据');
                        } else if (data.code == 424) {
                            U.msg('该字典数据已被使用，无法删除');
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