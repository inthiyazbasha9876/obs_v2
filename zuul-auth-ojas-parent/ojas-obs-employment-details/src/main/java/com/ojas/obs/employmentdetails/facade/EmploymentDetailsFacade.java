package com.ojas.obs.employmentdetails.facade;

import com.ojas.obs.employmentdetails.exception.DataNotInsertedException;
import com.ojas.obs.employmentdetails.exception.EmploymentDetailsException;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsRequest;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsResponse;

public interface EmploymentDetailsFacade {
	EmploymentDetailsResponse saveEmploymentDetails(EmploymentDetailsRequest employmentDetailsRequest)
			throws EmploymentDetailsException, DataNotInsertedException;

	EmploymentDetailsResponse viewEmploymentDetails(EmploymentDetailsRequest employmentDetailsRequest)
			throws EmploymentDetailsException;

}
