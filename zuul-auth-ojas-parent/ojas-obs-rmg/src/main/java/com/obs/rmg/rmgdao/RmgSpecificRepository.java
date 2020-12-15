package com.obs.rmg.rmgdao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.obs.rmg.rmgmodel.RmgSpecific;

public interface RmgSpecificRepository extends JpaRepository<RmgSpecific, Integer>
{
	@Query("FROM RmgSpecific where flag = 1")
	public List<RmgSpecific> findAllSpecific();
}
