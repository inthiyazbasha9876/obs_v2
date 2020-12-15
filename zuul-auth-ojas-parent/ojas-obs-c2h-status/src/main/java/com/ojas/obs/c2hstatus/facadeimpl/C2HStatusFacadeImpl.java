package com.ojas.obs.c2hstatus.facadeimpl;

import static com.ojas.obs.c2hstatus.constants.Constants.DELETE;
import static com.ojas.obs.c2hstatus.constants.Constants.GETALL;
import static com.ojas.obs.c2hstatus.constants.Constants.GETBYID;
import static com.ojas.obs.c2hstatus.constants.Constants.SAVE;
import static com.ojas.obs.c2hstatus.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.c2hstatus.facade.C2HStatusFacade;
import com.ojas.obs.c2hstatus.model.C2HStatus;
import com.ojas.obs.c2hstatus.repository.C2HStatusRepository;
import com.ojas.obs.c2hstatus.request.C2HStatusRequest;
import com.ojas.obs.c2hstatus.response.C2HStatusResponse;

@Service
public class C2HStatusFacadeImpl implements C2HStatusFacade {

	@Autowired
	private C2HStatusRepository c2hStatusRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveC2HStatus(C2HStatusRequest c2hstatusRequestObject) {

		C2HStatusResponse response = null;

		logger.debug("request coming to the facade");

		if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new C2HStatusResponse();

			List<C2HStatus> contract = c2hstatusRequestObject.getC2hstatuslist();

			for (C2HStatus c2h : contract) {
				C2HStatus save = c2hStatusRepo.save(c2h);

				if (save.getC2hstatusId() != null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("C2HStatus saved successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("save method");
					response.setStatusCode("422");
					response.setMessage("failed to save");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new C2HStatusResponse();
			List<C2HStatus> contract = c2hstatusRequestObject.getC2hstatuslist();
			C2HStatus c2h = contract.get(0);
			Integer id = c2h.getC2hstatusId();
			Optional<C2HStatus> findById = c2hStatusRepo.findById(id);

			if (findById.isPresent() &&findById.get().getC2hstatusId() != null) {
				c2hStatusRepo.save(c2h);
				response.setStatusCode("200");
				response.setMessage("C2HStatus updated successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("409");
			response.setMessage("failed to update");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new C2HStatusResponse();
			Integer id = c2hstatusRequestObject.getC2hstatuslist().get(0).getC2hstatusId();
			C2HStatus status = c2hStatusRepo.getOne(id);
			status.setStatus(!status.getStatus());
			C2HStatus data = c2hStatusRepo.save(status);

			if (data.getC2hstatusId() != null) {
				response.setStatusCode("200");
				response.setMessage("C2HStatus deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("409");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getC2HStatus(C2HStatusRequest c2hstatusRequestObject) {
		List<C2HStatus> list = c2hstatusRequestObject.getC2hstatuslist();
		logger.debug(" getAll customer details");
		C2HStatusResponse response = null;
		List<C2HStatus> getAll = null;

		if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = c2hStatusRepo.findAll();
			if (getAll.isEmpty()) {
				response = new C2HStatusResponse();
				response.setC2hstatuslist(new ArrayList<C2HStatus>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			response = new C2HStatusResponse();
			response.setC2hstatuslist(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (c2hstatusRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getC2hstatusId() != null) {

			for (C2HStatus details : list) {

				if (details.getC2hstatusId() != null) {
					Integer id = c2hstatusRequestObject.getC2hstatuslist().get(0).getC2hstatusId();

					ArrayList<C2HStatus> contractlist = new ArrayList<>();

					C2HStatus getById = c2hStatusRepo.findById(id).orElse(new C2HStatus());

					contractlist.add(getById);

					if (getById != null && getById.getC2hstatusId() != null) {
						response = new C2HStatusResponse();
						response.setC2hstatuslist(contractlist);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
					response = new C2HStatusResponse();
					response.setStatusCode("409");
					response.setMessage("please provide valid id");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
