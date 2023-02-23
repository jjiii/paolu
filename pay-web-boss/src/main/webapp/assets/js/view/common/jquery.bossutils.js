/**
 * 
 */
;(function($,window,undefined){
	if(!$)
		 console.error("bossutils need jquery");
	
	var bossutils = {
		rootPath:function(){	// 获取项目根路径
			var strFullPath=window.document.location.href; 
			var strPath=window.document.location.pathname; 
			var pos=strFullPath.indexOf(strPath); 
			var prePath=strFullPath.substring(0,pos); 
			var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1); 
			return(prePath+postPath);
		}(),
		serializeObject:function(sel){	// 序列化json格式的表单参数
			var o = {};
		    var a = $(sel).serializeArray();
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
		},
		getQueryString:function(name,url){	// 获取在url中指定参数名的参数值
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		    var r = url?url.substr(url.lastIndexOf('?')+1).match(reg):window.location.search.substr(1).match(reg);
		    if(r!=null)return  unescape(r[2]); return null;
		},
		formatNumber:function(pnumber,decimals){	// 格式化数字，保留指定位数
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
		},
		formatDate:function(date,format){	// 格式化日期，可以传日期获时间戳
			  var v = ""; 
			  (typeof date == "number")&& (date=new Date(date));
		      if (typeof date == "string" ||typeof date != "object") { 
		        return; 
		      }
		      var year  = date.getFullYear(); 
		      var month  = date.getMonth()+1; 
		      var day   = date.getDate(); 
		      var hour  = date.getHours(); 
		      var minute = date.getMinutes(); 
		      var second = date.getSeconds(); 
		      var weekDay = date.getDay(); 
		      var ms   = date.getMilliseconds(); 
		      var weekDayString = ""; 
		        
		      if (weekDay == 1) { 
		        weekDayString = "星期一"; 
		      } else if (weekDay == 2) { 
		        weekDayString = "星期二"; 
		      } else if (weekDay == 3) { 
		        weekDayString = "星期三"; 
		      } else if (weekDay == 4) { 
		        weekDayString = "星期四"; 
		      } else if (weekDay == 5) { 
		        weekDayString = "星期五"; 
		      } else if (weekDay == 6) { 
		        weekDayString = "星期六"; 
		      } else if (weekDay == 7) { 
		        weekDayString = "星期日"; 
		      } 
		  
		      v = format; 
		      //Year 
		      v = v.replace(/yyyy/g, year); 
		      v = v.replace(/YYYY/g, year); 
		      v = v.replace(/yy/g, (year+"").substring(2,4)); 
		      v = v.replace(/YY/g, (year+"").substring(2,4)); 
		  
		      //Month 
		      var monthStr = ("0"+month); 
		      v = v.replace(/MM/g, monthStr.substring(monthStr.length-2)); 
		  
		      //Day 
		      var dayStr = ("0"+day); 
		      v = v.replace(/dd/g, dayStr.substring(dayStr.length-2)); 
		  
		      //hour 
		      var hourStr = ("0"+hour); 
		      v = v.replace(/HH/g, hourStr.substring(hourStr.length-2)); 
		      v = v.replace(/hh/g, hourStr.substring(hourStr.length-2)); 
		  
		      //minute 
		      var minuteStr = ("0"+minute); 
		      v = v.replace(/mm/g, minuteStr.substring(minuteStr.length-2)); 
		  
		      //Millisecond 
		      v = v.replace(/sss/g, ms); 
		      v = v.replace(/SSS/g, ms); 
		        
		      //second 
		      var secondStr = ("0"+second); 
		      v = v.replace(/ss/g, secondStr.substring(secondStr.length-2)); 
		      v = v.replace(/SS/g, secondStr.substring(secondStr.length-2)); 
		        
		      //weekDay 
		      v = v.replace(/E/g, weekDayString); 
		      return v; 
		}
	};
	
	window.bossutils = bossutils;

})(jQuery,window);