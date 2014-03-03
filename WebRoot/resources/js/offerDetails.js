$(function(){
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}
	var PK = getQueryString("PK");
	var htmlStr = "";
	$("#downloadButton").attr("href","offerAnswersExport.action?PK_offerKey="+PK)
	$.get("viewOfferFlowDetails.action?PK_offerKey="+PK,null,function(data){
		var answerCount = 0;
		var jsonObj = eval('('+data+')');
		var offerName = jsonObj.offer.offerName;
		var offerDescription = jsonObj.offer.offerDescription;
		$("#offerName").text(offerName);
		$("#offerDescription").text(offerDescription);
		for(var i = 0; jsonObj.offerFlows[i] != null; i++){
			var flowSpecies = jsonObj.offerFlows[i].flowSpecies; //0问题 1选项
			var PK_flowKey = jsonObj.offerFlows[i].PK_flowKey;//key
			var flowDescription = jsonObj.offerFlows[i].flowDescription;//描述
			var flowType = jsonObj.offerFlows[i].flowType;//如果是问题，则此字段表明单选或多选或其他
			var flowNum = jsonObj.offerFlows[i].flowNum;//如果是问题，代表题号，如果是选项，代表选项号
			var fatherFlowKey = jsonObj.offerFlows[i].fatherFlowKey;//如果是选项，代表问题的key值
			if(flowSpecies == "0"){
				if(flowType == "sinSelect"){
					htmlStr += "<span class = 'badge badge-info'>单选</span>";
					htmlStr += "<strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
					htmlStr += flowNum;
					htmlStr += "：</strong>";
					htmlStr += flowDescription;
					htmlStr += "<div class = 'row'><div class = 'span8'><table class = 'table table-striped table-condensed'>";
					htmlStr += "<thead><tr><th>选项</th><th class = 'span1'>小计</th><th class = 'span3'>百分比</th></tr></thead>";
				}
				else if(flowType == "mulSelect"){
					htmlStr += "<span class = 'badge badge-info'>多选</span>";
					htmlStr += "<strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
					htmlStr += flowNum;
					htmlStr += "：</strong>";
					htmlStr += flowDescription;
					htmlStr += "<div class = 'row'><div class = 'span8'><table class = 'table table-striped table-condensed'>";
					htmlStr += "<thead><tr><th>选项</th><th class = 'span1'>小计</th><th class = 'span3'>百分比</th></tr></thead>";
				}
				else if(flowType == "checkOption"){
					htmlStr += "<span class = 'badge badge-info'>判断</span>";
					htmlStr += "<strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
					htmlStr += flowNum;
					htmlStr += "：</strong>";
					htmlStr += flowDescription;
					htmlStr += "<div class = 'row'><div class = 'span8'><table class = 'table table-striped table-condensed'>";
					htmlStr += "<thead><tr><th>选项</th><th class = 'span1'>小计</th><th class = 'span3'>百分比</th></tr></thead>";
				}
				else if(flowType == "textInput"){
					htmlStr += "<span class = 'badge badge-info'>论述</span>";
					htmlStr += "<strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
					htmlStr += flowNum;
					htmlStr += "：</strong>";
					htmlStr += flowDescription;
					htmlStr += "<br/>请下载详细统计数据以查看结果";
					htmlStr += "<hr/>";
				}
				else if(flowType == "fileUpload" || flowType == "fileUpload"){
					htmlStr += "<span class = 'badge badge-info'>文件</span>";
					htmlStr += "<strong>&nbsp;&nbsp;&nbsp;&nbsp;问题";
					htmlStr += flowNum;
					htmlStr += "：</strong>";
					htmlStr += flowDescription;
					htmlStr += "<br/>请下载详细统计数据以查看详细结果";
					htmlStr += "<hr/>";
				}
			}//end of if
			else if(flowSpecies == "1"){
				if(flowType == "textInput" || flowType == "fileUpload"){
					answerCount++;
				}
				else{
					var answerPrecent = jsonObj.answerPrecent[answerCount];
					answerPrecent = parseFloat(answerPrecent);
					if(answerPrecent == 1)
						answerPrecent = "100%";
					else{
						answerPrecent = answerPrecent.toFixed(2);
						answerPrecent = answerPrecent.slice(2,4)+"%";
					}
					var count = jsonObj.count[answerCount];
					htmlStr += "<tr><td>";
					htmlStr += flowDescription;
					htmlStr += "</td><td>";
					htmlStr += count;
					htmlStr += "</td><td>";
					htmlStr += "<div class = 'progress progress-striped active span2'>";
					htmlStr += "<div class = 'bar' style = 'width:";
					htmlStr += answerPrecent;
					htmlStr += ";'></div>";
					htmlStr += "</div>";
					htmlStr += answerPrecent;
					htmlStr += "</td></tr>";
					if(jsonObj.offerFlows[i+1] == null || jsonObj.offerFlows[i+1].flowSpecies != "1"){
						htmlStr += "</table>";
						htmlStr += "<hr/>"
					}
					answerCount++;
				}
			}
		}//end of for
		$("#content").html(htmlStr);
//		$("#test").text(htmlStr);
	})//end of get
})