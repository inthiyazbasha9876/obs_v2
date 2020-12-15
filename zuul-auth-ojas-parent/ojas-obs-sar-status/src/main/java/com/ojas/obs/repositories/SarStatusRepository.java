package com.ojas.obs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.SarStatus;
@Repository
public interface SarStatusRepository extends JpaRepository<SarStatus, Integer>{

}
