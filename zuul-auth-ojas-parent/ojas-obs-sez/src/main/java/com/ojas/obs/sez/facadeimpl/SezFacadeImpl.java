package com.ojas.obs.sez.facadeimpl;

import static com.ojas.obs.sez.constants.Constants.DELETE;
import static com.ojas.obs.sez.constants.Constants.GETALL;
import static com.ojas.obs.sez.constants.Constants.GETBYID;
import static com.ojas.obs.sez.constants.Constants.SAVE;
import static com.ojas.obs.sez.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.sez.facade.SezFacade;
import com.ojas.obs.sez.model.Sez;
import com.ojas.obs.sez.repositories.SezRepository;
import com.ojas.obs.sez.request.SezRequest;
import com.ojas.obs.sez.response.ErrorResponse;
import com.ojas.obs.sez.response.SezResponse;

@Service
public class SezFacadeImpl implements SezFacade {
	@Autowired
	private SezRepository sezDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(SezRequest sezRequest)  {
		List<Sez> list = sezRequest.getSezlist();
		for (Sez sez : list) {
			if (sezRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				Sez sez2 = sezDao.save(sez);
				if (sez2.getId() != null) {
					SezResponse sezResponse = new SezResponse();
					sezResponse.setMessage("record saved successfully");
					sezResponse.setStatusCode("200");
					return new ResponseEntity<>(sezResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
			if (sezRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				Sez sez2 = sezDao.getOne(sez.getId());
				sez2.setStatus(!sez2.getStatus());
				Sez sez3 = sezDao.save(sez2);
				if (sez3.getId() != null) {
					SezResponse sezResponse = new SezResponse();
					sezResponse.setMessage("sucessfully deleted");
					sezResponse.setStatusCode("200");
					return new ResponseEntity<>(sezResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
		}

		if (sezRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {

			Sez fetchedRecord = sezDao.getOne(list.get(0).getId());
			if (fetchedRecord.getId() != null) {
				sezDao.save(list.get(0));
				SezResponse response = new SezResponse();
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

	public ResponseEntity<Object> getAllDetails(SezRequest sezRequest)  {
		logger.debug("request coming to the facade");
		List<Sez> list2 = new ArrayList<>();
		if (sezRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = sezDao.findAll();
			if (!list2.isEmpty()) {
				SezResponse response = new SezResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setSezlist(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		if (sezRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			Sez findById = sezDao.getOne(sezRequest.getSezlist().get(0).getId());
			if (findById.getId() != null) {
				list2.add(findById);
				SezResponse response = new SezResponse();
				response.setMessage("suucessfully get the single record");
				response.setStatusCode("200");
				response.setSezlist(list2);
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
