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
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="height:100%;">
        <form id="form">
            <table style="margin: 0 auto; padding: 10px;">
                <tr>
                    <td>上级菜单:</td>
                    <td><input id="comb-menu" name="parentId" class="easyui-combotree"/></td>
                </tr>
                <tr>
                    <td>菜单名称:</td>
                    <td><input name="name" class="easyui-textbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td align="right">路径:</td>
                    <td><input name="url" class="easyui-textbox"/></td>
                </tr>
                <tr>
                    <td align="right">排序:</td>
                    <td><input name="sequence" class="easyui-numberspinner" value="0" data-options="spinAlign:'right'"/></td>
                </tr>
                <tr>
                    <td align="right">描述:</td>
                    <td>
                        <input name="description" class="easyui-textbox" style="height:60px;" data-options="multiline:true">
                        <input name="type" type="hidden" value="1"/>
                        <input name="id" type="hidden" value="${id}"/>
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

<script>
    $(function () {
        U.get({
            url: "${ctx}/sys/resource/" + ${id},
            loading: true,
            success: function (data, status) {
                if (data.code == 200) {
                    var menu = data.body;

                    $('#form').form('load', data.body);

                    //设置上级菜单
                    $('#comb-menu').combotree({
                        method: "get",
                        url: '${ctx}/sys/resource/tree',
                        panelHeight: 'auto',
                        onLoadSuccess: function (node, data) {
                            if(menu && menu.parent){
                                $('#comb-menu').combotree('setValue', {id: menu.parent.id, text: menu.parent.name});
                            }else{
                                $('#comb-menu').combotree('setValue', -1);
                            }
                        }
                    });

                } else if (data.code == 400) {//参数验证失败
                    U.msg('参数验证失败');
                } else if (data.code == 404) {
                    U.msg('未找到该菜单');
                } else {
                    U.msg('服务器异常');
                }
            }
        });
    });
</script>

</body>
</html>