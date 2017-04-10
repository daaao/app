
/**
 * 封装的工具类
 */
var U = {
	/**
	 * 封装的AJAX请求方式
	 * @param args 请求配置参数，详解如下
	 * @param url:请求地址，必选
	 * @param data:请求参数，默认使用JSON.stringify()转为json对象，必选
	 * @param type:请求方式，默认POST，可选
	 * @param dataType:响应数据类型，默认json，可选
	 * @param contentType:请求参数数据类型，默认json， 可以是form，可选
	 * @param loading:是否需要loading加载框，默认不显示，配置loading:true则显示，可选
	 * @param success:请求成功回调，必选
	 * @param error:请求失败回调，可选
	 */
	ajax:function(args){
        var layerIndex;
		if(args.loading){
			 layerIndex = U.loading();
		}
		var params = args.data;
		var contentType = "application/x-www-form-urlencoded; charset=UTF-8";

        if (args.contentType == "json") {
            contentType = "application/json; charset=UTF-8";
            params = JSON.stringify(args.data);
        } else if (args.contentType) {
            contentType = args.contentType;
        }

		$.ajax({
			async : args.async || true,
	        url: args.url,
			type:args.type || "POST",
			dataType:args.dataType || "json",
	        contentType: contentType,//必须有application/x-www-form-urlencoded; charset=UTF-8
	        data: params, //JSON.stringify({ 'foo': 'foovalue', 'bar': 'barvalue' }),  //相当于 //data: "{'str1':'foovalue', 'str2':'barvalue'}",
	        success: function(data,status){
				if(data==null){
                    U.msg("服务器异常：响应结果为空");
				}else {
                    args.success(data,status);
                }

	        	if(args.loading){
	    			U.closeLoading(layerIndex);
	    		}
	        },
	        error: function (xhr, statusText, err) {
	        	if(args.loading){
	        		U.closeLoading(layerIndex);
	        	}

	        	if(args.error){
	        		args.error(xhr, statusText, err);
	        	}else{
	        		U.msg("服务器异常");
	        	}
	        },

	        /*请求头设置*/
	        headers: {
	            "X-Api-Version":"1",//接口API版本
	            "X-User-Agent":"WEB",//WEB=key
	            "X-User-OrgID":1,//所属机构ID，写死湖大=1
	            "X-Client-Language":"zh"//语言
	        }
	    });
	},
    get: function (args) {
        args.type = 'GET';
		U.ajax(args);
    },
    post: function (args) {
        args.type = 'POST';
        U.ajax(args);
    },
    put: function (args) {
        args.type = 'PUT';
        U.ajax(args);
    },
    delete: function (args) {
        args.type = 'DELETE';
        U.ajax(args);
    },

	/**
	 * 将ajax请求结果用指定模板类容解析到指定的容器内
	 * @param  async 是否异步，默认为true
	 * @param  url 请求URL
	 * @param  type 请求类型，默认为POST
	 * @param  data 请求参数
	 * @param  success 解析成功后的回调函数
	 * @param  replace 是否替换，默认为true（true or fasle），true=全部替换，fasle = 在内容后面追加
	 * @return {[type]}      [description]
	 */
	tmpl:function(args){
        var layerIndex;
        if(args.loading){
            layerIndex = U.loading();
        }

		var isReplace = args.replace == undefined ? true : args.replace;//是否替换（replace or append）
		var async = args.async == undefined ? true : args.async;

        var params = args.data;
        var contentType = "application/x-www-form-urlencoded; charset=UTF-8";

        if (args.contentType == "json") {
            contentType = "application/json; charset=UTF-8";
            params = JSON.stringify(args.data);
        } else if (args.contentType) {
            contentType = args.contentType;
        }


		$.ajax({
			async:true,
	        url: args.url,
			type:args.type || "POST",
			dataType:args.dataType || "json",
	        contentType: contentType,//必须有
	        data: params, //JSON.stringify({ 'foo': 'foovalue', 'bar': 'barvalue' }),  //相当于 //data: "{'str1':'foovalue', 'str2':'barvalue'}",
	        success: function(data,status){
//	        	alert(JSON.stringify(data));
	        	//解析模板前的回调函数
				if(args.before){
					args.before(data,status);
				}

	        	var html = template(args.tmplId, data);
	        	//替换 or 追加
				if(isReplace){
					// document.getElementById('group_container').innerHTML = html;
					$('#'+args.containerId).html(html);
				}else{
					$('#'+args.containerId).append(html);
				}

				//解析执行成功后的回调函数
				if(args.after){
					args.after(data,status);
				}

                if(args.loading){
                    U.closeLoading(layerIndex);
                }
	        },
	        error: args.error || function (xhr, statusText, err) {
                if(args.loading){
                    U.closeLoading(layerIndex);
                }
                U.msg("服务器异常");
	        }
	    });
	},
	getUrlParam:function(paramName) {
		var url = window.location.href;
		var oRegex = new RegExp('[\?&]' + paramName + '=([^&]+)', 'i');
		var oMatch = oRegex.exec(url);
		if (oMatch && oMatch.length > 1) {
			return decodeURI(oMatch[1]);
		} else {
			return "";
		}
	},
	isEmpty:function(obj) {
		if (obj == null) {
			return true;
		} else if ( typeof (obj) == "undefined") {
			return true;
		}else if(obj == "undefined"){
			return true;
		} else if (obj == "") {
			return true;
		} else if ($.trim(obj) == "") {
			return true;
		} else if (obj.length == 0) {
			return true;
		} else {
			return false;
		}
	},
	trim:function(obj) {
		if (obj == null) {
			return "";
		} else if ( typeof (obj) == "undefined") {
			return "";
		} else if (obj == "") {
			return "";
		} else if ($.trim(obj) == "") {
			return "";
		} else {
			return obj;
		}
	},
	msg:function(msg){
        //提示层
        layer.msg(msg, {
			/*	    icon: 6,*/
            time: 3000 //2秒关闭（如果不配置，默认是3秒）
        });
    },
    loading:function(){
        //加载层-风格3
        var index = layer.load(2);
        return index;
    },
    closeLoading:function(index){
        layer.close(index); //此时你只需要把获得的index，轻轻地赋予layer.close即可
    },
    tips:function(msg, element){
        //小tips
        layer.tips(msg, element, {
            tips: [2, '#FF9900'],
            time: 1500,
            tipsMore: true
        });
    }
};
