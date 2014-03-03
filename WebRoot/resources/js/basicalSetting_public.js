var firstResult = 1;
var maxResult = 20;

function pageChange(change){
	firstResult += change;
	teacherDataReload();
}
function maxResult(max){
	maxResult = max;
	teacherDataReload();
}




function teacherDataReload(){
	$.post("getAdminsInPagination.action?firstResult="+firstResult+"&maxResult="+maxResult,null,function(data){
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
	$.post("viewBuuFaculties.action",null,function(data){
		var facultyDataJsonObj = eval('('+data+')');
		$("#facultiesList").empty();
		for(var i = 0; facultyDataJsonObj.faculties[i] != null; i++) {
			var facultyName = facultyDataJsonObj.faculties[i].className;
			var facultyPK = facultyDataJsonObj.faculties[i].PK_classKey;
			$("#facultiesList").append("<option value = '"+facultyPK+"'>"+facultyName+"</option>");
		}
		var facultySelected = $("#facultiesList").children("option:selected").val();
		$.post("viewBuuMajors.action?facultyKey="+facultySelected,null,function(data){
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
				$("#majorDataTable").append("<tr>"+
										"<td><input type = 'checkbox' value = '"+majorPK+"' class = 'check'></td>"+
										"<td>"+majorName+"</td>"+
										"<td>"+majorAdminStr+"</td>"+
										"</tr>");
				majorAdminStr = "";
			}
		})
	})
}

function classDataReload(){
	$.post("viewBuuFaculties.action",null,function(data){
		var facultyDataJsonObj = eval('('+data+')');
		$("#facultiesList_Class").empty();
		for(var i = 0; facultyDataJsonObj.faculties[i] != null; i++) {
			var facultyName = facultyDataJsonObj.faculties[i].className;
			var facultyPK = facultyDataJsonObj.faculties[i].PK_classKey;
			$("#facultiesList_Class").append("<option value = '"+facultyPK+"'>"+facultyName+"</option>");
		}
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
}