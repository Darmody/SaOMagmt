$(function(){

$("#studentDataTable thead tr").find("th:eq(6)").hide();
$("#studentDataTable thead tr").find("th:eq(8)").hide();
$("#studentDataTable thead tr").find("th:eq(9)").hide();
$("#studentDataTable thead tr").find("th:eq(10)").hide();
$("#studentDataTable thead tr").find("th:eq(11)").hide();
$("#studentDataTable thead tr").find("th:eq(12)").hide();
$("#studentDataTable thead tr").find("th:eq(13)").hide();
$("#studentDataTable thead tr").find("th:eq(14)").hide();
$("#studentDataTable thead tr").find("th:eq(15)").hide();
$(".hideDiv").hide();

$("#facultyAdd").change(function(){
//更新专业列表
	var fatherClassKey = $("#facultyAdd").val();
	appendMajorsList("#majorAdd",fatherClassKey);
//更新班级列表
	var fatherClassKey = $("#majorAdd").val();
	appendClassList("#classCodeAdd",fatherClassKey);
//更新班主任
	var classAdmin = $("#classCodeAdd").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Add").val(classAdmin);
})

$("#majorAdd").change(function(){
//更新班级列表
	var fatherClassKey = $("#majorAdd").val();
	appendClassList("#classCodeAdd",fatherClassKey);
//更新班主任
	var classAdmin = $("#classCodeAdd").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Add").val(classAdmin);
})

$("#classCodeAdd").change(function(){
//更新班主任
	var classAdmin = $("#classCodeAdd").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Add").val(classAdmin);
})


$("#facultyEdit").change(function(){
//更新专业列表
	var fatherClassKey = $("#facultyEdit").val();
	appendMajorsList("#majorEdit",fatherClassKey);
//更新班级列表
	var fatherClassKey_major = $("#majorEdit").val();
	appendClassList("#classCodeEdit",fatherClassKey_major);
//更新班主任
	var classAdmin = $("#classCodeEdit").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Edit").val(classAdmin);
})
$("#majorEdit").change(function(){
//更新班级列表
	var fatherClassKey = $("#majorEdit").val();
	appendClassList("#classCodeEdit",fatherClassKey);
//更新班主任
	var classAdmin = $("#classCodeEdit").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Edit").val(classAdmin);
})
$("#classCodeEdit").change(function(){
//更新班主任
	var classAdmin = $("#classCodeEdit").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Edit").val(classAdmin);
})


function viewStuDataTable () {
	// body...
$.get("viewStudentsInPagination.action?firstResult=1&maxResult=30",null,function(data){
	var htmlStr = "";
var getStudentDataJsonObj = eval('('+data+')');
$("#studentDataTable tbody").empty();
for(var i = 0; getStudentDataJsonObj.students[i] != null; i++) {
	
	var PK_stuKey = getStudentDataJsonObj.students[i].PK_stuKey; //主键
	var name = getStudentDataJsonObj.students[i].name;	//姓名
	var stuCode = getStudentDataJsonObj.students[i].stuCode;  //学号
	var thnic = getStudentDataJsonObj.students[i].thnic;  //民族
	var schoolRoll = getStudentDataJsonObj.students[i].schoolRoll;	//学籍状态
	var schoolingYear = getStudentDataJsonObj.students[i].schoolingYear;	//学制
	var gender = getStudentDataJsonObj.students[i].gender;	//性别
	var IDNum = getStudentDataJsonObj.students[i].IDNum;	//身份证号
	var dormitoryCode = getStudentDataJsonObj.students[i].dormitoryCode;	//宿舍号
	var classCode = getStudentDataJsonObj.students[i].classInfo.className;	//班级
	var classAdmin = "";
	if(getStudentDataJsonObj.students[i].classInfo.admins[0].adminType >= 2)
		classAdmin = getStudentDataJsonObj.students[i].classInfo.admins[0].name;
	if(getStudentDataJsonObj.students[i].classInfo.admins[1].adminType >= 2)		//班主任
		classAdmin = getStudentDataJsonObj.students[i].classInfo.admins[1].name;
	var classPK = getStudentDataJsonObj.students[i].classInfo.PK_classKey;	//班级
	var college = getStudentDataJsonObj.students[i].college;	//学院
	var faculty = getStudentDataJsonObj.students[i].facultyInfo.className;	//系别
	var facultyPK = getStudentDataJsonObj.students[i].facultyInfo.PK_classKey; //系别 主键
	var major = getStudentDataJsonObj.students[i].majorInfo.className;	//专业
	var majorPK = getStudentDataJsonObj.students[i].majorInfo.PK_classKey; //专业 主键
	var grade = getStudentDataJsonObj.students[i].grade;	//年级
	var classAdmin2 = "";	//班主任
	var classAdmin3 = getStudentDataJsonObj.students[i].counselor;	//辅导员
	var birthday = getStudentDataJsonObj.students[i].birthday;	//生日
	var phone = getStudentDataJsonObj.students[i].phone;	//手机号
	var trId = i + 1;

	htmlStr += "<tr>"+
									"<td><input type = 'checkbox' value = '"+PK_stuKey+"' class = 'editId'/></td>"+
									"<td>"+stuCode+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+name+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+gender+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+faculty+"<input type = 'hidden' value = '"+trId+"' class = 'trId'><input type = 'hidden' value = '"+facultyPK+"' class = 'facultyPK'></td>"+
									"<td>"+major+"<input type = 'hidden' value = '"+trId+"' class = 'trId'><input type = 'hidden' value = '"+majorPK+"' class = 'majorPK'></td>"+
									"<td>"+grade+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+classCode+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td><input type = 'hidden' value = '"+classPK+"' class = 'classPK'></td>"+
									"<td>"+classAdmin2+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+classAdmin3+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+schoolingYear+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+schoolRoll+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+IDNum+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+thnic+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+birthday+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+dormitoryCode+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+classAdmin+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+phone+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"</tr>";
	

$("#studentDataTable tbody tr").find("td:eq(6)").hide();
$("#studentDataTable tbody tr").find("td:eq(8)").hide();
$("#studentDataTable tbody tr").find("td:eq(9)").hide();
$("#studentDataTable tbody tr").find("td:eq(10)").hide();
$("#studentDataTable tbody tr").find("td:eq(11)").hide();
$("#studentDataTable tbody tr").find("td:eq(12)").hide();
$("#studentDataTable tbody tr").find("td:eq(13)").hide();
$("#studentDataTable tbody tr").find("td:eq(14)").hide();
$("#studentDataTable tbody tr").find("td:eq(15)").hide();
$("#studentDataTable tbody tr").find("td:eq(16)").hide();
$("#studentDataTable tbody tr").find("td:eq(17)").hide();




}//end of for
	$("#studentDataTable").append(htmlStr);
	$("#studentDataTable tbody tr").find("td:eq(6)").hide();
	$("#studentDataTable tbody tr").find("td:eq(8)").hide();
	$("#studentDataTable tbody tr").find("td:eq(9)").hide();
	$("#studentDataTable tbody tr").find("td:eq(10)").hide();
	$("#studentDataTable tbody tr").find("td:eq(11)").hide();
	$("#studentDataTable tbody tr").find("td:eq(12)").hide();
	$("#studentDataTable tbody tr").find("td:eq(13)").hide();
	$("#studentDataTable tbody tr").find("td:eq(14)").hide();
	$("#studentDataTable tbody tr").find("td:eq(15)").hide();
	$("#studentDataTable tbody tr").find("td:eq(16)").hide();
	$("#studentDataTable tbody tr").find("td:eq(17)").hide();

// 给tr绑定click事件 
	$("#studentDataTable tr:gt(0) td:first-child").nextAll("td").bind("click",function(){
		$("#stuDataEditModal").modal("show");
		var trId = $(this).find(".trId").val();
		var fatherClassKey_faculty = $("#studentDataTable").find("tr").eq(trId).find("td:eq(4)").find(".facultyPK").val();
		var fatherClassKey_major = $("#studentDataTable").find("tr").eq(trId).find("td:eq(5)").find(".majorPK").val();
		var fatherClassKey_class = $("#studentDataTable").find("tr").eq(trId).find("td:eq(7)").find(".classPK").val();
		appendFacultiesList("#facultyEdit",fatherClassKey_faculty);
		$("#editId").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(0).find(".editId").val());
		$("#stuCodeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(1).text());
		$("#stuNameEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(2).text());
		$("#genderEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(3).text());
//		$("#collegeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(4).text());
//		$("#facultyEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(4).text());

		appendMajorsList("#majorEdit",fatherClassKey_faculty,fatherClassKey_major);

//		$("#majorEdit").val(fatherClassKey_major);
		$("#gradeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(6).text());

		appendClassList("#classCodeEdit",fatherClassKey_major,fatherClassKey_class);

//		$("#classCodeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(7).text());
		$("#classAdmin2Edit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(8).text());
		$("#classAdmin3Edit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(9).text());
		$("#schoolingYearEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(10).text());
		$("#schoolRollEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(11).text());
		$("#IDNumEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(12).text());
		$("#thnicEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(13).text());
		$("#birthdayEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(14).text());
		$("#dormitoryCodeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(15).text());
		$("#classAdmin2Edit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(16).text());
		$("#phoneEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(17).text());
});//end of bind

	$("#studentDataTable").append("<tr><td colspan = '7'><div class = 'btn-group' data-toggle='buttons-radio'>"+
															"<button class = 'btn' onclick = 'javascript:pageChange(-1)'>上一页</button>"+
															"<button class = 'btn' onclick = 'javascript:maxResult(30);'>30</button>"+
															"<button class = 'btn' onclick = 'javascript:maxResult(50);'>50</button>"+
															"<button class = 'btn' onclick = 'javascript:maxResult(100);'>100</button>"+
															"<button class = 'btn' onclick = 'javascript:pageChange(+1)'>下一页</button>"+
														"</div></td></tr>");
});// end of get
}//end of function

viewStuDataTable();

//添加数据按钮	
$("#stuDataAddButton").click(function(){
$("#stuDataAddModal").modal("show");
appendFacultiesList("#facultyAdd");
//更新班主任
	var classAdmin = $("#classCodeAdd").children("option:selected").attr("data-classAdmin");
	$("#classAdmin2Add").val(classAdmin);
});

// 添加数据提交按钮 
$("#stuDataAddSubmit").click(function(){
var stuCodeAdd = $("#stuCodeAdd").val();
var stuNameAdd = $("#stuNameAdd").val();
var genderAdd = $("#genderAdd").val();
var collegeAdd = $("#collegeAdd").val();
var facultyAdd = $("#facultyAdd").val();
var majorAdd = $("#majorAdd").val();
var gradeAdd = $("#gradeAdd").val();
var classCodeAdd = $("#classCodeAdd").val();
var classAdmin2Add = $("#classAdmin2Add").val();
var classAdmin3Add = $("#classAdmin3Add").val();
var schoolingYearAdd = $("#schoolingYearAdd").val();
var schoolRollAdd = $("#schoolRollAdd").val();
var IDNumAdd = $("#IDNumAdd").val();
var thnicAdd = $("#thnicAdd").val();
var birthdayAdd = $("#birthdayAdd").val();
var dormitoryCodeAdd = $("#dormitoryCodeAdd").val();
var phoneAdd = $("#phoneAdd").val();
$.post("addStudent.action?stuCode="+stuCodeAdd+"&name="+stuNameAdd+"&gender="+genderAdd+"&college="+collegeAdd+"&faculty="+facultyAdd+"&major="+majorAdd+"&grade="+gradeAdd+"&classCode="+classCodeAdd+"&classAdmin2="+classAdmin2Add+"&classAdmin3="+classAdmin3Add+"&schoolingYear="+schoolingYearAdd+"&schoolRoll="+schoolRollAdd+"&IDNum="+IDNumAdd+"&thnic="+thnicAdd+"&birthday="+birthdayAdd+"&dormitoryCode="+dormitoryCodeAdd+"&phone="+phoneAdd,null,function(data){
var jsonObj = eval('('+data+')');

alert(jsonObj.returnMessage);
viewStuDataTable();
$("#stuDataAddModal").modal("hide");
});
});

// 编辑数据提交按钮 
$("#stuDataEditSubmit").click(function(){
var editStuID = $("#editId").val();
var stuCodeEdit = $("#stuCodeEdit").val();
var stuNameEdit = $("#stuNameEdit").val();
var genderEdit = $("#genderEdit").val();
var collegeEdit = $("#collegeEdit").val();
var facultyEdit = $("#facultyEdit").val();
var majorEdit = $("#majorEdit").val();
var gradeEdit = $("#gradeEdit").val();
var classCodeEdit = $("#classCodeEdit").val();
var classAdmin2Edit = $("#classAdmin2Edit").val();
var classAdmin3Edit = $("#classAdmin3Edit").val();
var schoolingYearEdit = $("#schoolingYearEdit").val();
var schoolRollEdit = $("#schoolRollEdit").val();
var IDNumEdit = $("#IDNumEdit").val();
var thnicEdit = $("#thnicEdit").val();
var birthdayEdit = $("#birthdayEdit").val();
var dormitoryCodeEdit = $("#dormitoryCodeEdit").val();
var phoneEdit = $("#phoneEdit").val();
$.post("updateStudent.action?PK_stuKey="+editStuID+"&stuCode="+stuCodeEdit+"&name="+stuNameEdit+"&gender="+genderEdit+"&college="+collegeEdit+"&faculty="+facultyEdit+"&major="+majorEdit+"&grade="+gradeEdit+"&classCode="+classCodeEdit+"&classAdmin2="+classAdmin2Edit+"&classAdmin3="+classAdmin3Edit+"&schoolingYear="+schoolingYearEdit+"&schoolRoll="+schoolRollEdit+"&IDNum="+IDNumEdit+"&thnic="+thnicEdit+"&birthday="+birthdayEdit+"&dormitoryCode="+dormitoryCodeEdit+"&phone="+phoneEdit,null,function(data){
var jsonObj = eval('('+data+')');

alert(jsonObj.returnMessage);
viewStuDataTable();
$("#stuDataEditModal").modal("hide");
});
});

// 删除按钮
$("#stuDataDeleteButton").click(function(){
var str="";
var checkbox = $("#studentDataTable").find("input:checked");

checkbox.each(function(){
	str += $(this).val();
	str += ":";
});
if(str != "")
{

$.get("deleteStudents.action?PK_stuKey="+str,null,function(data){

var jsonObj = eval('('+data+')');

alert(jsonObj.returnMessage);
viewStuDataTable();
});
}
else
alert("您未选中任何项目");
});
//全选
$("#checkButton").click(function(){
	$("input[type='checkbox']").each(function(){
		this.checked = !this.checked;
	})
})
//批量添加
$("#bulkAddButton").click(function(){
	$("#bulkAddModal").modal("show");
})

$("#bulkAddSubmit").click(function(data){
	$("#bulkAddForm").ajaxSubmit();
	$("#bulkAddModal").modal("hide");
	var jsonObj = eval('('+data+')');
	alert(jsonObj.returnMessage);
})

$("#bulkAddModal").on("hidden",function(){
	viewStuDataTable();
})


});