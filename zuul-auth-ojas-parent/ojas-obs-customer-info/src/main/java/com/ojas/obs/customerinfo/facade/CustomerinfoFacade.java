package com.ojas.obs.customerinfo.facade;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.customerinfo.request.CustomerinfoRequest;

public interface CustomerinfoFacade {

	ResponseEntity<Object> setCustomer(CustomerinfoRequest customerrequest) throws SQLException, IOException;

	ResponseEntity<Object> getCustomerinfo(CustomerinfoRequest request)
			throws SQLException, IOException, URISyntaxException;

}
