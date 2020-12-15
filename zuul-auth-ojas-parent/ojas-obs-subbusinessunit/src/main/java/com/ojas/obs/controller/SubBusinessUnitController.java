package com.ojas.obs.controller;

import static com.ojas.obs.constants.SubBusinessUnitConstants.GET;
import static com.ojas.obs.constants.SubBusinessUnitConstants.SET;
import static com.ojas.obs.constants.SubBusinessUnitConstants.UPDATE;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETALL;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETBYID;
import static com.ojas.obs.constants.SubBusinessUnitConstants.DATAINVALID;
import static com.ojas.obs.constants.SubBusinessUnitConstants.INSIDECATCHBLOCK;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETHEADS;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.SubBusinessUnitFacade;
import com.ojas.obs.model.SubBusinessUnit;
import com.ojas.obs.request.SubBusinessUnitRequest;

/**
 * 
 * @author asuneel
 *
 */
//@RequestMapping(SUBBUSINESSUNIT)
@Controller
public class SubBusinessUnitController {

	@Autowired
	SubBusinessUnitFacade subBusinessUnitFacade;
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param subBusinessRequest
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@PostMapping(SET)
	public ResponseEntity<Object> setSubBusinessUnit(@RequestBody SubBusinessUnitRequest subBusinessUnitRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws SQLException {

		logger.info("incoming requests " + subBusinessUnitRequest);

		try {
			List<SubBusinessUnit> subBusinessUnitList = subBusinessUnitRequest.getSubBusinessUnitModel();
			if (subBusinessUnitRequest.getTransactionType() == null || null == subBusinessUnitList) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("request is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			for (SubBusinessUnit subBusinessUnit : subBusinessUnitList) {

				if ((subBusinessUnitRequest.getTransactionType().equalsIgnoreCase("save")
						|| subBusinessUnitRequest.getTransactionType().equalsIgnoreCase("update"))
						&& ((null == subBusinessUnit.getName() || subBusinessUnit.getName().isEmpty())
								|| (subBusinessUnit.getBusinessUnitId() == null))) {

					ErrorResponse error = new ErrorResponse();
					logger.debug("requested data is  invalid");
					error.setStatusCode("422");
					error.setMessage("Request is  invalid");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}

				if (subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(UPDATE)
						&& subBusinessUnit.getId() == null) {
					ErrorResponse error = new ErrorResponse();
					logger.debug("requested data is  invalid");
					error.setMessage("Request is  invalid");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}

			}
			return subBusinessUnitFacade.setSubBusinessUnit(subBusinessUnitRequest);

		} catch (DuplicateKeyException exception) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getCause().getLocalizedMessage());
			error.setMessage("DuplicateKeyException");
			error.setStatusCode("409");
			logger.error("DuplicateKeyException");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error(INSIDECATCHBLOCK);
			ErrorResponse error = new ErrorResponse();
			logger.error(DATAINVALID);
			error.setStatusMessage(exception.getMessage());
			error.setMessage("SQLException");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		catch (Exception exception) {
			logger.error(INSIDECATCHBLOCK);
			ErrorResponse error = new ErrorResponse();
			logger.error(DATAINVALID);
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	/**
	 * 
	 * @param subBusinessRequest
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	@PostMapping(GET)
	public ResponseEntity<Object> getSubBusinessUnit(@RequestBody SubBusinessUnitRequest subBusinessUnitRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		try {
			if (!subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(GETALL)
					&& !subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(GETBYID)
					&& !subBusinessUnitRequest.getTransactionType().equalsIgnoreCase(GETHEADS)) {
				logger.debug("request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Transaction type miss match");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			return subBusinessUnitFacade.getSubBusinessUnit(subBusinessUnitRequest);

		} catch (SQLException exception) {
			logger.error("inside SubBusinessUnitFacade catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.error("data is  invalid");
			error.setStatusMessage(exception.getMessage());
			error.setMessage("SQLException");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		catch (Exception exception) {
			logger.error(INSIDECATCHBLOCK);
			ErrorResponse error = new ErrorResponse();
			logger.error(DATAINVALID);
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}
}
