package com.ojas.obs.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ojas.obs.model.States;
import com.ojas.obs.request.StatesRequest;
import static com.ojas.obs.constants.Constants.INSERT_STATES;
import static com.ojas.obs.constants.Constants.UPDATE_STATES;
import static com.ojas.obs.constants.Constants.SELECT_STATES;
import static com.ojas.obs.constants.Constants.STATESCOUNT;
import static com.ojas.obs.constants.Constants. SELECT_STATES_BY_ID;
import static com.ojas.obs.constants.Constants.*;

@Repository
public class StatesDAOImpl implements StatesDao {
    @Autowired
	private JdbcTemplate jdbcTemplate;
    Logger logger = Logger.getLogger(this.getClass());
    
	
	@Override
	public boolean saveStates(StatesRequest statesRequestObj) throws SQLException {

		logger.info("@@@@Inside saveStates..DAO"+statesRequestObj);
		List<States> states = statesRequestObj.getStates();
		List<Object[]> list = new ArrayList<>();
		int count=0;
	boolean status = true;
			for (States states1 : states) {
				Object[] params = new Object[] { states1.getStateName(),status };
				list.add(params);
			}
			int[] batchUpdate = jdbcTemplate.batchUpdate(INSERT_STATES, list);
			int len=batchUpdate.length;
			for (int i : batchUpdate) {
				if (i > 0) {
					count++;
				}
			}
			if (len == count) {
				logger.debug("@@@@saved successfully through DaoImpl");
				return true;
			}
		
		
		logger.info("@@@@failed to save through daoImpl Method");
		return false;
	}

	@Override
	public int getAllStatesCount() throws SQLException {
		logger.info("@@@@Inside getAllStatesCount method");
		int count=0;
		
			count=jdbcTemplate.queryForObject(STATESCOUNT,Integer.class);
		
		
		return count;
	}

	@Override
	public boolean updateStates(StatesRequest statesRequestObj) throws SQLException {
		logger.info("@@@@Inside updatestates Method..DAO "+statesRequestObj);
		List<States> states1=statesRequestObj.getStates();
		List<Object[]> list=new ArrayList<>();
		int count=0;
		
			for(States states:states1) {
				Object[] params=new Object[] {states.getStateName(),states.getId()};
				list.add(params);
			}
			int[] batchUpdate=jdbcTemplate.batchUpdate(UPDATE_STATES,list );
			int len=batchUpdate.length;
			for (int i : batchUpdate) {
				if (i > 0) {
					count++;
				}
			}
			if (len == count) {
				logger.debug("updated successfully through DaoImpl");
				return true;
			}
		
		
			logger.debug("failed to save through daoImpl Method");
			return false;
	}

	
	 
	  public boolean deleteStates(StatesRequest statesRequestObj) throws SQLException { logger.debug("Inside deletestates..DAO *****");
	  List<States> states1=statesRequestObj.getStates();
	  List<Object[]> list=new ArrayList<>();
	  for(States states:states1) {
		  Object[] params=new Object[] 
		  {states.getId()};
		  list.add(params); 
		  }
	  int[] count;
	    count=jdbcTemplate.batchUpdate(DELETE_STATES, list); 
	    for (int i : count) {
		  if (i > 0) { 
		  logger.debug("saved successfully through DaoImpl" + i);
		  return true; } }
	  logger.debug("failed to save through daoImpl Method");
	  return false;
	  
	  }
	 

	@Override
	public List<States> getAll(StatesRequest statesRequestObj) throws SQLException {
		logger.info("@@@@Inside getAll method..DAO "+statesRequestObj);
		 List<States> list=null;
		
			 list=jdbcTemplate.query(SELECT_STATES, new BeanPropertyRowMapper<States>(States.class));
	
		
		return list;
	}
      
	
	@Override
	public List<States>getStateById(StatesRequest statesRequestObj) throws SQLException {
		logger.info("@@@@Inside getStateById method..DAO "+statesRequestObj);
		List<Object[]> inputList = new ArrayList<>();
		Object[] stateId=null;
		List<States> list=null;
	
			List<States> statesList=statesRequestObj.getStates();
			for(States state:statesList) {
			   stateId=new Object[] {state.getId()};
				inputList.add(stateId);
			}
			list=jdbcTemplate.query(SELECT_STATES_BY_ID,stateId, new
					BeanPropertyRowMapper<>(States.class));
		
		
		return list; 
	
	}

}
