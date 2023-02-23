/**
 * 本函数为平台基本异步调用的默认函数
 * 1、基于平台的架构考虑，所有ajax的调用方式全部规定为POST,后台对应@RequestMapping的method要设置为method=RequestMethod.POST，返回值加上@ResponseBody注解
 * 2、为来保证每次调用的执行，本函数自动在URL上附上一个随机数例如:/cat/get/0.21212121
 *
 * @param {} url      指向后端的url
 * @param {} data     推送到服务端的数据以key/value形式组成例如:{"pageSize":${"#pageSize"}.val(),"pageNo":${"#pageNo"}.val()}
 * @param {} datatype 服务器端返回的数据类型，有如下几种:xml,html,script,json,jsonp,text
 * @param {} succ_fn  异步调用成功时服务器执行的回调函数，函数执行过程由开发人员定义
 * @param {} err_fn   异步调用失败时服务器执行的回调函数，函数执行过程由开发人员定义
 */
function defaultAjax(url,data,datatype,succ_fn,err_fn){
	var uri = $.trim(url) + '/' + Math.random();	      //去除空格
	jQuery.ajax({
		type:"POST",
		url:uri,
		async:true,
		data:data,
		dataType:datatype,
		success:function(response){
			succ_fn(response);
		},
		error:err_fn
	}
	);

}

/**
 * ajax封装
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
 *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
 * type 请求方式("POST" 或 "GET")， 默认为 "GET"
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
 * successfn 成功回调函数
 * errorfn 失败回调函数
 */
var ajaxFlag = true;
jQuery.baseAjax=function(url, data, async, type, dataType, successfn, errorfn) {
    async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
    type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
    dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    if(!ajaxFlag) return;
    ajaxFlag = false;
    $.ajax({
        type: type,
        async: async,
        data: data,
        url: url,
        dataType: dataType,
        success: function(d){
            successfn(d);
            ajaxFlag = true;
        },
        error: function(e){
            errorfn(e);
            ajaxFlag = true;
        }
    });
};

/**
 * ajax封装(使用了节流)
 * url 发送请求的地址
 * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
 * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
 *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
 * type 请求方式("POST" 或 "GET")， 默认为 "GET"
 * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
 * successfn 成功回调函数
 * errorfn 失败回调函数
 * time 延时时间
 */
var timer = null;
jQuery.throttleAjax=function(url, data, async, type, dataType, successfn, errorfn, time) {
    async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
    type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
    dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    clearTimeout(timer);//如果再点击就重新计算延时时间
    timer = setTimeout(function(){
	    $.ajax({
	        type: type,
	        async: async,
	        data: data,
	        url: url,
	        dataType: dataType,
	        success: function(d){
	            successfn(d);
	        },
	        error: function(e){
	            errorfn(e);
	        }
	    });
    },time);
};