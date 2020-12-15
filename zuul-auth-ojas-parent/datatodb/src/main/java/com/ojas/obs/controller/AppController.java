package com.ojas.obs.controller;

import static com.ojas.obs.constants.UtilConstants.SET;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facade.AppFacade;
import com.ojas.obs.request.AppRequest;
import com.ojas.obs.response.AppResponse;
import com.ojas.obs.response.ErrorResponse;

import sun.misc.BASE64Decoder;

@RestController
public class AppController {
	@Autowired
	private AppFacade appfacade;
	@Autowired
	private Environment env;
	@PostMapping(value = SET)
	public ResponseEntity<Object> setProjectInfo(@RequestBody AppRequest appRequest) throws IOException, ParseException {
		
		AppResponse response=new AppResponse();
		boolean success = false;
		
	
		if (appRequest == null || appRequest.getTransactionType() == null || appRequest.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Invalid Request");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
			try {
		if (appRequest.getApplist().getXlfile()!=null ) {
			//(&& appRequest.getApplist().getConfigFile()!=null)
							String xslFile = appRequest.getApplist().getXlfile();
							byte[] bufferxsl = new BASE64Decoder().decodeBuffer(xslFile);
							

							
							String fir=env.getProperty("file_excelfilename");

							File efile = new File(fir);	
							success = efile.createNewFile();
						
						
							
							FileOutputStream streamxsl = new FileOutputStream(efile);
							streamxsl.write(bufferxsl);															
							String xslsheet = streamxsl.toString();
							streamxsl.close();
							

							
							
					
							
							
							

//							String cfir=env.getProperty("file_configfilename");
//
//							File cdfile = new File(cfir);	
//							success = cdfile.createNewFile();
//							
//							String configFile = appRequest.getApplist().getConfigFile();
//							byte[] buffer = new BASE64Decoder().decodeBuffer(configFile);
//							
//							FileOutputStream stream = new FileOutputStream(cdfile);
//							stream.write(buffer);
//							String config = stream.toString();
//							stream.close();

						
					}
		else {
			response.setMessage("requset unable to processed");
			response.setStatusCode("422");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
			} catch(Exception exception) {
				ErrorResponse error = new ErrorResponse();
			    exception.getMessage();
				error.setMessage("Exception");
				error.setStatusMessage(exception.getCause().getMessage());
				error.setStatusCode("409");
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
				
			}
		return appfacade.setCustomer(appRequest);
		
	}

}
