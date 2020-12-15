package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;
import com.ojas.obs.model.Services;
import com.ojas.obs.request.ServicesRequest;

public interface ServicesDao {
	public boolean saveServices(ServicesRequest servicesRequestObj) throws SQLException;
	public int getAllServicesCount() throws SQLException;
    public boolean updateServices(ServicesRequest servicesRequestObj) throws SQLException;
    public List<Services> getAll(ServicesRequest servicesRequestObj) throws SQLException;
	public List<Services> getCountPerPage(List<Services> serviceList, int pageSize, int pageNo);
	public List<Services> getServiceById(ServicesRequest servicesRequestObj) throws SQLException;
    public boolean deleteServices(ServicesRequest servicesRequestObj) throws SQLException;
	
	//boolean deleteStates(StatesRequest statesRequestObj) throws SQLException;

}
