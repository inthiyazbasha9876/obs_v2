package com.ojas.obs.resourcetype.facade;

import static com.ojas.obs.resourcetype.constant.ResourceTypesConstants.DELETE;
import static com.ojas.obs.resourcetype.constant.ResourceTypesConstants.GETALL;
import static com.ojas.obs.resourcetype.constant.ResourceTypesConstants.SAVE;
import static com.ojas.obs.resourcetype.constant.ResourceTypesConstants.UPDATE;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ojas.obs.resourcetype.dao.ResourceTypeDAO;
import com.ojas.obs.resourcetype.model.ResourceType;
import com.ojas.obs.resourcetype.model.ResourceTypeRequest;
import com.ojas.obs.resourcetype.model.ResourceTypeResponse;

/**
 * service class inserts,updates,deletes and retrieves data into/from
 * employment_details table based on transaction type
 * 
 * @author vjithin
 *
 */
@Service
public class ResourceTypeFacadeImpl implements ResourceTypeFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceTypeFacadeImpl.class);

	@Autowired
	private ResourceTypeDAO employmentDetailsDAO;

	/**
	 * method inserts,updates,deletes data into employment_details table based on
	 * transaction type
	 * 
	 * @throws EmploymentDetailsException
	 * @throws DataNotInsertedException
	 */
	@Override
	public ResourceTypeResponse saveResourceTypes(ResourceTypeRequest resourceTypeRequest)throws SQLException{
	
		ResourceTypeResponse resourceTypeResponse = new ResourceTypeResponse();

		LOGGER.debug("the requested object is");

		if (StringUtils.isEmpty(resourceTypeRequest.getTransactionType())
				|| CollectionUtils.isEmpty(resourceTypeRequest.getResourceTypes())) {
			LOGGER.error("the requested object has null values" );
			throw new SQLException("Requested object has null values");
		}
		if (resourceTypeRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			LOGGER.debug("the response object is : ");
			return insertResourceTypes(resourceTypeRequest.getResourceTypes(), resourceTypeResponse);
		} else if (resourceTypeRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			LOGGER.debug("the response object is ");
			return updateResourceTypes(resourceTypeRequest.getResourceTypes(), resourceTypeResponse);
		} else if (resourceTypeRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			LOGGER.debug("the response object is ");
			return deleteResourceTypes(resourceTypeRequest.getResourceTypes(), resourceTypeResponse);
		}

		resourceTypeResponse.setStatusCode("400");
		resourceTypeResponse.setStatusMessage("Requested transaction type is wrong");
		LOGGER.debug("the response object is");

		return resourceTypeResponse;
	
	}
	

	/**
	 * method deletes data from employment_details table
	 * 
	 * @param employmentDetails
	 * @param employementDetailsResponse
	 * @return
	 * @throws EmploymentDetailsException
	 * @throws DataNotInsertedException
	 * @throws SQLException 
	 */

	private ResourceTypeResponse deleteResourceTypes(List<ResourceType> employmentDetailsList,
			ResourceTypeResponse employmentDetailsResponse)
			throws SQLException {
		employmentDetailsResponse.setStatusCode("201");
		employmentDetailsResponse.setStatusMessage("Resource Type not deleted successfully");

		if (employmentDetailsDAO.deleteResourceTypes(employmentDetailsList)) {
			employmentDetailsResponse.setStatusCode("201");
			employmentDetailsResponse.setStatusMessage("Resource Type deleted successfully");
		}
		return employmentDetailsResponse;
	}

	/**
	 * method updates data into employment_details table
	 * 
	 * @param employmentDetails
	 * @param employementDetailsResponse
	 * @return
	 * @throws EmploymentDetailsException
	 * @throws DataNotInsertedException
	 * @throws SQLException 
	 */

	private ResourceTypeResponse updateResourceTypes(List<ResourceType> employmentDetailsList,
			ResourceTypeResponse employmentDetailsResponse)
			throws SQLException {
		employmentDetailsResponse.setStatusCode("201");
		employmentDetailsResponse.setStatusMessage("Resource Type not updated successfully");

		if (employmentDetailsDAO.updateResourceTypes(employmentDetailsList)) {
			employmentDetailsResponse.setStatusCode("201");
			employmentDetailsResponse.setStatusMessage("Resource Type updated successfully");
		}
		return employmentDetailsResponse;
	}

	/**
	 * method inserts data into employment_details table
	 * 
	 * @param employmentDetails
	 * @param employementDetailsResponse
	 * @return
	 * @throws EmploymentDetailsException
	 * @throws DataNotInsertedException
	 */
	private ResourceTypeResponse insertResourceTypes(List<ResourceType> employmentDetailsList,
			ResourceTypeResponse employmentDetailsResponse)
			throws SQLException {
		employmentDetailsResponse.setStatusCode("400");
		employmentDetailsResponse.setStatusMessage("Resource Type not saved successfully");

		if (employmentDetailsDAO.saveResourceTypes(employmentDetailsList)) {
			employmentDetailsResponse.setStatusCode("201");
			employmentDetailsResponse.setStatusMessage("Resource Type saved successfully");
		}
		return employmentDetailsResponse;

	}

	/**
	 * method retrieves data from employment_details table
	 * @throws SQLException 
	 */
	@Override
	public ResourceTypeResponse viewResourceTypes(ResourceTypeRequest resourceTypeRequest)
			throws SQLException {

		ResourceTypeResponse employmentDetailsResponse = new ResourceTypeResponse();
		employmentDetailsResponse.setStatusCode("204");
		employmentDetailsResponse.setStatusMessage("Resource Types not found");

		if (StringUtils.isEmpty(resourceTypeRequest.getTransactionType())) {
			LOGGER.error("the requested object has null values" );
			throw new SQLException("Requested object has null values");
		}
		List<ResourceType> employmentDetailsList = null;
		if (resourceTypeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			if (null != resourceTypeRequest.getResourceTypes() && null != resourceTypeRequest.getResourceTypes().get(0)
					&& null != resourceTypeRequest.getResourceTypes().get(0).getId()) {
				employmentDetailsList = employmentDetailsDAO
						.getResourceTypeById(resourceTypeRequest.getResourceTypes().get(0).getId());
			} else {
				employmentDetailsList = employmentDetailsDAO.getAllResourceTypes();
			}
		}

		if (!CollectionUtils.isEmpty(employmentDetailsList)) {
			employmentDetailsResponse.setEmploymentDetailsList(employmentDetailsList);
			employmentDetailsResponse.setStatusCode("200");
			employmentDetailsResponse.setStatusMessage("ResourceTypes found");
		}
		LOGGER.debug("the response object is" );
		return employmentDetailsResponse;
	}

}
