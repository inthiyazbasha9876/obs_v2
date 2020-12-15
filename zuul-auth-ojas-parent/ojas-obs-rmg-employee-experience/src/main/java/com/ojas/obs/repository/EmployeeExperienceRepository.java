package com.ojas.obs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.EmployeeExperience;
@Repository
public interface EmployeeExperienceRepository extends JpaRepository<EmployeeExperience, Integer> {

}
