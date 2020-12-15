package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.model.EmployeeExperience;
import com.ojas.obs.repository.EmployeeExperienceRepository;
import com.ojas.obs.request.EmployeeExperienceRequest;
import com.ojas.obs.response.EmployeeExperienceResponse;

@Service
public class EmployeeExperienceFacadeImpl {

	@Autowired
	EmployeeExperienceRepository empExpRepo;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> setEmpExp(EmployeeExperienceRequest empExpReq) {

		EmployeeExperienceResponse response = null;

		logger.debug("request coming to the facade");

		if (empExpReq.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new EmployeeExperienceResponse();

			List<EmployeeExperience> exp=empExpReq.getEmpExperienceList();
			 List<EmployeeExperience> saved=empExpRepo.saveAll(exp);

			if (saved !=null) { 
				logger.debug("save method");
				response.setStatusCode("200");
				response.setMessage("Experience details saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("409");
			response.setMessage("failed to save");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (empExpReq.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new EmployeeExperienceResponse();

			for (EmployeeExperience experience : empExpReq.getEmpExperienceList()) {
				Integer id = empExpReq.getEmpExperienceList().get(0).getEmpExperienceId();
				Optional<EmployeeExperience> findById = empExpRepo.findById(id);
				if (findById.isPresent() && findById.get().getEmpExperienceId() != null) {
					empExpRepo.save(experience);
					response.setStatusCode("200");
					response.setMessage("Experience details updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}

		if (empExpReq.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new EmployeeExperienceResponse();

			Integer id = empExpReq.getEmpExperienceList().get(0).getEmpExperienceId();
			EmployeeExperience ser = empExpRepo.getOne(id);

			ser.setStatus(!ser.getStatus());
			EmployeeExperience locationdata = empExpRepo.save(ser);

			if (locationdata.getEmpExperienceId() != null) {
				response.setStatusCode("200");
				response.setMessage("Experience details deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getEmpExp(EmployeeExperienceRequest empExpReq) {
		List<EmployeeExperience> list = empExpReq.getEmpExperienceList();
		logger.debug(" getAll customer details");
		EmployeeExperienceResponse response = null;
		List<EmployeeExperience> getAll = null;

		if (empExpReq.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = empExpRepo.findAll();
			if (getAll.isEmpty()) {
				response = new EmployeeExperienceResponse();
				response.setEmpExperienceList(new ArrayList<EmployeeExperience>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			response = new EmployeeExperienceResponse();
			response.setEmpExperienceList(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}
		if (empExpReq.getTransactionType().equalsIgnoreCase(GETBYID) && list.get(0).getEmpExperienceId() != null) {

			for (EmployeeExperience type : list) {

				if (type.getEmpExperienceId() != null) {
					Integer id = empExpReq.getEmpExperienceList().get(0).getEmpExperienceId();

					ArrayList<EmployeeExperience> listDetails = new ArrayList<EmployeeExperience>();

					EmployeeExperience getById = empExpRepo.findById(id).orElse(new EmployeeExperience());

					listDetails.add(getById);

					if (getById != null && getById.getEmpExperienceId() != null) {
						response = new EmployeeExperienceResponse();
						response.setEmpExperienceList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
					response = new EmployeeExperienceResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}


	}


