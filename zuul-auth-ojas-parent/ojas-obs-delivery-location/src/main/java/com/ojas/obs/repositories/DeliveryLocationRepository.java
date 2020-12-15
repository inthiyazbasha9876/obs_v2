package com.ojas.obs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.obs.model.DeliveryLocation;

public interface DeliveryLocationRepository extends JpaRepository<DeliveryLocation, Integer> 
{

}
