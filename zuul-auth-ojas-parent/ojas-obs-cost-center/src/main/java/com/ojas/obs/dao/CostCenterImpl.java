package com.ojas.obs.dao;

import static com.ojas.obs.constants.UserConstants.SOFTDELETECOSTCENTER;
import static com.ojas.obs.constants.UserConstants.GETCOSTCENTER;
import static com.ojas.obs.constants.UserConstants.GETCOSTCENTERCOUNT;
import static com.ojas.obs.constants.UserConstants.INSERTCOSTCENTER;
import static com.ojas.obs.constants.UserConstants.UPDATECOSTCENTER;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.CostCenter;
import com.ojas.obs.model.CostCenterRequest;

@Repository
public class CostCenterImpl implements CostCenterDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

 
	// saving the data into database
	@Override
	public boolean save(CostCenterRequest costCenterRequest) throws SQLException {
		logger.info(" The Request INSIDE the save Method....");
		int c = 0;
		boolean b = false;
		List<CostCenter> lists = costCenterRequest.getCostCenter();
		ArrayList<Object[]> list = new ArrayList<>();

		for (CostCenter costCenter2 : lists) {
			Object[] param = new Object[] { costCenter2.getCostCenterCode() };
			list.add(param);
		}

		int[] i = jdbcTemplate.batchUpdate(INSERTCOSTCENTER, list);

		for (int j : i) {
			if (j > 0) {

				c++;
			}

		}
		if (costCenterRequest.getCostCenter().size() == c) {
			b = true;
		}
		return b;

	}

	// update the data into database

	@Override
	public boolean updateCenter(List<CostCenter> lists) throws SQLException {
		logger.info("The Request INSIDE the updateCenter Method....");
		int c = 0;
		boolean b = false;
		ArrayList<Object[]> list = new ArrayList<>();

		for (CostCenter costCenter2 : lists) {
			Object[] param = new Object[] { costCenter2.getCostCenterCode(), costCenter2.getId() };

			list.add(param);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATECOSTCENTER, list);

		for (int i : batchUpdate) {

			if (i > 0) {
				c++;
			}
		}
		if (lists.size() == c) {
			b = true;
		}

		return b;

	}

	// retrieve the data in database

	@Override
	public List<CostCenter> getAllCostCenter(CostCenterRequest costCenterReq) throws SQLException {

		logger.info(" the request INSIDE the getAllCostCenter....");
		List<CostCenter> cost = null;

		List<CostCenter> costCenter = costCenterReq.getCostCenter();

		if (costCenterReq.getTransactionType().equalsIgnoreCase("DELETE")) {
			System.out.println("query --- " + SOFTDELETECOSTCENTER);

			ArrayList<Object[]> list = new ArrayList<>();

			for (CostCenter costCenter2 : costCenter) {
				Object[] param = new Object[] { costCenter2.getId() };

				list.add(param);
			}
			jdbcTemplate.batchUpdate(SOFTDELETECOSTCENTER, list);

		} else {

			cost = jdbcTemplate.query(GETCOSTCENTER, new BeanPropertyRowMapper<>(CostCenter.class));
		}
		return cost;
	}

	// get costcenter count

	@Override
	public int getAllCostCenterCount() throws SQLException {

		logger.info("The Request INSIDE the getAllCostCenterCount....");
		int i = jdbcTemplate.queryForObject(GETCOSTCENTERCOUNT, Integer.class);
		logger.info("The Request INSIDE the getAllCostCenterCount.1...");
		// jdbcTemplate.getDataSource().getConnection().close();
		return i;

	}

	/*
	 * @Override public List<CostCenter> getCountPerPage(List<CostCenter> list, int
	 * pageSize, int pageNo) {
	 * logger.info(" the Request INSIDE the getCountPerPage....");
	 * 
	 * List<CostCenter> getAllFilteredList = new ArrayList<>(); if (list != null &&
	 * !list.isEmpty()) { pageSize = pageSize > 0 ? pageSize : pageSize * -1; pageNo
	 * = pageNo > 0 ? pageNo : pageNo == 0 ? 1 : pageNo * -1; if (pageSize != 0) {
	 * int endIndex = pageNo * pageSize; int startIndex = endIndex - pageSize;
	 * endIndex = endIndex < list.size() ? endIndex : list.size(); startIndex =
	 * startIndex < list.size() ? startIndex : 0; getAllFilteredList =
	 * list.subList(startIndex, endIndex); } } return getAllFilteredList;
	 * 
	 * }
	 */

	@Override
	public boolean deleteCostCenter(CostCenterRequest costCenterReq) throws SQLException {

		logger.info(" the request INSIDE the getAllCostCenter....");
		int c = 0;
		boolean b = false;

		List<CostCenter> costCenter = costCenterReq.getCostCenter();

		if (costCenterReq.getTransactionType().equalsIgnoreCase("DELETE")) {
			System.out.println("query --- " + SOFTDELETECOSTCENTER);

			ArrayList<Object[]> list = new ArrayList<>();

			for (CostCenter costCenter2 : costCenter) {
				Object[] param = new Object[] { costCenter2.getId() };

				list.add(param);
			}
			int[] batchUpdate = jdbcTemplate.batchUpdate(SOFTDELETECOSTCENTER, list);

			for (int i : batchUpdate) {

				if (i > 0) {
					c++;
				}
			}
			if (costCenter.size() == c) {
				b = true;
			}

		}
		return b;
	}

}
