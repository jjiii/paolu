var rootPath = getRootPath();
/**
 * 参数序列化为对象
 */
$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/**
 * 获取根路径
 * @returns
 */
function getRootPath(){ 
	var strFullPath=window.document.location.href; 
	var strPath=window.document.location.pathname; 
	var pos=strFullPath.indexOf(strPath); 
	var prePath=strFullPath.substring(0,pos); 
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1); 
	return(prePath+postPath); 
}

/**
 * 取参
 * @param name
 * @returns
 */
function getQueryString(name,url){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = url?url.substr(url.lastIndexOf('?')+1).match(reg):window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/**
 *   数字格式化
 *   Usage:  formatNumber(12345.678, 2);
 *   result: 12345.68
 **/
function formatNumber(pnumber,decimals){
    if (isNaN(pnumber)) { return 0};
    if (pnumber=='') { return 0};
    var snum = new String(pnumber);
    var sec = snum.split('.');
    var whole = parseFloat(sec[0]);
    var result = '';
      
    if(sec.length > 1){
        var dec = new String(sec[1]);
        dec = String(parseFloat(sec[1])/Math.pow(10,(dec.length - decimals)));
        dec = String(whole + Math.round(parseFloat(dec))/Math.pow(10,decimals));
        var dot = dec.indexOf('.');
        if(dot == -1){
            dec += '.';
            dot = dec.indexOf('.');
        }
        while(dec.length <= dot + decimals) { dec += '0'; }
        result = dec.substring(0, dot + decimals+1);
    } else{
        var dot;
        var dec = new String(whole);
        dec += '.';
        dot = dec.indexOf('.');   
        while(dec.length <= dot + decimals) { dec += '0'; }
        result = dec;
    } 
    return result;
}

/**
 * 全屏弹层
 * @param url
 * @param title
 */
function openModal(url,title){
	var index = layer.open({
		type: 2,
		title:title,
		content: url,
		area: ['320px', '195px'],
		maxmin: false,
		end:function(){
			$('#issave').val()==1 && location.reload();
		},
		success:function(){
			//var body = layer.getChildFrame('body', index);
		}
	});
	layer.full(index);
}
