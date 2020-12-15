package com.ojas.obs.customerinfo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.obs.customerinfo.model.BillingAddress;


public interface BillingAddressRepository extends JpaRepository<BillingAddress, Integer>{

}
