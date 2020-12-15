package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.ResignationRequest;

public interface ResignationFacadeInterface {
   public ResponseEntity<Object> setResignation(ResignationRequest resignationRequest) ;
   public ResponseEntity<Object> getResignation(ResignationRequest resignationRequest) ;
   
}
