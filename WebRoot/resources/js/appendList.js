 $.ajaxSetup({ 
    async : false 
}); 

function appendFacultiesList(jQueryObj,key){
	$(jQueryObj).empty();
	$.post("viewBuuFaculties.action",null,function(data){
		var facultiesDatajsonObj = eval('('+data+')');
		for(var i = 0; facultiesDatajsonObj.faculties[i] != null; i++){
			var facultyName = facultiesDatajsonObj.faculties[i].className;
			var facultyPK = facultiesDatajsonObj.faculties[i].PK_classKey;
			if(i == 0)	fatherClassKey = facultyPK;
			$(jQueryObj).append("<option value = '"+facultyPK+"'>"+facultyName+"</option>");
		}
		if(jQueryObj === "#facultyAdd")
			appendMajorsList("#majorAdd",fatherClassKey);
		else if(jQueryObj == "#facultyEdit" && key != null){
			$(jQueryObj).val(key);
		}
	})
}

function appendMajorsList(jQueryObj,fatherClassKey,key){
	$(jQueryObj).empty();
	$.post("viewBuuMajors.action?facultyKey="+fatherClassKey,null,function(data){
		var majorsDatajsonObj = eval('('+data+')');
		for(var i = 0; majorsDatajsonObj.majors[i] != null; i++){
			var majorName = majorsDatajsonObj.majors[i].className;
			var majorPK = majorsDatajsonObj.majors[i].PK_classKey;
			if(i == 0)	fatherClassKey = majorPK;
			$(jQueryObj).append("<option value = '"+majorPK+"'>"+majorName+"</option>");
		}
		if(jQueryObj == "#majorAdd")
			appendClassList("#classCodeAdd",fatherClassKey);
		else if(jQueryObj == "#majorEdit" && key != null){
			$(jQueryObj).val(key);
		}
	})
}

function appendTeachersList(jQueryObj){
	$(jQueryObj).empty();
	$.post("getAdmins.action",null,function(data){
		var teachersDataJsonObj = eval('('+data+')');
		for(var i = 0; teachersDataJsonObj.administrators[i] != null; i++){
			var teacherName = teachersDataJsonObj.administrators[i].name;
			var teacherLoginAccount = teachersDataJsonObj.administrators[i].loginAccount;
			$(jQueryObj).append("<option value = '"+teacherLoginAccount+"'>"+teacherName+" " +teacherLoginAccount+"</option>");
		}
	})
}

function appendClassList(jQueryObj,fatherClassKey,key){
	$(jQueryObj).empty();
	$.post("viewBuuClasses.action?majorKey="+fatherClassKey,null,function(data){
		var classDataJson = eval('('+data+')');
		for(var i = 0; classDataJson.classes[i] != null; i++){
			var className = classDataJson.classes[i].className;
			var classPK = classDataJson.classes[i].PK_classKey;
			var classAdmin = "";
			if(classDataJson.classes[i].admins[0].adminType >= 2) classAdmin = classDataJson.classes[i].admins[0].name;
			if(classDataJson.classes[i].admins[1].adminType >= 2) classAdmin = classDataJson.classes[i].admins[1].name;
			$(jQueryObj).append("<option value = '"+classPK+"' data-classAdmin = '"+classAdmin+"'>"+className+"</option>");
		}
		if(jQueryObj == "#classCodeEdit" && key != null)
			$(jQueryObj).val(key);
	})
}

function appendFacultiesDropDown(jQueryObj){
	$(jQueryObj).empty();
	$(jQueryObj).append("<li><a href = 'javascript:viewFaculty(\"*\");'>"+"全部"+"</a></li>");
	$.post("viewBuuFaculties.action",null,function(data){
		var facultiesDatajsonObj = eval('('+data+')');
		for(var i = 0; facultiesDatajsonObj.faculties[i] != null; i++){
			var facultyName = facultiesDatajsonObj.faculties[i].className;
			var facultyPK = facultiesDatajsonObj.faculties[i].PK_classKey;
			$(jQueryObj).append("<li><a href = 'javascript:viewFaculty(\""+facultyPK+"\",\""+facultyName+"\");'>"+facultyName+"</a></li>");
		}
	})
}

function appendMajorsDropDown(jQueryObj,fatherClassKey){
	$(jQueryObj).empty();
	$(jQueryObj).append("<li><a href = 'javascript:viewMajor(\"*\");'>"+"全部"+"</a></li>");
	$.post("viewBuuMajors.action?facultyKey="+fatherClassKey,null,function(data){
		var majorsDatajsonObj = eval('('+data+')');
		for(var i = 0; majorsDatajsonObj.majors[i] != null; i++){
			var majorName = majorsDatajsonObj.majors[i].className;
			var majorPK = majorsDatajsonObj.majors[i].PK_classKey;
			$(jQueryObj).append("<li><a href = 'javascript:viewMajor(\""+majorPK+"\",\""+majorName+"\");'>"+majorName+"</a></li>");
		}
	})
}

function appendClassDropDown(jQueryObj,fatherClassKey){
	$(jQueryObj).empty();
	$(jQueryObj).append("<li><a href = 'javascript:viewClass(\"*\");'>"+"全部"+"</a></li>");
	$.post("viewBuuClasses.action?majorKey="+fatherClassKey,null,function(data){
		var classDataJson = eval('('+data+')');
		for(var i = 0; classDataJson.classes[i] != null; i++){
			var className = classDataJson.classes[i].className;
			var classPK = classDataJson.classes[i].PK_classKey;
			$(jQueryObj).append("<li><a href = 'javascript:viewClass(\""+classPK+"\",\""+className+"\");'>"+className+"</a></li>");
		}
	})
}