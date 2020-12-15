package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.NULLVALUE;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;
import static com.ojas.obs.constants.Constants.SET;
import static com.ojas.obs.constants.Constants.GET;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.InterviewResultFacadeImpl;
import com.ojas.obs.model.InterviewResult;
import com.ojas.obs.request.InterviewResultRequest;
import com.ojas.obs.response.ErrorResponse;
@RestController
//@RequestMapping("obs/interviewresult")
public class InterviewResultController { 
	@Autowired
	private InterviewResultFacadeImpl interviewfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> saveDetails(@RequestBody InterviewResultRequest interviewRequestObject, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("data coming into controller");
		
			List<InterviewResult> interviewresult = interviewRequestObject.getInterviewresultList(); 

			try {
				if ((interviewRequestObject == null) || (interviewRequestObject.getInterviewresultList() == null)
						||interviewRequestObject.getTransactionType() == null) 
				{
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage("reqest object is null");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				for (InterviewResult type : interviewresult) {

				if (interviewRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
						&& type.getInterviewResult().isEmpty())
						{
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(NULLVALUE);
							return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					
			
				if ((interviewRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						||interviewRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
				        && type.getInterviewresultId()==null)
							
						{
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(NULLVALUE);
							return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				
		
			
			return interviewfacadeImpl.saveDetails(interviewRequestObject);
			
		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
  }


@PostMapping(GET)
public ResponseEntity<Object> getDetails(@RequestBody InterviewResultRequest interviewRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) 
{

    logger.debug("received data into controller");
    try {
        if (interviewRequestObject == null || interviewRequestObject.getInterviewresultList()== null
                || interviewRequestObject.getInterviewresultList().isEmpty()
                || interviewRequestObject.getTransactionType() == null
                || interviewRequestObject.getTransactionType().isEmpty()) 
        {
            ErrorResponse error = new ErrorResponse();
            error.setStatusCode("422");
            error.setStatusMessage("object is null");
            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        if (interviewRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                && interviewRequestObject.getInterviewresultList().get(0).getInterviewresultId() == null) {
            logger.error("please provide id");
            ErrorResponse error = new ErrorResponse();
            error.setStatusCode("422");
            error.setStatusMessage("Type must be getAll");
            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return interviewfacadeImpl.getDetails(interviewRequestObject);
    } 
    catch (Exception e) {
        logger.debug("inside catch block.***");
        ErrorResponse er = new ErrorResponse();
        er.setStatusCode("409");
        er.setStatusMessage(e.getMessage());
        return new ResponseEntity<>(er, HttpStatus.CONFLICT);
    }



}

}


