package com.ojas.obs.customerinfo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ojas.obs.customerinfo.model.RegisteredAddress;

@Repository
public interface RegisteredAddressRepository extends JpaRepository<RegisteredAddress, Integer> {

}
