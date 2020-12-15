package com.ojas.obs.actionowner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.actionowner.model.ActionOwner;
@Repository
public interface ActionOwnerRepository extends JpaRepository<ActionOwner, Integer> {

}
