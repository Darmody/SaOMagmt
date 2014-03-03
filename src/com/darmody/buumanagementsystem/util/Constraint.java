package com.darmody.buumanagementsystem.util;

/**
 * 2013.7.29.17:39
 * @author Caihuanyu
 * @content 属性统一限定类
 */

public class Constraint {

	/*
	 * URL
	 */
	
	//管理员登录页地址
	public final static String ADMIN_LOGIN_PAGE = "/login.html?logout=true";
	
	/*
	 * Session Attribute
	 */
	//管理员标识
//	public final static String SESSION_LOGIN_ADMIN = "administrator";
	//学生标识
//	public final static String SESSION_LOGIN_STUDENT = "student";
	//登录标识
	public final static String SESSION_LOGIN = "login";
	
	public final static String SESSION_ADMIN_LEVEL = "admin_level";
	
	/*
	 * File Location
	 */
	
	//web应用位置
	public final static String WEB_LOCATION = "../webapps/SDMS/";
	
	//offer文件夹位置
	public final static String OFFER_LOCATION = WEB_LOCATION + "offer/";
	
	/*
	 * 属性
	 */
	public final static String DEFAULT_PASSWORD = "123456";
	public final static String ERROR_MESSAGE = "出现错误了！请检查提交的信息并重试，如无效请联系管理员";
	
	public final static String OFFER_STATUS_ENABLE = "启动";
	public final static String OFFER_STATUS_DISABLE = "停用";
}
