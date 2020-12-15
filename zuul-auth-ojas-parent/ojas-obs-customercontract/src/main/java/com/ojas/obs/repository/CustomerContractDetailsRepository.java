package com.ojas.obs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.CustomerContractDetails;

@Repository

public interface CustomerContractDetailsRepository extends JpaRepository<CustomerContractDetails, Integer> {

	@Query("select new com.ojas.obs.model.CustomerContractDetails(contractid, customerid, contractname, description, startdate, enddate, servicetype, deliverylocation, createdby, contractvalue, contractowner, status, contractcurrency, executingcompany, contractcompany, documenttype, documentstage, multilocationdelivery, updatedBy, createdDate, updatedDate, buHead, sbuHead, comment, buStatus, financeStatus, contractType) FROM CustomerContractDetails where customerid= :customerid")
	public List<CustomerContractDetails> getByCustomerid(Integer customerid);

	@Query("select new com.ojas.obs.model.CustomerContractDetails(contractid, customerid, contractname, description, startdate, enddate, servicetype, deliverylocation, createdby, contractvalue, contractowner, status, contractcurrency, executingcompany, contractcompany, documenttype, documentstage, multilocationdelivery, updatedBy, createdDate, updatedDate, buHead, sbuHead, comment, buStatus, financeStatus, contractType) FROM CustomerContractDetails where status = true")
	public List<CustomerContractDetails> getCustomercontractdetailslist();

	@Query("select new com.ojas.obs.model.CustomerContractDetails(contractid, customerid, contractname, description, startdate, enddate, servicetype, deliverylocation, createdby, contractvalue, contractowner, status, contractcurrency, executingcompany, contractcompany, documenttype, documentstage, multilocationdelivery, updatedBy, createdDate, updatedDate, buHead, sbuHead, comment, buStatus, financeStatus, contractType) FROM CustomerContractDetails where contractid= :contractid")
	public List<CustomerContractDetails> getByContractid(Integer contractid);

	@Query("Select document FROM CustomerContractDetails WHERE contractid = :contractid")
	public String getDocument(Integer contractid);
}
