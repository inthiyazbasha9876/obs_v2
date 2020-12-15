package com.ojas.obs.contractcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.contractcompany.model.ContractCompany;

@Repository
public interface ContractCompanyRepository extends JpaRepository<ContractCompany, Integer> {
}
