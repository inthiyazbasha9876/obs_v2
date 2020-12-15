package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.EmployeeExperienceFacadeImpl;
import com.ojas.obs.model.EmployeeExperience;
import com.ojas.obs.request.EmployeeExperienceRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
public class EmployeeExperienceController {

	@Autowired
	EmployeeExperienceFacadeImpl empExpFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/set")
	public ResponseEntity<Object> setEmpExp(@RequestBody EmployeeExperienceRequest emprequest,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("data coming into controller");

		List<EmployeeExperience> exp = emprequest.getEmpExperienceList();
		
		try {
			if (emprequest.getEmpExperienceList() == null || exp.isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for(EmployeeExperience e:exp) {
				
			if (emprequest.getTransactionType().equalsIgnoreCase(SAVE)
					&&e.getEmpExperience()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage(" Field is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

		
				if (emprequest.getTransactionType().equalsIgnoreCase(UPDATE)
						&& emprequest.getEmpExperienceList().get(0).getEmpExperience() == null
						|| emprequest.getTransactionType().equalsIgnoreCase(DELETE)
								&& emprequest.getEmpExperienceList().get(0).getEmpExperience() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
				return empExpFacadeImpl.setEmpExp(emprequest);

			} catch (DuplicateKeyException e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage("duplicate key Exception");
				error.setStatusMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			} catch (Exception e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage(e.getMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			}
		}
	@PostMapping(value = "/get")
	public ResponseEntity<Object> getEmpExp(@RequestBody EmployeeExperienceRequest empExpReq,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if (empExpReq == null || empExpReq.getEmpExperienceList() == null
					|| empExpReq.getEmpExperienceList().isEmpty() || empExpReq.getTransactionType() == null
					|| empExpReq.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (empExpReq.getTransactionType().equalsIgnoreCase(GETBYID)
					&& empExpReq.getEmpExperienceList().get(0).getEmpExperienceId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return empExpFacadeImpl.getEmpExp(empExpReq);
			
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
