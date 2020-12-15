package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.apache.log4j.Logger;

import com.ojas.obs.model.SeparationType;

import com.ojas.obs.request.SeparationTypeRequest;
//import com.ojas.obs.request.SeparationTypeRequest;

/**
 * 
 * @author nsrikanth
 *
 */

@Repository
public class SeparationTypeDaoImpl implements SeparationTypeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	Logger logger = Logger.getLogger(this.getClass());
	public static final String DELETEEDUCATION = "update obs_separationtype  set status = false where separationTypeId = ?";
	public static final String SAVESeparationTtype = "Insert into obs_separationtype(separationType,status) values(?,?)";
	public static final String UPDATESeparationTtype = "Update obs_separationtype set separationType =? where separationTypeId =? ";
	public static final String DELETESeparationTtype = "Delete from obs_separationtype where separationTypeId =?";
	public static final String SELECTSeparationTtype = "Select * from obs_separationtype ";
	public static final String Getbyid = "Select * from obs_separationtype where separationTypeId = ";

	@Override
	public boolean saveSeparationType(SeparationTypeRequest separationTypeReq) throws SQLException {
		logger.info("The requests inside save in SeparationTypeDaoImpl " + separationTypeReq);

		boolean status = true;
		List<Object[]> inputList = new ArrayList<Object[]>();

		for (SeparationType separationType : separationTypeReq.getSeparationType()) {

			logger.debug("Inside saveforecah in SeparationTypeDaoImpl " + separationTypeReq);
			Object[] obj = new Object[] { separationType.getSeparationType(),status };

			inputList.add(obj);

		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(SAVESeparationTtype, inputList);
		if (batchUpdate.length > 0) {
			return true;
		}
		return false;

	}

	@Override
	public boolean updateSeparationType(SeparationTypeRequest separationTypeRequest) throws SQLException {

		List<Object[]> inputList = new ArrayList<Object[]>();
		logger.info("The requests inside update in SeparationTypeDaoImpl " + separationTypeRequest);

		List<SeparationType> separationType = separationTypeRequest.getSeparationType();

		for (SeparationType plan : separationType) {
			logger.debug("Inside updateforeach SeparationTypeDaoImpl " + separationTypeRequest);
			
			Object[] obj = new Object[] { plan.getSeparationType(), plan.getSeparationTypeId() };
			inputList.add(obj);

		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATESeparationTtype, inputList);

		if (batchUpdate.length > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<SeparationType> getAllSeparationType() throws SQLException {

		logger.debug("The requests inside getall in  SeparationTypeDaoImpl ");

		List<SeparationType> separationType = jdbcTemplate.query(SELECTSeparationTtype,
				new BeanPropertyRowMapper<>(SeparationType.class));

		return separationType;

	}

	public List<SeparationType> getById(SeparationTypeRequest separationTypeRequest) throws SQLException {
		logger.info("The requests inside getById in SeparationTypeDaoImpl.***" + separationTypeRequest);

		List<SeparationType> separationTypeList = new ArrayList<>();
		List<SeparationType> insuranceList = separationTypeRequest.getSeparationType();
		for (SeparationType separationType : insuranceList) {
			List<SeparationType> query = jdbcTemplate.query(Getbyid + separationType.getSeparationTypeId(),
					new BeanPropertyRowMapper<>(SeparationType.class));
			separationTypeList.addAll(query);
		}
		return separationTypeList;

	}

	@Override
	public boolean deleteSeparationType(int separationTypeId) throws SQLException {
		
	    int delete = jdbcTemplate.update(DELETEEDUCATION, separationTypeId);

	    

        if (delete > 0)
            return true;
        else
		return false;
	}
	

}
