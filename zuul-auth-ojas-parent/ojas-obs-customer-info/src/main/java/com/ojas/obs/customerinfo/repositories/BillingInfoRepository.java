package com.ojas.obs.customerinfo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.customerinfo.model.BillingInfo;
import com.ojas.obs.customerinfo.model.ContactInfo;

@Repository
public interface BillingInfoRepository  extends JpaRepository<BillingInfo, Integer>
{

}
