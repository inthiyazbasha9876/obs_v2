package com.ojas.obs.interviewmode.facadeimpl;

import static com.ojas.obs.interviewmode.constants.Constants.DELETE;
import static com.ojas.obs.interviewmode.constants.Constants.GETALL;
import static com.ojas.obs.interviewmode.constants.Constants.GETBYID;
import static com.ojas.obs.interviewmode.constants.Constants.SAVE;
import static com.ojas.obs.interviewmode.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.interviewmode.facade.InterviewModeFacade;
import com.ojas.obs.interviewmode.model.InterviewMode;
import com.ojas.obs.interviewmode.repository.InterviewModeRepository;
import com.ojas.obs.interviewmode.request.InterviewModeRequest;
import com.ojas.obs.interviewmode.response.ErrorResponse;
import com.ojas.obs.interviewmode.response.InterviewModeResponse;

@Service
public class InterviewModeFacadeImpl implements InterviewModeFacade {
	@Autowired
	private InterviewModeRepository interviewModeRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(InterviewModeRequest interviewModeRequest)  {
		List<InterviewMode> list = interviewModeRequest.getInterviewmodeList();
		for (InterviewMode interviewMode : list) {
			if (interviewModeRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				InterviewMode interviewMode2 = interviewModeRepository.save(interviewMode);
				if (interviewMode2.getInterviewmodeId() != null) {
					InterviewModeResponse interviewModeResponse = new InterviewModeResponse();
					interviewModeResponse.setMessage("record saved successfully");
					interviewModeResponse.setStatusCode("200");
					return new ResponseEntity<>(interviewModeResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
			if (interviewModeRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				InterviewMode interviewMode2 = interviewModeRepository.getOne(interviewMode.getInterviewmodeId());
				interviewMode2.setStatus(!interviewMode2.getStatus());
				InterviewMode interviewMode3 = interviewModeRepository.save(interviewMode2);
				if (interviewMode3.getInterviewmodeId() != null) {
					InterviewModeResponse interviewModeResponse = new InterviewModeResponse();
					interviewModeResponse.setMessage("sucessfully deleted");
					interviewModeResponse.setStatusCode("200");
					return new ResponseEntity<>(interviewModeResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
		}

		if (interviewModeRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {

			InterviewMode fetchedRecord = interviewModeRepository.getOne(list.get(0).getInterviewmodeId());
			if (fetchedRecord.getInterviewmodeId() != null) {
				interviewModeRepository.save(list.get(0));
				InterviewModeResponse response = new InterviewModeResponse();
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

	public ResponseEntity<Object> getAllDetails(InterviewModeRequest interviewModeRequest)  {
		logger.debug("request coming to the facade");
		List<InterviewMode> list2 = new ArrayList<>();
		if (interviewModeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = interviewModeRepository.findAll();
			if (!list2.isEmpty()) {
				InterviewModeResponse response = new InterviewModeResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setInterviewmodeList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		if (interviewModeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			InterviewMode findById = interviewModeRepository.getOne(interviewModeRequest.getInterviewmodeList().get(0).getInterviewmodeId());
			if (findById.getInterviewmodeId() != null) {
				list2.add(findById);
				InterviewModeResponse response = new InterviewModeResponse();
				response.setMessage("suucessfully get the single record");
				response.setStatusCode("200");
				response.setInterviewmodeList(list2);
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
