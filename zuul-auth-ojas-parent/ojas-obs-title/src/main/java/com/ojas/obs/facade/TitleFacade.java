package com.ojas.obs.facade;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.FAILED;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.SUCCESS;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ojas.obs.dao.TitleDao;
import com.ojas.obs.model.Model;
import com.ojas.request.Request;
import com.ojas.response.Response;

/**
 * 
 * @author uyashwanth
 *
 */
@Service
public class TitleFacade {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TitleDao titleDao;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> setTitle(Request request) throws SQLException {
		logger.debug("inside setTitle method : " + request);
		Response response = null;
		ResponseEntity<Object> abc = null;
		if (request.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new Response();
			boolean saveTitle = titleDao.saveTitle(request);
			logger.debug("*****inside  save condition.***** : " + saveTitle);
			if (saveTitle) {
				response.setMessage("Successfully Title record saved");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				logger.error("**Failed to save the record(s)***");
				response.setMessage(FAILED);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}

		if (request.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new Response();
			boolean updateTitle = titleDao.updateTitle(request);
			logger.debug("*****inside  update condition.***** : " + updateTitle);
			if (updateTitle) {
				response.setMessage("Successfully Title record updated");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				logger.error("**Failed to update the record(s)***");
				response.setMessage(FAILED);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}

		if (request.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new Response();
			boolean deleteId = false;
			logger.debug("*****inside  delete condition.***** : " + deleteId);
			List<Model> modellist = request.getModel();
			for (Model model : modellist) {
				Integer id = model.getId();
				logger.debug("inside  delete condition.****** : ");
				deleteId = titleDao.deleteEmployeeRecord(id);
			}
			if (deleteId) {
				response.setMessage("Successfully Title record deleted");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				logger.error("**Failed to delete the record(s)***");
				response.setMessage(FAILED);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		return abc;

	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> getTitle(Request request) throws SQLException {
		Response response = null;
		ResponseEntity<Object> resp = null;
		logger.debug("inside  get condition.****** : " + request);
		if (request.getTransactionType().equalsIgnoreCase(GETALL)) {
			logger.debug("inside  get condition.****** : ");
			response = new Response();
			List<Model> allCertificationDetails = titleDao.getAllTitle(request);

			if (allCertificationDetails == null || allCertificationDetails.isEmpty()) {
				response.setListCourse(new ArrayList<>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} else {
				response.setMessage(SUCCESS);
				response.setStatusCode("200");
				response.setListCourse(allCertificationDetails);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		if (request.getTransactionType().equalsIgnoreCase(GETBYID)) {
			response = new Response();
			List<Model> list = null;
			logger.debug("inside  get_by_id condition.****** : ");
			String EmpId = request.getModel().get(0).getEmployeeId();
			if (EmpId == null) {
				list = titleDao.getById(request);
			} else {
				list = titleDao.getByEmpId(request);

			}

			if (list.size() == 0) {
				response.setMessage("No record Present");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} else {
				response.setMessage(SUCCESS);
				response.setStatusCode("200");
				response.setListCourse(list);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return resp;

	}

}
