package com.ojas.obs.interviewmode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.interviewmode.model.InterviewMode;

@Repository
public interface InterviewModeRepository extends JpaRepository<InterviewMode, Integer> {
}
