package com.ojas.obs.psa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.obs.psa.model.ProjectResourceMapping;

public interface ResourceMapRepository extends JpaRepository<ProjectResourceMapping, Integer>{

}
