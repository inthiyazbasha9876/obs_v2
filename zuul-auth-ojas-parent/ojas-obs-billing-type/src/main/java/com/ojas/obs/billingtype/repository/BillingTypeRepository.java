package com.ojas.obs.billingtype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.billingtype.model.BillingType;

@Repository
public interface BillingTypeRepository extends JpaRepository<BillingType, Integer> {
}
