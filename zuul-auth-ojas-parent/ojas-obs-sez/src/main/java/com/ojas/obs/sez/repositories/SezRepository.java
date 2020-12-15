package com.ojas.obs.sez.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.sez.model.Sez;

@Repository
public interface SezRepository extends JpaRepository<Sez, Integer> {

}
