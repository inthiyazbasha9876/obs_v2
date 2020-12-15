package com.ojas.obs.mailSend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository

public class MailSend {

	@Autowired
	private Environment environment;

	String str = "select employee_id,start_Date,end_Date from obs_projectdetails";
	String GETBYBASICINFO = "select reportingManager from obs_employeeinfo where employeeId =";

	String GETMEILIDS = "select email from obs_employeecontactinfo where emp_Id in ";

	Connection conn = null;
	Statement stmt = null;

	

	@PostConstruct
	@Scheduled(fixedRate = 1000)
	public void callProjectDetails() throws Exception {
	;
				
				System.out.println();

		try {

			System.out.println("hr mail id ...........................  ...." + environment.getProperty("hrmail"));

			String hrEmailId = environment.getProperty("hrmail");
			String dataSourceUrl = environment.getProperty("spring.datasource.url");
			String dataSourceUsername = environment.getProperty("spring.datasource.username");
			String dataSourcePassword = environment.getProperty("spring.datasource.password");
			String dataSourceDriver = environment.getProperty("spring.datasource.driver-class-name");

			Class.forName(dataSourceDriver);

			conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while (rs.next()) {
				// Retrieve by column name
				Timestamp startDate = rs.getTimestamp("start_Date");
				Timestamp endDate = rs.getTimestamp("end_Date");
				String employeeId = rs.getString("employee_id");
				System.out.println(employeeId + " employee id of users ");

				// Display values

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String da1 = null;
				String da2 = null;

				da1 = sdf.format(startDate);
				da2 = sdf.format(endDate);

				Date d1 = new Date();
				Date d2 = null;

				d2 = sdf.parse(da2);
				long diff = d2.getTime() - d1.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				System.out.println(" days gap present " + diffDays);

				if (diffDays < 1) {

					List<String> listFromBasicInfo = getByBasicInfoEmpId(employeeId);
					System.out.println("lis" + listFromBasicInfo.get(0));
					String managerEmpId = listFromBasicInfo.get(0);
					List<String> byContactInfoMail = getByContactInfoMail(managerEmpId, employeeId);
					System.out.println("email ids in 000000000000000000000000000000000000" + byContactInfoMail);

					Properties prop = new Properties();
					prop.put(environment.getProperty("smtphost"), environment.getProperty("mailType"));
					prop.put(environment.getProperty("mailport"), environment.getProperty("mailportNo"));
					prop.put(environment.getProperty("mailauth"), environment.getProperty("mailauthboolean"));
					prop.put(environment.getProperty("mailsmtpstarttls"),
							environment.getProperty("mailstarttlsenable")); // TLS

					Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(environment.getProperty("mailname"),
									environment.getProperty("mailpass"));
						}
					});

					try {

						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(environment.getProperty("mailname")));
						message.setRecipients(Message.RecipientType.TO,
// 			              
								InternetAddress.parse(
										byContactInfoMail.get(0) + "," + byContactInfoMail.get(1) + "," + hrEmailId)

						);
						message.setSubject("Testing OBS-HRMS");
						message.setText("Dear OBS Team," + "\n\n Test for OBS-HRMS resignation module - Please ignore"
								+ " kfkjdfklfjklfjlk");

						Transport.send(message);

						System.out.println("Done");

					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}

			}

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	public List<String> getByBasicInfoEmpId(String s) {
		List<String> list = new ArrayList<String>();

		try {

			String dataSourceUrl = environment.getProperty("spring.datasource.url");
			String dataSourceUsername = environment.getProperty("spring.datasource.username");
			String dataSourcePassword = environment.getProperty("spring.datasource.password");
			String dataSourceDriver = environment.getProperty("spring.datasource.driver-class-name");

			Class.forName(dataSourceDriver);

			conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
			stmt = conn.createStatement();
			System.out.println("---------------------------------------------" + GETBYBASICINFO + s);
			String query = GETBYBASICINFO + "'" + s + "'";
			ResultSet rs = stmt.executeQuery(query);
			// System.out.println("resultset " + rs.first());
			BasicInfoModel bsi = new BasicInfoModel();
			while (rs.next()) {
				String string = rs.getString("reportingManager");
				System.out.println("reportingManager" + string);
//				bsi.setReportingManager(rs.getString("reportingManager"));
//				bsi.setEmployeeId(rs.getString("employeeId"));
				list.add(string);
			}
			System.out.println("list present in another method " + list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return list;

	}

	public List<String> getByContactInfoMail(String s1, String s2) {
		List<String> list = new ArrayList<String>();

		try {

			String dataSourceUrl = environment.getProperty("spring.datasource.url");
			String dataSourceUsername = environment.getProperty("spring.datasource.username");
			String dataSourcePassword = environment.getProperty("spring.datasource.password");
			String dataSourceDriver = environment.getProperty("spring.datasource.driver-class-name");

			Class.forName(dataSourceDriver);

			conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
			stmt = conn.createStatement();
			System.out.println(
					"---------------------------------------------" + GETMEILIDS + s1 + "/n" + s2 + "===============");
			String query = GETMEILIDS + "(" + "'" + s1 + "'" + "," + "'" + s2 + "'" + ")";
			ResultSet rs = stmt.executeQuery(query);
			// System.out.println("resultset " + rs.first());
			BasicInfoModel bsi = new BasicInfoModel();
			while (rs.next()) {
				String string = rs.getString("email");
				System.out.println("emailid's" + string);
//				bsi.setReportingManager(rs.getString("reportingManager"));
//				bsi.setEmployeeId(rs.getString("employeeId"));
				list.add(string);
			}
			System.out.println("list present in another method " + list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return list;

	}

}
