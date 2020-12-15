package com.ojas.obs.dao;

import static com.ojas.obs.constants.ResignationConstants.GETALLRESIGNATION;

import static com.ojas.obs.constants.ResignationConstants.GETBY_EMPIDRESIGNATION;
import static com.ojas.obs.constants.ResignationConstants.GETBY_IDRESIGNATION;
import static com.ojas.obs.constants.ResignationConstants.SAVERESIGNATION;
import static com.ojas.obs.constants.ResignationConstants.UPDATERESIGNATION;
import static com.ojas.obs.constants.ResignationConstants.UPDATESTATERESIGNATION;

import static com.ojas.obs.constants.ResignationConstants.GETBYROLEID;
import static com.ojas.obs.constants.ResignationConstants.GETBYBASICINFO;
import static com.ojas.obs.constants.ResignationConstants.GETMEILIDS;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.BasicInfoModel;
import com.ojas.obs.model.Resignation;
import com.ojas.obs.model.RoleModel;
import com.ojas.obs.request.ResignationRequest;

@Repository
public class ResignationDaoImpl implements ResignationDaoInterface {

	@Autowired
	private JdbcTemplate jdbctemplate;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean saveResignation(ResignationRequest resignationRequest) {
		Boolean b = false;

		List<Object[]> inputList = new ArrayList<>();

		for (Resignation resig : resignationRequest.getResignation()) {
			resig.setState("Applied");
			Object[] obj = new Object[] { resig.getResignationType(), resig.getResignationSubmittedOn(),
					resig.getFinalSettlementDate(), resig.getLeavingReason(), resig.getLeavingDate(),
					resig.getEmployeeIsDeceased(), resig.getDateOfDemise(), resig.getEmailId1(), resig.getEmailId2(),
					resig.getEmailId3(), resig.getEmailId4(), resig.getEmployeeId(), resig.getRemarks(),
					resig.getState() };

			inputList.add(obj);
		}
		int[] batchSave = jdbctemplate.batchUpdate(SAVERESIGNATION, inputList);
		if (batchSave.length > 0) {
			b = true;
		}

		return b;

	}

	@Override
	public boolean updateResignation(ResignationRequest resignationRequest) {
		Boolean b = false;

		List<Object[]> inputList = new ArrayList<>();
		for (Resignation resig : resignationRequest.getResignation()) {
			Object[] obj = new Object[] { resig.getResignationType(), resig.getResignationSubmittedOn(),
					resig.getFinalSettlementDate(),resig.getLeavingDate(),
					resig.getEmployeeId(), 
					resig.getState(), resig.getId() };

			inputList.add(obj);
		}
		int[] batchSave = jdbctemplate.batchUpdate(UPDATERESIGNATION, inputList);
		if (batchSave.length > 0) {
			b =  true;
		}
		return b ;
	}

	@Override
	public boolean updateStateResignation(ResignationRequest resignationRequest) {
		Boolean b = false;

		List<Object[]> inputList = new ArrayList<>();
		for (Resignation resig : resignationRequest.getResignation()) {
			Object[] obj = new Object[] { resig.getState(), resig.getId() };

			inputList.add(obj);
		}
		int[] batchSave = jdbctemplate.batchUpdate(UPDATESTATERESIGNATION, inputList);
		if (batchSave.length > 0) {
			b = true;
		}
		return b ;
	}

	@Override
	public List<Resignation> getAll(ResignationRequest resignationRequest) {

		logger.info("in dao class");
		List<Resignation> list = jdbctemplate.query(GETALLRESIGNATION, new BeanPropertyRowMapper<>(Resignation.class));
		logger.info("list of records in dao ");
		return list;
	}

	@Override
	public List<Resignation> getById(String empId) {

		return jdbctemplate.query(GETBY_IDRESIGNATION + empId, new BeanPropertyRowMapper<>(Resignation.class));
	}

	@Override
	public List<Resignation> getByEmpId(String s) {

		return jdbctemplate.query(GETBY_EMPIDRESIGNATION + "'" + s + "'",
				new BeanPropertyRowMapper<>(Resignation.class));

	}

	@Override
	public List<RoleModel> getByRoleId() {

		return jdbctemplate.query(GETBYROLEID, new BeanPropertyRowMapper<>(RoleModel.class));

	}

	@Override
	public List<BasicInfoModel> getByBasicInfoEmpId(String s) {

		List<BasicInfoModel> lsBasicInfo = jdbctemplate.query(GETBYBASICINFO + "'" + s + "'",
				new BeanPropertyRowMapper<>(BasicInfoModel.class));
		logger.info("This is basic info list based on Resignation emp id  in  DAO===================");
		logger.info("thsi is in dao calss ============");
		return lsBasicInfo;

	}

	@Override
	public List<String> getByContactId1(String s1, String s2) {

		List<String> forList = jdbctemplate.queryForList(GETMEILIDS + "(" + "'" + s1 + "'" + "," + "'" + s2 + "'" + ")",
				String.class);

		logger.info("email id getting multiple ");
		logger.info("getByContactId1(String s1, String s2, String s3)");
		return forList;

	}

}
