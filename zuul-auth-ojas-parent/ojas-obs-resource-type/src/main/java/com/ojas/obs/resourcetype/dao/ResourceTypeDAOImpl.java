package com.ojas.obs.resourcetype.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ojas.obs.resourcetype.mapper.ResourceTypeRowMapper;
import com.ojas.obs.resourcetype.model.ResourceType;
import com.ojas.obs.resourcetype.util.QueryUtil;

/**
 * dao class inserts,updates,deletes and retrieves data into/from
 * employment_details table
 * 
 * @author vjithin
 *
 */
@Repository
public class ResourceTypeDAOImpl implements ResourceTypeDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceTypeDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private QueryUtil queryUtil;

	/**
	 * method inserts employment details into employement_table
	 * 
	 * @throws DataNotInsertedException
	 */

	@Override

	
	public boolean saveResourceTypes(List<ResourceType> resourceTypes) throws SQLException {

		for (ResourceType resourceType : resourceTypes) {

			if (null != resourceType) {
				try {
					Object[] inputArgs = { resourceType.getResourceTypeName() };
					int save = jdbcTemplate.update(queryUtil.getQuery("INSERT_RESOURCE_TYPES_STMT"), inputArgs);
					if (save >= 1) {
						return true;
					}
				} finally {
					if (jdbcTemplate != null) {
						try {
							jdbcTemplate.getDataSource().getConnection().close();
						} catch (Exception exception) {
							exception.getMessage();
						}
					}
				}
			}
		}

		LOGGER.debug("Resource Types inserted succefully ");
		return true;

	}

	/**
	 * method updates employment details into employement_table
	 * 
	 * @throws DataNotInsertedException
	 */

	@Override
	public boolean updateResourceTypes(List<ResourceType> resourceTypes) throws SQLException {

		for (ResourceType resourceType : resourceTypes) {

			if (null != resourceType && isResourceTypeExits(resourceType.getId())) {
					Object[] inputArgs = { resourceType.getResourceTypeName(), resourceType.getId() };
					int update = jdbcTemplate.update(queryUtil.getQuery("UPDATE_RESOURCE_TYPES_STMT"), inputArgs);
					if (update > 0) {
						return true;
					}

			} else {
				LOGGER.debug("Resource Types  records not get updated ");
				throw new SQLException("Resource Type not found  with  id ");
			}
		}

		LOGGER.debug("Resource Types not updated succefully ");
		return true;
	}

	private boolean isResourceTypeExits(Integer id) {
		Boolean b = true;
			int count = jdbcTemplate.queryForObject("select count(*) from obs_resourcetype where resourcetype_id=?",
					Integer.class, id);
			if (count == 0) {
				b = false;
			} return b;

	}

	/**
	 * method deletes employment details from employement_table
	 * 
	 * @throws DataNotInsertedException
	 */

	@Override
	public boolean deleteResourceTypes(List<ResourceType> resourceTypes) throws SQLException{

		for (ResourceType resourceType : resourceTypes) {

			if (null != resourceType && isResourceTypeExits(resourceType.getId())) {
					Object[] inputArgs = { resourceType.getId() };
					jdbcTemplate.update(queryUtil.getQuery("DELETE_RESOURCE_TYPES_STMT"), inputArgs);

			} else {
				LOGGER.debug("All requested Employee  records not get deleted ");
				throw new SQLException("Employee details not found for employee with employee id ");
			}
		}

		LOGGER.debug("All requested Employee  records  got deleted ");
		return true;
	}

	/**
	 * method retrieves employment details from employement_table
	 */
	@Override
	public List<ResourceType> getAllResourceTypes() {

		RowMapper<ResourceType> rowMapper = new ResourceTypeRowMapper();
			List<ResourceType> employmentDetailsList = jdbcTemplate.query(queryUtil.getQuery("GET_RESOURCE_TYPES_STMT"),
					rowMapper);
			LOGGER.debug("Employee  details list ");
			return employmentDetailsList;

	}

	@Override
	public List<ResourceType> getResourceTypeById(Integer id) throws SQLException {

		RowMapper<ResourceType> rowMapper = new ResourceTypeRowMapper();
			List<ResourceType> employmentDetailsList = jdbcTemplate
					.query(queryUtil.getQuery("GET_RESOURCE_TYPES_BY_ID_STMT"), new Object[] { id }, rowMapper);
			LOGGER.debug("Employee  details list " );
			return employmentDetailsList;

	}
}
