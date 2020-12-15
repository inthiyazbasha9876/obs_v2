package com.ojas.obs.dao;

import java.util.List;

import com.ojas.obs.model.RoleModel;
import com.ojas.obs.model.BasicInfoModel;
import com.ojas.obs.model.Resignation;
import com.ojas.obs.request.ResignationRequest;

public interface ResignationDaoInterface {

	
	
	public boolean saveResignation(ResignationRequest resignationRequest);
	public boolean updateResignation(ResignationRequest resignationRequest);
	public List<Resignation> getAll(ResignationRequest resignationRequest);
	public List<Resignation> getByEmpId(String s);
	
	
	public List<RoleModel> getByRoleId();
	public List<BasicInfoModel> getByBasicInfoEmpId(String s);
	public List<String> getByContactId1(String s1, String s2);
	public boolean updateStateResignation(ResignationRequest resignationRequest);
	public List<Resignation> getById(String empId);
}
