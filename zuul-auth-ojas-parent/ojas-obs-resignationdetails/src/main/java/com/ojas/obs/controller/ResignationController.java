package com.ojas.obs.controller;

import static com.ojas.obs.constants.ResignationConstants.GET;
import static com.ojas.obs.constants.ResignationConstants.SAVE;
import static com.ojas.obs.constants.ResignationConstants.SET;
import static com.ojas.obs.constants.ResignationConstants.UPDATE;
import static com.ojas.obs.constants.ResignationConstants.UPDATESTATE;
import static com.ojas.obs.constants.ResignationConstants.INVALIDREQUEST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.errormessage.ErrorResponse;
import com.ojas.obs.facade.ResignationFacadeInterface;
import com.ojas.obs.model.Resignation;
import com.ojas.obs.request.ResignationRequest;

@RestController
//@RequestMapping(RESIGNATION)
public class ResignationController {

	@Autowired
	ResignationFacadeInterface resignationFacadeInterface;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	String transactionType = null;

	@PostMapping(SET)
	public ResponseEntity<Object> setResignationController(@RequestBody ResignationRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		try {

			List<Resignation> list = request.getResignation();
			if (null == request || null == list) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage(INVALIDREQUEST);
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
			transactionType = request.getTransactionType();
			for (Resignation res : list) {
				if (transactionType.equalsIgnoreCase(SAVE)
						&& ((res.getEmployeeId() == null || res.getEmployeeId().isEmpty())
								|| null == res.getFinalSettlementDate() || null == res.getLeavingDate()
								|| (null == res.getLeavingReason() || res.getLeavingReason().isEmpty())
								|| null == res.getResignationSubmittedOn()
								|| (null == res.getResignationType() || res.getResignationType().isEmpty()))) {
					ErrorResponse error = new ErrorResponse();
					error.setMessage("set request is invalid");
					return new ResponseEntity<>(error, HttpStatus.CONFLICT);

				}

				if (transactionType.equalsIgnoreCase(UPDATE)
						&& ((res.getId() == 0) || (res.getEmployeeId() == null || res.getEmployeeId().isEmpty())
								|| null == res.getFinalSettlementDate() || null == res.getLeavingDate()
								|| null == res.getResignationSubmittedOn()
								|| (null == res.getResignationType() || res.getResignationType().isEmpty())
								|| (res.getState() == null || res.getState().isEmpty()))) {

					ErrorResponse error = new ErrorResponse();
					error.setMessage("set request is invalid");
					return new ResponseEntity<>(error, HttpStatus.CONFLICT);

				}

				if (transactionType.equalsIgnoreCase(UPDATESTATE) && res.getId() == 0 ||

						res.getState() == null || res.getState().isEmpty()) {

					ErrorResponse error = new ErrorResponse();
					error.setMessage("state set request is invalid");
					return new ResponseEntity<>(error, HttpStatus.CONFLICT);

				}

			}

			return resignationFacadeInterface.setResignation(request);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(e.getMessage());
			error.setMessage("Exception Occurred");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@PostMapping(GET)
	public ResponseEntity<Object> getResponse(@RequestBody ResignationRequest request,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			logger.info("in ccontroller clas");
			if (null == request) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage(INVALIDREQUEST);
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
			return resignationFacadeInterface.getResignation(request);
		}

		catch (Exception exception) {

			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("Exception Occurred");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

}
