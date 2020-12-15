package com.ojas.obs.projecttype.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.projecttype.model.ProjectType;

@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer> {

}
