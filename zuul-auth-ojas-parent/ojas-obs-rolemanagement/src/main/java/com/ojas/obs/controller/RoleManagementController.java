
package com.ojas.obs.controller;

import static com.ojas.obs.constants.RoleServiceConstants.GET;
//import static com.ojas.obs.constants.RoleServiceConstants.ROLEMANAGEMENT;
import static com.ojas.obs.constants.RoleServiceConstants.SET;
import static com.ojas.obs.constants.RoleServiceConstants.UPDATE;
import static com.ojas.obs.constants.RoleServiceConstants.DELETE;
import static com.ojas.obs.constants.RoleServiceConstants.GETALL;
import static com.ojas.obs.constants.RoleServiceConstants.GETBYID;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.RoleManagementFacade;
import com.ojas.obs.model.RoleManagement;
import com.ojas.obs.request.RoleManagementRequest;

/**
 * 
 * @author asuneel
 *
 */
@RestController
//@RequestMapping(ROLEMANAGEMENT)
public class RoleManagementController {

	@Autowired
	RoleManagementFacade roleManagementFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping(SET)
	public ResponseEntity<Object> setRoleManagement(@RequestBody RoleManagementRequest roleManagementRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws SQLException {

		logger.info("roleRequest request " + roleManagementRequest);
		try {

			List<RoleManagement> roleManagement = roleManagementRequest.getRoleManagement();

			if (roleManagementRequest.getTransactionType() == null || roleManagement == null) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Data must not be null");
				error.setStatusCode("422");
				logger.debug("request is not valid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (RoleManagement roleManagementItem : roleManagement) {

				if ((roleManagementRequest.getTransactionType().equalsIgnoreCase("save")
						|| roleManagementRequest.getTransactionType().equalsIgnoreCase("update"))
						&& (roleManagementItem.getRoleName() == null || roleManagementItem.getRoleName().isEmpty())) {
					logger.debug("requested data is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("Data must not be null");
					error.setStatusCode("422");

					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}

				if (roleManagementRequest.getTransactionType().equalsIgnoreCase(UPDATE)
						&& roleManagementItem.getId() == null) {
					logger.debug("requested data is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("Data must not be null");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
			}
			return roleManagementFacade.setRoleManagement(roleManagementRequest);

		} catch (DuplicateKeyException exception) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("duplicates not allowed");
			error.setStatusMessage(exception.getCause().getMessage());
			logger.error("data is  invalid. DuplicateKeyException");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		catch (SQLException exception) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("SQLException");
			error.setStatusMessage(exception.getMessage());
			logger.error("data is  invalid");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		catch (Exception exception) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception");
			error.setMessage(exception.getMessage());
			logger.error("data is  invalid");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(GET)
	public ResponseEntity<Object> getRoleManagement(@RequestBody RoleManagementRequest roleManagementRequest,
			HttpServletRequest servletRequest, HttpServletResponse ServletResponse) {

		try {

			if (!roleManagementRequest.getTransactionType().equalsIgnoreCase(GETALL)
					&& !roleManagementRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
				logger.debug("request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Data must not be null");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			return roleManagementFacade.getRoleManagement(roleManagementRequest);

		}
		catch (SQLException exception) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("SQLException");
			error.setStatusMessage(exception.getMessage());
			logger.error("data is  invalid");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		catch (Exception exception) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception");
			error.setStatusMessage(exception.getMessage());
			logger.error("data is  invalid");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

}
