package com.ojas.obs.projecttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.projecttask.model.ProjectTask;

@RestController
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Integer> {

}
