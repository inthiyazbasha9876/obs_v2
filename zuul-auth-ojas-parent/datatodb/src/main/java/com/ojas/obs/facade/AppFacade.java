package com.ojas.obs.facade;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.AppRequest;

public interface AppFacade {

	ResponseEntity<Object> setCustomer(AppRequest appRequest) throws  IOException, ParseException;

}
