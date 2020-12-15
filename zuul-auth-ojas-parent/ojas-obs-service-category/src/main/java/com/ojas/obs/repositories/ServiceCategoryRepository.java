package com.ojas.obs.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.ServiceCategory;



@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer>{

}
