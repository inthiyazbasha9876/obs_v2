package com.ojas.obs.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "obs_customer_contract")
public class CustomerContractDetails {

	@Id
	@SequenceGenerator(name = "contractid", initialValue = 101)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "contractid")
	@Column
	private Integer contractid;

	@Column
	private Integer customerid;

	@Column

	private String contractname;

	@Column
	private String description;

	@Column

	private Date startdate;

	@Column

	private Date enddate;

	@Column

	private String servicetype;

	@Column

	private String deliverylocation;

	@Column

	private String createdby;

	@Column

	private Double contractvalue;

	@Column

	private String contractowner;

	@Column(columnDefinition = "tinyint default true")

	private Boolean status;

	@Column
	private String contractcurrency;

	@Column
	private String executingcompany;

	@Column
	private String contractcompany;

	@Column
	private String documenttype;

	@Column
	private String documentstage;

	@Column
	private String[] multilocationdelivery;

	@Lob
	@Column
	private String document;

	@Column(length = 8)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(length = 8)
	private String buHead;

	@Column(length = 8)
	private String sbuHead;

	@Column
	private String comment;

	@Column
	private String buStatus;

	@Column
	private String financeStatus;
	
	@Column
	private String contractType;

	public Integer getContractid() {
		return contractid;
	}

	public void setContractid(Integer contractid) {
		this.contractid = contractid;
	}

	public Integer getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}

	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getDeliverylocation() {
		return deliverylocation;
	}

	public void setDeliverylocation(String deliverylocation) {
		this.deliverylocation = deliverylocation;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Double getContractvalue() {
		return contractvalue;
	}

	public void setContractvalue(Double contractvalue) {
		this.contractvalue = contractvalue;
	}

	public String getContractowner() {
		return contractowner;
	}

	public void setContractowner(String contractowner) {
		this.contractowner = contractowner;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getContractcurrency() {
		return contractcurrency;
	}

	public void setContractcurrency(String contractcurrency) {
		this.contractcurrency = contractcurrency;
	}

	public String getExecutingcompany() {
		return executingcompany;
	}

	public void setExecutingcompany(String executingcompany) {
		this.executingcompany = executingcompany;
	}

	public String getContractcompany() {
		return contractcompany;
	}

	public void setContractcompany(String contractcompany) {
		this.contractcompany = contractcompany;
	}

	public String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}

	public String getDocumentstage() {
		return documentstage;
	}

	public void setDocumentstage(String documentstage) {
		this.documentstage = documentstage;
	}

	public String[] getMultilocationdelivery() {
		return multilocationdelivery;
	}

	public void setMultilocationdelivery(String[] multilocationdelivery) {
		this.multilocationdelivery = multilocationdelivery;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getBuHead() {
		return buHead;
	}

	public void setBuHead(String buHead) {
		this.buHead = buHead;
	}

	public String getSbuHead() {
		return sbuHead;
	}

	public void setSbuHead(String sbuHead) {
		this.sbuHead = sbuHead;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getBuStatus() {
		return buStatus;
	}

	public void setBuStatus(String buStatus) {
		this.buStatus = buStatus;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}
	

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	@Override
	public String toString() {
		return "CustomerContractDetails [contractid=" + contractid + ", customerid=" + customerid + ", contractname="
				+ contractname + ", description=" + description + ", startdate=" + startdate + ", enddate=" + enddate
				+ ", servicetype=" + servicetype + ", deliverylocation=" + deliverylocation + ", createdby=" + createdby
				+ ", contractvalue=" + contractvalue + ", contractowner=" + contractowner + ", status=" + status
				+ ", contractcurrency=" + contractcurrency + ", executingcompany=" + executingcompany
				+ ", contractcompany=" + contractcompany + ", documenttype=" + documenttype + ", documentstage="
				+ documentstage + ", multilocationdelivery=" + Arrays.toString(multilocationdelivery) + ", document="
				+ document + ", updatedBy=" + updatedBy + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", buHead=" + buHead + ", sbuHead=" + sbuHead + ", comment=" + comment + ", buStatus="
				+ buStatus + ", financeStatus=" + financeStatus + ", contractType=" + contractType + "]";
	}

	public CustomerContractDetails() {
	}
	

	public CustomerContractDetails(Integer contractid, Integer customerid, String contractname, String description,
			Date startdate, Date enddate, String servicetype, String deliverylocation, String createdby,
			Double contractvalue, String contractowner, Boolean status, String contractcurrency,
			String executingcompany, String contractcompany, String documenttype, String documentstage,
			String[] multilocationdelivery, String updatedBy, Date createdDate, Date updatedDate, String buHead,
			String sbuHead, String comment, String buStatus, String financeStatus, String contractType) {
		super();
		this.contractid = contractid;
		this.customerid = customerid;
		this.contractname = contractname;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
		this.servicetype = servicetype;
		this.deliverylocation = deliverylocation;
		this.createdby = createdby;
		this.contractvalue = contractvalue;
		this.contractowner = contractowner;
		this.status = status;
		this.contractcurrency = contractcurrency;
		this.executingcompany = executingcompany;
		this.contractcompany = contractcompany;
		this.documenttype = documenttype;
		this.documentstage = documentstage;
		this.multilocationdelivery = multilocationdelivery;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.buHead = buHead;
		this.sbuHead = sbuHead;
		this.comment = comment;
		this.buStatus = buStatus;
		this.financeStatus = financeStatus;
		this.contractType = contractType;
	}

}
