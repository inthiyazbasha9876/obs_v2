package com.ojas.obs.dao;

import static com.ojas.obs.constants.GpaServiceConstants.GETBYID;
import static com.ojas.obs.constants.GpaServiceConstants.GETGPACOUNT;
import static com.ojas.obs.constants.GpaServiceConstants.GETGPAPLAN;
import static com.ojas.obs.constants.GpaServiceConstants.INSERTGPA;
import static com.ojas.obs.constants.GpaServiceConstants.UPDATEGPAPLAN;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.GpaPlan;
import com.ojas.obs.request.GpaRequest;

@Repository
public class GpaPlanDaoImpl implements GpaPlanDao {

	@Autowired
	private JdbcTemplate jdbcTemplaeObject;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean saveGpaPlan(GpaRequest gpaPlan) throws SQLException {

			List<Object[]> inputList = new ArrayList<Object[]>();

			for (GpaPlan plan : gpaPlan.getGpaPlan()) {
				logger.debug("Inside save Gpa..DAO Request" + gpaPlan);
				Object[] obj = new Object[] { plan.getGpaPlanType(), plan.getGpaPremium(),
						plan.getTotalPremium() };

				inputList.add(obj);

				/*
				 * if (n > 0) { return true; } return false;
				 */
			}
			int[] batchUpdate = jdbcTemplaeObject.batchUpdate(INSERTGPA, inputList);
			if (batchUpdate.length > 0) {
				return true;
			}
			return false;
		
	}

	@Override
	public boolean updateGpa(GpaRequest gpaRequest) throws SQLException {

		List<Object[]> inputList = new ArrayList<Object[]>();
		logger.debug("Inside updateGpa..DAO *****Request "+gpaRequest);
		
			List<GpaPlan> gpa = gpaRequest.getGpaPlan();

			for (GpaPlan plan : gpa) {

				Object[] obj = new Object[] { plan.getGpaPlanType(), plan.getGpaPremium(), plan.getTotalPremium(),
						plan.getId() };
				/*
				 * if (n > 0) return true; else return false;
				 */

				inputList.add(obj);

			}
			int[] batchUpdate = jdbcTemplaeObject.batchUpdate(UPDATEGPAPLAN, inputList);

			if (batchUpdate.length > 0) {
				return true;
			}
			return false;
	}

	@Override
	public List<GpaPlan> getAllGpaDetails(GpaRequest gpaRequest) throws SQLException {
		
			logger.debug("Inside getAllGpaDetails DAO .***Request " + gpaRequest);
			System.out.println("Inside getall");
			List<GpaPlan> gpa = jdbcTemplaeObject.query(GETGPAPLAN, new BeanPropertyRowMapper<>(GpaPlan.class));

			return gpa;
		
	}

	// @Override
//	public boolean deleteGpaRecord(int courseId) throws SQLException {
//		logger.debug("Inside deleteGpa...DAO");
//
//		int i = jdbcTemplaeObject.update(DELETEGPA, new Timestamp(new Date().getTime()), courseId);
//		if (i > 0) {
//			return true;
//		} else {
//			return false;
//
//		}
//	}
	@Override
	public List<GpaPlan> getPageRecords(List<GpaPlan> allGpaDetails, int pageSize, int pageNum) {
		

		List<GpaPlan> gpaDetails = new ArrayList<>();

		if (allGpaDetails != null && !allGpaDetails.isEmpty()) {

			pageSize = pageSize > 0 ? pageSize : pageSize * -1;

			pageNum = pageNum > 0 ? pageNum : pageNum == 0 ? 1 : pageNum * -1;

			if (pageSize != 0) {

				int endIndex = pageNum * pageSize;

				int startIndex = endIndex - pageSize;

				endIndex = endIndex < allGpaDetails.size() ? endIndex : allGpaDetails.size();

				startIndex = startIndex < allGpaDetails.size() ? startIndex : 0;

				gpaDetails = allGpaDetails.subList(startIndex, endIndex);
			}
		}
		return gpaDetails;
		

	}

	@Override
	public int getAllGpaDetailsCount() throws SQLException {
			logger.debug("Inside getCount DAO .***");
			return jdbcTemplaeObject.queryForObject(GETGPACOUNT, Integer.class);
		

	}

	@Override
	public List<GpaPlan> getById(GpaRequest gpaRequest) throws SQLException {
		// TODO Auto-generated method stub
		logger.debug("Inside getByGpaPlanIdGpaDetails DAO .***Request" + gpaRequest); 
		List<GpaPlan> gpa = jdbcTemplaeObject.query(GETBYID + gpaRequest.getGpaPlan().get(0).getId(), new BeanPropertyRowMapper<>(GpaPlan.class)); 
		return gpa;
		
	
	}

	/*
	 * @Override public List<GpaPlan> getByGpaId(Integer gpaPlanid) throws
	 * SQLException { try {
	 * logger.debug("Inside getByGpaPlanIdGpaDetails DAO .***"); List<GpaPlan> gpa =
	 * jdbcTemplaeObject.query(GETBYGPAID + gpaPlanid, new
	 * BeanPropertyRowMapper<>(GpaPlan.class)); return gpa; } finally {
	 * jdbcTemplaeObject.getDataSource().getConnection().close(); } }
	 */

	/*
	 * @Override public List<GpaPlan> getById(GpaRequest gpaRequest) throws
	 * SQLException {
	 * 
	 * try { logger.debug("Inside getById in DAO"); if (
	 * gpaRequest.getGpaPlan().get(0).getGpaPlanId() == null) { return
	 * jdbcTemplaeObject.query(GETBYID + gpaRequest.getGpaPlan().get(0).getId(), new
	 * BeanPropertyRowMapper<>(GpaPlan.class)); } if
	 * (gpaRequest.getGpaPlan().get(0).getGpaPlanId() != 0) { return
	 * getByGpaId(gpaRequest.getGpaPlan().get(0).getGpaPlanId()); } } finally {
	 * jdbcTemplaeObject.getDataSource().getConnection().close(); } return null;
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * }
	 */
}
