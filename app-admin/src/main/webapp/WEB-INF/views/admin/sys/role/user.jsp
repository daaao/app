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
    <div data-options="region:'center',border:false" style="height:100%">
        <table id="user-dg" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<div id="dlg"></div>
<script>
    var userDataGrid;
    $(function () {
        userDataGrid = $('#user-dg').datagrid({
            method: "get",
            url: '${ctx}/sys/user/role/${id}',
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
                        if (row.isFixed == 1) {//固定的
                            return '';
                        } else {
                            return d;
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
                    $('#btn-delete').hide();
                } else {
                    $('#btn-delete').show();
                }
            },
            queryParams: {}
        });
    });

    function queryUsers() {
        $(userDataGrid).datagrid('load', {});
    }


    function remove(id) {
        if (id == null) {
            var row = userDataGrid.datagrid('getSelected');
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
                    url: "${ctx}/sys/role/${id}/user/" + id + "/delete",
                    loading: true,
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('删除成功');
                            queryUsers();
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