package com.ojas.obs.rateType.facadeImpl;

import static com.ojas.obs.rateType.constant.Constant.DELETE;
import static com.ojas.obs.rateType.constant.Constant.GETALL;
import static com.ojas.obs.rateType.constant.Constant.GETBYID;
import static com.ojas.obs.rateType.constant.Constant.SAVE;
import static com.ojas.obs.rateType.constant.Constant.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ojas.obs.rateType.facade.RateTypeFacade;
import com.ojas.obs.rateType.model.RateType;
import com.ojas.obs.rateType.repository.RateTypeRepository;
import com.ojas.obs.rateType.request.RateTypeRequest;
import com.ojas.obs.rateType.response.RateTypeResponse;

@Service
public class RateTypeFacadeImpl implements RateTypeFacade {

	@Autowired
	private RateTypeRepository rateTypeRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveRateType(RateTypeRequest rateTypeRequestObject) {

		RateTypeResponse response = null;
	
		if (rateTypeRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new RateTypeResponse();
			List<RateType> rateType = rateTypeRequestObject.getRateType();	
			List<RateType> save = rateTypeRepo.saveAll(rateType);
				if (!save.isEmpty()) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("RateType  saved successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to save");
					return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
				
			}	
		if (rateTypeRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new RateTypeResponse();
			for (RateType details : rateTypeRequestObject.getRateType()) {
				Integer id = rateTypeRequestObject.getRateType().get(0).getId();
				Optional<RateType> findById = rateTypeRepo.findById(id);
				if (findById.isPresent() && findById.get().getId() != null){
					rateTypeRepo.save(details);
					response.setStatusCode("200");
					response.setMessage("RateType has updated successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
				}
			}
		}
		if (rateTypeRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			 response=new RateTypeResponse();
			
				 Integer id=rateTypeRequestObject.getRateType().get(0).getId();
				 RateType rate = rateTypeRepo.getOne(id);
				 rate.setStatus(!rate.getStatus());
				 RateType ratetype = rateTypeRepo.save(rate);
				  if (ratetype.getId() != null)   {
                     response.setStatusCode("200");
                     response.setMessage("RateType deleted successfully");
                     return new ResponseEntity<Object>(response, HttpStatus.OK);
                 }  
				 response.setStatusCode("422");
                 response.setMessage("failed to delete");
                 return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				  
			 }
	    	return new ResponseEntity<Object>(response, HttpStatus.OK);
	
	}
	
	@Override
	public ResponseEntity<Object> getCustomerDetails(RateTypeRequest rateTypeRequest) {
		List<RateType> list = rateTypeRequest.getRateType();
		logger.debug(" getAll RateType");
		RateTypeResponse response = null;
		List<RateType> getAll = null;
		if (rateTypeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = rateTypeRepo.findAll();
			if (getAll.isEmpty()) {
				response = new RateTypeResponse();
				response.setRateTypeList(new ArrayList<RateType>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			} 
				response = new RateTypeResponse();
				response.setRateTypeList(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			
		}
		if (rateTypeRequest.getTransactionType().equalsIgnoreCase(GETBYID) && list.get(0).getId() != null) {
			for (RateType details : list) {
				if (details.getId() != null) {
					Integer id = rateTypeRequest.getRateType().get(0).getId();
					ArrayList<RateType> listRateType = new ArrayList<RateType>();
					RateType getById = rateTypeRepo.findById(id).orElse(new RateType());
					listRateType.add(getById);
					if (getById != null && getById.getId() != null) {
						response = new RateTypeResponse();
						response.setRateTypeList(listRateType);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
						response = new RateTypeResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
