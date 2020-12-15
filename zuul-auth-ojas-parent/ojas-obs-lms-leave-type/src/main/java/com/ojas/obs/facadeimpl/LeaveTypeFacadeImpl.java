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

import com.ojas.obs.model.LeaveType;
import com.ojas.obs.repository.LeaveTypeRepository;
import com.ojas.obs.request.LeaveTypeRequest;
import com.ojas.obs.response.LeaveTypeResponse;

@Service
public class LeaveTypeFacadeImpl {
	@Autowired
	private LeaveTypeRepository leaveTypeRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(LeaveTypeRequest leaveTypeRequestObject) {

		LeaveTypeResponse response = null;

		logger.debug("request coming to the facade");

		if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new LeaveTypeResponse();

			List<LeaveType> geo = leaveTypeRequestObject.getLeaveTypeList();
			List<LeaveType> save = leaveTypeRepo.saveAll(geo);

			if (!save.isEmpty()) {
				logger.debug("save method"); 
				response.setStatusCode("200");
				response.setMessage("Leave type details saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("409");
			response.setMessage("failed to save");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new LeaveTypeResponse();

			for (LeaveType tech : leaveTypeRequestObject.getLeaveTypeList()) {
				Integer id = leaveTypeRequestObject.getLeaveTypeList().get(0).getLeaveTypeId();
				Optional<LeaveType> findById = leaveTypeRepo.findById(id);
				if (findById.isPresent() && findById.get().getLeaveTypeId() != null) {
					leaveTypeRepo.save(tech);
					response.setStatusCode("200");
					response.setMessage("LeaveType details updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}  

		if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new LeaveTypeResponse();

			Integer id = leaveTypeRequestObject.getLeaveTypeList().get(0).getLeaveTypeId();
			LeaveType ser = leaveTypeRepo.getOne(id);

			ser.setStatus(!ser.getStatus());
			LeaveType locationdata = leaveTypeRepo.save(ser);

			if (locationdata.getLeaveTypeId() != null) {
				response.setStatusCode("200");
				response.setMessage("leaveType details deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getDetails(LeaveTypeRequest leaveTypeRequestObject) {
		List<LeaveType> list = leaveTypeRequestObject.getLeaveTypeList();
		logger.debug(" getAll customer details");
		LeaveTypeResponse response = null;
		List<LeaveType> getAll = null;

		if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = leaveTypeRepo.findAll();
			if (getAll.isEmpty()) {
				response = new LeaveTypeResponse();
				response.setLeaveTypeList(new ArrayList<LeaveType>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			response = new LeaveTypeResponse();
			response.setLeaveTypeList(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}
		if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getLeaveTypeId() != null) {

			for (LeaveType type : list) {

				if (type.getLeaveTypeId() != null) {
					Integer id = leaveTypeRequestObject.getLeaveTypeList().get(0).getLeaveTypeId();

					ArrayList<LeaveType> listDetails = new ArrayList<LeaveType>();

					LeaveType getById = leaveTypeRepo.findById(id).orElse(new LeaveType());

					listDetails.add(getById);
           
					if (getById != null && getById.getLeaveTypeId() != null) {
						response = new LeaveTypeResponse();
						response.setLeaveTypeList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK); 
					}
					response = new LeaveTypeResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}



