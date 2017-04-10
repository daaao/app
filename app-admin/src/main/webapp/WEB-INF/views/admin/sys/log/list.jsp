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
    <title>日志记录</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false,minWidth:1330">
    <div data-options="region:'north',border:false" style="padding: 10px 5px;overflow: hidden;">
        <input id="username" class="easyui-textbox" data-options="label:'用户名'" style="width:160px;"/>
        <input id="module" class="easyui-textbox" data-options="label:'模块'" style="width:150px;"/>
        <input id="description" class="easyui-textbox" data-options="label:'描述'" style="width:150px;"/>
        <input id="ip" class="easyui-textbox" data-options="label:'IP'" style="width:150px;"/>
        <input id="startCreateTime" type="text" placeholder="创建时间" class="easyui-datetimebox" style="width:200px;"
               label='开始时间'/>
        <input id="endCreateTime" type="text" placeholder="创建时间" class="easyui-datetimebox" style="width:200px;"
               label='结束时间'/>
        <a href="javascript:void(0)" onclick="findLog()" class="easyui-linkbutton button-line-blue"
           style="width: 70px;margin-left: 10px;">查&nbsp;询</a>
    </div>

    <div data-options="region:'center',border:false" style="height:100%">
        <table id="dg" style="width:100%;height:100%;">
        </table>
    </div>
</div>
<script>
    var dg;
    $(function () {
        dg = $('#dg').datagrid({
            method: "get",
            url: '${ctx}/sys/logs/page',
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
                {field: 'ck', checkbox: false},
                {field: 'id', title: 'id', hidden: true},
                {field: 'username', title: '用户名', sortable: true, width: 10},
                {field: 'module', title: '模块', sortable: true, width: 10},
                {field: 'description',title: '描述',sortable: true,width: 15},
                {field: 'ip', title: 'IP', sortable: true, width: 10},
                {field: 'createTime', title: '创建时间', sortable: true, width: 15},
                {
                    field: 'content', title: '内容', sortable: true, width: 40,
                    formatter: function (value, row, index) {
                        return '<span title="' + value + '">' + value + '</span>';
                    }
                }
            ]],
            queryParams: {
                username: $('#username').val(),
                module: $('#module').val(),
                description: $('#description').val(),
                ip: $('#ip').val(),
                startCreateTime: $('#startCreateTime').val(),
                endCreateTime: $('#endCreateTime').val()
            }
        });
    });

    function findLog() {
        $(dg).datagrid('load', {
                username: $('#username').val(),
                module: $('#module').val(),
                description: $('#description').val(),
                ip: $('#ip').val(),
                startCreateTime: $('#startCreateTime').val(),
                endCreateTime: $('#endCreateTime').val()
            }
        );
    }
</script>
</body>
</html>