package com.ojas.obs.actiontype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.actiontype.model.ActionType;

@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Integer> {
}
