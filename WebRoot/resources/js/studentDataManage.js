$(function(){

function viewStuDataTable () {
	// body...
$.get("viewStudents.action",null,function(data){
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
	var classCode = getStudentDataJsonObj.students[i].classCode;	//班级
	var college = getStudentDataJsonObj.students[i].college;	//学院
	var faculty = getStudentDataJsonObj.students[i].faculty;	//系别
	var major = getStudentDataJsonObj.students[i].major;	//专业
	var grade = getStudentDataJsonObj.students[i].grade;	//年级
	var classAdmin2 = getStudentDataJsonObj.students[i].classAdmin2.name;	//班主任
	var classAdmin3 = getStudentDataJsonObj.students[i].classAdmin3.name;	//辅导员
	var birthday = getStudentDataJsonObj.students[i].birthday;	//生日
	var trId = i + 1;
	$("#studentDataTable").append("<tr>"+
									"<td><input type = 'checkbox' value = '"+PK_stuKey+"' class = 'editId'/></td>"+
									"<td>"+stuCode+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+name+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+gender+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+faculty+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+major+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+grade+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+classCode+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+classAdmin2+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+classAdmin3+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+schoolingYear+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+schoolRoll+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+IDNum+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+thnic+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+birthday+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"<td>"+dormitoryCode+"<input type = 'hidden' value = '"+trId+"' class = 'trId'></td>"+
									"</tr>");
	
	// 给tr绑定click事件 
	$("#studentDataTable tr:gt(0) td:first-child").nextAll("td").bind("click",function(){
		$("#stuDataEditModal").modal("show");
		var trId = $(this).find(".trId").val();
		$("#editId").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(0).find(".editId").val());
		$("#stuCodeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(1).text());
		$("#stuNameEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(2).text());
		$("#genderEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(3).text());
//		$("#collegeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(4).text());
		$("#facultyEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(4).text());
		$("#majorEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(5).text());
		$("#gradeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(6).text());
		$("#classCodeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(7).text());
		$("#classAdmin2Edit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(8).text());
		$("#classAdmin3Edit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(9).text());
		$("#schoolingYearEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(10).text());
		$("#schoolRollEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(11).text());
		$("#IDNumEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(12).text());
		$("#thnicEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(13).text());
		$("#birthdayEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(14).text());
		$("#dormitoryCodeEdit").val($("#studentDataTable").find("tr").eq(trId).find("td").eq(15).text());
});

}	
});
}

viewStuDataTable();

//添加数据按钮	
$("#stuDataAddButton").click(function(){
$("#stuDataAddModal").modal("show");
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
$.post("addStudent.action?stuCode="+stuCodeAdd+"&name="+stuNameAdd+"&gender="+genderAdd+"&college="+collegeAdd+"&faculty="+facultyAdd+"&major="+majorAdd+"&grade="+gradeAdd+"&classCode="+classCodeAdd+"&classAdmin2="+classAdmin2Add+"&classAdmin3="+classAdmin3Add+"&schoolingYear="+schoolingYearAdd+"&schoolRoll="+schoolRollAdd+"&IDNum="+IDNumAdd+"&thnic="+thnicAdd+"&birthday="+birthdayAdd+"&dormitoryCode="+dormitoryCodeAdd,null,function(data){
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
$.post("updateStudent.action?PK_stuKey="+editStuID+"&stuCode="+stuCodeEdit+"&name="+stuNameEdit+"&gender="+genderEdit+"&college="+collegeEdit+"&faculty="+facultyEdit+"&major="+majorEdit+"&grade="+gradeEdit+"&classCode="+classCodeEdit+"&classAdmin2="+classAdmin2Edit+"&classAdmin3="+classAdmin3Edit+"&schoolingYear="+schoolingYearEdit+"&schoolRoll="+schoolRollEdit+"&IDNum="+IDNumEdit+"&thnic="+thnicEdit+"&birthday="+birthdayEdit+"&dormitoryCode="+dormitoryCodeEdit,null,function(data){
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

});