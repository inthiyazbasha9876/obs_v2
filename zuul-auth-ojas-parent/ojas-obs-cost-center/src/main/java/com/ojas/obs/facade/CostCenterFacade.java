package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.model.CostCenterRequest;

public interface CostCenterFacade {

	public ResponseEntity<Object> set(CostCenterRequest costReq) throws Exception;

	public ResponseEntity<Object> get(CostCenterRequest costReq) throws Exception;

	public ResponseEntity<Object> validateCostCenter(CostCenterRequest costReq) throws Exception;

	public ResponseEntity<Object> deleteCenterCode(CostCenterRequest costReq) throws Exception;
}
