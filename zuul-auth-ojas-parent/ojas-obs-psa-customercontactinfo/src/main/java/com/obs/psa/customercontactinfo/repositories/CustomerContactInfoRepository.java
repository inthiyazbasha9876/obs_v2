package com.obs.psa.customercontactinfo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obs.psa.customercontactinfo.model.CustomerContactInfo;
import com.obs.psa.customercontactinfo.request.CustomerContactInfoRequest;


@Repository
public interface CustomerContactInfoRepository extends JpaRepository<CustomerContactInfo, Integer>{




}
