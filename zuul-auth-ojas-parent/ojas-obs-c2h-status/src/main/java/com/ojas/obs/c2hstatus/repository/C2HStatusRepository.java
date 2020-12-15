package com.ojas.obs.c2hstatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.c2hstatus.model.C2HStatus;

@Repository
public interface C2HStatusRepository extends JpaRepository<C2HStatus, Integer> {

}
