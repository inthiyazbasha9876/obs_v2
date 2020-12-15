package com.ojas.obs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.DocumentStage;
@Repository
public interface DocumentStageRepository extends JpaRepository<DocumentStage, Integer> {

}
