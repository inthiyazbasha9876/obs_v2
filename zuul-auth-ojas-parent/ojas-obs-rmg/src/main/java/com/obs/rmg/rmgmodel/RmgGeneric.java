package com.obs.rmg.rmgmodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class RmgGeneric 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int genericId;
	
	@Column
    private Double resourceExperience;
   
    @ElementCollection
    @Column
    private Set<String> resourceSkills;
    
    @Column
	private String startDate;
	
	@Column
	private String endDate;
	
	@Column
	private float billRate;

	@Column
	private int resourceCount;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","rmggeneric"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "rmggeneric")
    private Set<RMG> rmg = new HashSet<>();
	

	@OneToMany(mappedBy = "rmggeneric", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("rmggeneric")
	private Set<RmgGenericResourceMap> rmggenericresourcemap = new HashSet<>();


	public int getGenericId() {
		return genericId;
	}



	public void setGenericId(int genericId) {
		this.genericId = genericId;
	}



	public Double getResourceExperience() {
		return resourceExperience;
	}



	public void setResourceExperience(Double resourceExperience) {
		this.resourceExperience = resourceExperience;
	}



	public Set<String> getResourceSkills() {
		return resourceSkills;
	}



	public void setResourceSkills(Set<String> resourceSkills) {
		this.resourceSkills = resourceSkills;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public float getBillRate() {
		return billRate;
	}



	public void setBillRate(float billRate) {
		this.billRate = billRate;
	}



	public int getResourceCount() {
		return resourceCount;
	}



	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}



	public Set<RMG> getRmg() {
		return rmg;
	}



	public void setRmg(Set<RMG> rmg) {
		this.rmg = rmg;
	}



	public Set<RmgGenericResourceMap> getRmggenericresourcemap() {
		return rmggenericresourcemap;
	}



	public void setRmggenericresourcemap(Set<RmgGenericResourceMap> rmggenericresourcemap) {
		this.rmggenericresourcemap = rmggenericresourcemap;
	}


	
}
