$(function(){




	var headerHtmlStr = "<div class = 'navbar navbar-fixed-top' id = 'navBar'><div class = 'navbar-inner'>";
	if($.cookie("userType") == "admin"){
	headerHtmlStr += "<div class = 'container'><a class = 'brand' href = '/SDMS/index.html'>BUU应用科技学院报盘管理系统</a>";
	}
	else{
		headerHtmlStr += "<div class = 'container'><a class = 'brand' href = '#'>BUU应用科技学院报盘管理系统</a>";
	}
	if($.cookie("userType") == "admin"){
	headerHtmlStr += "<div class = 'nav-collapse'><ul class='nav'>"
	headerHtmlStr += "<li class = 'divider-vertical'></li>";
	headerHtmlStr += "<li><a href = '/SDMS/admin/studentDataManage.html'>学生数据管理</a>";
	headerHtmlStr += "<li class = 'divider-vertical'></li>";
	headerHtmlStr += "<li><a href = '/SDMS/admin/basicalSetting.html'>基础数据管理</a>";
	headerHtmlStr += "<li class = 'divider-vertical'></li>";
	headerHtmlStr += "<li><a href = '/SDMS/offer/offerDataManage.html'>报盘管理</a>";
	headerHtmlStr += "<li class = 'divider-vertical'></li>";
	headerHtmlStr += "</ul></div>";
	}
	headerHtmlStr += "<div class = 'nav-collapse'><ul class='nav pull-right'>";
	if($.cookie("userName") != null){
		if($.cookie("userType") == "student"){
			headerHtmlStr += "<li><a href = '/SDMS/profile.html'>";
		}
		else if($.cookie("userType") == "admin"){
			headerHtmlStr += "<li><a href = '/SDMS/admin/profile.html'>";
		}
		headerHtmlStr += $.cookie("userName");
		headerHtmlStr += "</a></li><li><a href = '/SDMS/logout.html'>退出</a></li></ul></div></div></div></div>";
	}
	else{
		headerHtmlStr += "<li><a href = '/SDMS/login.html'>登录</a></li></ul></div></div></div></div>";
	}
	if($.cookie("userType") == "admin"){
		headerHtmlStr += "<div id = 'breadcrumbNav' class = 'container'><ul class = 'breadcrumb'><li>";
		headerHtmlStr += "<a href = '/SDMS/index.html'>首页</a> <span class = 'divider'>/</span></li>";
		
		var url = window.location.pathname;
		var array = new Array();
		array = url.split("/");
		if(array[array.length - 1] == "login.html"){
			headerHtmlStr += "<li class = 'active'>登录</li></ul></div>";
		}
		else if(array[array.length - 1] == "about.html"){
			headerHtmlStr += "<li class = 'active'>关于我们</li></ul></div>";
		}
		else if(array[array.length - 1] == "index.html"){
			headerHtmlStr += "";
		}
		else if(array[array.length - 1] == "logout.html"){
			headerHtmlStr += "<li class = 'active'>登录</li></ul></div>";
		}
		else if(array[array.length - 1] == "profile.html"){
			headerHtmlStr += "<li class = 'active'>个人设置</li></ul></div>";
		}
		else if(array[array.length - 1] == "404.html"){
			headerHtmlStr += "<li class = 'active'>404</li></ul></div>";
		}
		else if(array[array.length - 1] == "500.html"){
			headerHtmlStr += "<li class = 'active'>500</li></ul></div>";
		}
		else if(array[array.length - 1] == "studentDataManage.html"){
			headerHtmlStr += "<li class = 'active'>学生数据管理</li></ul></div>";
		}
		else if(array[array.length - 1] == "basicalSetting.html"){
			headerHtmlStr += "<li class = 'active'>基础数据管理</li></ul></div>";
		}
		else if(array[array.length - 1] == "offerDataManage.html"){
			headerHtmlStr += "<li class = 'active'>报盘管理</li></ul></div>";
		}
		else if(array[array.length - 1] == "offerDetails.html"){
			headerHtmlStr += "<li class = 'active'>报盘结果</li></ul></div>";
		}
		else{
			headerHtmlStr += "<li class = 'active'>报盘</li></ul></div>";
		}
	}

	$("#header").html(headerHtmlStr);
})