package com.ojas.obs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.Geo;
@Repository
public interface GeoRepository extends JpaRepository<Geo, Integer>{

}
