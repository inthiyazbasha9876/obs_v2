package com.ojas.obs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.LeaveType;
@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer>{

}
