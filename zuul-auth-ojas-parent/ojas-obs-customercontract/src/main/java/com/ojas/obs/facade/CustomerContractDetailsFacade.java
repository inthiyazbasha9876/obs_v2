package com.ojas.obs.facade;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.CustomerContractDetailsRequest;

public interface CustomerContractDetailsFacade {

	public ResponseEntity<Object> setCustomerContractDetails(
			CustomerContractDetailsRequest customerContractDetailsRequest) throws SQLException, IOException;

	public ResponseEntity<Object> getCustomerContractDetails(
			CustomerContractDetailsRequest customerContractDetailsRequest) throws SQLException, IOException;

}
