package com.obs.rmg.rmgdao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.obs.rmg.rmgmodel.EmployeeProjects;
import com.obs.rmg.rmgmodel.ProjectList;
import com.obs.rmg.rmgmodel.RMG;
import com.obs.rmg.rmgmodel.RmgEmployeeList;



public interface RmgDao extends JpaRepository<RMG, Integer>
{

    @Query("FROM RmgEmployeeList  where employmentStatus='Bench' or employmentStatus='Lateral'")
	public List<RmgEmployeeList> getEmpIdsByStatus();
    
    public List<RMG> getByprojectId(String projectId);

  
}
