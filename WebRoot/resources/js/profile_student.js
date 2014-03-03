$(function(){
	$.get("viewStudent.action?stuCode="+ $.cookie("userName"),null,function(data){
		var getStudentDataJsonObj = eval('('+data+')');
			var PK_stuKey = getStudentDataJsonObj.student.PK_stuKey; //主键
			var name = getStudentDataJsonObj.student.name;	//姓名
			var stuCode = getStudentDataJsonObj.student.stuCode;  //学号
			var thnic = getStudentDataJsonObj.student.thnic;  //民族
			var schoolRoll = getStudentDataJsonObj.student.schoolRoll;	//学籍状态
			var schoolingYear = getStudentDataJsonObj.student.schoolingYear;	//学制
			var gender = getStudentDataJsonObj.student.gender;	//性别
			var IDNum = getStudentDataJsonObj.student.IDNum;	//身份证号
			var dormitoryCode = getStudentDataJsonObj.student.dormitoryCode;	//宿舍号
			var classCode = getStudentDataJsonObj.student.classInfo.className;	//班级
			var classAdmin2 = "";
			if(getStudentDataJsonObj.student.classInfo.admins[0].adminType >= 2)
				classAdmin2 = getStudentDataJsonObj.student.classInfo.admins[0].name;
			if(getStudentDataJsonObj.student.classInfo.admins[1].adminType >= 2)		//班主任
				classAdmin2 = getStudentDataJsonObj.student.classInfo.admins[1].name;
			var classPK = getStudentDataJsonObj.student.classInfo.PK_classKey;	//班级
			var college = getStudentDataJsonObj.student.college;	//学院
			var faculty = getStudentDataJsonObj.student.facultyInfo.className;	//系别
			var facultyPK = getStudentDataJsonObj.student.facultyInfo.PK_classKey; //系别 主键
			var major = getStudentDataJsonObj.student.majorInfo.className;	//专业
			var majorPK = getStudentDataJsonObj.student.majorInfo.PK_classKey; //专业 主键
			var grade = getStudentDataJsonObj.student.grade;	//年级
			var classAdmin3 = getStudentDataJsonObj.student.counselor;	//辅导员
			var birthday = getStudentDataJsonObj.student.birthday;	//生日
			var phone = getStudentDataJsonObj.student.phone;	//手机号
		$("#stuName").html(name);
		$("#stuCode").html(stuCode);
		$("#gender").html(gender);
		$("#college").html(college);
		$("#faculty").html(faculty);
		$("#major").html(major);
		$("#grade").html(grade);
		$("#classCode").html(classCode);
		$("#classAdmin2").html(classAdmin2);
		$("#classAdmin3").html(classAdmin3);
		$("#schoolingYear").html(schoolingYear);
		$("#schoolRoll").html(schoolRoll);
		$("#thnic").html(thnic);
		$("#dormitoryCode").html(dormitoryCode);
		$("#IDNum").html(IDNum);
		$("#birthday").html(birthday);
		$("#phoneNum").html(phone);
	});

$.get("viewAllOffersInPagination.action?firstResult=1&maxResult=15",null,function(data){
		$("#offerDataTable").empty();
		var tableHtml = "";
		var jsonObj = eval('('+data+')');
		for(var i = 0; jsonObj.offers[i] != null; i++){
			var offerName = jsonObj.offers[i].offerName;
			var offerUrl = jsonObj.offers[i].offerUrl;
			tableHtml += "<tr><td>"+offerName+"</td>";
			tableHtml += "<td><a href = '"+offerUrl+"'>查看</a></td></tr>";
		}
		$("#offerDataTable").append(tableHtml);
})

$("#newPassword1").blur(function(){
	var newPassword1 = $("#newPassword1").val();
	if(newPassword1.length < 6){
		$("#passwordCheck").html("<font color = 'red'>密码少于6位</font>");
	}
	else{
		$("#passwordCheck").html();
	}

})
$("#newPassword2").blur(function(){
	var newPassword1 = $("#newPassword1").val();
	var newPassword2 = $("#newPassword2").val();
	if(newPassword2 != newPassword1){
		$("#passwordCheck").html("<font color = 'red'>两次输入密码不同</font>");

	}
	else if(newPassword2 == newPassword1){
		$("#passwordCheck").html();
	}
})

$("#updatePasswordSubmit").click(function(){
	var lastPassword = $("#lastPassword").val();
	var newPassword1 = $("#newPassword1").val();
	var newPassword2 = $("#newPassword2").val();
	if(newPassword2.length <6){
		alert("新密码少于6位，请重新输入");
	}
	else if(newPassword1 != newPassword2)
		alert("两次输入密码不同，请重新输入");
	else{
		$.get("changeStudentPassword.action?stuCode="+$.cookie("userName")+"&lastPassword="+lastPassword+"&newPassword="+newPassword2,null,function(data){
			var jsonObj = eval('('+data+')');
			alert(jsonObj.returnMessage);
			setTimeout("window.location.reload();",2000);
		})
	}
});

})

var firstPage = 1;

function viewOfferData(){
	$.get("viewAllOffersInPagination.action?firstResult="+firstPage+"&maxResult=15",null,function(data){
		$("#offerDataTable").empty();
		var tableHtml = "";
		var jsonObj = eval('('+data+')');
		for(var i = 0; jsonObj.offers[i] != null; i++){
			var offerName = jsonObj.offers[i].offerName;
			var offerUrl = jsonObj.offers[i].offerUrl;
			tableHtml += "<tr><td>"+offerName+"</td>";
			tableHtml += "<td><a href = '/SDMS"+offerUrl+"'>查看</a></td></tr>";
		}
		$("#offerDataTable").append(tableHtml);
	})
}

function pageChange(change){
	firstPage += change;
	viewOfferData();
}