package com.ojas.obs.contractcompany.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.contractcompany.request.ContractCompanyRequest;

public interface ContractCompanyFacade {
	public ResponseEntity<Object> saveDetails(ContractCompanyRequest companyRequest);

	public ResponseEntity<Object> getAllDetails(ContractCompanyRequest companyRequest);

}
