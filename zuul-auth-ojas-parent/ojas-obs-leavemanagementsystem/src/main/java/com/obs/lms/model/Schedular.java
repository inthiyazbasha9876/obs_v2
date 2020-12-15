package com.obs.lms.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static com.obs.lms.constants.Constants.GETEMPLYINFO;
import com.obs.lms.facade.LmsFacadeImpl;
import com.obs.lms.repository.LeaveBalanceRepo;

@Component
public class Schedular {
	@Autowired
	private LeaveBalanceRepo leaveBalanceRepo;
	@Autowired
	private LmsFacadeImpl lmsfacadeimpl;
	

	@Scheduled(cron = "0 0 0 25 * ?")

	public void cronJobSch() {
		JdbcTemplate jdbc = new JdbcTemplate(lmsfacadeimpl.getDataSource());
		List<String> empIds = jdbc.queryForList(GETEMPLYINFO, String.class);
		
		List<LeaveBalance> balance = leaveBalanceRepo.findByEmpIdIn(empIds);
		System.out.println("Emp ids : " + empIds);
		for (LeaveBalance balance2 : balance) {
			System.out.println("leaves added:" + balance2);
			balance2.setTotalSickLeave((float) (balance2.getTotalSickLeave() + 0.5));
			balance2.setTotalCasualLeave(balance2.getTotalCasualLeave() + 1);
		}
		leaveBalanceRepo.saveAll(balance);
	}

}
