package com.obs.rmg.rmgfacade;

import org.springframework.http.ResponseEntity;

import com.obs.rmg.rmgmodel.RMG;
import com.obs.rmg.rmgrequest.RmgRequest;


public interface RmgFacade {
	
	ResponseEntity<Object> setRmg(RmgRequest rmgRequest);

	ResponseEntity<Object> getRmg(RmgRequest rmgRequest);

}
