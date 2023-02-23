var listPage = parent;	//父级页面
var indexPage = parent.parent;  //顶级页面index

$(function(){
	$("#data-form").validate({
		ignore: ".ignore",
		submitHandler: function(form) {
			eval($(form).data('method'))();
		}
	});
	channelChange();
});

/**
 * 初始化文件上传框的方法
 */
function initFileInput(){
	 // Basic example
	$('.file-input').each(function(){
		$(this).fileinput({
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
		       initialCaption: "请选择文件"
		   });
	})
}

/**
 * 选择支付接口时的事件
 */
function channelChange(){
	$('#channel').on('change',function(){
		var eleId = $(this).val();
		$('#activ-input').html($('#'+eleId).html());
		$('#activ-input').find('input[type="file"]').addClass('file-input');
		initFileInput();
	});
}

/**
 * 查看支付接口页面时默认加载的支付接口表单
 */
function initChannel(){
	var eleId = $('#channel').val();
	$('#activ-input').html($('#'+eleId).html());
}

/**
 * 新增商户
 */
function addMerchantAppConfigData(){	
	addOrEdit(rootPath+'/merchantappconfig/adddata');
    
}

/**
 * 更新商户
 * 
 */
function editMerchantAppConfigData(){
	addOrEdit(rootPath+'/merchantappconfig/editdata');
	
}

/**
 * 新增或编辑通用
 * @param url
 */
function addOrEdit(url){
	var loadIndex = indexPage.layer.load(0, {shade: [0.3,'#fff']});
	var ajaxFlag = true;
	if(!ajaxFlag) return;
    ajaxFlag = false;
	var formData = new FormData($('#data-form')[0]);
    $.ajax({
        type:"post",
        url:url,
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
				indexPage.alertModal(data.errmsg,1,function(){
					// 标识表单已修改成功
					$(listPage.document).find('#issave').val(1);
				});
			}else{
				indexPage.msgModal(data.errmsg,2);
			}
        	indexPage.layer.close(loadIndex);
        	ajaxFlag = true;
        },error:function(){
        	indexPage.layer.close(loadIndex);
        	ajaxFlag = true;
        }        
    });	
}

/**
 * 关闭当前弹层
 */
function closePage(){
	//先得到当前iframe层的索引
	var index = parent.layer.getFrameIndex(window.name); 
	//再执行关闭   
	parent.layer.close(index); 
}
