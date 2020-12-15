package com.ojas.obs.facade;

import static com.ojas.obs.constants.RoleServiceConstants.FAILED;
import static com.ojas.obs.constants.RoleServiceConstants.GETALL;
import static com.ojas.obs.constants.RoleServiceConstants.GETBYID;
import static com.ojas.obs.constants.RoleServiceConstants.SAVE;
import static com.ojas.obs.constants.RoleServiceConstants.SUCCESS;
import static com.ojas.obs.constants.RoleServiceConstants.UPDATE;
import static com.ojas.obs.constants.RoleServiceConstants.DELETE;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.RoleManagementDao;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.model.RoleManagement;
import com.ojas.obs.request.RoleManagementRequest;
import com.ojas.obs.response.RoleManagementResponse;

/**
 * 
 * @author asuneel
 *
 */

@Service
public class RoleManagementFacade {
	@Autowired
	RoleManagementDao roleManagementDao;
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param roleManagementRequest
	 * @return
	 * @throws SQLException
	 */

	public ResponseEntity<Object> setRoleManagement(RoleManagementRequest roleManagementRequest) throws SQLException {

		logger.info("Inside setRoleManagement method in Facade");
		logger.info("Incoming request is : " + roleManagementRequest);
		RoleManagementResponse roleManagementResponse = new RoleManagementResponse();

		if (roleManagementRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			boolean saveRole = roleManagementDao.saveRoleManagement(roleManagementRequest);
			logger.debug("inside  save condition. Record save : " + saveRole);
			if (saveRole) {

				roleManagementResponse.setMessage("Successfully Role Management record saved");
				roleManagementResponse.setStatusCode("200");

				return new ResponseEntity<>(roleManagementResponse, HttpStatus.OK);
			} else {
				ErrorResponse error = new ErrorResponse();
				error.setMessage(FAILED);
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}

		}
		
		if (roleManagementRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			boolean updateRole = roleManagementDao.updateRoleManagement(roleManagementRequest);
			logger.debug("inside  update condition. Record update : " + updateRole);

			if (updateRole) {

				roleManagementResponse.setMessage("Success fully record updated");
				roleManagementResponse.setStatusCode("200");

				return new ResponseEntity<>(roleManagementResponse, HttpStatus.OK);
			} else {
				ErrorResponse error = new ErrorResponse();
				error.setMessage(FAILED);
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}

		}
		
		if (roleManagementRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			boolean deleteRole = roleManagementDao.deleteRoleManagement(roleManagementRequest);
			logger.debug("inside  delete condition. Record deleted : " + deleteRole);

			if (deleteRole) {

				roleManagementResponse.setMessage("Success fully record deleted");
				roleManagementResponse.setStatusCode("200");

				return new ResponseEntity<>(roleManagementResponse, HttpStatus.OK);
			} else {
				ErrorResponse error = new ErrorResponse();
				error.setMessage(FAILED);
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}

		}

		return new ResponseEntity<>(null, HttpStatus.CONFLICT);

		
	}

	/**
	 * 
	 * @param roleManagementRequest
	 * @return
	 * @throws SQLException
	 */

	public ResponseEntity<Object> getRoleManagement(RoleManagementRequest roleManagementRequest) throws SQLException {

		RoleManagementResponse roleManagementResponse = new RoleManagementResponse();
		logger.info("Inside getRoleManagement in Facade.");

		List<RoleManagement> getAllRoleManagements = null;
		if (roleManagementRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			Integer id = roleManagementRequest.getRoleManagement().get(0).getId();
			getAllRoleManagements = roleManagementDao.getByIdRollManagement(id);
		}
		if (roleManagementRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAllRoleManagements = roleManagementDao.getAllRollManagements();
		}
		if (getAllRoleManagements == null || getAllRoleManagements.isEmpty()) {
			roleManagementResponse.setMessage(FAILED);
			roleManagementResponse.setRoleManagementList(getAllRoleManagements);
			return new ResponseEntity<>(roleManagementResponse, HttpStatus.CONFLICT);
		} else {

			roleManagementResponse.setMessage(SUCCESS);
			roleManagementResponse.setStatusCode("200");

			roleManagementResponse.setRoleManagementList(getAllRoleManagements);
			return new ResponseEntity<>(roleManagementResponse, HttpStatus.OK);
		}

	}
}
