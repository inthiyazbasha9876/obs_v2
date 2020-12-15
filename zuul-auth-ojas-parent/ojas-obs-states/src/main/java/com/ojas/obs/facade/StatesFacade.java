package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.StatesRequest;

public interface StatesFacade {
	ResponseEntity<Object> getStates(StatesRequest statesRequestObj) throws SQLException;

	ResponseEntity<Object> setStates(StatesRequest statesRequestObj) throws SQLException;
}
