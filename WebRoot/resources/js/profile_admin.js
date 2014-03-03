$(function(){
	var adminType;
	var name;
	var password;
	var PK_adminKey;
	$.get("viewAdminData.action?loginAccount="+$.cookie("userName"),null,function(data){
		var adminDataJsonObj = eval('('+data+')');
		name = adminDataJsonObj.administrator.name;
		var adminCode = $.cookie("userName");
		var email = adminDataJsonObj.administrator.email;
		var phone = adminDataJsonObj.administrator.phone;
		adminType = adminDataJsonObj.administrator.adminType;
		password = adminDataJsonObj.administrator.password;
		PK_adminKey = adminDataJsonObj.administrator.PK_adminKey;
		$("#name").html(name);
		$("#adminCode").html(adminCode);
		$("#email").val(email);
		$("#phoneNum").val(phone);
	});

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
		$.get("changeAdminPassword.action?loginAccount="+$.cookie("userName")+"&lastPassword="+lastPassword+"&newPassword="+newPassword2,null,function(data){
			var jsonObj = eval('('+data+')');
			alert(jsonObj.returnMessage);
		})
	}
});

$("#editProfileSubmit").click(function(){
	var email = $("#email").val();
	var phone = $("#phoneNum").val();
	$.get("updateAdmin.action?loginAccount="+$.cookie("userName")+"&email="+email+"&phone="+phone+"&adminType="+adminType+"&name="+name+"&password="+password+"&PK_adminKey="+PK_adminKey,null,function(data){
		var jsonObj = eval('('+data+')');
		alert(jsonObj.returnMessage);
		setTimeout("window.location.reload();",2000);
	})
})



})
