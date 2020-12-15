package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.BudgetRequest;
@Service
public interface BudgetFacade {
	public ResponseEntity<Object> saveDetails(BudgetRequest budgetRequestObject);

	public ResponseEntity<Object> getDetails(BudgetRequest budgetRequestObject);
}
