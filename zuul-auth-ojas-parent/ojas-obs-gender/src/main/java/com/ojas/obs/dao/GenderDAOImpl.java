package com.ojas.obs.dao;

import static com.ojas.obs.constants.Constants.GENDERCOUNT;
import static com.ojas.obs.constants.Constants.GENDER_BY_ID;
import static com.ojas.obs.constants.Constants.INSERT_GENDER;
import static com.ojas.obs.constants.Constants.SELECT_GENDER;
import static com.ojas.obs.constants.Constants.UPDATE_GENDER;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;
@Repository
public class GenderDAOImpl implements GenderDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*

	 */
	Logger logger = Logger.getLogger(this.getClass());
     
	/**
	 * method to save the record from database
	 * @throws SQLException 
	 */
	@Override
	public boolean saveGender(GenderRequest genderRequest) throws SQLException {
		logger.info("@@@@Inside saveGender DAO Method");
		int count = 0;
		List<Genders> genders=genderRequest.getGender();
		List<Object[]> list= new ArrayList<>();
			 for(Genders gender:genders) {
					Object[] model=new Object[]{gender.getGender()};
					list.add(model);
				    }

				    int[] batchUpdate = jdbcTemplate.batchUpdate(INSERT_GENDER, list);
				    int noOfrecAdded=batchUpdate.length;
					for (int i : batchUpdate) {
						if (i > 0) 
						 count ++;
					}
					if(count==noOfrecAdded)
						return true;
	   
		logger.info("@@@@failed to save through daoImpl Method");
		return false;
	}
    
	/**
	 * method to update the record from database
	 * @throws SQLException 
	 */
	@Override
	public boolean updateGender(GenderRequest genderRequest) throws SQLException {
		logger.info("@@@@Inside updateGender DAO Method::");
		List<Genders> genders = genderRequest.getGender();
		List<Object[]> list = new ArrayList<>();
		int count=0;
		
			for (Genders gender : genders) {
				Object[] model = new Object[] { gender.getGender() ,gender.getId() };
				list.add(model);
			}

			int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATE_GENDER, list);
			int len=batchUpdate.length;
			for (int i : batchUpdate) {
				if (i > 0) 
				 count ++;
			}
			if(count==len) {
				return true;
			}
		
		logger.info("@@@@Failed to updated through daoImpl Method");
		return false;
	}
	/**
	 * method to delete the record from database
	 */

	
	
	 
    /**
     * method will get count of all record from database
     * @throws SQLException 
     */
	@Override
	public int getAllCount(GenderRequest genderRequest) throws SQLException {
		logger.info("@@@@Inside getAllCount DAO Method::");
		int count=0;
		
			count=jdbcTemplate.queryForObject(GENDERCOUNT, Integer.class);
		
	   return count;
	}
	/**
	 * method to get all the record from database
	 * @throws SQLException 
	 */

	@Override
	public List<Genders> getAll(GenderRequest genderRequest) throws SQLException {
		logger.info("@@@@Inside getAll DAO Method::");

		List<Genders> list=null;
		
			list=jdbcTemplate.query(SELECT_GENDER, new BeanPropertyRowMapper<Genders>(Genders.class));
		
		return list;
	}

	

	@Override
	public List<Genders> getGenderById(GenderRequest genderRequest) throws SQLException {
		logger.info("@@@@Inside getGenderById DAO Method::");
		List<Object[]> inputList = new ArrayList<>();
		Object[] stateId=null;
		List<Genders> genderList=genderRequest.getGender();
		List<Genders> list=null;
		
			for(Genders gender:genderList) {
				   stateId=new Object[] {gender.getId()};
					inputList.add(stateId);
			}
			list=jdbcTemplate.query(GENDER_BY_ID,stateId, new BeanPropertyRowMapper<>(Genders.class));
		
		return list;
	
	}

}
