package com.obs.employeeCertificationDetails.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;

public interface CertificationDetailsFacade {
	ResponseEntity<Object> setCertificationDetails(CertificationDetailsRequest certificationDetailsRequest) throws SQLException;

	ResponseEntity<Object> getCertificationDetails(CertificationDetailsRequest certificationDetailsRequest)throws SQLException;
}
