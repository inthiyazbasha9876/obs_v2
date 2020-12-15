package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.ServicesRequest;

public interface ServicesFacade {
	ResponseEntity<Object> getServices(ServicesRequest servicesRequestObj) throws SQLException;

	ResponseEntity<Object> setServices(ServicesRequest servicesRequestObj) throws SQLException;
}
