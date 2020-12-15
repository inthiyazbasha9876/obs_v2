package com.obs.psa.customercontactinfo.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.obs.psa.customercontactinfo.constants.ErrorResponse;
import com.obs.psa.customercontactinfo.facadeimpl.CustomerContactInfoFacadeImpl;
import com.obs.psa.customercontactinfo.model.CustomerContactInfo;
import com.obs.psa.customercontactinfo.request.CustomerContactInfoRequest;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.SAVE;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.UPDATE;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.DELETE;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.GETBYID;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.GETALL;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.FAILED;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.SET;
import static com.obs.psa.customercontactinfo.constants.CustomerContactInfoConstants.GET;

@RestController
//@RequestMapping("/customerContactInfo")
public class CustomerContactInfoController {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	CustomerContactInfoFacadeImpl customerContactInfoFacadeImpl;
	
	
	@PostMapping(SET)
	public ResponseEntity<Object> setCustomerContactInfo(@RequestBody CustomerContactInfoRequest customerContactInfoRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
		List<CustomerContactInfo> customerContactInfoList = customerContactInfoRequest.getCustomerContactInfo();
		try {
				if ((customerContactInfoRequest == null) || (customerContactInfoRequest.getCustomerContactInfo() == null)
						|| customerContactInfoRequest.getTransactionType() == null) 
				{
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage("reqest object is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(SAVE))
				{
					for (CustomerContactInfo contactInfo : customerContactInfoList) 
					{
						if ( (null == contactInfo.getContactName() || contactInfo.getContactName().isEmpty())
								|| (null == contactInfo.getDepartment() || contactInfo.getDepartment().isEmpty())
								|| (null == contactInfo.getPermanentMobileNumber()) 
								|| (null == contactInfo.getAlternateMobileNumber())
								|| (null == contactInfo.getPersonalEmail())
								|| (null == contactInfo.getOfficialEmail())
								||(null == contactInfo.getAddress1()||contactInfo.getAddress1().isEmpty())
								||(null == contactInfo.getAddress2()||contactInfo.getAddress2().isEmpty())
								||(null == contactInfo.getAddress3()||contactInfo.getAddress3().isEmpty())
								||(null == contactInfo.getBdm()||contactInfo.getBdm().isEmpty()))
						{
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(" field is null");
							return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				}
				if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(UPDATE)) 
				{
					for (CustomerContactInfo contactInfo : customerContactInfoList) 
					{
						if ( (null == contactInfo.getContactName() || contactInfo.getContactName().isEmpty())
								|| (null == contactInfo.getDepartment() || contactInfo.getDepartment().isEmpty())
								|| (null == contactInfo.getPermanentMobileNumber()) 
								|| (null == contactInfo.getAlternateMobileNumber())
								|| (null == contactInfo.getPersonalEmail())
								|| (null == contactInfo.getOfficialEmail())
								||(null == contactInfo.getAddress1()||contactInfo.getAddress1().isEmpty())
								||(null == contactInfo.getAddress2()||contactInfo.getAddress2().isEmpty())
								||(null == contactInfo.getAddress3()||contactInfo.getAddress3().isEmpty())
								||(null == contactInfo.getBdm()||contactInfo.getBdm().isEmpty()))
						{
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(" field is null");
							return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
						}
					}
				}
				
		for (CustomerContactInfo contactInfo : customerContactInfoList) {
			
			if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(DELETE)&&contactInfo.getContactId()==0) {
				
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(" field is null");
						return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					
				}
			}
			return customerContactInfoFacadeImpl.setCustomerContactInfo(customerContactInfoRequest);
		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(FAILED);
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		}
	}
	
	
	
	
	@PostMapping(GET)
    public ResponseEntity<Object> getCustomerContactInfoDetails(@RequestBody CustomerContactInfoRequest customerContactInfoRequest,HttpServletRequest servletRequest, HttpServletResponse servletResponse) 
	{

        logger.debug("received data into controller" + customerContactInfoRequest);
        try {
            if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(GETALL)&&customerContactInfoRequest == null || customerContactInfoRequest.getCustomerContactInfo()== null
                    || customerContactInfoRequest.getCustomerContactInfo().isEmpty()
                    || customerContactInfoRequest.getTransactionType() == null
                    || customerContactInfoRequest.getTransactionType().isEmpty()) 
            {
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("object is null");
                return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            
            if (customerContactInfoRequest.getTransactionType().equalsIgnoreCase(GETBYID)
                    && customerContactInfoRequest.getCustomerContactInfo().get(0).getContactId() == null) {
                logger.error("please provide id");
                ErrorResponse error = new ErrorResponse();
                error.setStatusCode("422");
                error.setStatusMessage("Type must be getAll");
                return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return customerContactInfoFacadeImpl.getCustomerContactInfo(customerContactInfoRequest);
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
