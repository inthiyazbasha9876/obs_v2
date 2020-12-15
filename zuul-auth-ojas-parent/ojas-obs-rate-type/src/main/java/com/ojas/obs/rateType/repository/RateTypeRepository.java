package com.ojas.obs.rateType.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.rateType.model.RateType;

@Repository
public interface RateTypeRepository extends JpaRepository<RateType, Integer> {

}
