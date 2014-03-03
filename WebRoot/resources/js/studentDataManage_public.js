//	var searchStr = "?name=-1&stuCode=-1&gender=-1&college=-1&faculty=-1&major=-1&grade=-1&classCode=-1&classAdmin2=-1&classAdmin3=-1&schoolingYear=-1&schoolRoll=-1&IDNum=-1&thnic=-1&birthday=-1&dormitoryCode=-1";
	var searchStr = new Array(16);
	var pageCount = 1;
	var pageMax = 30;
	searchStr['stuCode'] = "stuCode=*";
	searchStr['name'] = "name=*";
	searchStr['gender'] = "gender=*";
//	searchStr['college'] = "college=-1";
	searchStr['faculty'] = "faculty=-1";
	searchStr['major'] = "major=-1";
//	searchStr['grade'] = "grade=-1";
	searchStr['classCode'] = "classCode=-1";
	searchStr['firstResult'] = "firstResult="+pageCount;
	searchStr['maxResult'] = "maxResult="+pageMax;
//	searchStr['classAdmin2'] = "classAdmin2=-1";
//	searchStr['classAdmin3'] = "classAdmin3=-1";
//	searchStr['schoolingYear'] = "schoolingYear=-1";
//	searchStr['schoolRoll'] = "schoolRoll=-1";
//	searchStr['IDNum'] = "IDNum=-1";
//	searchStr['thnic'] = "thnic=-1";
//	searchStr['birthday'] = "birthday=-1";
//	searchStr['dormitoryCode'] = "dormitoryCode=-1";
function pageChange(change){
	pageCount += change;
	searchStr['firstResult'] = "firstResult="+pageCount;
	loadStuDataTable();
}
function maxResult(max){
	pageMax = max;
	searchStr['maxResult'] = "maxResult="+pageMax;
	loadStuDataTable();
}
function viewStuCode(){
	var stuCode = $("#theadStuCode").val();
	if(stuCode == "")
	{
		searchStr['stuCode'] = "stuCode=*";
		loadStuDataTable();
	}
	else
	{
		searchStr['stuCode'] = "stuCode="+stuCode;
		loadStuDataTable();
	}
}

function viewStuName(){
	var stuName = $("#theadStuName").val();
	if(stuName == "")
	{
		searchStr['name'] = "name=*";
		loadStuDataTable();
	}
	else
	{
		searchStr['name'] = "name="+stuName;
		loadStuDataTable();
	}
}

function viewIDNum(){
	var IDNum = $("#theadIDNum").val();
	if(IDNum == "")
	{
		searchStr['IDNum'] = "IDNum=-1";
		loadStuDataTable();
	}
	else
	{
		searchStr['IDNum'] = "IDNum="+IDNum;
		loadStuDataTable();
	}
}

function viewGender(gender){
	if(gender == "*")
	{
		$("#theadGender").html("性别<span class='caret'></span>");
		searchStr['gender'] = "gender=*";
		loadStuDataTable();
	}
	else
	{
		$("#theadGender").html(gender+"<span class='caret'></span>");
		searchStr['gender'] = "gender="+gender;
		loadStuDataTable();
	}
}

function viewFaculty(facultyPK,facultyName){
	if(facultyPK == "*")
	{
		$("#theadFaculty").html("系别<span class='caret'></span>");
		searchStr['faculty'] = "faculty=-1";
		loadStuDataTable();
		$("#majorsDropDown").empty();
		$("#majorsDropDown").html("<li><a href = 'javascript:viewMajor('-1');'>全部</a></li>");
	}
	else
	{
		$("#theadFaculty").html(facultyName+"<span class='caret'></span>");
		searchStr['faculty'] = "faculty="+facultyPK;
		appendMajorsDropDown("#majorsDropDown",facultyPK);
		loadStuDataTable();
	}
}

function viewMajor(majorPK,majorName){
	if(majorPK == "*")
	{
		$("#theadMajor").html("专业<span class='caret'></span>");
		searchStr['major'] = "major=-1";
		loadStuDataTable();
		$("#classesDropDown").empty();
		$("#classesDropDown").html("<li><a href = 'javascript:viewClass('-1');'>全部</a></li>");
	}
	else
	{
		$("#theadMajor").html(majorName+"<span class='caret'></span>");
		searchStr['major'] = "major="+majorPK;
		appendClassDropDown("#classesDropDown",majorPK);
		loadStuDataTable();
	}
}

function viewClass(classCode,className){
	if(classCode == "*")
	{
		$("#theadClass").html("班级<span class='caret'></span>");
		searchStr['classCode'] = "classCode=-1";
		loadStuDataTable();
	}
	else
	{
		$("#theadClass").html(className+"<span class='caret'></span>");
		searchStr['classCode'] = "classCode="+classCode;
		loadStuDataTable();
	}
}

function viewGrade(grade){
	if(grade == "-1")
	{
		$("#theadGrade").html("年级<span class='caret'></span>");
		searchStr['grade'] = "grade=-1";
		loadStuDataTable();
	}
	else
	{
		$("#theadGrade").html(grade+"<span class='caret'></span>");
		searchStr['grade'] = "grade="+grade;
		loadStuDataTable();
	}
}

function viewSchoolingYear(schoolingYear){
	if(schoolingYear == "-1")
	{
		$("#theadSchoolingYear").html("学制<span class='caret'></span>");
		searchStr['schoolingYear'] = "schoolingYear=-1";
		loadStuDataTable();
	}
	else
	{
		$("#theadSchoolingYear").html(schoolingYear+"<span class='caret'></span>");
		searchStr['schoolingYear'] = "schoolingYear="+schoolingYear;
		loadStuDataTable();
	}
}

function viewSchooRoll(schoolRoll){
	if(schoolRoll == "-1")
	{
		$("#theadSchoolRoll").html("学籍状态<span class='caret'></span>");
		searchStr['schoolRoll'] = "schoolRoll=-1";
		loadStuDataTable();
	}
	else
	{
		$("#theadSchoolRoll").html(schoolRoll+"<span class='caret'></span>");
		searchStr['schoolRoll'] = "schoolRoll="+schoolRoll;
		loadStuDataTable();
	}
}

function viewThnic(thnic){
	if(thnic == "-1")
	{
		$("#theadThnic").html("民族<span class='caret'></span>");
		searchStr['thnic'] = "thnic=-1";
		loadStuDataTable();
	}
	else
	{
		$("#theadThnic").html(thnic+"<span class='caret'></span>");
		searchStr['thnic'] = "thnic="+thnic;
		loadStuDataTable();
	}
}


//加载学生数据
function loadStuDataTable(){
	var searchStrs = "";
	for(var key in searchStr)
	{

		searchStrs += searchStr[key];
		searchStrs += "&";
	}
$.get("searchStudentsInPagination.action?"+searchStrs,null,function(data){
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
	}); //end of bind

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
	}); //end of bind

	$("#studentDataTable").append("<tr><td colspan = '7'><div class = 'btn-group' data-toggle='buttons-radio'>"+
															"<button class = 'btn' onclick = 'javascript:pageChange(-1)'>上一页</button>"+
															"<button class = 'btn' onclick = 'javascript:maxResult(30);'>30</button>"+
															"<button class = 'btn' onclick = 'javascript:maxResult(50);'>50</button>"+
															"<button class = 'btn' onclick = 'javascript:maxResult(100);'>100</button>"+
															"<button class = 'btn' onclick = 'javascript:pageChange(+1)'>下一页</button>"+
														"</div></td></tr>");
});//end of get
}//end of function