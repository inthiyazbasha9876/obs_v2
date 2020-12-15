package com.ojas.obs.tms.facade;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.tms.request.TmsRequest;

public interface TmsFacade {
	ResponseEntity<Object> setTimesheet(TmsRequest request) throws SQLException, IOException;
	ResponseEntity<Object> getTimesheet(TmsRequest request) throws SQLException, IOException, URISyntaxException;
}
