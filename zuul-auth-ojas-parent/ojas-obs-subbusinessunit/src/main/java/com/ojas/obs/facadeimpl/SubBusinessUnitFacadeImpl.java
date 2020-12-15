package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.SubBusinessUnitConstants.FAILED;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETALL;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETBYID;
import static com.ojas.obs.constants.SubBusinessUnitConstants.SAVE;
import static com.ojas.obs.constants.SubBusinessUnitConstants.SUCCESS;
import static com.ojas.obs.constants.SubBusinessUnitConstants.UPDATE;
import static com.ojas.obs.constants.SubBusinessUnitConstants.DELETE;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETHEADS;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.SubBusinessUnitDao;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.SubBusinessUnitFacade;
import com.ojas.obs.model.SubBusinessUnit;
import com.ojas.obs.request.SubBusinessUnitRequest;
import com.ojas.obs.response.SubBusinessUnitResponse;

/**
 * 
 * @author asuneel
 *
 */
@Service
public class SubBusinessUnitFacadeImpl implements SubBusinessUnitFacade {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	SubBusinessUnitDao subBusinessDAO;

	@Override
	public ResponseEntity<Object> setSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest) throws SQLException {

		logger.info("inside saveSubBusinessUnit method : " + subBusinessUnitRequest);
		SubBusinessUnitResponse subBusinessUnitResponse = null;
		

			if (subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				subBusinessUnitResponse = new SubBusinessUnitResponse();
				boolean saveSubBusinessUnit = subBusinessDAO.saveSubBusinessUnit(subBusinessUnitRequest);
				logger.info("inside  save condition.****** : " + saveSubBusinessUnit);
				if (saveSubBusinessUnit) {
					subBusinessUnitResponse.setMessage("Successfully record added");
					subBusinessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.OK);
				} else {
					ErrorResponse error = new ErrorResponse();
					error.setMessage(FAILED);
					error.setStatusCode("409");
					return new ResponseEntity<>(error, HttpStatus.CONFLICT);
				}

			}
			if (subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				subBusinessUnitResponse = new SubBusinessUnitResponse();
				boolean updateSubBusinessUnit = subBusinessDAO.updateSubBusinessUnit(subBusinessUnitRequest);
				logger.info("inside  update condition.****** : " + updateSubBusinessUnit);
				if (updateSubBusinessUnit) {
					subBusinessUnitResponse.setMessage("Successfully record updated");
					subBusinessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.OK);
				} else {
					ErrorResponse error = new ErrorResponse();
					error.setMessage("record not updated");
					error.setStatusCode("409");
					return new ResponseEntity<>(error, HttpStatus.CONFLICT);
				}
			}
			
			if (subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				subBusinessUnitResponse = new SubBusinessUnitResponse();
				boolean deleteSubBusinessUnit = subBusinessDAO.deleteSubBusinessUnit(subBusinessUnitRequest);
				logger.info("inside  update condition.****** : " + deleteSubBusinessUnit);
				if (deleteSubBusinessUnit) {
					subBusinessUnitResponse.setMessage("Successfully record updated");
					subBusinessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.OK);
				} else {
					ErrorResponse error = new ErrorResponse();
					error.setMessage("record not updated");
					error.setStatusCode("409");
					return new ResponseEntity<>(error, HttpStatus.CONFLICT);
				}
			}
			
			ErrorResponse error = new ErrorResponse();
			error.setMessage(FAILED);
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			
	

	}

	@Override
	public ResponseEntity<Object> getSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest) throws SQLException{

		SubBusinessUnitResponse subBusinessUnitResponse = new SubBusinessUnitResponse();
		logger.info("inside getSubBusinessUnit in SubBusinessUnitFacade.***");

		
			List<SubBusinessUnit> allSubBusinessUnitDetails = null;
			List<String> sbuHeads = null;
			if (subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			 allSubBusinessUnitDetails = subBusinessDAO.getAllSubBusinessUnitDetails();
			}
			if (subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
				Integer id = subBusinessUnitRequest.getSubBusinessUnitModel().get(0).getId();
				 allSubBusinessUnitDetails = subBusinessDAO.getByIdSubBusinessUnitDetails(id);
				}
			if(subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(GETHEADS)) {
				sbuHeads = subBusinessDAO.getSbuHeads();
				if (sbuHeads == null || sbuHeads.isEmpty()) {
					subBusinessUnitResponse.setMessage("No records found");
					subBusinessUnitResponse.setSbuHeads(sbuHeads);
					return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.CONFLICT);
				} else {
					subBusinessUnitResponse.setSbuHeads(sbuHeads);
					subBusinessUnitResponse.setMessage(SUCCESS);
					subBusinessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.OK);
				}
			}
			if (allSubBusinessUnitDetails == null || allSubBusinessUnitDetails.isEmpty()) {
				subBusinessUnitResponse.setMessage("No records found");
				subBusinessUnitResponse.setSubBusinessUnitList(allSubBusinessUnitDetails);
				return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.CONFLICT);
			} else {
				subBusinessUnitResponse.setSubBusinessUnitList(allSubBusinessUnitDetails);
				subBusinessUnitResponse.setSbuHeads(sbuHeads);
				subBusinessUnitResponse.setMessage(SUCCESS);
				subBusinessUnitResponse.setStatusCode("200");
				return new ResponseEntity<>(subBusinessUnitResponse, HttpStatus.OK);
			}

		

	}

}
