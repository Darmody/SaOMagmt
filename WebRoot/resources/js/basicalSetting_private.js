$(function(){
	var facultyAdminCount = 1;
	var majorAdminCount = 1;
function teacherDataReload(){
	$.post("getAdminsInPagination.action?firstResult=1&maxResult=20",null,function(data){
		var htmlStr = "";
		var teacherDataJsonObj = eval('('+data+')');
		$("#teacherDataTable tbody").empty();
		var teacherChargedClassStr = "";
		for(var i = 0; teacherDataJsonObj.administrators[i] != null; i++) {
			var teacherCode = teacherDataJsonObj.administrators[i].loginAccount;
			var teacherName = teacherDataJsonObj.administrators[i].name;
			var teacherPhone = teacherDataJsonObj.administrators[i].phone;
			var teacherEmail = teacherDataJsonObj.administrators[i].email;
			var teacherChargedClassesObj = teacherDataJsonObj.administrators[i].chargedClasses;
			for(var j = 0; teacherChargedClassesObj[j] != null; j++){
				teacherChargedClassStr += teacherChargedClassesObj[j].className;
				if(teacherChargedClassesObj[j+1] != null)
					teacherChargedClassStr += "<br/>";
			}
			var teacherPK = teacherDataJsonObj.administrators[i].PK_adminKey;
			htmlStr += "<tr>"+
										"<td><input type = 'checkbox' value = '"+teacherPK+"' class = 'check'></td>"+
										"<td>"+teacherCode+"</td>"+
										"<td>"+teacherName+"</td>"+
										"<td>"+teacherChargedClassStr+"</td>"+
										"<td>"+teacherPhone+"</td>"+
										"<td>"+teacherEmail+"</td>"+
										"</tr>";
			teacherChargedClassStr = "";
		}
		$("#teacherDataTable tbody").append(htmlStr);
		$("#teacherDataTable tbody").append("<tr><td colspan = '6'>"+
											"<div class='btn-group' data-toggle='buttons-radio'>"+
											"<button class = 'btn' onclick = 'javascript:pageChange(-1)'>上一页</button>"+
											"<button class = 'btn' onclick = 'javascript:maxResult(20);'>20</button>"+
											"<button class = 'btn' onclick = 'javascript:maxResult(30);'>30</button>"+
											"<button class = 'btn' onclick = 'javascript:maxResult(50);'>50</button>"+
											"<button class = 'btn' onclick = 'javascript:pageChange(+1)'>下一页</button>"+
											"</div></td></tr>");
	})
}
//加载完成时执行 加载教师数据
	teacherDataReload();
//加载系数据
function facultyDataReload(){
	$.post("viewBuuFaculties.action",null,function(data){
		var htmlStr = "";
		var facultyDataJsonObj = eval('('+data+')');
		var facultyAdminStr = "";
		$("#facultyDataTable tbody").empty();
		for(var i = 0; facultyDataJsonObj.faculties[i] != null; i++) {
			var facultyName = facultyDataJsonObj.faculties[i].className;
			var facultyAdminJsonObj = facultyDataJsonObj.faculties[i].admins;
			for(var j = 0; facultyAdminJsonObj[j] != null; j++){
				facultyAdminStr += facultyAdminJsonObj[j].name;
				if(facultyAdminJsonObj[j+1] != null)
					facultyAdminStr += "<br/>";
			}
			var facultyPK = facultyDataJsonObj.faculties[i].PK_classKey;
			htmlStr += "<tr>"+
										"<td><input type = 'checkbox' value = '"+facultyPK+"' class = 'check'></td>"+
										"<td>"+facultyName+"</td>"+
										"<td>"+facultyAdminStr+"</td>"+
										"</tr>";
			facultyAdminStr = "";
		}
		$("#facultyDataTable tbody").append(htmlStr);
	})
}
//加载专业数据
function majorDataReload(){
	var facultySelected = $("#facultiesList").children("option:selected").val();
	$.post("viewBuuMajors.action?facultyKey="+facultySelected,null,function(data){
		var htmlStr = "";
		var majorDataJsonObj = eval('('+data+')');
		$("#majorDataTable tbody").empty();
		var majorAdminStr = "";
		for(var i = 0; majorDataJsonObj.majors[i] != null; i++) {
			var majorName = majorDataJsonObj.majors[i].className;
			var majorAdminJsonObj = majorDataJsonObj.majors[i].admins;
			for(var j = 0; majorAdminJsonObj[j] != null; j++){
				majorAdminStr += majorAdminJsonObj[j].name;
				if(majorAdminJsonObj[j+1] != null)
					majorAdminStr += "<br/>";
			}
			var majorPK = majorDataJsonObj.majors[i].PK_classKey;
			htmlStr += "<tr>"+
									"<td><input type = 'checkbox' value = '"+majorPK+"' class = 'check'></td>"+
									"<td>"+majorName+"</td>"+
									"<td>"+majorAdminStr+"</td>"+
									"</tr>"; 
			majorAdminStr = "";
		}
		$("#majorDataTable tbody").append(htmlStr);
	})
}

function classDataReload(){
	$("#classDataTable tbody").empty();
	var majorPK = $("#majorsList_Class").children("option:selected").val();
				$.post("viewBuuClasses.action?majorKey="+majorPK,null,function(data){
					var htmlStr = "";
					var classDataJsonObj = eval('('+data+')');
					for(var i = 0; classDataJsonObj.classes[i] != null; i++){
						var className = classDataJsonObj.classes[i].className;
						var classAdmin0 = classDataJsonObj.classes[i].admins[0].name;
						var classAdmin1 = classDataJsonObj.classes[i].admins[1].name;
						var classPK =classDataJsonObj.classes[i].PK_classKey;
						htmlStr += "<tr>"+
													"<td><input type = 'checkbox' value = '"+classPK+"'/></td>"+
													"<td>"+className+"</td>"+
													"<td>"+classAdmin0+"<br/>"+classAdmin1+"</td>"+
													"</tr>";
					}
					$("#classDataTable").append(htmlStr);
				})
}

$("#facultiesList").change(function(){
	majorDataReload();
})

$("#facultiesList_Class").change(function(){
	$("#majorsList_Class").empty();
	var facultySelected_Class = $("#facultiesList_Class").children("option:selected").val();
	$.post("viewBuuMajors.action?facultyKey="+facultySelected_Class,null,function(data){
			var majorDataJsonObj = eval('('+data+')');
			for(var i = 0; majorDataJsonObj.majors[i] != null; i++){
				var majorName = majorDataJsonObj.majors[i].className;
				var majorPK = majorDataJsonObj.majors[i].PK_classKey;
				$("#majorsList_Class").append("<option value = '"+majorPK+"'>"+majorName+"</option>");
			}
			$("#classDataTable tbody").empty();
				var majorPK = $("#majorsList_Class").children("option:selected").val();
					$.post("viewBuuClasses.action?majorKey="+majorPK,null,function(data){
					var classDataJsonObj = eval('('+data+')');
					for(var i = 0; classDataJsonObj.classes[i] != null; i++){
						var className = classDataJsonObj.classes[i].className;
						var classAdmin0 = classDataJsonObj.classes[i].admins[0].name;
						var classAdmin1 = classDataJsonObj.classes[i].admins[1].name;
						var classPK =classDataJsonObj.classes[i].PK_classKey;
						$("#classDataTable").append("<tr>"+
													"<td><input type = 'checkbox' value = '"+classPK+"'/></td>"+
													"<td>"+className+"</td>"+
													"<td>"+classAdmin0+"<br/>"+classAdmin1+"</td>"+
													"</tr>");
					}
				})
	})
})

$("#majorsList_Class").change(function(){
	$("#classDataTable tbody").empty();
	var majorPK = $("#majorsList_Class").children("option:selected").val();
					$.post("viewBuuClasses.action?majorKey="+majorPK,null,function(data){
					var classDataJsonObj = eval('('+data+')');
					for(var i = 0; classDataJsonObj.classes[i] != null; i++){
						var className = classDataJsonObj.classes[i].className;
						var classAdmin0 = classDataJsonObj.classes[i].admins[0].name;
						var classAdmin1 = classDataJsonObj.classes[i].admins[1].name;
						var classPK =classDataJsonObj.classes[i].PK_classKey;
						$("#classDataTable").append("<tr>"+
													"<td><input type = 'checkbox' value = '"+classPK+"'/></td>"+
													"<td>"+className+"</td>"+
													"<td>"+classAdmin0+"<br/>"+classAdmin1+"</td>"+
													"</tr>");
					}
				})
})

//添加教师数据按钮
	$("#teacherDataAddButton").click(function(){
		$("#teacherDataAddModal").modal("show");
	});
//添加教师数据提交按钮
	$("#teacherDataAddSubmit").click(function(){
		var teacherCodeAdd = $("#teacherCodeAdd").val();
		var teacherNameAdd = $("#teacherNameAdd").val();
		var teacherPhoneAdd = $("#teacherPhoneAdd").val();
		var teacherEmailAdd = $("#teacherEmailAdd").val();
		$.post("addAdmin.action?loginAccount="+teacherCodeAdd+"&name="+teacherNameAdd+"&phone="+teacherPhoneAdd+"&email="+teacherEmailAdd,null,function(data){
			var jsonObj = eval('('+data+')');
			alert(jsonObj.returnMessage);
			teacherDataReload();
			$("#teacherDataAddModal").modal("hide");	
		})
	});
// 删除教师数据按钮
$("#teacherDataDeleteButton").click(function(){
	var str="";
	var checkbox = $("#teacherDataTable").find("input:checked");
	checkbox.each(function(){
		str += $(this).val();
		str += ":";
	});
	
	if(str != "")
	{
		$.get("deleteAdmins.action?PK_adminKey="+str,null,function(data){
		var jsonObj = eval('('+data+')');
		alert(jsonObj.returnMessage);
		teacherDataReload();
		});
	}
	else
	alert("您未选中任何项目");
	});

//添加系数据按钮
	$("#facultyDataAddButton").click(function(){
		$.post("getAdmins.action",null,function(data){
		var teacherDataJsonObj = eval('('+data+')');
		$("#facultyAdminAddAgainDiv").empty();
		$(".facultyAdminAdd:eq(0)").empty();
		for(var i = 0; teacherDataJsonObj.administrators[i] != null; i++) {
			if(teacherDataJsonObj.administrators[i].adminType >= 2){
			var teacherCode = teacherDataJsonObj.administrators[i].loginAccount;
			var teacherName = teacherDataJsonObj.administrators[i].name;
			$(".facultyAdminAdd:eq(0)").append("<option value = '"+teacherCode+"'>"+teacherName+" "+teacherCode+"</option>");
			}
		}
		$("#facultyDataAddModal").modal("show");
	});
	});
// 删除系数据按钮
$("#facultyDataDeleteButton").click(function(){
	var str="";
	var checkbox = $("#facultyDataTable").find("input:checked");
	checkbox.each(function(){
		str += $(this).val();
		str += ":";
	});
	
	if(str != "")
	{
		$.get("deleteBuuClasses.action?PK_classKey="+str,null,function(data){
		var jsonObj = eval('('+data+')');
		alert(jsonObj.returnMessage);
		facultyDataReload();
		});
	}
	else
	alert("您未选中任何项目");
});
//新增一个系管理员
$("#facultyAdminAddAgainSubmitButton").click(function(){
	$("#facultyAdminAddAgainDiv").append("<div class = 'control-group'>"+
											"<label class = 'control-label' for = 'facultyAdminAdd'>系管理员</label>"+
											"<div class = 'controls'>"+
												"<select name = 'facultyAdminAdd' class = 'facultyAdminAdd'>"+
												"</select>"+
											"</div>"+
											"</div>");
	$(".facultyAdminAdd:eq("+facultyAdminCount+")").append("<option value = ''>无</option>");
	$.post("getAdmins.action",null,function(data){
		var teacherDataJsonObj = eval('('+data+')');
	for(var i = 0; teacherDataJsonObj.administrators[i] != null; i++) {
		if(teacherDataJsonObj.administrators[i].adminType >= 2){
			var teacherCode = teacherDataJsonObj.administrators[i].loginAccount;
			var teacherName = teacherDataJsonObj.administrators[i].name;
			$(".facultyAdminAdd:eq("+facultyAdminCount+")").append("<option value = '"+teacherCode+"'>"+teacherName+" "+teacherCode+"</option>");
		}
	}
	facultyAdminCount++;
	})
})
//添加系数据提交按钮
	$("#facultyDataAddSubmit").click(function(){
		var facultyAdminAddStr = "";
		var facultyName = $("#facultyNameAdd").val();
		for(var i = 0; i < facultyAdminCount; i++){
			var facultyAdmin = $(".facultyAdminAdd:eq("+i+")").val();
			if(facultyAdmin != ""){
			facultyAdminAddStr += "&admins=";
			facultyAdminAddStr += facultyAdmin;
			}
		}
		$("#facultyDataAddModal").modal("hide");
		var classType = 0;
		var fatherClassKey = -1;
		$.post("addBuuClass.action?className="+facultyName+"&classType="+classType+"&fatherClassKey="+fatherClassKey+facultyAdminAddStr,null,function(data){
			var jsonObj = eval('('+data+')');
			alert(jsonObj.returnMessage);
			facultyDataReload();
		})
		facultyAdminCount = 1;
	});


//添加专业数据按钮
$("#majorDataAddButton").click(function(){
		$("#majorAdminAddAgainDiv").empty();
		$.post("viewBuuFaculties.action",null,function(data){
			var facultyDataJsonObj = eval('('+data+')');
			$("#facultyListAdd").empty();
			for(var i = 0; facultyDataJsonObj.faculties[i] != null; i++){
				var facultyName = facultyDataJsonObj.faculties[i].className;
				var facultyPK = facultyDataJsonObj.faculties[i].PK_classKey;
				$("#facultyListAdd").append("<option value = '"+facultyPK+"'>"+facultyName+"</option>");
			}
		});

		$.post("getAdmins.action",null,function(data){
		var teacherDataJsonObj = eval('('+data+')');
		$("#majorAdminAdd").empty();
		for(var i = 0; teacherDataJsonObj.administrators[i] != null; i++) {
			if(teacherDataJsonObj.administrators[i].adminType >= 2){
			var teacherCode = teacherDataJsonObj.administrators[i].loginAccount;
			var teacherName = teacherDataJsonObj.administrators[i].name;
			$("#majorAdminAdd").append("<option value = '"+teacherCode+"'>"+teacherName+" "+teacherCode+"</option>");
			}
		}
		$("#majorDataAddModal").modal("show");
	});
});
//添加专业数据提交按钮
$("#majorDataAddSubmit").click(function(){
	var majorAdminAddStr = "";
	var majorName = $("#majorNameAdd").val();
	var classType = 1;
	var fatherClassKey = $("#facultyListAdd").val();
	for(var i = 0; i < majorAdminCount; i++){
		var majorAdmin = $(".majorAdminAdd:eq("+i+")").val();
		if(majorAdmin != ""){
			majorAdminAddStr += "&admins=";
			majorAdminAddStr += majorAdmin;
		}
	}
	$.post("addBuuClass.action?className="+majorName+"&classType="+classType+"&fatherClassKey="+fatherClassKey+majorAdminAddStr,null,function(data){
		var jsonObj = eval('('+data+')');
		alert(jsonObj.returnMessage);
		majorDataReload();
		$("#majorDataAddModal").modal("hide");
		majorAdminCount = 1;
	})
});
// 删除专业数据按钮
$("#majorDataDeleteButton").click(function(){
	var str="";
	var checkbox = $("#majorDataTable").find("input:checked");
	checkbox.each(function(){
		str += $(this).val();
		str += ":";
	});
	
	if(str != "")
	{
		$.get("deleteBuuClasses.action?PK_classKey="+str,null,function(data){
		var jsonObj = eval('('+data+')');
		alert(jsonObj.returnMessage);
		majorDataReload();
		});
	}
	else
	alert("您未选中任何项目");
});
//新增一个专业管理员
$("#majorAdminAddAgainSubmitButton").click(function(){
	$("#majorAdminAddAgainDiv").append("<div class = 'control-group'>"+
											"<label class = 'control-label' for = 'facultyAdminAdd'>专业管理员</label>"+
											"<div class = 'controls'>"+
												"<select name = 'majorAdminAdd' class = 'majorAdminAdd'>"+
												"</select>"+
											"</div>"+
											"</div>");
	$(".majorAdminAdd:eq("+majorAdminCount+")").append("<option value = ''>无</option>");
	$.post("getAdmins.action",null,function(data){
		var teacherDataJsonObj = eval('('+data+')');
	for(var i = 0; teacherDataJsonObj.administrators[i] != null; i++) {
		if(teacherDataJsonObj.administrators[i].adminType >= 2){
			var teacherCode = teacherDataJsonObj.administrators[i].loginAccount;
			var teacherName = teacherDataJsonObj.administrators[i].name;
			$(".majorAdminAdd:eq("+majorAdminCount+")").append("<option value = '"+teacherCode+"'>"+teacherName+" "+teacherCode+"</option>");
		}
	}
	majorAdminCount++;
	})
})
//添加班级按钮
$("#classDataAddButton").click(function(){
	$.post("viewBuuFaculties.action",null,function(data){
		var facultyDataJsonObj = eval('('+data+')');
		$("#facultiesListAdd_Class").empty();
		for(var i = 0; facultyDataJsonObj.faculties[i] != null; i++) {
			var facultyName = facultyDataJsonObj.faculties[i].className;
			var facultyPK = facultyDataJsonObj.faculties[i].PK_classKey;
			$("#facultiesListAdd_Class").append("<option value = '"+facultyPK+"'>"+facultyName+"</option>");
		}
		$("#majorsListAdd_Class").empty();
		var facultySelected_Class = $("#facultiesListAdd_Class").children("option:selected").val();
		$.post("viewBuuMajors.action?facultyKey="+facultySelected_Class,null,function(data){
				var majorDataJsonObj = eval('('+data+')');
				for(var i = 0; majorDataJsonObj.majors[i] != null; i++){
					var majorName = majorDataJsonObj.majors[i].className;
					var majorPK = majorDataJsonObj.majors[i].PK_classKey;
					$("#majorsListAdd_Class").append("<option value = '"+majorPK+"'>"+majorName+"</option>");
				}
		})
	})
	$("#facultiesListAdd_Class").unbind('change');
	$("#facultiesListAdd_Class").change(function(){
		$("#majorsListAdd_Class").empty();
		var facultySelected_Class = $("#facultiesListAdd_Class").children("option:selected").val();
		$.post("viewBuuMajors.action?facultyKey="+facultySelected_Class,null,function(data){
				var majorDataJsonObj = eval('('+data+')');
				for(var i = 0; majorDataJsonObj.majors[i] != null; i++){
					var majorName = majorDataJsonObj.majors[i].className;
					var majorPK = majorDataJsonObj.majors[i].PK_classKey;
					$("#majorsListAdd_Class").append("<option value = '"+majorPK+"'>"+majorName+"</option>");
				}
		})
	})
	$.post("getAdmins.action",null,function(data){
		var teacherDataJsonObj = eval('('+data+')');
		$("#classAdminAdd").empty();
		for(var i = 0; teacherDataJsonObj.administrators[i] != null && teacherDataJsonObj.administrators[i].adminType >= 2; i++) {
			var teacherCode = teacherDataJsonObj.administrators[i].loginAccount;
			var teacherName = teacherDataJsonObj.administrators[i].name;
			$("#classAdminAdd").append("<option value = '"+teacherCode+"'>"+teacherName+" "+teacherCode+"</option>");
		}
	})
	$("#classDataAddModal").modal("show");
})
//添加班级提交按钮
$("#classDataAddSubmit").click(function(){
	var className = $("#classNameAdd").val();
	var fatherClassKey = $("#majorsListAdd_Class").children("option:selected").val();
	var classType = 2;
	var classAdmin = $("#classAdminAdd").children("option:selected").val();
	$.post("addBuuClass.action?className="+className+"&classType="+classType+"&fatherClassKey="+fatherClassKey+"&admins="+classAdmin,null,function(data){
			var jsonObj = eval('('+data+')');
			alert(jsonObj.returnMessage);
			classDataReload();
			$("#classDataAddModal").modal("hide");
	})
})
// 删除班级数据按钮
$("#classDataDeleteButton").click(function(){
	var str="";
	var checkbox = $("#classDataTable").find("input:checked");
	checkbox.each(function(){
		str += $(this).val();
		str += ":";
	});
	
	if(str != "")
	{
		$.get("deleteBuuClasses.action?PK_classKey="+str,null,function(data){
		var jsonObj = eval('('+data+')');
		alert(jsonObj.returnMessage);
		classDataReload();
		});
	}
	else
	alert("您未选中任何项目");
});

})