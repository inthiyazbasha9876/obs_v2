package com.obs.employeeCertificationDetails.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.obs.employeeCertificationDetails.constants.Constants.INSERT;
import static com.obs.employeeCertificationDetails.constants.Constants.UPDATEQ;
import static com.obs.employeeCertificationDetails.constants.Constants.DELETECERTIFICATIONDETAILS;
import static com.obs.employeeCertificationDetails.constants.Constants.GETCERTIFICATIONDETAILSCOUNT;
import static com.obs.employeeCertificationDetails.constants.Constants.GETCERTIFICATIONDETAILS;
import static com.obs.employeeCertificationDetails.constants.Constants.GETCERTIFICATIONDETAILBYID;
import static com.obs.employeeCertificationDetails.constants.Constants.GETBYEMPID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.obs.employeeCertificationDetails.constants.PropsReaderUtil;
import com.obs.employeeCertificationDetails.dao.EmployeeCertificationDAO;
import com.obs.employeeCertificationDetails.model.CertificationDetails;
import com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;

@Repository
public class EmployeeCertificationDAOImpl implements EmployeeCertificationDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	PropsReaderUtil propsReaderUtil;
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Boolean saveCertificationDetails(CertificationDetailsRequest certificationDetailsRequest) throws SQLException{
		List<CertificationDetails> modelList = certificationDetailsRequest.getCertificationDetailsModel();
		List<Object[]> list= new ArrayList<>();
		int count=0;
		boolean flag=true;
		
			for (CertificationDetails details : modelList) {
				Object[] model = new Object[] { details.getCertificationName(), details.getIssuedBy(),
						details.getDateOfIssue(), details.getEmployeeId(), details.getCreatedBy(), flag };
				list.add(model);
			}
			int[] batchUpdate = jdbcTemplate.batchUpdate(INSERT, list);
			int len = batchUpdate.length;
			for (int i : batchUpdate) {
				if (i > 0) {
					count++;
				}
			}
			if (count == len) {
				logger.debug("saved successfully through DaoImpl");
				return true;
			}
		logger.debug("failed to save through daoImpl Method");
		return false;
	}

	@Override
	public Boolean updateCertificationDetails(CertificationDetailsRequest certificationDetailsRequest) throws SQLException{
		List<CertificationDetails> modelList = certificationDetailsRequest.getCertificationDetailsModel();
		List<Object[]> list= new ArrayList<>();
		int count = 0;

			for(CertificationDetails details :modelList) {
				Object[] model= new Object[] { details.getCertificationName(), details.getIssuedBy(),details.getDateOfIssue(), details.getCreatedBy(), details.getUpdatedBy(),details.getId()};
			    list.add(model);
			}
			int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATEQ,list);
			int len=batchUpdate.length;
			 for (int i : batchUpdate) {
					if (i > 0) {
						count++;
					 }
				}
		        if(count==len) {
		        	logger.debug("updated successfully through DaoImpl");
					return true;
		        }

		logger.debug("failed to update through daoImpl Method");
		return false;
	}

	@Override
	public Boolean deleteCertificationDetails(CertificationDetailsRequest certificationDetailsRequest) throws SQLException{
		List<CertificationDetails> modelList = certificationDetailsRequest.getCertificationDetailsModel();
		List<Object[]> list= new ArrayList<>();
		int[] count = null;

			for (CertificationDetails details : modelList) {
				Object[] model = new Object[] { details.getUpdatedBy(), details.getId() };
				list.add(model);
			}
		
			count = jdbcTemplate.batchUpdate(DELETECERTIFICATIONDETAILS, list);
            for (int i : count) {
				if (i > 0) {
					logger.debug("updated successfully through DaoImpl" + i);
					return true;
				}
			}
		
		logger.debug("failed to update through daoImpl Method");
		return false;
	}

	@Override
	public List<CertificationDetails> getAllCertificationDetails() throws SQLException{
		List<CertificationDetails> list=null;
		
			list=jdbcTemplate.query(GETCERTIFICATIONDETAILS,new BeanPropertyRowMapper<CertificationDetails>(CertificationDetails.class));
		
		return list;
	}

	@Override
	public int getAllCertificationDetailsCount() throws SQLException {
		int count=0;
		
			count=jdbcTemplate.queryForObject(GETCERTIFICATIONDETAILSCOUNT, Integer.class);
		
		return count;
	}


	@Override
	public List<CertificationDetails> getDetailById(CertificationDetailsRequest certificationDetailsRequest)
			throws SQLException {
		List<CertificationDetails> modelList = certificationDetailsRequest.getCertificationDetailsModel();
		List<Object[]> list= new ArrayList<>();
		Object[] param=null;
		List<CertificationDetails> query=null;

			for(CertificationDetails details :modelList) {
				param	 = new Object[] { details.getId() };
				list.add(param);
			}
		   query = jdbcTemplate.query(GETCERTIFICATIONDETAILBYID,param,new BeanPropertyRowMapper<>(CertificationDetails.class));
		
		
		return query;
	}
	@Override
	public List<CertificationDetails> getDetailByEmpId(CertificationDetailsRequest certificationDetailsRequest)throws SQLException {
		List<CertificationDetails> modelList = certificationDetailsRequest.getCertificationDetailsModel();
		List<Object[]> list= new ArrayList<>();
		Object[] param=null;
		List<CertificationDetails> query=null;
		
			for(CertificationDetails details :modelList) {
				param	 = new Object[] { details.getEmployeeId()};
				list.add(param);
			}
		   query = jdbcTemplate.query(GETBYEMPID,param,new BeanPropertyRowMapper<>(CertificationDetails.class));
		
		
		return query;
	}

}
