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
    <title>后台管理界面平台</title>

    <%@ include file="/WEB-INF/views/include/common.jsp" %>

    <style>
        #mainTabs .tabs-panels > .panel > .panel-body {
            overflow: hidden;
        }

        #mainTabs > .tabs-header {
            padding-top: 0px;
        }
    </style>
</head>

<body>
<div id="master-layout">
    <div data-options="region:'north',border:false,bodyCls:'theme-header-layout'">
        <div class="theme-navigate">
            <div class="left">
                <a href="#" class="easyui-linkbutton left-control-switch"><i class="fa fa-bars fa-lg"></i></a>
                <a href="#" class="easyui-menubutton theme-navigate-user-button"
                   data-options="menu:'.theme-navigate-user-panel'">${user.username}<%--匿名--%></a>
                <a href="#" class="easyui-linkbutton">新建</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm1',hasDownArrow:false">文件</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm2',hasDownArrow:false">编辑</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm3',hasDownArrow:false">消息<span
                        class="badge color-default">15</span></a>

                <select id="cc1" class="easyui-combobox theme-navigate-combobox" name="dept" style="width:120px;">
                    <option>选择样式</option>
                    <option>Insdep</option>
                    <option>Bootstrap</option>
                    <option>Gray</option>
                    <option>Metro</option>
                    <option>Material</option>
                </select>

                <div id="mm1" class="theme-navigate-menu-panel">
                    <div>新建</div>
                    <div>打开</div>
                    <div>
                        <span>打开最近文件</span>
                        <div>
                            <div>1 index.html</div>
                            <div>2 calendar-custom.html</div>
                            <div>3 combo-animation.html</div>
                            <div>4 datebox-restrict.html</div>
                            <div>5 datetimespinner-icon.html</div>
                            <div>6 filebox-button-align.html</div>
                            <div>7 menubutton-alignment.html</div>
                            <div>8 messager-interactive.html</div>
                            <div>9 propertygrid-group-format.html</div>
                            <div class="menu-sep"></div>
                            <div>启动时重新打开文件</div>
                        </div>
                    </div>
                    <div>关闭</div>
                    <div>全部关闭</div>
                    <div class="menu-sep"></div>
                    <div data-options="disabled:true,iconCls:'icon-save'">保存</div>
                    <div>另存为</div>
                    <div data-options="disabled:true">保存为全部</div>
                    <div class="menu-sep"></div>
                    <div>
                        <span>导入</span>
                        <div>
                            <div>XML 到模板</div>
                            <div>表格式数据</div>
                            <div data-options="disabled:true">Word 文档</div>
                            <div data-options="disabled:true">Excel 文档</div>
                        </div>
                    </div>
                    <div>
                        <span>导出</span>
                        <div>
                            <div>表格</div>
                        </div>
                    </div>
                    <div class="menu-sep"></div>
                    <div>退出</div>
                </div>

                <div id="mm2" class="theme-navigate-menu-panel">
                    <div>撤销</div>
                    <div data-options="disabled:true">重做</div>
                    <div class="menu-sep"></div>
                    <div>剪切</div>
                    <div>复制</div>
                    <div data-options="disabled:true">粘贴</div>
                    <div data-options="disabled:true">选择性粘贴</div>
                    <div data-options="disabled:true">清除</div>
                    <div class="menu-sep"></div>
                    <div>全选</div>
                    <div>选择父标签</div>
                    <div>选择子标签</div>
                    <div class="menu-sep"></div>
                    <div>查找和替换</div>
                    <div>查找所选</div>
                    <div>查找下一个</div>
                    <div class="menu-sep"></div>
                    <div>快捷键</div>
                    <div>首选项</div>
                </div>

                <div id="mm3" class="theme-navigate-menu-panel" style="width:180px;">
                    <div>产品消息<span class="badge color-success">5</span></div>
                    <div>安全消息<span class="badge color-important">10</span></div>
                    <div>服务消息</div>
                    <div class="menu-sep"></div>
                    <div>查看历史消息</div>
                    <div class="menu-sep"></div>
                    <div>清除消息提示</div>
                </div>

                <div class="theme-navigate-user-panel">
                    <dl>
                        <dd>
                            <img src="${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png" width="86"
                                 height="86"
                                 onerror="javascript:this.src='${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png'">
                            <b class="badge-prompt"><%--匿名--%>${user.username}<i
                                    class="badge color-important">10</i></b>
                            <span>${user.email}<%--examples@insdep.com--%></span>
                            <p>安全等级：<i class="text-success">高</i></p>
                        </dd>
                        <dt>
                            <a class="theme-navigate-user-modify">修改资料</a>
                            <a style="margin-left: 5px;" onclick="modifyPassword()">修改密码</a>
                            <a class="theme-navigate-user-logout" href="javascript:void(0);" onclick="logout()">注销</a>
                        </dt>
                    </dl>
                </div>
            </div>
            <div class="right">
                <select id="cc2" class="easyui-combobox theme-navigate-combobox" name="dept" style="width:180px;">
                    <option>Choose a language</option>
                    <option>Chinese</option>
                    <option>English</option>
                    <option>Korean</option>
                    <option>Japanese</option>
                    <option>Arabic</option>
                </select>
                <input class="easyui-searchbox theme-navigate-search"
                       data-options="prompt:'输入搜索的关键词..',menu:'#mm',searcher:doSearch" style="width:300px"/>
                <a href="#" class="easyui-menubutton theme-navigate-more-button"
                   data-options="menu:'#more',hasDownArrow:false"></a>
                <div id="more" class="theme-navigate-more-panel">
                    <div>联系我们</div>
                    <div>参与改进计划</div>
                    <div>检测更新</div>
                    <div>关于</div>
                </div>
                <div id="mm" class="theme-navigate-menu-panel">
                    <div data-options="name:'all'">全部内容</div>
                    <div data-options="name:'sports'">标题</div>
                    <div data-options="name:'sports'">作者</div>
                    <div data-options="name:'sports'">内容</div>
                </div>
            </div>
        </div>
    </div>

    <!--开始左侧菜单-->
    <div data-options="region:'west',border:false,bodyCls:'theme-left-layout'" style="width:200px;">

        <!--正常菜单-->
        <div class="theme-left-normal">
            <!--theme-left-switch 如果不需要缩进按钮，删除该对象即可-->
            <div class="left-control-switch theme-left-switch"><i class="fa fa-chevron-left fa-lg"></i></div>

            <!--start class="easyui-layout"-->
            <div class="easyui-layout" data-options="border:false,fit:true">
                <!--start region:'north'-->
                <div data-options="region:'north',border:false" style="height:100px;">
                    <!--start theme-left-user-panel-->
                    <div class="theme-left-user-panel">
                        <dl>
                            <dt>
                                <img src="${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png" width="43"
                                     height="43"
                                     onerror="javascript:this.src='${ctxstatic}/plugin/easyui/themes/insdep/images/portrait86x86.png'">
                            </dt>
                            <dd>
                                <b class="badge-prompt"><%--匿名--%>${user.username} <i
                                        class="badge color-important">10</i></b>
                                <span>${user.name}</span>
                                <p>安全等级：<i class="text-success">高</i></p>
                            </dd>

                        </dl>
                    </div>
                    <!--end theme-left-user-panel-->
                </div>
                <!--end region:'north'-->

                <!--start region:'center'-->
                <div data-options="region:'center',border:false">

                    <!--start easyui-accordion-->
                    <div class="easyui-accordion" data-options="border:false,fit:true" id="menu-container">
                        <c:forEach var="menu" items="${menus}">
                            <c:if test="${menu._parentId==null}">
                                <div title="${menu.name}">
                                    <ul class="easyui-datalist"
                                        data-options="border:false,fit:true, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                        <c:forEach var="childMenu" items="${menus}">
                                            <c:if test='${childMenu._parentId==menu.id}'>
                                                <li value="${ctx}/${childMenu.url}">${childMenu.name}</li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <!--end easyui-accordion-->
                </div>
                <!--end region:'center'-->
            </div>
            <!--end class="easyui-layout"-->

        </div>
        <!--最小化菜单-->
        <div class="theme-left-minimal">
            <ul class="easyui-datalist" data-options="border:false,fit:true">

                <c:forEach var="menu" items="${menus}">
                    <c:if test="${menu._parentId==null}">
                        <li><%--<i class="fa fa-home fa-2x"></i>--%>
                            <a class="left-control-switch">
                                <i class="fa fa-cube fa-2x" aria-hidden="true"></i>
                                <p>${menu.name}</p>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
                <li>
                    <a class="left-control-switch">
                        <i class="fa fa-chevron-right fa-2x"></i>
                        <p>打开</p>
                    </a>
                </li>
            </ul>
        </div>

    </div>
    <!--结束左侧菜单-->

    <%--<div data-options="region:'center',border:false,href:'${ctx}/admin/login'" id="control" style="padding:20px; background:#fff;">
        <iframe class="page-iframe" src="workbench.html" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
    </div>--%>

    <div data-options="region: 'center',border:false" style="padding:0px;margin: 0px; background:#fff;">
        <div id="mainTabs_tools" class="tabs-tool">
            <table>
                <tr>
                    <td><a id="mainTabs_jumpHome"
                           class="easyui-linkbutton easyui-tooltip" title="跳转至主页选项卡"
                           data-options="plain: true, iconCls: 'icon-back'" onclick="javascript:ztab.home()"></a></td>
                    <td>
                        <div class="datagrid-btn-separator"></div>
                    </td>
                    <td><a id="mainTabs_refTab"
                           class="easyui-linkbutton easyui-tooltip" title="刷新当前选中的选项卡"
                           data-options="plain: true, iconCls: 'icon-reload'"
                           onclick="javascript:ztab.refreshCurrentTab()"></a></td>
                    <td>
                        <div class="datagrid-btn-separator"></div>
                    </td>
                    <td><a id="mainTabs_closeTab"
                           class="easyui-linkbutton easyui-tooltip" title="关闭所有选项卡"
                           data-options="plain: true, iconCls: 'icon-cancel'" onclick="javascript:ztab.closeAll()"></a>
                    </td>
                </tr>
            </table>
        </div>

        <div id="mainTabs"
             class="easyui-tabs <%--theme-tab-blue-block theme-tab-unradius theme-tab-line-bold theme-tab-unbackdrop--%>"
             data-options="tabPosition:'top',plain:true,narrow:true,fit: true, border: false, tools: '#mainTabs_tools'"
             style="margin: 0px; padding: 0px;">
        </div>
    </div>
</div>
<div id="dlg"></div>

<script>
    $(function () {
        /*布局部分*/
        $('#master-layout').layout({
            fit: true/*布局框架全屏*/
        });

        var left_control_status = true;
        var left_control_panel = $("#master-layout").layout("panel", 'west');

        $(".left-control-switch").on("click", function () {
            if (left_control_status) {
                left_control_panel.panel('resize', {width: 100});
                left_control_status = false;
                $(".theme-left-normal").hide();
                $(".theme-left-minimal").show();
            } else {
                left_control_panel.panel('resize', {width: 200});
                left_control_status = true;
                $(".theme-left-normal").show();
                $(".theme-left-minimal").hide();
            }
            $("#master-layout").layout('resize', {width: '100%'})
        });

        $(".theme-navigate-user-modify").on("click", function () {
            $('.theme-navigate-user-panel').menu('hide');
//            $.insdep.window({id: "personal-set-window", href: "user.html", title: "修改资料"});
            modifyUserInfo();
        });
        //$.insdep.control("list.html");

        var cc1 = $('#cc1').combo('panel');
        cc1.panel({cls: "theme-navigate-combobox-panel"});
        var cc2 = $('#cc2').combo('panel');
        cc2.panel({cls: "theme-navigate-combobox-panel"});


        /*$("#open-layout").on("click",function(){
         var option = {
         "region":"west",
         "split":true,
         "title":"title",
         "width":180
         };
         $('#master-layout').layout('add', option);

         });*/

        //主页
        ztab.openWithOptions({title: '主页', url: '${ctx}/admin/dashboard'});
    });
    function doSearch(value, name) {
        alert('You input: ' + value + '(' + name + ')');
    }

    /**
     * 注销、退出
     */
    function logout() {
        parent.$.messager.confirm('提示', '您确定要退出系统吗？', function (data) {
            if (data) {
                window.location.href = '${ctx}/account/logout';
            }
        });
    }

    /**
     * 修改用户资料
     * @param id
     */
    function modifyUserInfo() {
        var dialog = $("#dlg").dialog({
            title: '修改资料',
            width: 380,
            height: 300,
            href: '${ctx}/admin/user/edit/${user.id}',
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
                                    U.msg('修改成功，请重新登录！');
                                    dialog.dialog('close');

                                    setTimeout(function () {
                                        window.location.href = '${ctx}/account/logout';
                                    }, 2000);
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

    function modifyPassword() {
        var dialog = $("#dlg").dialog({
            title: '修改密码',
            width: 380,
            height: 260,
            href: '${ctx}/admin/user/${user.id}/modifypwd',
            maximizable: false,
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#form").form('validate');
                    if (isValid) {
                        U.post({
                            url: "${ctx}/sys/user/${user.id}/modifypwd",
                            loading: true,
                            data: {
                                originalPassword:$('#originalPassword').val(),
                                newPassword:$('#newPassword').val()
                            },
                            success: function (data) {
                                if (data.code == 200) {
                                    U.msg('修改成功，请重新登录！');
                                    dialog.dialog('close');

                                    setTimeout(function () {
                                        window.location.href = '${ctx}/account/logout';
                                    }, 2000);
                                } else if (data.code == 200003) {
                                    U.msg('原密码错误');
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
</script>
</body>
</html>
