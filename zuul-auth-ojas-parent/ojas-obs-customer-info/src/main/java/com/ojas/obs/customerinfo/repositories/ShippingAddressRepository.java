package com.ojas.obs.customerinfo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.customerinfo.model.ShippingAddress;
import com.ojas.obs.customerinfo.model.Customer;


@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer>{


}
