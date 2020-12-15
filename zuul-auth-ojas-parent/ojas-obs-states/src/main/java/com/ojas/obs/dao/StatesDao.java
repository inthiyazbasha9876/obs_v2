package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;
import com.ojas.obs.model.States;
import com.ojas.obs.request.StatesRequest;

public interface StatesDao {
	public boolean saveStates(StatesRequest statesRequestObj) throws SQLException;
	public int getAllStatesCount() throws SQLException;
    public boolean updateStates(StatesRequest statesRequestObj) throws SQLException;
    public List<States> getAll(StatesRequest statesRequestObj) throws SQLException;
	public List<States> getStateById(StatesRequest statesRequestObj) throws SQLException;
    public boolean deleteStates(StatesRequest statesRequestObj) throws SQLException;
	


}
