$(function(){
	var submitterStr = "";
	$("#adminSubmit").hide();
	if($.cookie("adminType") > 0)
		$("#adminSubmit").show();
	$("#offerSubmitBtn").click(function(){
		var questionRealCount = $("#offerInfo").attr("data-questionCount");
		var urlStr = "?";
		var answer = "";
		for(var i = 1;i < questionRealCount;i++){
			var questionType = $("#questionInfo-"+i).attr("data-questionType");
			if(questionType == "sinSelect" || questionType == "mulSelect" || questionType == "checkOption"){
				var checked = $("#question"+i).find("input:checked");
				checked.each(function(){
					answer += $(this).val();
					answer += "|";
				})
				answer = answer.substr(0,answer.length - 1);
				urlStr += "&answer=";
				urlStr += encodeURIComponent(answer);
				answer = "";
			}
			else if(questionType == "textInput"){
				answer = $("#question"+i).find("textarea").val();
				urlStr += "&answer=";
				urlStr += encodeURIComponent(answer);
				answer = "";
			}
			else if(questionType == "fileUpload"){
				urlStr += "&answer=fileUpload";
			}

		}
		var url = window.location.pathname;
		url = url.substr(5);
		if($.cookie("adminType") > 0){
			var submitters = $("#submitters").val();
			var submitterArray = submitters.split(",");
			for(var i = 0; i < submitterArray.length; i++){
				submitterStr += "&stuCode=";
				submitterStr += submitterArray[i];
			}
			$.get("submitOfferByAdmin.action"+urlStr+submitterStr+"&offerUrl="+url,null,function(data){
				var jsonObj = eval('('+data+')');
				alert(jsonObj.returnMessage);
				setTimeout("window.location.reload();",2000);
			})
		}
		else{
			$.get("submitOffer.action"+urlStr+"&submitter="+$.cookie("userName")+"&offerUrl="+url,null,function(data){
				var jsonObj = eval('('+data+')');
				alert(jsonObj.returnMessage);
				setTimeout("window.location.reload();",2000);
			})
		}
	})
})


function fileUpload(num){
	var url = window.location.pathname;
		url = url.substr(12);
	var offerPK = url.split("/")[0];
	$("iframe").attr("src","../offerFileUpload.html?PK_offerKey="+offerP+"&flowNum="+num);
	$("#fileUploadModal").modal("show");
}