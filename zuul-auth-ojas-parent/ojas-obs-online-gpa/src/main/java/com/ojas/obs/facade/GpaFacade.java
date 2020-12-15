package com.ojas.obs.facade;

import static com.ojas.obs.constants.GpaServiceConstants.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ojas.obs.dao.GpaPlanDao;
import com.ojas.obs.errorMessage.ErrorResponse;
import com.ojas.obs.model.GpaPlan;
import com.ojas.obs.request.GpaRequest;
import com.ojas.obs.response.GpaResponse;

@Service
public class GpaFacade {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	GpaPlanDao gpaPlanDaoImpl;

	/**
	 * 
	 * @param gpaRequest
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> saveGpaPlan(GpaRequest gpaRequest) throws SQLException {

		logger.debug("The request details in Facade set method: " + gpaRequest);
		GpaResponse gpaResponse = null;
		try {

			if (gpaRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				gpaResponse = new GpaResponse();
				boolean saveGpa = gpaPlanDaoImpl.saveGpaPlan(gpaRequest);
				logger.debug("inside  save condition .****** : " + saveGpa);
				if (saveGpa) {
					logger.debug("inside  save method Success fully record added.****** : " + saveGpa);
					gpaResponse.setMessage("Success fully record added");
					gpaResponse.setStatusCode("200");
					return new ResponseEntity<>(gpaResponse, HttpStatus.OK);
				} else {
					gpaResponse.setMessage("Failed to add record in gpaFacade save method:" + saveGpa);
					gpaResponse.setMessage(FAILED);
					gpaResponse.setStatusCode("200");
					return new ResponseEntity<>(gpaResponse, HttpStatus.CONFLICT);
				}
			}

			if (gpaRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				gpaResponse = new GpaResponse();
				boolean updateGpa = gpaPlanDaoImpl.updateGpa(gpaRequest);
				if (updateGpa) {
					logger.debug("inside  save method Success fully record updated.****** : " + updateGpa);
					gpaResponse.setMessage("Success fully record updated");
					gpaResponse.setStatusCode("200");
					return new ResponseEntity<>(gpaResponse, HttpStatus.OK);
				} else {
					gpaResponse.setMessage("Failed to update record in gpaFacade save method:" + updateGpa);
					gpaResponse.setMessage(FAILED);
					gpaResponse.setStatusCode("200");
					return new ResponseEntity<>(gpaResponse, HttpStatus.CONFLICT);
				}
			}

//			if (gpaRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
//				gpaResponse = new GpaResponse();
//				Integer planId = gpaRequest.getGpaPlan().getGpaPlanId();
//				boolean deleteGpaRecord = gpaPlanDaoImpl.deleteGpaRecord(planId);
//
//				logger.debug("inside  delete condition.****** and id is : " + planId);
//				if (deleteGpaRecord) {
//					gpaResponse.setStatusMessage("Success fully record deleted");
//					return new ResponseEntity<>(gpaResponse, HttpStatus.OK);
//				} else {
//					gpaResponse.setStatusMessage(FAILED);
//					return new ResponseEntity<>(gpaResponse, HttpStatus.CONFLICT);
//				}
			// }
		} catch (DuplicateKeyException e) {
			logger.debug(" inside gpaFacade catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("  GpaFacade saveGpaPlan() catch block Duplicates are not allowed.");
			 error.setStatusMessage(e.getMessage());
			error.setMessage("Duplicates are not allowed.");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		} catch (SQLException e) {
			logger.debug(" inside gpaFacade catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug(" GpaFacade saveGpaPlan() catch block data is  invalid");
			 error.setStatusMessage(e.getMessage());
			error.setMessage("SQL Exception");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		} catch (Exception exception) {
			logger.debug(" inside gpaFacade catch blockkkk.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug(" GpaFacade saveGpaPlan() catch block data is  invalid");
			error.setStatusMessage(exception.getMessage());
			error.setMessage("Exception Occuerd");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		return null;
	}

	/**
	 * 
	 * @param gpaRequest
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> getAllGpaDetails(GpaRequest gpaRequest) throws SQLException {
		GpaResponse gpaResponse = new GpaResponse();

		try {

			if (gpaRequest.getTransactionType().equalsIgnoreCase(GETALL)) {

				logger.debug(" inside getAllGpaDetails in gpaFacade.***");

				List<GpaPlan> allGpaDetails = gpaPlanDaoImpl.getAllGpaDetails(gpaRequest);

				/*
				 * List<GpaPlan> recordsPerPage = gpaPlanDaoImpl.getPageRecords(allGpaDetails,
				 * gpaRequest.getPageSize(), gpaRequest.getPageNum());
				 */
				if (allGpaDetails == null || allGpaDetails.isEmpty()) {
					gpaResponse.setGpa(new ArrayList<>());
					logger.debug(" inside  getAll method in FACADE class, getting all records from dao is null:"
							+ allGpaDetails);
					gpaResponse.setMessage("No records found");
					gpaResponse.setStatusCode("200");
					return new ResponseEntity<>(gpaResponse, HttpStatus.CONFLICT);
				} else {

					// if (gpaRequest.getPageNum() == 0 || gpaRequest.getPageSize() == 0) {
					logger.debug(
							" inside  getAll method in FACADE class, getting all records from dao successfully :"
									+ allGpaDetails);
					gpaResponse.setGpa(allGpaDetails);
					gpaResponse.setMessage("Success");
					gpaResponse.setStatusCode("200");
					 
					return new ResponseEntity<>(gpaResponse, HttpStatus.OK);
				} /*
					 * else { int count = gpaPlanDaoImpl.getAllGpaDetailsCount();
					 * gpaResponse.setGpa(recordsPerPage); gpaResponse.setStatusMessage("Success");
					 * gpaResponse.setPageNum(gpaRequest.getPageNum());
					 * gpaResponse.setPageSize(gpaRequest.getPageSize());
					 * 
					 * gpaResponse.setTotalCount(count); }
					 * 
					 */ // return new ResponseEntity<>(gpaResponse, HttpStatus.OK);

			}
			if (gpaRequest.getTransactionType().equalsIgnoreCase(GETID)) {

				List<GpaPlan> gpaPlanList = gpaRequest.getGpaPlan();
				for (GpaPlan gpaPlan : gpaPlanList) {
					logger.debug("inside getByidGpaDetails in gpaFacade.***");
					List<GpaPlan> allGpaDetails = null;
					// System.out.println("Inside
					// facade"+gpaRequest.getGpaPlan().get(0).getGpaPlanId());
					/*
					 * if (gpaPlan.getGpaPlanId() != 0 && gpaPlan.getGpaPlanId() != null) {
					 * allGpaDetails = gpaPlanDaoImpl.getByGpaId(gpaPlan.getGpaPlanId()); }
					 */
					allGpaDetails = gpaPlanDaoImpl.getById(gpaRequest);
					if (allGpaDetails == null || allGpaDetails.isEmpty()) {
						gpaResponse.setGpa(new ArrayList<>());
						gpaResponse.setMessage("No records found");
						gpaResponse.setStatusCode("200");
						logger.debug("Inside  getAllGpaDetails method in FACADE class, getting  record from dao is null:"
								+ allGpaDetails);
						 
						return new ResponseEntity<>(gpaResponse, HttpStatus.CONFLICT);
					} else {
						gpaResponse.setGpa(allGpaDetails);
						logger.debug("Inside  getAllGpaDetails method in FACADE class, getting  record from dao is success:"
								+ allGpaDetails);
						gpaResponse.setMessage("Success");
						gpaResponse.setStatusCode("200");  
						return new ResponseEntity<>(gpaResponse, HttpStatus.OK);
					}
					// return new ResponseEntity<>(gpaResponse, HttpStatus.OK);
				}
			}
		} catch (SQLException e) {
			logger.debug(" Inside gpaFacade catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("catch block data is  invalid"+e.getMessage());
			 error.setStatusMessage(e.getMessage());
			 error.setStatusCode("409");
			error.setMessage("SQL Exception");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		} 
		catch (Exception e) {
			
			logger.error(" inside catch block of getMethod ServiceClass ***********"+e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Experience occured");
			error.setStatusMessage(e.getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		return null;

	}

}
