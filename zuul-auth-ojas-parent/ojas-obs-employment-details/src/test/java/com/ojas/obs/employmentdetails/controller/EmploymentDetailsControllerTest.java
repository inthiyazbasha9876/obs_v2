
package com.ojas.obs.employmentdetails.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.ojas.obs.employmentdetails.exception.DataNotInsertedException;
import com.ojas.obs.employmentdetails.exception.EmploymentDetailsException;
import com.ojas.obs.employmentdetails.facade.EmploymentDetailsFacade;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsRequest;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsResponse;

@RunWith(MockitoJUnitRunner.class)
public class EmploymentDetailsControllerTest {

	@InjectMocks
	private EmploymentDetailsController employmentDetailsController;

	@Mock
	private EmploymentDetailsFacade employmentDetailsFacade;

	private EmploymentDetailsRequest employmentDetailsRequest = null;

	@Test
	public void testSetEmploymentDetails() throws EmploymentDetailsException, DataNotInsertedException {
		employmentDetailsRequest = new EmploymentDetailsRequest();

		when(employmentDetailsFacade.saveEmploymentDetails(employmentDetailsRequest))
				.thenReturn(new EmploymentDetailsResponse());

		assertEquals(HttpStatus.OK,
				employmentDetailsController.setEmploymentDetails(employmentDetailsRequest).getStatusCode());
	}
	

	@Test(expected = EmploymentDetailsException.class)
	public void testSetEmploymentDetailsWithNullRequest() throws EmploymentDetailsException, DataNotInsertedException {

		employmentDetailsController.setEmploymentDetails(employmentDetailsRequest);
	}

	@Test
	public void testGetEmploymentDetails() throws EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();

		when(employmentDetailsFacade.viewEmploymentDetails(employmentDetailsRequest))
				.thenReturn(new EmploymentDetailsResponse());

		assertEquals(HttpStatus.OK,
				employmentDetailsController.getEmploymentDetails(employmentDetailsRequest).getStatusCode());
	}

	@Test(expected = EmploymentDetailsException.class)
	public void testGetEmploymentDetailsWithNullRequest() throws EmploymentDetailsException {

		employmentDetailsController.getEmploymentDetails(employmentDetailsRequest);
	}
}
