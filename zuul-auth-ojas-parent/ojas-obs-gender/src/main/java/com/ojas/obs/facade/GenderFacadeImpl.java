package com.ojas.obs.facade;
import static com.ojas.obs.constants.Constants.FAILED;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;
import static com.ojas.obs.constants.Constants.DELETE;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ojas.obs.dao.GenderDAO;
import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;
import com.ojas.obs.response.GenderResponse;


@Service
public class GenderFacadeImpl implements GenderFacade {
    @Autowired
	private GenderDAO genderDAOImpl;
    Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * this method will call insert , update and delete methods of dao class based upon the transaction type
     */
	@Override
	public ResponseEntity<Object> setGender(GenderRequest genderRequest) throws SQLException {
		logger.info("@@@@Inside set method : " + genderRequest);
		GenderResponse genderResponse = null;
		
			if (genderRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				genderResponse = new GenderResponse();
				boolean saveGender = genderDAOImpl.saveGender(genderRequest);
				logger.debug("@@@@Inside  savetransactionType condition.");
				if (saveGender) {
					logger.debug("@@@@Inside  save condition.****** : " + saveGender);
					genderResponse.setMessage("Successfully record added");
					genderResponse.setStatusCode("200");
					return new ResponseEntity<>(genderResponse, HttpStatus.OK);
				} else {
					genderResponse.setMessage(FAILED);
					genderResponse.setStatusCode("200");
					return new ResponseEntity<>(genderResponse, HttpStatus.CONFLICT);
				}
			}
			if (genderRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				logger.debug("@@@@Inside  Update transactionType condition.");
				genderResponse = new GenderResponse();
				boolean updateGender = genderDAOImpl.updateGender(genderRequest);
				logger.debug("inside  update condition.****** : " + updateGender);
				if (updateGender) {
					logger.debug("@@@@Inside  Update condition."+updateGender);
					genderResponse.setMessage("Successfully record updated");
					genderResponse.setStatusCode("200");
					return new ResponseEntity<>(genderResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside  Update condition."+updateGender);
					genderResponse.setMessage(FAILED);
					genderResponse.setStatusCode("200");
					return new ResponseEntity<>(genderResponse, HttpStatus.CONFLICT);
				}
			}
		
			boolean b = (genderRequest.getTransactionType().equalsIgnoreCase(SAVE)
					|| genderRequest.getTransactionType().equalsIgnoreCase(UPDATE)
					|| genderRequest.getTransactionType().equalsIgnoreCase(DELETE));
			    
					if (!b) {
						logger.debug("@@@@Inside  transactionType Check condition.");
						genderResponse = new GenderResponse();
						genderResponse.setStatusCode("200");
						genderResponse.setMessage("transaction type is not correct");
					}
					return new ResponseEntity<>(genderResponse, HttpStatus.CONFLICT);
		
	}
	 
    /**
     * this method will call select , selectAll methods of dao class based upon the transaction type
     */
	@Override
	public ResponseEntity<Object> getGender(GenderRequest genderRequest) throws SQLException {
		GenderResponse genderResponse = new GenderResponse();
		logger.info("@@@@@Inside getGender"+genderRequest);
		Integer id=genderRequest.getGender().get(0).getId();
		List<Genders> genderList=null;
		if(id==null) {
		 logger.debug("@@@@@Inside getGender id is null");
	     genderList = genderDAOImpl.getAll(genderRequest);
	     genderResponse.setGenderList(genderList);
	 	 genderResponse.setStatusCode("200");
	     genderResponse.setMessage("All records are retrieved");
		}
		if(id!=null) {
	     genderList = genderDAOImpl.getGenderById(genderRequest);
	     genderResponse.setGenderList(genderList);
	 	 genderResponse.setStatusCode("200");
	     genderResponse.setMessage("Record is retrieved");
		}
		if (genderList == null) {    //removed condition    list == null ||
			logger.debug("@@@@@Inside getGender genderList is null");
			genderResponse.setGenderList(new ArrayList<>());
			genderResponse.setMessage("No records found");
			genderResponse.setStatusCode("200");
			
		}
		return new ResponseEntity<>(genderResponse, HttpStatus.OK);
	}
}
