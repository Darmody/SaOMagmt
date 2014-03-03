$(function(){
$("#offerDesign").hide();
$("#offerPreview").hide();
$("#offerSetting").hide();
$("#offerPublish").hide();
$("#offerCreate").hide();
var htmlStr = "";
var urlStr = "&";
var optionCount = new Array();
//重写题目序号
function reloadQuestionId(){
	$(".questionId").each(function(index){
		index++;
		$(this).html("问题"+index);
	})
}
	var questionCount = 1;
//新增单选题
	$("#sinSelectAdd").bind("click",function(){
		optionCount[questionCount] = 3;
		$("#offerContent").append("<div id = 'topic"+questionCount+"' data-type = 'sinSelect'>"+
								"<span class = 'badge badge-info'>单选</span>"+
								"<a href = '#' class = 'pull-right deleteTopicDiv' >&times;</a><form class='form-horizontal'>"+
								"<div class='control-group info'>"+
								"<label class='control-label' for='question"+questionCount+"'>"+
								"<span class = 'questionId'>问题"+questionCount+"</span></label>"+
								"<div class='controls'>"+
								"<textarea id = 'question"+questionCount+"' rows = '3'  class = 'input-xxlarge'></textarea></div></div>"+
								"<div id = 'optionDiv"+questionCount+"'>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-1'>"+
								"<span class = 'optionId"+questionCount+"'>选项1</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-1' class = 'input-xxlarge'></div></div>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-2'>"+
								"<span class = 'optionId"+questionCount+"'>选项2</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-2' class = 'input-xxlarge'></div></div>"+
								"</div>"+
								"<a class = 'btn' id = 'optionAdd"+questionCount+"'><i class = 'icon-plus'></a>"+
								"</form><hr/></div>");
//设置焦点		
		$("#question"+questionCount).focus();
		$("html,body").animate({scrollTop:$("#question"+questionCount).offset().top},1000);
//deleteTopicDiv
		$("#topic"+questionCount+" .deleteTopicDiv").bind("click",function(){
			$(this).parent().remove();
			reloadQuestionId();
		})
		var tmp = questionCount;
		$("#optionAdd" + tmp).bind("click",function(){

			$("#optionDiv" + tmp).append("<div class='control-group'>"+
									"<label class='control-label' for='answer-"+tmp+"-"+optionCount[tmp]+"'>"+
									"<span class = 'optionId"+tmp+"'>选项"+optionCount[tmp]+"</span></label>"+
									"<div class='controls'><input type = 'text' id = 'answer-"+tmp+"-"+optionCount[tmp]+"' class = 'input-xxlarge'>"+
									"&nbsp;&nbsp;<a href = 'javascript:void(0);' class = 'deleteOptionDiv'>&times;</a>"+
									"</div></div>");
			optionCount[tmp]++;
//deleteOption
			$(".deleteOptionDiv").bind("click",function(){
				$(this).parent().parent().remove();
				$(".optionId"+tmp).each(function(index){
					index += 1;
					$(this).html("选项"+index);
				})
			})
			$(".optionId"+tmp).each(function(index){
				index += 1;
				$(this).html("选项"+index);
			})
		})
		questionCount++;
		reloadQuestionId();
	})
//新增多选题
	$("#mulSelectAdd").bind("click",function(){
		optionCount[questionCount] = 3;
		$("#offerContent").append("<div id = 'topic"+questionCount+"' data-type = 'mulSelect'>"+
								"<span class = 'badge badge-info'>多选</span>"+
								"<a href = '#' class = 'pull-right deleteTopicDiv' >&times;</a><form class='form-horizontal'>"+
								"<div class='control-group info'>"+
								"<label class='control-label' for='question"+questionCount+"'>"+
								"<span class = 'questionId'>问题"+questionCount+"</span></label>"+
								"<div class='controls'>"+
								"<textarea id = 'question"+questionCount+"' rows = '3'  class = 'input-xxlarge'></textarea></div></div>"+
								"<div id = 'optionDiv"+questionCount+"'>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-1'>"+
								"<span class = 'optionId"+questionCount+"'>选项1</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-1' class = 'input-xxlarge'></div></div>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-2'>"+
								"<span class = 'optionId"+questionCount+"'>选项2</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-2' class = 'input-xxlarge'></div></div>"+
								"</div>"+
								"<a class = 'btn' id = 'optionAdd"+questionCount+"'><i class = 'icon-plus'></a>"+
								"</form><hr/></div>");
//设置焦点		
		$("#question"+questionCount).focus();
		$("html,body").animate({scrollTop:$("#question"+questionCount).offset().top},1000);
//deleteTopicDiv
		$("#topic"+questionCount+" .deleteTopicDiv").bind("click",function(){
			$(this).parent().remove();
			reloadQuestionId();
		})
		var tmp = questionCount;
		$("#optionAdd" + tmp).bind("click",function(){

			$("#optionDiv" + tmp).append("<div class='control-group'>"+
									"<label class='control-label' for='answer-"+tmp+"-"+optionCount[tmp]+"'>"+
									"<span class = 'optionId"+tmp+"'>选项"+optionCount[tmp]+"</span></label>"+
									"<div class='controls'><input type = 'text' id = 'answer-"+tmp+"-"+optionCount[tmp]+"' class = 'input-xxlarge'>"+
									"&nbsp;&nbsp;<a href = 'javascript:void(0);' class = 'deleteOptionDiv'>&times;</a>"+
									"</div></div>");
			optionCount[tmp]++;
//deleteOption
			$(".deleteOptionDiv").bind("click",function(){
				$(this).parent().parent().remove();
				$(".optionId"+tmp).each(function(index){
					index += 1;
					$(this).html("选项"+index);
				})
			})
			$(".optionId"+tmp).each(function(index){
				index += 1;
				$(this).html("选项"+index);
			})
		})
		questionCount++;
		reloadQuestionId();
	})
//新增判断题
$("#checkOptionAdd").bind("click",function(){
		$("#offerContent").append("<div id = 'topic"+questionCount+"' data-type = 'checkOption'>"+
								"<span class = 'badge badge-info'>判断</span>"+
								"<a href = '#' class = 'pull-right deleteTopicDiv' >&times;</a><form class='form-horizontal'>"+
								"<div class='control-group info'>"+
								"<label class='control-label' for='question"+questionCount+"'>"+
								"<span class = 'questionId'>问题"+questionCount+"</span></label>"+
								"<div class='controls'>"+
								"<textarea id = 'question"+questionCount+"' rows = '3'  class = 'input-xxlarge'></textarea></div></div>"+
								"<div id = 'optionDiv"+questionCount+"'>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-1'>"+
								"<span class = 'optionId"+questionCount+"'>选项1</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-1' class = 'input-xxlarge' value = '正确'></div></div>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-2'>"+
								"<span class = 'optionId"+questionCount+"'>选项2</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-2' class = 'input-xxlarge' value = '错误'></div></div>"+
								"</div>"+
								"</form><hr/></div>");
//设置焦点		
		$("#question"+questionCount).focus();
		$("html,body").animate({scrollTop:$("#question"+questionCount).offset().top},1000);
//deleteTopicDiv
		$("#topic"+questionCount+" .deleteTopicDiv").bind("click",function(){
			$(this).parent().remove();
			reloadQuestionId();
		})
		var tmp = questionCount;
		questionCount++;
		optionCount[tmp] = 2;
		reloadQuestionId();
})
//新增论述题
$("#textInputAdd").bind("click",function(){
		$("#offerContent").append("<div id = 'topic"+questionCount+"' data-type = 'textInput'>"+
								"<span class = 'badge badge-info'>论述</span>"+
								"<a href = '#' class = 'pull-right deleteTopicDiv' >&times;</a><form class='form-horizontal'>"+
								"<div class='control-group info'>"+
								"<label class='control-label' for='question"+questionCount+"'>"+
								"<span class = 'questionId'>问题"+questionCount+"</span></label>"+
								"<div class='controls'>"+
								"<textarea id = 'question"+questionCount+"' rows = '3'  class = 'input-xxlarge'></textarea></div></div>"+
								"<div id = 'optionDiv"+questionCount+"'>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-1'>"+
								"<span class = 'optionId"+questionCount+"'>回答</span></label>"+
								"<div class='controls'><input type = 'text' id = 'answer-"+questionCount+"-1' class = 'input-xxlarge' disabled = 'disabled'></div></div>"+
								"</div>"+
								"</form><hr/></div>");
//设置焦点		
		$("#question"+questionCount).focus();
		$("html,body").animate({scrollTop:$("#question"+questionCount).offset().top},1000);
//deleteTopicDiv
		$("#topic"+questionCount+" .deleteTopicDiv").bind("click",function(){
			$(this).parent().remove();
			reloadQuestionId();
		});
		var tmp = questionCount;
		questionCount++;
		optionCount[tmp] = 2;
		reloadQuestionId();
});
//新增文件上传题
$("#fileUploadAdd").bind("click",function(){
		$("#offerContent").append("<div id = 'topic"+questionCount+"' data-type = 'fileUpload'>"+
								"<span class = 'badge badge-info'>文件</span>"+
								"<a href = '#' class = 'pull-right deleteTopicDiv' >&times;</a><form class='form-horizontal'>"+
								"<div class='control-group info'>"+
								"<label class='control-label' for='question"+questionCount+"'>"+
								"<span class = 'questionId'>问题"+questionCount+"</span></label>"+
								"<div class='controls'>"+
								"<textarea id = 'question"+questionCount+"' rows = '3'  class = 'input-xxlarge'></textarea></div></div>"+
								"<div id = 'optionDiv"+questionCount+"'>"+
								"<div class='control-group'><label class='control-label' for='answer-"+questionCount+"-1'>"+
								"<span class = 'optionId"+questionCount+"'>文件</span></label>"+
								"<div class='controls'><input type = 'file' id = 'answer-"+questionCount+"-1' class = 'input-xxlarge' disabled = 'disabled'></div></div>"+
								"</div>"+
								"</form><hr/></div>");
//设置焦点		
		$("#question"+questionCount).focus();
		$("html,body").animate({scrollTop:$("#question"+questionCount).offset().top},1000);
//deleteTopicDiv
		$("#topic"+questionCount+" .deleteTopicDiv").bind("click",function(){
			$(this).parent().remove();
			reloadQuestionId();
		});
		var tmp = questionCount;
		questionCount++;
		optionCount[tmp] = 2;
		reloadQuestionId();
});
//报盘名设置页下一步(报盘内容设计)
	$("#offerCreateNextBtn").bind("click",function(){
		if($("#offerNameCreate").val() == "")
		{
			alert("请输入报盘名");
			return;
		}
		$("#offerCreate").hide();
		$("#offerDesign").show();
		$("#offerName").html($("#offerNameCreate").val());
	});
//报盘内容设计页下一步(权限设置)
	$("#offerDesignNextBtn").bind("click",function(){
		$("#offerDesign").hide();
		$("#offerSetting").show();
	})
//报盘内容设计页上一步
	$("#offerDesignPreviousBtn").bind("click",function(){
		$("#offerCreate").show();
		$("#offerDesign").hide();
	})
//报盘预览页下一步(发布)
	$("#offerPreviewNextBtn").bind("click",function(){
		$("#offerPreview").hide();
		$("#offerPublish").show();
		var temp = "<!DOCTYPE html><meta charset = 'utf-8'><html http-equiv = 'Content-Language' content = 'zh-CN'><head>";
		temp += "<script type = 'text/javascript' src = '/SDMS/resources/js/jquery-1.9.1.min.js'></script>";
		temp += "<script type = 'text/javascript' src = '/SDMS/resources/js/bootstrap.min.js'></script>";
		temp += "<script type = 'text/javascript' src = '/SDMS/resources/js/offerAction.js'></script>";
		temp += "<script type = 'text/javascript' src = '/SDMS/resources/js/jquery-cookie.js'></script>";
		temp += "<script type = 'text/javascript' src = '/SDMS/resources/js/header.js'></script>";
		temp += "<link rel = 'stylesheet' media = 'screen' href = '/SDMS/resources/css/bootstrap.min.css'/>";
		temp += "<link rel = 'stylesheet' media = 'screen' href = '/SDMS/resources/css/style.css'/><title>";
		temp += $("#offerNameCreate").val();
		temp += "</title></head><body><div id = 'header'></div>";
		temp += "<div id = 'offerContent' class = 'container'>";
		htmlStr = temp + htmlStr;
		htmlStr += "<div class='modal hide fade' id = 'fileUploadModal'>";
		htmlStr += "<div class='modal-header'>";
		htmlStr += "<a class='close' data-dismiss='modal'>×</a>";
		htmlStr += "<h3>导入学生数据</h3>";
		htmlStr += "</div>";
		htmlStr += "<div class='modal-body'>";
		htmlStr += "<iframe src = ' ../offerFileUpload.html' width = '100%' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='no' allowtransparency='yes'></iframe>";
		htmlStr += "</div>";
		htmlStr += "</div>";
		htmlStr += "感谢您的配合!请在问卷填写完毕后点击下面的按钮提交!<div style = 'margin:15px auto;text-align:center;'>";
		htmlStr += "<button class = 'btn btn-primary' id = 'offerSubmitBtn'>提交</button></div></div>";
		htmlStr += "<div id = 'adminSubmit'>请填写您代填的学生的学号：<input type = 'textarea' id = 'submitters' class = 'input-xlarge' rows = '5' placeholder = '多个学号请用半角逗号分割'></div>"
		htmlStr += "<div id = 'footer' class = 'navbar-static-bottom'>";
		htmlStr += "<hr />";
		htmlStr += "<span style='font-family:arial;'>Copyright &copy; 2013</span>";
		htmlStr += "<br>Powered by <a href = 'http://www.gstill.com/'>Still</a> & <a href = 'http://www.idarmody.com'>Darmody</a>";
		htmlStr += "<br><a href = 'about.html'>About us</a>";
		htmlStr += "</div>";
		htmlStr += "</body></html>";
		htmlStr = encodeURIComponent(htmlStr);
		var offerSubmittersStrs = "";
		for(var key in offerSubmittersStr)
		{
			offerSubmittersStrs += "&offerSubmitters=";
			offerSubmittersStrs += offerSubmittersStr[key];
		}
		var timeStart = $("#timeStart").val();
		var timeEnd = $("#timeEnd").val();
		var countLimit = $("#countLimit").val();
//		var testStr = "htmlStr="+htmlStr+urlStr+offerSubmittersStrs+"&offerStartDate="+timeStart+"&offerCloseData="+timeEnd+"&countLimit="+countLimit;
//		$("#publishDiv").html(testStr);
		// $.post("generateNewOffer.action?htmlStr="+htmlStr+urlStr+offerSubmittersStrs+"&offerStartDate="+timeStart+"&offerCloseDate="+timeEnd+"&countLimit="+countLimit,null,function(data){
		// 	alert(data);
		// })
		$.ajax({
			type : "post",
			url : "generateNewOffer.action",
			data : "htmlStr="+htmlStr+urlStr+offerSubmittersStrs+"&offerStartDate="+timeStart+"&offerCloseDate="+timeEnd+"&countLimit="+countLimit,
			success : function(data){
				var jsonObj = eval('('+data+')');
				alert(jsonObj.returnMessage);
			},
		});
	})
//报盘预览页上一步
	$("#offerPreviewPreviousBtn").bind("click",function(){
		$("#offerSetting").show();
		$("#offerPreview").hide();
	})
//报盘权限设置页上一步
	$("#offerSettingPreviousBtn").bind("click",function(){
		$("#offerSetting").hide();
		$("#offerDesign").show();
	})
//报盘权限设置页下一步（预览）
	$("#offerSettingNextBtn").bind("click",function(){
		if($("#timeStart").val() == null || $("#timeStart").val() == "" || $("#timeEnd").val() == null || $("#timeEnd").val() == "" || $("#countLimit").val() == null || $("#countLimit").val() == "" || offerSubmittersStr == null || offerSubmittersStr == ""){
			alert("请填完所有项！");
			return;
		}
		htmlStr = "";
		urlStr = "&";
		var offerSettingHtmlStr = "";
		$("#offerSetting").hide();
		$("#offerPreview").show();
		var questionRealCount = 1;
		var optionRealCount = new Array();
		$("#offerDesign").hide();
		$("#offerPreview").show();
			htmlStr += "<h2 class = 'text-center' id = 'offerName'>";
			htmlStr += $("#offerNameCreate").val();
			htmlStr += "</h2>";
			htmlStr += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			htmlStr += $("#offerDescription").val();
			htmlStr += "</p><hr/>";

			urlStr += "offerName=";
			urlStr += encodeURIComponent($("#offerNameCreate").val());
			urlStr += "&offerDescription=";
			urlStr += encodeURIComponent($("#offerDescription").val());
			urlStr += "&";
			for(var i = 1;i <= questionCount;i++){
				if($("#question"+i) != "" && typeof($("#question"+i).val()) != "undefined"){
					var questionType = $("#topic"+i).attr("data-type");
					if(questionType == "sinSelect"){
						optionRealCount[questionRealCount] = 1;
						htmlStr += "<div id = 'question";
						htmlStr += questionRealCount;
						htmlStr += "'><span class = 'badge badge-info'>单选</span><strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
						htmlStr += questionRealCount;
						htmlStr += ":</strong>";
						htmlStr += $("#question"+i).val();
						htmlStr += "<div class = 'options'>";

						urlStr += "questionType=sinSelect&";
						urlStr += "question=";
						urlStr += encodeURIComponent($("#question"+i).val());
						urlStr += "&";
						for(var j = 1;j <= optionCount[i];j++){
							if($("#answer-"+i+"-"+j).val() != "" && typeof($("#answer-"+i+"-"+j).val()) != "undefined"){
								htmlStr += "<label class = 'radio'><input type = 'radio' name = 'answer-";
								htmlStr += questionRealCount;
								htmlStr += "' value = '";
								htmlStr += optionRealCount[questionRealCount];
								htmlStr += "'>";
								htmlStr += $("#answer-"+i+"-"+j).val();
								htmlStr += "</label>";
								optionRealCount[questionRealCount]++;

								urlStr += "answer=";
								urlStr += encodeURIComponent($("#answer-"+i+"-"+j).val());
								urlStr += "&";
							}
						}
						htmlStr += "<span id = 'questionInfo-";
						htmlStr += questionRealCount;
						htmlStr += "' data-questionType = 'sinSelect' data-optionRealCount = '"+ optionRealCount[questionRealCount] +"'></span>";
						htmlStr += "</div></div><hr />";

						urlStr += "optionCount=";
						urlStr += optionRealCount[questionRealCount] - 1;
						urlStr += "&";
						questionRealCount++;
					}//end of if(questionType = "sinSelect")
					else if(questionType == "mulSelect"){
						optionRealCount[questionRealCount] = 1;
						htmlStr += "<div id = 'question";
						htmlStr += questionRealCount;
						htmlStr += "'><span class = 'badge badge-info'>多选</span><strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
						htmlStr += questionRealCount;
						htmlStr += ":</strong>";
						htmlStr += $("#question"+i).val();
						htmlStr += "<div class = 'options'>";

						urlStr += "questionType=mulSelect&";
						urlStr += "question=";
						urlStr += encodeURIComponent($("#question"+i).val());
						urlStr += "&";
						for(var j = 1;j <= optionCount[i];j++){
							if($("#answer-"+i+"-"+j).val() != "" && typeof($("#answer-"+i+"-"+j).val()) != "undefined"){
								htmlStr += "<label class = 'checkbox'><input type = 'checkbox' name = 'answer-";
								htmlStr += questionRealCount;
								htmlStr += "' value = '";
								htmlStr += optionRealCount[questionRealCount];
								htmlStr += "'>";
								htmlStr += $("#answer-"+i+"-"+j).val();
								htmlStr += "</label>";
								optionRealCount[questionRealCount]++;

								urlStr += "answer=";
								urlStr += encodeURIComponent($("#answer-"+i+"-"+j).val());
								urlStr += "&";
							}
						}
						htmlStr += "<span id = 'questionInfo-";
						htmlStr += questionRealCount;
						htmlStr += "' data-questionType = 'mulSelect' data-optionRealCount = '"+ optionRealCount[questionRealCount] +"'></span>";
						htmlStr += "</div></div><hr />";

						urlStr += "optionCount=";
						urlStr += optionRealCount[questionRealCount] - 1;
						urlStr += "&";
						questionRealCount++;
					}//end of if(questionType = "mulSelect")
					else if(questionType == "checkOption"){
						htmlStr += "<div id = 'question";
						htmlStr += questionRealCount;
						htmlStr += "'><span class = 'badge badge-info'>判断</span><strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
						htmlStr += questionRealCount;
						htmlStr += ":</strong>";
						htmlStr += $("#question"+i).val();
						htmlStr += "<div class = 'options'><label class = 'radio'>";
						htmlStr += "<input type = 'radio' value = '1' name = 'answer-";
						htmlStr += questionRealCount;
						htmlStr += "' value = '1'>";
						htmlStr += $("#answer-"+i+"-1").val();
						htmlStr += "</label><label class = 'radio'>";
						htmlStr += "<input type = 'radio' value = '2' name = 'answer-";
						htmlStr += questionRealCount;
						htmlStr += "' value = '2'>";
						htmlStr += $("#answer-"+i+"-2").val();
						htmlStr += "</label><span id = 'questionInfo-";
						htmlStr += questionRealCount;
						htmlStr += "' data-questionType = 'checkOption'></span></div></div><hr/>";

						urlStr += "questionType=checkOption&";
						urlStr += "question=";
						urlStr += encodeURIComponent($("#question"+i).val());
						urlStr += "&";
						urlStr += "optionCount=2&answer=";
						urlStr += encodeURIComponent($("#answer-"+i+"-1").val());
						urlStr += "&answer=";
						urlStr += encodeURIComponent($("#answer-"+i+"-2").val());
						urlStr += "&";
						questionRealCount++;
					}
					else if(questionType == "textInput"){
						htmlStr += "<div id = 'question";
						htmlStr += questionRealCount;
						htmlStr += "'><span class = 'badge badge-info'>论述</span><strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
						htmlStr += questionRealCount;
						htmlStr += ":</strong>";
						htmlStr += $("#question"+i).val();
						htmlStr += "<div class = 'options'><textarea class = 'input-xxlarge' rows = '3'></textarea>";
						htmlStr += "<span id = 'questionInfo-";
						htmlStr += questionRealCount;
						htmlStr += "'' data-questionType = 'textInput'></div></div><hr />";

						urlStr += "questionType=textInput";
						urlStr += "&question=";
						urlStr += encodeURIComponent($("#question"+i).val());
						urlStr += "&optionCount=1&answer=textInput&";
						questionRealCount++;
					}
					else if(questionType == "fileUpload"){
						htmlStr += "<div id = 'question";
						htmlStr += questionRealCount;
						htmlStr += "'><span class = 'badge badge-info'>文件</span><strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
						htmlStr += questionRealCount;
						htmlStr += ":</strong>";
						htmlStr += $("#question"+i).val();
						htmlStr += "<div class = 'options'><button class = 'btn' onclick = 'fileUpload(";
						htmlStr += questionRealCount;
						htmlStr += ");'>上传文件</button><span class='help-inline'><font color = 'red'>请将文件名改为提交者的学号，并确保文件小于5M！否则将提交失败！</font></span>"
						htmlStr += "<span id = 'questionInfo-";
						htmlStr += questionRealCount;
						htmlStr += "'' data-questionType = 'fileUpload'></div></div><hr />";

						urlStr += "questionType=fileUpload";
						urlStr += "&question=";
						urlStr += encodeURIComponent($("#question"+i).val());
						urlStr += "&optionCount=1&answer=fileUpload&";
						questionRealCount++;
					}
				}
			}
		htmlStr += "<span id = 'offerInfo' data-questionCount = '"+ questionRealCount +"'>";
		urlStr += "questionCount=";
		urlStr += questionRealCount - 1;
		offerSettingHtmlStr += "<h3 class = 'text-center'>报盘权限设置</h3>";
		offerSettingHtmlStr += "<table class ='table'>";
		offerSettingHtmlStr += "<tr><td>";
		offerSettingHtmlStr += "报盘起始时间";
		offerSettingHtmlStr += "</td><td>";
		offerSettingHtmlStr += $("#timeStart").val();
		offerSettingHtmlStr += "</td></tr><tr><td>";
		offerSettingHtmlStr += "报盘结束时间";
		offerSettingHtmlStr += "</td><td>";
		offerSettingHtmlStr += $("#timeEnd").val();
		offerSettingHtmlStr += "</td></tr><tr><td>";
		offerSettingHtmlStr += "提交人数上限";
		offerSettingHtmlStr += "</td><td>";
		offerSettingHtmlStr += $("#countLimit").val();
		offerSettingHtmlStr += "</td></tr><tr><td>";
		offerSettingHtmlStr += "报盘可提交者";
		offerSettingHtmlStr += "</td><td>";
		for(var j = 0; offerSubmittersNameStr[j] != null; j++){
			offerSettingHtmlStr += offerSubmittersNameStr[j]
			offerSettingHtmlStr += "<br/>";
		}
		offerSettingHtmlStr += "</td></tr></table>";
		$("html, body").animate({ scrollTop: 0 }, 120);
		$("#offerPreviewHtml").html(htmlStr);
		$("#offerSettingPreviewHtml").html(offerSettingHtmlStr);
//		$("#offerPreviewText").text(htmlStr);
//		$("#offerPreviewUrl").text(urlStr);
		
	})
//报盘发布页上一步
	$("#offerPublishPreviousBtn").bind("click",function(){
		$("#offerPublish").hide();
		$("#offerPreview").show();
	})
//新建报盘button
	$("#createNewOfferButton").click(function(){
		$("#viewOffers").hide();
		$("#offerCreate").show();
	})

$.post("viewBuuFaculties.action",null,function(data){
	$("#offerSubmitters_faculty").empty();
	var facultiesDataJson = eval('('+data+')');
	for(var i = 0; facultiesDataJson.faculties[i] != null; i++){
		var facultyName = facultiesDataJson.faculties[i].className;
		var facultyPK = facultiesDataJson.faculties[i].PK_classKey;
		$("#offerSubmitters_faculty").append("<option value = '"+facultyPK+"'>"+facultyName+"</option>");
	}
})

$("#offerSubmitters_faculty").change(function(){
	var fatherClassKey_faculty = $("#offerSubmitters_faculty").val();
	$.post("viewBuuMajors.action?facultyKey="+fatherClassKey_faculty,null,function(data){
		$("#offerSubmitters_major").empty();
		var majorsDataJson = eval('('+data+')');
		for(var i = 0; majorsDataJson.majors[i] != null; i++){
			var majorName = majorsDataJson.majors[i].className;
			var majorPK = majorsDataJson.majors[i].PK_classKey;
			$("#offerSubmitters_major").append("<option value = '"+majorPK+"'>"+majorName+"</option>");
		}
		var fatherClassKey_major = $("#offerSubmitters_major").val();
		$.post("viewBuuClasses.action?majorKey="+fatherClassKey_major,null,function(data){
			$("#offerSubmitters_class").empty();
			var classesDataJson = eval('('+data+')');
			for(var i = 0; classesDataJson.classes[i] != null; i++){
				var className = classesDataJson.classes[i].className;
				var classPK = classesDataJson.classes[i].PK_classKey;
				$("#offerSubmitters_class").append("<option value = '"+classPK+"'>"+className+"</option>");
			}
		})
	})
})

$("#offerSubmitters_major").change(function(){
	var fatherClassKey_major = $("#offerSubmitters_major").val();
		$.post("viewBuuClasses.action?majorKey="+fatherClassKey_major,null,function(data){
			$("#offerSubmitters_class").empty();
			var classesDataJson = eval('('+data+')');
			for(var i = 0; classesDataJson.classes[i] != null; i++){
				var className = classesDataJson.classes[i].className;
				var classPK = classesDataJson.classes[i].PK_classKey;
				$("#offerSubmitters_class").append("<option value = '"+classPK+"'>"+className+"</option>");
			}
		})
})

var offerSubmittersStr = new Array();
var offerSubmittersNameStr = new Array();
var arrayKey = 0;

$("#facultySubmitterAdd").click(function(){
	var facultySubmitterName = $("#offerSubmitters_faculty").find("option:selected").text();
	var facultySubmitterPK = $("#offerSubmitters_faculty").val();
	offerSubmittersStr[arrayKey] = facultySubmitterPK;
	offerSubmittersNameStr[arrayKey] = facultySubmitterName;
	arrayKey++;
	$("#offerSubmittersDetailTable").append("<tr class = 'remove'><td><input type = 'hidden' value = '"+facultySubmitterPK+"'><a href='javascript:void(0);'>"+facultySubmitterName+" &times;</a></td></tr>");
	
	$(".remove").bind("click",function(){
	var PK = $(this).find("td").find("input").val();;
	var i = 0;
	while(offerSubmittersStr[i] == PK){
		offerSubmittersStr.splice(i,1);
		offerSubmittersNameStr.splice(i,1);
		break;
	}
	$(this).remove();
	})
})
$("#majorSubmitterAdd").click(function(){
	var majorSubmitterName = $("#offerSubmitters_major").find("option:selected").text();
	var majorSubmitterPK = $("#offerSubmitters_major").val();
	offerSubmittersStr[arrayKey] = majorSubmitterPK;
	offerSubmittersNameStr[arrayKey] = majorSubmitterName;
	arrayKey++;
	$("#offerSubmittersDetailTable").append("<tr class = 'remove'><td><input type = 'hidden' value = '"+majorSubmitterPK+"'><a href='javascript:void(0);'>"+majorSubmitterName+" &times;</a></td></tr>");

	$(".remove").bind("click",function(){
	var PK = $(this).find("td").find("input").val();
	for(var i = 0; i < offerSubmittersStr.length; i++){
		if(offerSubmittersStr[i] == PK){
			offerSubmittersStr.splice(i,1);
			offerSubmittersNameStr.splice(i,1);
			arrayKey--;
			break;
		}
	}
	$(this).remove();
	})
})
$("#classSubmitterAdd").click(function(){
	var classSubmitterName = $("#offerSubmitters_class").find("option:selected").text();
	var classSubmitterPK = $("#offerSubmitters_class").val();
	offerSubmittersStr[arrayKey] = classSubmitterPK;
	offerSubmittersNameStr[arrayKey] = classSubmitterName;
	arrayKey++;
	$("#offerSubmittersDetailTable").append("<tr class = 'remove'><td><input type = 'hidden' value = '"+classSubmitterPK+"'><a href='javascript:void(0);'>"+classSubmitterName+" &times;</a></td></tr>");

	$(".remove").bind("click",function(){
	var PK = $(this).find("td").find("input").val();;
	for(var i = 0; i < offerSubmittersStr.length; i++){
		if(offerSubmittersStr[i] == PK){
			offerSubmittersStr.splice(i,1);
			offerSubmittersNameStr.splice(i,1);
			arrayKey--;
			break;
		}
	}
	$(this).remove();
	})
})


//end of $(function)
})