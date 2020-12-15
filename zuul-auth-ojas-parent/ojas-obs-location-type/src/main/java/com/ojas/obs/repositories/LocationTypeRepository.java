package com.ojas.obs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.LocationType;
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Integer>{

}
