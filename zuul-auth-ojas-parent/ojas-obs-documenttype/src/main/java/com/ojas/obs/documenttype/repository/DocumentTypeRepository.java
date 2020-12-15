package com.ojas.obs.documenttype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ojas.obs.documenttype.model.DocumentType;


@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

}
