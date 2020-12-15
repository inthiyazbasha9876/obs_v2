package com.ojas.obs.billingtype.facadeimpl;

import static com.ojas.obs.billingtype.constants.Constants.DELETE;
import static com.ojas.obs.billingtype.constants.Constants.GETALL;
import static com.ojas.obs.billingtype.constants.Constants.GETBYID;
import static com.ojas.obs.billingtype.constants.Constants.SAVE;
import static com.ojas.obs.billingtype.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.billingtype.facade.BillingTypeFacade;
import com.ojas.obs.billingtype.model.BillingType;
import com.ojas.obs.billingtype.repository.BillingTypeRepository;
import com.ojas.obs.billingtype.request.BillingTypeRequest;
import com.ojas.obs.billingtype.response.BillingTypeResponse;
import com.ojas.obs.billingtype.response.ErrorResponse;

@Service
public class BillingTypeFacadeImpl implements BillingTypeFacade {
	@Autowired
	private BillingTypeRepository billingTypeRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(BillingTypeRequest billingTypeRequest) {
		List<BillingType> list = billingTypeRequest.getBillingList();
		for (BillingType billingType : list) {
			if (billingTypeRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				BillingType billingType2 = billingTypeRepository.save(billingType);
				if (billingType2.getId() != null) {
					BillingTypeResponse billingTypeResponse = new BillingTypeResponse();
					billingTypeResponse.setMessage("record saved successfully");
					billingTypeResponse.setStatusCode("200");
					return new ResponseEntity<>(billingTypeResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
			if (billingTypeRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				BillingType billingType2 = billingTypeRepository.getOne(billingType.getId());
				billingType2.setStatus(!billingType2.getStatus());
				BillingType billingType3 = billingTypeRepository.save(billingType2);
				if (billingType3.getId() != null) {
					BillingTypeResponse billingTypeResponse = new BillingTypeResponse();
					billingTypeResponse.setMessage("sucessfully deleted");
					billingTypeResponse.setStatusCode("200");
					return new ResponseEntity<>(billingTypeResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
		}

		if (billingTypeRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {

			BillingType fetchedRecord = billingTypeRepository.getOne(list.get(0).getId());
			if (fetchedRecord.getId() != null) {
				billingTypeRepository.save(list.get(0));
				BillingTypeResponse response = new BillingTypeResponse();
				response.setMessage("updated successfully");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("not updated");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	public ResponseEntity<Object> getAllDetails(BillingTypeRequest billingTypeRequest)  {
		logger.debug("request coming to the facade");
		List<BillingType> list2 = new ArrayList<>();
		if (billingTypeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = billingTypeRepository.findAll();
			if (!list2.isEmpty()) {
				BillingTypeResponse response = new BillingTypeResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setBillingList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		if (billingTypeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			BillingType findById = billingTypeRepository.getOne(billingTypeRequest.getBillingList().get(0).getId());
			if (findById.getId() != null) {
				list2.add(findById);
				BillingTypeResponse response = new BillingTypeResponse();
				response.setMessage("suucessfully get the single record");
				response.setStatusCode("200");
				response.setBillingList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("record is not getting");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
}
