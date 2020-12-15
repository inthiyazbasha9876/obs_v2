package com.ojas.obs.customerinfo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.customerinfo.model.CustomerGst;


@Repository
public interface CustomerGstRepository  extends JpaRepository<CustomerGst, Integer> {

}
