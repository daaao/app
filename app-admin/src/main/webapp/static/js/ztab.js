/**

 @Name： 主页Tab方法封装
 @Author：Hao
 */

// ;!function (window, undefined) {
    //默认内置方法。
var ztab = {
        tabs: '#mainTabs',
        //各种快捷引用
        open: function (title, url) {
            var e = ztab.exists(title);
            if (e) {
                $(ztab.tabs).tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;margin: 0px;padding: 0px;"></iframe>';
                $(ztab.tabs).tabs('add', {
                    title: title,
                    content: content,
                    closable: true,
                    tools: [{
                        iconCls: 'icon-mini-refresh',
                        handler: function () {
                            ztab.refresh(title);
                        }
                    }]
                });
            }
        },
        close: function () {
            var tab = $(ztab.tabs).tabs('getSelected');//获取当前选中tabs
            var index = $(ztab.tabs).tabs('getTabIndex', tab);//获取当前选中tabs的index
            $(ztab.tabs).tabs('close', index);//关闭对应index的tabs
        },
        closeAll: function () {
            var titles = new Array();
            var tabs = $(ztab.tabs).tabs('tabs');
            var len = tabs.length;
            if (len > 0) {
                for (var j = 1; j < len; j++) {
                    var a = tabs[j].panel('options').title;
                    titles.push(a);
                }
                for (var i = 0; i < titles.length; i++) {
                    $(ztab.tabs).tabs('close', titles[i]);
                }
            }
        },
        refresh: function (title) {
            var tab = $(ztab.tabs).tabs('getTab', title);
            // var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
            if (tab != null) {
                $(ztab.tabs).tabs('update', {
                    tab: tab,
                    options: {
                        /*title: title,
                         content: content,
                         closable: true,
                         selected: true, tools: [{
                         iconCls: 'icon-mini-refresh',
                         handler: function () {
                         ztab.refresh();
                         }
                         }]*/
                    }
                });
            }
        },
        refreshCurrentTab: function () {
            var tab = $(ztab.tabs).tabs('getSelected');
            var title = tab.panel('options').title;
            ztab.refresh(title);
        },
        exists: function (title) {
            var ret = $(ztab.tabs).tabs('exists', title);
            return ret;
        },
        home: function () {
            var tabs = $(ztab.tabs).tabs('tabs');
            if (tabs.length > 0) {
                var home = tabs[0].panel('options').title;
                $(ztab.tabs).tabs('select', home);
            }
        },
        openWithOptions: function (options) {
            var title = '新选项卡';
            var url = options.url;
            var closable = false;
            var tools = [{
                iconCls: 'icon-mini-refresh',
                handler: function () {
                    ztab.refresh(title);
                }
            }];

            if (options.title) {
                title = options.title;
            }

            if (options.closable) {
                closable = options.closable;
            }

            if (options.tools) {
                tools = options.tools;
            }

            var e = ztab.exists(title);
            if (e) {
                $(ztab.tabs).tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;margin: 0px;padding: 0px;"></iframe>';
                $(ztab.tabs).tabs('add', {
                    title: title,
                    content: content,
                    closable: closable,
                    tools: tools
                });
            }
        }
    };
// }(window);