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

import com.ojas.obs.facade.BudgetFacade;
import com.ojas.obs.model.Budget;
import com.ojas.obs.repositories.BudgetRepository;
import com.ojas.obs.request.BudgetRequest;
import com.ojas.obs.response.BudgetResponse;

@Service
public class BudgetFacadeImpl implements BudgetFacade {
	@Autowired
	private BudgetRepository budgetRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveDetails(BudgetRequest budgetRequestObject) {

		BudgetResponse response = null;

		logger.debug("request coming to the facade");

		if (budgetRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new BudgetResponse();

			List<Budget> budget = budgetRequestObject.getBudgetList();
			List<Budget> save = budgetRepo.saveAll(budget);

			if (!save.isEmpty()) {
				logger.debug("save method");
				response.setStatusCode("200");
				response.setMessage("Budget  details  saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("409");
			response.setMessage("failed to save");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (budgetRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new BudgetResponse();

			for (Budget tech : budgetRequestObject.getBudgetList()) {
				Integer id = budgetRequestObject.getBudgetList().get(0).getId();
				Optional<Budget> findById = budgetRepo.findById(id);
				if (findById.isPresent() && findById.get().getId() != null) {
					budgetRepo.save(tech);
					response.setStatusCode("200");
					response.setMessage("BudgetList details updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}

		if (budgetRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new BudgetResponse();

			Integer id = budgetRequestObject.getBudgetList().get(0).getId();
			Budget ser = budgetRepo.getOne(id);

			ser.setStatus(!ser.getStatus());
			Budget budgetdata = budgetRepo.save(ser);

			if (budgetdata.getId() != null) {
				response.setStatusCode("200");
				response.setMessage("Budget details deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getDetails(BudgetRequest budgetRequestObject) {
		List<Budget> list = budgetRequestObject.getBudgetList();
		logger.debug(" getAll customer details");
		BudgetResponse response = null;
		List<Budget> getAll = null;

		if (budgetRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = budgetRepo.findAll();
			if (getAll.isEmpty()) {
				response = new BudgetResponse();
				response.setBudgetList(new ArrayList<Budget>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			response = new BudgetResponse();
			response.setBudgetList(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}
		if (budgetRequestObject.getTransactionType().equalsIgnoreCase(GETBYID) && list.get(0).getId() != null) {

			for (Budget type : list) {

				if (type.getId() != null) {
					Integer id = budgetRequestObject.getBudgetList().get(0).getId();

					ArrayList<Budget> listDetails = new ArrayList<Budget>();

					Budget getById = budgetRepo.findById(id).orElse(new Budget());

					listDetails.add(getById);

					if (getById != null && getById.getId() != null) {
						response = new BudgetResponse();
						response.setBudgetList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
					response = new BudgetResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
