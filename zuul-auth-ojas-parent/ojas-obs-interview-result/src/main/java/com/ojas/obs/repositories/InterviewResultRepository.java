package com.ojas.obs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.InterviewResult;
@Repository
public interface InterviewResultRepository extends JpaRepository<InterviewResult, Integer> {

}
