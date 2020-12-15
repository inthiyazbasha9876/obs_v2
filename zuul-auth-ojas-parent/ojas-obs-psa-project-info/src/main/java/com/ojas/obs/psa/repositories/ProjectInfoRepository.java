package com.ojas.obs.psa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.obs.psa.model.ProjectInfo;

public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Integer>{
	public List<ProjectInfo> getByContractId(Integer contractId);
	public List<ProjectInfo> getByCustomerId(Integer customerId);
	@Query("FROM ProjectInfo where flag = true")
	public List<ProjectInfo> getAllProjects();
	@Query("Select projectName from ProjectInfo")
	public List<String> getAllProjectNames();

}
