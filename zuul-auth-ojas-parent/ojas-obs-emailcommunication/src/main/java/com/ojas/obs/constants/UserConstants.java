package com.ojas.obs.constants;

public class UserConstants {
	
	public static final String FAILED = "Failed";
	public static final String SUCCESS = "Success";
	public static final String EMAILCOMMUNICATION = "/obs/emailcommunication";
	public static final String SET = "/set";
	public static final String GET = "/get";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String GETALL = "getAll";
	
	/*
	 * // Username & Password public static final String USER_NAME =
	 * "murali.chilabandla@ojas-it.com"; public static final String PASSWORD =
	 * "Krishna@630";
	 */

	// Out Look 365 Properties
	public static final String Mail_Smtp_Auth = "mail.smtp.auth";
	public static final String Mail_Smtp_Starttls_Enable = "mail.smtp.starttls.enable";
	public static final String Mail_Smtp_Host = "mail.smtp.host";
	public static final String Mail_Smtp_Port = "mail.smtp.port";
	
//	public static final String Mail_Smtp_Auth_Value = "true";
//	public static final String Mail_Smtp_Starttls_Enable_Value = "true";
//	public static final String Mail_Smtp_Host_Value = "outlook.office365.com";
//	public static final String Mail_Smtp_Port_Value = "587";

	
		
	// SQL Queries
	public static final String INSERT_EMAIL = "insert into obs_emailcommunication(toAddress, fromAddress, emailAddress) values(?, ?, ?)";
	public static final String UPDATE_EMAIL = "update  obs_emailcommunication set toAddress=?, toAddress=? where toAddress =?";

	public static final String SELECT_EMAIL = "select * from  obs_emailcommunication";

}
