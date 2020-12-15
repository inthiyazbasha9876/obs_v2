package com.ojas.obs.contractcompany.facadeimpl;

import static com.ojas.obs.contractcompany.constants.Constants.DELETE;
import static com.ojas.obs.contractcompany.constants.Constants.GETALL;
import static com.ojas.obs.contractcompany.constants.Constants.GETBYID;
import static com.ojas.obs.contractcompany.constants.Constants.SAVE;
import static com.ojas.obs.contractcompany.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.contractcompany.facade.ContractCompanyFacade;
import com.ojas.obs.contractcompany.model.ContractCompany;
import com.ojas.obs.contractcompany.repository.ContractCompanyRepository;
import com.ojas.obs.contractcompany.request.ContractCompanyRequest;
import com.ojas.obs.contractcompany.response.ContractCompanyResponse;
import com.ojas.obs.contractcompany.response.ErrorResponse;

@Service
public class ContractCompanyFacadeImpl implements ContractCompanyFacade {
	@Autowired
	private ContractCompanyRepository contractCompanyRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(ContractCompanyRequest request) {
		List<ContractCompany> list = request.getCompanyList();
			if (request.getTransactionType().equalsIgnoreCase(SAVE)) {
				List<ContractCompany> company = contractCompanyRepository.saveAll(list);
				if (!company.isEmpty()) {
					ContractCompanyResponse companyResponse = new ContractCompanyResponse();
					companyResponse.setMessage("record saved successfully");
					companyResponse.setStatusCode("200");
					return new ResponseEntity<>(companyResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}

			if (request.getTransactionType().equalsIgnoreCase(UPDATE)) {

				ContractCompany fetchedRecord = contractCompanyRepository.getOne(list.get(0).getId());
				if (fetchedRecord.getId() != null) {
					contractCompanyRepository.saveAll(list);
					ContractCompanyResponse response = new ContractCompanyResponse();
					response.setMessage("updated successfully");
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not updated");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
			if (request.getTransactionType().equalsIgnoreCase(DELETE)) {
				ContractCompany company = contractCompanyRepository.getOne(list.get(0).getId());
				company.setStatus(!company.getStatus());
				ContractCompany company2 = contractCompanyRepository.save(company);
				if (company2.getId() != null) {
					ContractCompanyResponse companyResponse = new ContractCompanyResponse();
					companyResponse.setMessage("sucessfully deleted");
					companyResponse.setStatusCode("200");
					return new ResponseEntity<>(companyResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	public ResponseEntity<Object> getAllDetails(ContractCompanyRequest companyRequest)
			 {
		logger.debug("request coming to the facade");
		List<ContractCompany> list2 = new ArrayList<>();
		if (companyRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = contractCompanyRepository.findAll();
			if (!list2.isEmpty()) {
				ContractCompanyResponse response = new ContractCompanyResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setComapnayList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		if (companyRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			ContractCompany findById = contractCompanyRepository
					.getOne(companyRequest.getCompanyList().get(0).getId());
			if (findById.getId() != null) {
				list2.add(findById);
				ContractCompanyResponse response = new ContractCompanyResponse();
				response.setMessage("suucessfully get the single record");
				response.setStatusCode("200");
				response.setComapnayList(list2);
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
