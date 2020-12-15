package com.ojas.obs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

}
