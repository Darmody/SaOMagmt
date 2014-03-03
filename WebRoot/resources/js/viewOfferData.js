var firstResult = 1;
var maxResult = 10;

function pageChange(change){
	firstResult += change;
	loadOfferDataTable();
}
function changeMaxResult(max){
	maxResult = max;
	loadOfferDataTable();
}

function loadOfferDataTable(){
	$.get("viewOfferByAdminInPagination.action?loginAccount="+$.cookie("userName")+"&firstResult="+firstResult+"&maxResult="+maxResult,null,function(data){
		var htmlStr = "";
		var jsonObj = eval('('+data+')');
		$("#offerDataTable tbody").empty();
		for(var i = 0; jsonObj.offers[i] != null; i++){
			var offerCreateDate = jsonObj.offers[i].offerCreateDate;
			var offerName = jsonObj.offers[i].offerName;
			var offerCreater = jsonObj.offers[i].creater;
			var offerUrl = jsonObj.offers[i].offerUrl;
			var offerStatus = jsonObj.offers[i].offerStatus;
			var offerStartDate = jsonObj.offers[i].offerStartDate;
			var offerCloseDate = jsonObj.offers[i].offerCloseDate;
			var countLimit = jsonObj.offers[i].countLimit;
			var countFinish = jsonObj.offers[i].countFinish;
			var offerPK = jsonObj.offers[i].PK_offerKey;
			if(countLimit == 0)
				var process = "100%";
			else{
				var process = countFinish/countLimit;
				if(process == "1")
					process = "100%";
				else{
					process = process.toFixed(2);
					process = process.slice(2,4)+"%";
				}
			}
			htmlStr += "<tr>"+
												"<td>"+offerName+"</td>"+
												"<td>"+offerCreater+"</td>"+
												"<td><a target = '_blank' href = '/SDMS"+offerUrl+"'>"+offerUrl+"</a></td>"+
												"<td>"+offerStatus+"<br/><a class = 'btn' target = '_blank' href = 'offerDetails.html?PK="+offerPK+"'>查看结果</a></td>"+
												"<td>"+offerStartDate+"<br/>"+offerCloseDate+"</td>"+
												"<td class = 'viewOfferProcessDetail' data-offerPK = '"+offerPK+"'>"+
													countFinish + "/" + countLimit +
													"<div class = 'progress progress-striped active'>"+
														"<div class = 'bar' style = 'width:"+process+";'></div>"+
													"</div>"+
												"</td>"+
											  "</tr>";

		} // end of for
		$("#offerDataTable tbody").append(htmlStr);
		$("#offerDataTable tbody").append("<tr colspan = '6'><td>"+
										"<div class='btn-group'>"+
											"<button class = 'btn' onclick = 'javascript:pageChange(-1)'>上一页</button>"+
											"<button class = 'btn' onclick = 'javascript:changeMaxResult(10);'>10</button>"+
											"<button class = 'btn' onclick = 'javascript:changeMaxResult(20);'>20</button>"+
											"<button class = 'btn' onclick = 'javascript:changeMaxResult(30);'>30</button>"+
											"<button class = 'btn' onclick = 'javascript:pageChange(+1)'>下一页</button>"+
										"</div></td></tr>");
		$(".viewOfferProcessDetail").bind("click",function(){
				var offerPK = $(this).attr("data-offerPK");
				$("#offerProcessDetails tbody").empty();
				$.get("viewClassesProcess.action?PK_offerKey="+offerPK,null,function(data){
					var htmlStr2 = "";
					var jsonObj = eval('('+data+')');
					for(var j = 0; jsonObj.classProcess[j] != null; j++){
						var className = jsonObj.classesInfo[j].className;
						var classProcess = jsonObj.classProcess[j];
						var classSize = jsonObj.classSize[j];
						if(classSize == classProcess)
							var process = "100%";
						else{
							var process = classProcess/classSize;
							process = process.toFixed(2);
							process = process.slice(2,4)+"%";
						}
						var adminName1 = jsonObj.classesInfo[j].admins[0].name;
						var adminName2 = jsonObj.classesInfo[j].admins[1].name;
						var adminPhone1 = jsonObj.classesInfo[j].admins[0].phone;
						var adminPhone2 = jsonObj.classesInfo[j].admins[1].phone;
						htmlStr2 += "<tr>"+
																	"<td>"+
																		className+
																	"</td>"+
																	"<td>"+
																		classProcess +"/" + classSize +
																		"<div class = 'progress progress-striped active'>"+
																			"<div class = 'bar' style = 'width:"+process+";'></div>"+
																		"</div>"+
																	"</td>"+
																	"<td>"+
																		adminName1 + " " + adminPhone1 + "<br/>" +
																		adminName2 + " " + adminPhone2 +
																	"</td>" +
																"</tr>";
					}
					$("#offerProcessDetails tbody").append(htmlStr2);
				}) //end of get
				$("#offerProcessDetail").modal("show");
			}) //end of bind
	})
}





$(function(){
		$.get("viewOfferByAdminInPagination.action?loginAccount="+$.cookie("userName")+"&firstResult=1&maxResult=" + maxResult,null,function(data){
	
		var htmlStr = "";
		var jsonObj = eval('('+data+')');
		$("#offerDataTable tbody").empty();
		for(var i = 0; jsonObj.offers[i] != null; i++){
			var offerCreateDate = jsonObj.offers[i].offerCreateDate;
			var offerName = jsonObj.offers[i].offerName;
			var offerCreater = jsonObj.offers[i].creater;
			var offerUrl = jsonObj.offers[i].offerUrl;
			var offerStatus = jsonObj.offers[i].offerStatus;
			var offerStartDate = jsonObj.offers[i].offerStartDate;
			var offerCloseDate = jsonObj.offers[i].offerCloseDate;
			var countLimit = jsonObj.offers[i].countLimit;
			var countFinish = jsonObj.offers[i].countFinish;
			var offerPK = jsonObj.offers[i].PK_offerKey;
			if(countLimit == 0)
				var process = "100%";
			else{
				var process = countFinish/countLimit;
				if(process == "1")
					process = "100%";
				else{
					process = process.toFixed(2);
					process = process.slice(2,4)+"%";
				}
			}
			htmlStr += "<tr>"+
												"<td>"+offerName+"</td>"+
												"<td>"+offerCreater+"</td>"+
												"<td><a target = '_blank' href = '/SDMS"+offerUrl+"'>"+offerUrl+"</a></td>"+
												"<td>"+offerStatus+"<br/><a class = 'btn' target = '_blank' href = 'offerDetails.html?PK="+offerPK+"'>查看结果</a></td>"+
												"<td>"+offerStartDate+"<br/>"+offerCloseDate+"</td>"+
												"<td class = 'viewOfferProcessDetail' data-offerPK = '"+offerPK+"'>"+
													countFinish + "/" + countLimit +
													"<div class = 'progress progress-striped active'>"+
														"<div class = 'bar' style = 'width:"+process+";'></div>"+
													"</div>"+
												"</td>"+
											  "</tr>";

		} // end of for
		
		$("#offerDataTable tbody").append(htmlStr);
		$("#offerDataTable tbody").append("<tr colspan = '6'><td>"+
										"<div class='btn-group'>"+
											"<button class = 'btn' onclick = 'javascript:pageChange(-1)'>上一页</button>"+
											"<button class = 'btn' onclick = 'javascript:changeMaxResult(10);'>10</button>"+
											"<button class = 'btn' onclick = 'javascript:changeMaxResult(20);'>20</button>"+
											"<button class = 'btn' onclick = 'javascript:changeMaxResult(30);'>30</button>"+
											"<button class = 'btn' onclick = 'javascript:pageChange(+1)'>下一页</button>"+
										"</div></td></tr>");
		$(".viewOfferProcessDetail").bind("click",function(){
				var offerPK = $(this).attr("data-offerPK");
				$("#offerProcessDetails tbody").empty();
				$.get("viewClassesProcess.action?PK_offerKey="+offerPK,null,function(data){
					var htmlStr2 = "";
					var jsonObj = eval('('+data+')');
					for(var j = 0; jsonObj.classProcess[j] != null; j++){
						var className = jsonObj.classesInfo[j].className;
						var classProcess = jsonObj.classProcess[j];
						var classSize = jsonObj.classSize[j];
						if(classSize == classProcess)
							var process = "100%";
						else{
							var process = classProcess/classSize;
							process = process.toFixed(2);
							process = process.slice(2,4)+"%";
						}
						var adminName1 = jsonObj.classesInfo[j].admins[0].name;
						var adminName2 = jsonObj.classesInfo[j].admins[1].name;
						var adminPhone1 = jsonObj.classesInfo[j].admins[0].phone;
						var adminPhone2 = jsonObj.classesInfo[j].admins[1].phone;
						htmlStr2 += "<tr>"+
																	"<td>"+
																		className+
																	"</td>"+
																	"<td>"+
																		classProcess +"/" + classSize +
																		"<div class = 'progress progress-striped active'>"+
																			"<div class = 'bar' style = 'width:"+process+";'></div>"+
																		"</div>"+
																	"</td>"+
																	"<td>"+
																		adminName1 + " " + adminPhone1 + "<br/>" +
																		adminName2 + " " + adminPhone2 +
																	"</td>" +
																"</tr>";
					}
					$("#offerProcessDetails tbody").append(htmlStr2);
				}) //end of get
				$("#offerProcessDetail").modal("show");
			}) //end of bind
	})
})