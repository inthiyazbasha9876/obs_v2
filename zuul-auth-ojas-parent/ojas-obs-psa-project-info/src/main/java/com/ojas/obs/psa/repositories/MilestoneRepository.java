package com.ojas.obs.psa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.obs.psa.model.Milestone;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer>{
	
}
