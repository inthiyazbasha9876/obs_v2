package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.facade.InterviewResultFacade;
import com.ojas.obs.model.InterviewResult;
import com.ojas.obs.repositories.InterviewResultRepository;
import com.ojas.obs.request.InterviewResultRequest;
import com.ojas.obs.response.InterviewResultResponse;

@Service
public class InterviewResultFacadeImpl implements InterviewResultFacade {
	@Autowired
	private InterviewResultRepository interviewModeRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(InterviewResultRequest interviewRequestObject) {

		InterviewResultResponse response = null;

		logger.debug("request coming to the facade");

		if (interviewRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new InterviewResultResponse();

			List<InterviewResult> result = interviewRequestObject.getInterviewresultList();

			for (InterviewResult type : result) {
				InterviewResult save = interviewModeRepo.save(type);

				if (save.getInterviewresultId() != null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("Interview Result saved successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("422");
					response.setMessage("failed to save");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}

			}

		}

		if (interviewRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new InterviewResultResponse();
			List<InterviewResult> result = interviewRequestObject.getInterviewresultList();

			InterviewResult interview = result.get(0);
			Integer resultId = interview.getInterviewresultId();
			InterviewResult findById = interviewModeRepo.getOne(resultId);
			if (findById.getInterviewresultId() != null) {
				interviewModeRepo.save(interview);
				response.setStatusCode("200");
				response.setMessage("interview result updated successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("409");
			response.setMessage("failed to update");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		if (interviewRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new InterviewResultResponse();
			Integer resultId = interviewRequestObject.getInterviewresultList().get(0).getInterviewresultId();
			InterviewResult ser = interviewModeRepo.getOne(resultId);

			ser.setStatus(!ser.getStatus());
			InterviewResult data = interviewModeRepo.save(ser);

			if (data.getInterviewresultId() != null) {
				response.setStatusCode("200");
				response.setMessage("interview result deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getDetails(InterviewResultRequest interviewRequestObject) {
		List<InterviewResult> list = interviewRequestObject.getInterviewresultList();
		logger.debug(" getAll customer details");
		InterviewResultResponse response = null;
		List<InterviewResult> getAll = null;

		if (interviewRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = interviewModeRepo.findAll();
			if (getAll.isEmpty()) {
				response = new InterviewResultResponse();
				response.setInterviewresultList(new ArrayList<InterviewResult>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} 
				response = new InterviewResultResponse();
				response.setInterviewresultList(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		if (interviewRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getInterviewresultId() != null) {

			for (InterviewResult type : list) {

				if (type.getInterviewresultId() != null) {
					Integer id = interviewRequestObject.getInterviewresultList().get(0).getInterviewresultId();

					ArrayList<InterviewResult> listDetails = new ArrayList<>();

					InterviewResult getById = interviewModeRepo.findById(id).orElse(new InterviewResult());

					listDetails.add(getById);

					if (getById != null && getById.getInterviewresultId() != null) {
						response = new InterviewResultResponse();
						response.setInterviewresultList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
					response = new InterviewResultResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}

			}
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
