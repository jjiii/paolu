$(function(){
	var billDate = {
		  elem: '#billdate',
		  format: 'YYYY-MM-DD',
		  //min: laydate.now(), //设定最小日期为当前日期
		  max: '2099-06-16 23:59:59', //最大日期
		  istime: true,
		  istoday: false,

	};
	
	laydate(billDate);
});

/**
 * 初始化文件上传框的方法
 */
function initFileInput(){
	 // Basic example
	$('.file-input').fileinput({
	       browseLabel: '',
	       browseClass: 'btn btn-primary btn-icon',
	       removeLabel: '',
	       uploadLabel: '',
	       uploadClass: 'btn btn-default btn-icon',
	       browseIcon: '<i class="icon-plus22"></i> ',
	       uploadIcon: '<i class="icon-file-upload"></i> ',
	       removeClass: 'btn btn-danger btn-icon',
	       removeIcon: '<i class="icon-cancel-square"></i> ',
	       showUpload: false, //是否显示上传按钮  
	       showCaption: true,//是否显示标题  
	       showPreview:false,
	       layoutTemplates: {
	           caption: '<div tabindex="-1" class="form-control file-caption {class}">\n' + '<span class="icon-file-plus kv-caption-icon"></span><div class="file-caption-name"></div>\n' + '</div>'
	       },
	       initialCaption: "请选择对账文件"
	});
}

$(function(){
	initFileInput();
	
	$('#form-data').validate({
		ignore: ".ignore",
		submitHandler: function(form) {
			uploadFile(form)
		}
	});
	//uploadFile();
});

function uploadFile(formObj){
	//var curForm = $(this).parents('.panel').find('form');
	//console.info(curForm[0]);
	var loadIndex = parent.layer.load(0, {shade: [0.3,'#fff']});
	var ajaxFlag = true;
	if(!ajaxFlag) return;
    ajaxFlag = false;
	var formData = new FormData(formObj);
    $.ajax({
        type:"post",
        url:rootPath+'/testbill/uploadbillfile/uploadbillfile',
        async:false,
        contentType: false,    //这个一定要写
        processData: false, //这个也一定要写，不然会报错
        data:formData,
        dataType:'json',    //返回类型，有json，text，HTML。这里并没有jsonp格式，所以别妄想能用jsonp做跨域了。
        success:function(data){
			if(data.errcode==300){	// 登录超时
				parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
					location.reload();
				});
				return;
			}
        	if(data.errcode==0){
        		parent.alertModal(data.errmsg,1,function(index){
					layer.close(index);
				});
			}else{
        		parent.alertModal(data.errmsg,2,function(index){
					layer.close(index);
				});
			}
        	parent.layer.close(loadIndex);
        	ajaxFlag = true;
        },error:function(){
        	parent.layer.close(loadIndex);
        	ajaxFlag = true;
        }        
    });	
}
