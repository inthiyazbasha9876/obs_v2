package com.obs.rmg.rmgmodel;

import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETPROJECTENDDATERELEASE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETEMAILIDS;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.obs.rmg.rmgdao.RmgDao;
import com.obs.rmg.rmgdao.RmgGenericRepository;
import com.obs.rmg.rmgdao.RmgSpecificRepository;
import com.obs.rmg.rmgfacadeimpl.RmgFacadeImpl;
@Component
public class Schedular 
{

	@Autowired
	private RmgFacadeImpl rmgfacadeimpl;

	@Autowired
	private Environment env;
	@Autowired
	private JavaMailSender mailSender;
	
	@Scheduled(cron = "0 59 23 1/1 * ? ")
	public void cronJobSch() {
		JdbcTemplate jdbc = new JdbcTemplate(rmgfacadeimpl.getDataSource());
		jdbc.update(GETPROJECTENDDATERELEASE);
	}
	
	@Scheduled(cron = "0 0 6 1/1 * ?")
    public void notifyEndDate() {
        LocalDate date = LocalDate.now().plusDays(7);
        JdbcTemplate jdbc = new JdbcTemplate(getDataSource());
        List<String> emailIds = jdbc.queryForList(GETEMAILIDS, String.class);
        String[] to = emailIds.stream().toArray(String[]::new);
        System.out.println("Email************ ids : " + emailIds);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(env.getProperty("spring.mail.username"));
        mailMessage.setTo(to);
        mailMessage.setSubject("Project Notification");
        mailMessage.setText(
                "Hi,\n\t Your end date in the project is approaching. You will be released from the project on '" + date
                        + "'. Please extend the end date if required.");
        mailSender.send(mailMessage);
    }
	
	 public DataSource getDataSource() {
	        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	        driverManagerDataSource.setUrl(env.getProperty("hrms_db"));
	        driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
	        driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
	        driverManagerDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
	        return driverManagerDataSource;
	    }
	
}
