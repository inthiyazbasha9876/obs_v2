package com.ojas.obs.employmentdetails.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ojas.obs.employmentdetails.dao.EmploymentDetailsDAO;
import com.ojas.obs.employmentdetails.exception.DataNotInsertedException;
import com.ojas.obs.employmentdetails.exception.EmploymentDetailsException;
import com.ojas.obs.employmentdetails.model.EmploymentDetails;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsRequest;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsResponse;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class EmploymentDetailsFacadeImplTest {

	@InjectMocks
	private EmploymentDetailsFacadeImpl employmentDetailsFacadeImpl;

	@Mock
	private EmploymentDetailsDAO employmentDetailsDAO;

	private EmploymentDetailsRequest employmentDetailsRequest = null;

	public List<EmploymentDetails> getEmploymentDetailsRequest() {
		List<EmploymentDetails> list = new ArrayList<>();
		EmploymentDetails ed=new EmploymentDetails();
		//Date date=new Date(2019, 01, 02);
		ed.setId(101);
		ed.setEmployeeId("11");
		//ed.setJoiningDate(date);
		ed.setJoiningDate("2019-12-10");
		ed.setResourceType("1");
		ed.setBondStatus(true);
		/*
		 * ed.setResignationDate(date); ed.setExitDate(date); ed.setSeparationType(1);
		 */
		ed.setCreatedBy("sdhs");	
		list.add(ed);
		employmentDetailsRequest = new EmploymentDetailsRequest();
		employmentDetailsRequest .setTransactionType("save");
		employmentDetailsRequest.setEmploymentDetails(list);
		return list;
		
	}
	
	@Ignore
	@Test
	public void testSaveEmployementDetailsForInsert() throws DataNotInsertedException, EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();
		employmentDetailsRequest.setTransactionType("save");
		employmentDetailsRequest.setEmploymentDetails(Arrays.asList(new EmploymentDetails()));

		EmploymentDetailsResponse emplDetailsResponse = new EmploymentDetailsResponse();
		emplDetailsResponse.setStatusCode("201");
		emplDetailsResponse.setStatusMessage("Data is inserted successfully");
		when(employmentDetailsDAO.saveEmploymentDetails(getEmploymentDetailsRequest())).thenReturn(true);
//		when(employmentDetailsDAO
//				.saveEmploymentDetails((List<EmploymentDetails>) Matchers.anyCollectionOf(EmploymentDetails.class)))
//						.thenReturn(true);

		assertEquals("200",
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusCode());
//		assertEquals(emplDetailsResponse.getStatusMessage(),
//				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusMessage());
	}

	@Test
	public void testSaveEmployementDetailsForUpdate() throws DataNotInsertedException, EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();
		employmentDetailsRequest.setTransactionType("update");
		employmentDetailsRequest.setEmploymentDetails(Arrays.asList(new EmploymentDetails()));

		EmploymentDetailsResponse emplDetailsResponse = new EmploymentDetailsResponse();
		emplDetailsResponse.setStatusCode("201");
		emplDetailsResponse.setStatusMessage("Data is updated successfully");

		when(employmentDetailsDAO
				.updateEmploymentDetails((List<EmploymentDetails>) Matchers.anyCollectionOf(EmploymentDetails.class)))
						.thenReturn(true);

		assertEquals(emplDetailsResponse.getStatusCode(),
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusCode());
		assertEquals(emplDetailsResponse.getStatusMessage(),
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusMessage());
	}

	@Test
	public void testSaveEmployementDetailsForDelete() throws DataNotInsertedException, EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();
		employmentDetailsRequest.setTransactionType("delete");
		employmentDetailsRequest.setEmploymentDetails(Arrays.asList(new EmploymentDetails()));

		EmploymentDetailsResponse emplDetailsResponse = new EmploymentDetailsResponse();
		emplDetailsResponse.setStatusCode("201");
		emplDetailsResponse.setStatusMessage("Data is deleted successfully");

		when(employmentDetailsDAO
				.deleteEmploymentDetails((List<EmploymentDetails>) Matchers.anyCollectionOf(EmploymentDetails.class)))
						.thenReturn(true);

		assertEquals(emplDetailsResponse.getStatusCode(),
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusCode());
		assertEquals(emplDetailsResponse.getStatusMessage(),
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusMessage());
	}

	@Test(expected = EmploymentDetailsException.class)
	public void testSaveEmployementDetailsWithNullvalues() throws DataNotInsertedException, EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();

		employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest);

	}

	@Test
	public void testSaveEmployementDetailsWithNoTransactionType()
			throws DataNotInsertedException, EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();
		employmentDetailsRequest.setTransactionType("insert");
		employmentDetailsRequest.setEmploymentDetails(Arrays.asList(new EmploymentDetails()));

		EmploymentDetailsResponse emplDetailsResponse = new EmploymentDetailsResponse();
		emplDetailsResponse.setStatusCode("400");
		emplDetailsResponse.setStatusMessage("Requested transaction type is wrong");

		assertEquals(emplDetailsResponse.getStatusCode(),
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusCode());
		assertEquals(emplDetailsResponse.getStatusMessage(),
				employmentDetailsFacadeImpl.saveEmploymentDetails(employmentDetailsRequest).getStatusMessage());

	}

	@Test
	public void testViewEmployementDetails() throws EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();
		employmentDetailsRequest.setTransactionType("getAll");

		EmploymentDetailsResponse emplDetailsResponse = new EmploymentDetailsResponse();
		emplDetailsResponse.setStatusCode("200");
		emplDetailsResponse.setStatusMessage("Employee details found");
		emplDetailsResponse.setEmploymentDetailsList(Arrays.asList(new EmploymentDetails()));

		when(employmentDetailsDAO.getAllEmploymentDetails()).thenReturn(emplDetailsResponse.getEmploymentDetailsList());

		assertEquals(emplDetailsResponse.getStatusCode(),
				employmentDetailsFacadeImpl.viewEmploymentDetails(employmentDetailsRequest).getStatusCode());

		/*
		 * assertEquals("Employee details found",
		 * employmentDetailsFacadeImpl.viewEmploymentDetails(employmentDetailsRequest).
		 * getStatusMessage());
		 * 
		 * assertEquals(emplDetailsResponse.getEmploymentDetailsList().size(),
		 * employmentDetailsFacadeImpl
		 * .viewEmploymentDetails(employmentDetailsRequest).getEmploymentDetailsList().
		 * size());
		 */
	}

	@Test(expected = EmploymentDetailsException.class)
	public void testViewEmployementDetailsWithNoTransactionType()
			throws DataNotInsertedException, EmploymentDetailsException {
		employmentDetailsRequest = new EmploymentDetailsRequest();
		
		employmentDetailsFacadeImpl.viewEmploymentDetails(employmentDetailsRequest);

	}
 
}
