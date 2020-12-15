package com.ojas.obs.projecttechstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.projecttechstack.model.ProjectTechStack;

@Repository
public interface ProjectTechStackRepository extends JpaRepository<ProjectTechStack, Integer> {

}
