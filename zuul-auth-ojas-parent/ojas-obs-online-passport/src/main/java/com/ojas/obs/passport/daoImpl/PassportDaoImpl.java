package com.ojas.obs.passport.daoImpl;

import static com.ojas.obs.passport.utility.Constants.COUNTSTMT;
import static com.ojas.obs.passport.utility.Constants.GETTOBYID;
import static com.ojas.obs.passport.utility.Constants.GETTOTALSTMT;
//import com.ojas.obs.passport.utility.PropsReaderUtil;
import static com.ojas.obs.passport.utility.Constants.INSERTPASSPORTSTMT;
import static com.ojas.obs.passport.utility.Constants.DELETESTMT;
import static com.ojas.obs.passport.utility.Constants.UPDATESTMT;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.dao.PassportDao;
import com.ojas.obs.passport.model.Passport;

@Repository
public class PassportDaoImpl implements PassportDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	// public static final String countQuery="select count(*) from obs_password
	// where centername=?";
	/*
	 * @Autowired private PropsReaderUtil propsReaderUtil;
	 */
	Logger logger = Logger.getLogger(this.getClass());

	/** save method **/

	@Override
	public boolean savePassport(PassportRequest passportRequest) throws SQLException {
		logger.info("@@@@ In save Passport method jdbc template" + jdbcTemplate);

		List<Passport> passport = passportRequest.getPassportList();
		List<Object[]> list = new ArrayList<Object[]>();
		int count = 0;
		boolean flag = false;
		for (Passport passport2 : passport) {
			Object[] params = new Object[] { passport2.getCenterName() };
			list.add(params);
			// int[] update =
			// jdbcTemplate.batchUpdate(propsReaderUtil.getValue("INSERTPASSPORTSTMT"),
			// list);
			int[] update = jdbcTemplate.batchUpdate(INSERTPASSPORTSTMT, list);

//			for (int i : update) {
//				if (i > 0) {
//					logger.debug("@@@@saved successfully through DaoImpl" + i);
//					count = ++count;
//				}
//			}
//			if (count == update.length) {
//				flag = true;
//			}
			if (update.length > 0) {
				logger.debug("@@@@saved successfully through DaoImpl");
				return flag = true;
			}
		}
		logger.debug("@@@@failed to save through daoImpl Method");
		return flag;
	}

	// delete method

	@Override
	public boolean deletePassport(PassportRequest passportRequest) throws SQLException {
		logger.debug("Inside the delete passport method DaoImpl");
		List<Passport> passport = passportRequest.getPassportList();
		List<Object[]> list = new ArrayList<Object[]>();
		for (Passport passport2 : passport) {
			Object[] params = new Object[] { passport2.getId() };
			list.add(params);
		} // int[] update =
		//jdbcTemplate.batchUpdate(propsReaderUtil.getValue("DELETESTMT"), list);
		int[] update = jdbcTemplate.batchUpdate(DELETESTMT, list);
		for (int i : update) {
			if (i > 0) {
				logger.debug("delete passport in DaoImpl " + i);
				return true;
			}
		}
		logger.debug("failed delete passport in DaoImpl ");
		return false;
	}

	//

	/** update method **/

	@Override
	public boolean updatePassport(PassportRequest passportRequest) throws SQLException {
		logger.info("@@@@Inside the update passport method DaoImpl");
		List<Passport> passport = passportRequest.getPassportList();
		List<Object[]> list = new ArrayList<Object[]>();
		int count = 0;
		boolean flag = false;
		for (Passport passport2 : passport) {
			Object[] params = new Object[] { passport2.getCenterName(), passport2.getId() };
			list.add(params);

			// int[] update =
			// jdbcTemplate.batchUpdate(propsReaderUtil.getValue("UPDATESTMT"), list);
			int[] update = jdbcTemplate.batchUpdate(UPDATESTMT, list);
			for (int i : update) {
				if (i > 0) {
					logger.debug("@@@@updated successfully through DaoImpl" + i);
					count = ++count;
				}
			}
			if (count == update.length) {
				flag = true;
			}
		}
		logger.debug("@@@@failed to update through daoImpl Method");
		return flag;
	}

	/** GETALL method **/

	@Override
	public List<Passport> getAll(PassportRequest passportRequest) throws SQLException {
		logger.info("@@@@Inside the getAll passport method in DaoImpl");
		// List<Passport> query =
		// jdbcTemplate.query(propsReaderUtil.getValue("GETTOTALSTMT"),new
		// BeanPropertyRowMapper<>(Passport.class));
		List<Passport> query = null;

		query = jdbcTemplate.query(GETTOTALSTMT, new BeanPropertyRowMapper<>(Passport.class));

		logger.debug("@@@@Returned List" + query);
		return query;
	}

	/** Count number of records in database **/

	@Override
	public Integer getcountPassport(PassportRequest passportRequest) throws SQLException {
		logger.info("@@@@Inside the countPassport method in DaoImpl");
		// Integer update =
		// jdbcTemplate.queryForObject(propsReaderUtil.getValue("COUNTSTMT"),
		// Integer.class);
		Integer update = null;

		update = jdbcTemplate.queryForObject(COUNTSTMT, Integer.class);
		jdbcTemplate.getDataSource().getConnection().close();
		logger.debug("@@@@Returned count" + update);
		return update;
	}

	/** Pagination **/

	/*
	 * @Override public List<Passport> getCountPerPage(List<Passport> passportList,
	 * int pageSize, int pageNo) throws SQLException { List<Passport> getAllFiltered
	 * = new ArrayList<>(); if (passportList != null && !passportList.isEmpty()) {
	 * pageSize = pageSize > 0 ? pageSize : pageSize * -1; pageNo = pageNo > 0 ?
	 * pageNo : pageNo == 0 ? 1 : pageNo * -1; if (pageSize != 0) { int endIndex =
	 * pageNo * pageSize; int startIndex = endIndex - pageSize; endIndex = endIndex
	 * < passportList.size() ? endIndex : passportList.size(); startIndex =
	 * startIndex < passportList.size() ? startIndex : 0; getAllFiltered =
	 * passportList.subList(startIndex, endIndex); } } return getAllFiltered; }
	 */
	/** GetById **/

	@Override
	public List<Passport> getById(PassportRequest passportRequest) throws SQLException {
		logger.info("@@@@Inside the getById method in DaoImpl");
		List<Object[]> inputList = new ArrayList<Object[]>();
		Object[] update = null;

		List<Passport> listOfPassport = passportRequest.getPassportList();

		for (Passport passport : listOfPassport) {
			update = new Object[] { passport.getId() };
			inputList.add(update);
		}
		return jdbcTemplate.query(GETTOBYID, update, new BeanPropertyRowMapper<>(Passport.class));
	}
}
