package com.ojas.obs.daoImpl;

import static com.ojas.obs.constants.InsuranceConstants.DELETED;
import static com.ojas.obs.constants.InsuranceConstants.DELETEINSURANCEDETAILS;
import static com.ojas.obs.constants.InsuranceConstants.GETINSURANCEDETAILS;
import static com.ojas.obs.constants.InsuranceConstants.GETINSURANCEDETAILSCOUNT;
import static com.ojas.obs.constants.InsuranceConstants.SAVED;
import static com.ojas.obs.constants.InsuranceConstants.SAVEINSURANCE;
import static com.ojas.obs.constants.InsuranceConstants.UPDATED;
import static com.ojas.obs.constants.InsuranceConstants.UPDATEINSURANCEDETAILS;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.dao.InsuranceDao;
import com.ojas.obs.model.Insurance;
import com.ojas.obs.request.InsuranceRequest;

@Repository
public class InsuranceDaoImpl implements InsuranceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException {

		int saveInsurance = jdbcTemplate.update(SAVEINSURANCE,

				insuranceRequest.getInsurance().getAge_id(), insuranceRequest.getInsurance().getAge_band(),
				insuranceRequest.getInsurance().getPlan1(), insuranceRequest.getInsurance().getPlan2(), SAVED,
				new Timestamp(new Date().getTime()), insuranceRequest.getInsurance().getCreated_by());

		if (saveInsurance > 0) {
			return true;
		}

		return false;

	}

	@Override
	public boolean updateInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException {

		int updateInsurance = jdbcTemplate.update(UPDATEINSURANCEDETAILS,

				insuranceRequest.getInsurance().getAge_id(), insuranceRequest.getInsurance().getAge_band(),
				insuranceRequest.getInsurance().getPlan1(), insuranceRequest.getInsurance().getPlan2(), UPDATED,
				new Timestamp(new Date().getTime()), insuranceRequest.getInsurance().getUpdated_by(),
				insuranceRequest.getInsurance().getId());

		if (updateInsurance > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean deleteInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException {
		int deleteInsurance = jdbcTemplate.update(DELETEINSURANCEDETAILS, DELETED, new Timestamp(new Date().getTime()),
				insuranceRequest.getInsurance().getId());
		if (deleteInsurance > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Insurance> getAllInsuranceDetails(InsuranceRequest insuranceRequest) {
		List<Insurance> insurance = jdbcTemplate.query(GETINSURANCEDETAILS,
				new BeanPropertyRowMapper<>(Insurance.class));
		return insurance;
	}

	@Override
	public List<Insurance> getPageRecords(List<Insurance> allInsuranceDetails, int pageSize, int pageNum) {

		List<Insurance> getRecords = new ArrayList<>();

		if (allInsuranceDetails != null && !allInsuranceDetails.isEmpty()) {

			pageSize = pageSize > 0 ? pageSize : pageSize * -1;

			pageNum = pageNum > 0 ? pageNum : pageNum == 0 ? 1 : pageNum * -1;

			if (pageSize != 0) {

				int endIndex = pageNum * pageSize;

				int startIndex = endIndex - pageSize;

				endIndex = endIndex < allInsuranceDetails.size() ? endIndex : allInsuranceDetails.size();

				startIndex = startIndex < allInsuranceDetails.size() ? startIndex : 0;

				getRecords = allInsuranceDetails.subList(startIndex, endIndex);
			}
		}
		return getRecords;

	}

	@Override
	public int getAllInsuranceDetailsCount() throws SQLException {
		int count = jdbcTemplate.queryForObject(GETINSURANCEDETAILSCOUNT, Integer.class);

		return count;

	}

}
