package com.obs.rmg.rmgdao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.obs.rmg.rmgmodel.RmgGenericResourceMap;

public interface RmgGenericResourceMapRepository extends JpaRepository<RmgGenericResourceMap, Integer> 
{
	@Query("FROM RmgGenericResourceMap where flag = 1")
	public List<RmgGenericResourceMap> findAllGeneric();
}
