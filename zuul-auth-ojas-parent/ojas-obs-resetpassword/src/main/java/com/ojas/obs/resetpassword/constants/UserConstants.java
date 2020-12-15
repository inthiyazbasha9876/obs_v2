package com.ojas.obs.resetpassword.constants;
/**
 * 
 * @author Manohar
 *
 */
public class UserConstants {

	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String FAILED = "Failed";
	public static final String SUCCESS = "Success";
	public static final String SENDMAIL = "sendMail";

	// ***** URL Constants *****

	public static final String SET = "/set";
	public static final String GET = "/get";
	public static final String RESETPASSWORD = "/obs/ResetPassword";
	public static final String GETMAILID = "select email from obs_employeecontactinfo where emp_id = ";
	
	
	// Out Look 365 Properties
		public static final String Mail_Smtp_Auth = "mail.smtp.auth";
		public static final String Mail_Smtp_Starttls_Enable = "mail.smtp.starttls.enable";
		public static final String Mail_Smtp_Host = "mail.smtp.host";
		public static final String Mail_Smtp_Port = "mail.smtp.port";
}
