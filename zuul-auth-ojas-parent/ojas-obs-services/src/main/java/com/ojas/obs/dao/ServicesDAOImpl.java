package com.ojas.obs.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ojas.obs.model.Services;
import com.ojas.obs.request.ServicesRequest;
import static com.ojas.obs.constants.Constants.*;


@Repository
public class ServicesDAOImpl implements ServicesDao {
    @Autowired
	private JdbcTemplate jdbcTemplate;
    Logger logger = Logger.getLogger(this.getClass());
    
	/*
	 * @Autowired private PropsReaderUtil propsReaderUtil;
	 */
	@Override
	public boolean saveServices(ServicesRequest servicesRequestObj) throws SQLException {

		logger.info("@@@@Inside saveServices..DAO"+servicesRequestObj);
		List<Services> services = servicesRequestObj.getServices();
		List<Object[]> list = new ArrayList<Object[]>();
		int count=0;
	boolean status = true;
			for (Services services1 : services) {
				Object[] params = new Object[] { services1.getServiceName(),status };
				list.add(params);
			}
			
			int[] batchUpdate = jdbcTemplate.batchUpdate(INSERT_SERVICES, list);
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
	public int getAllServicesCount() throws SQLException {
		logger.info("@@@@Inside getAllServicesCount method");
		int count=0;
		
			count=jdbcTemplate.queryForObject(SERVICESCOUNT,Integer.class);
		
		
		return count;
	}

	@Override
	public boolean updateServices(ServicesRequest servicesRequestObj) throws SQLException {
		logger.info("@@@@Inside updateservices Method..DAO "+servicesRequestObj);
		List<Services> services1=servicesRequestObj.getServices();
		List<Object[]> list=new ArrayList<Object[]>();
		int count=0;
		
			for(Services services:services1) {
				Object[] params=new Object[] {services.getServiceName(),services.isStatus(),services.getId()};
				list.add(params);
			}
			
			int[] batchUpdate=jdbcTemplate.batchUpdate(UPDATE_SERVICES,list );
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

	
	 
	  public boolean deleteServices(ServicesRequest servicesRequestObj) throws SQLException { 
		  logger.debug("Inside deleteservices..DAO *****");
		  
	  List<Services> services1=servicesRequestObj.getServices();
	  List<Object[]> list=new ArrayList<Object[]>();
	  for(Services services:services1) {
		  Object[] params=new Object[] 
		  {services.getId()};
		  list.add(params); 
		  }
	  int[] count;

	    count=jdbcTemplate.batchUpdate(DELETE_SERVICES, list); 
	    for (int i : count) {
		  if (i > 0) { 
		  logger.debug("saved successfully through DaoImpl" + i);
		  return true; } }
	  logger.debug("failed to save through daoImpl Method");
	  return false;
	  
	  }
	 

	@Override
	public List<Services> getAll(ServicesRequest servicesRequestObj) throws SQLException {
		logger.info("@@@@Inside getAll method..DAO "+servicesRequestObj);
       
		 List<Services> list=null;
		
			 list=jdbcTemplate.query(SELECT_SERVICES, new BeanPropertyRowMapper<Services>(Services.class));
	
		
		return list;
	}
      
	@Override
	public List<Services> getCountPerPage(List<Services> serviceList, int pageSize, int pageNo) {
		logger.info("@@@@Inside getCountPerPage method..DAO ");
		
		List<Services> getAllFiltered = new ArrayList<>();
		if (serviceList != null && !serviceList.isEmpty()) {
			pageSize = pageSize > 0 ? pageSize : pageSize * -1;
			pageNo = pageNo > 0 ? pageNo : pageNo == 0 ? 1 : pageNo * -1;
			if (pageSize != 0) {
				int endIndex = pageNo * pageSize;
				int startIndex = endIndex - pageSize;
				endIndex = endIndex < serviceList.size() ? endIndex : serviceList.size();
				startIndex = startIndex < serviceList.size() ? startIndex : 0;
				getAllFiltered = serviceList.subList(startIndex, endIndex);
			}
		}
		return getAllFiltered;
	}

	@Override
	public List<Services>getServiceById(ServicesRequest servicesRequestObj) throws SQLException {
		logger.info("@@@@Inside getServiceById method..DAO "+servicesRequestObj);
		List<Object[]> inputList = new ArrayList<Object[]>();
		Object[] serviceId=null;
		List<Services> list=null;
	
			List<Services> servicesList=servicesRequestObj.getServices();
			for(Services service:servicesList) {
				serviceId=new Object[] {service.getId()};
				inputList.add(serviceId);
			}
			list=jdbcTemplate.query(SELECT_SERVICES_BY_ID,serviceId, new
					BeanPropertyRowMapper<>(Services.class));
		
		
		return list; 
	
	}

	

}
