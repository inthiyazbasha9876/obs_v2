package com.obs.rmg.rmgmodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table
@SequenceGenerator(name = "seq", initialValue =01)
public class RMG {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
	@Column
	private int bookingId;
	@Column
	private String resourceType;
	@Column
	private String projectId;

	@Column
	private String status;
	@Column
	private Boolean flag;
	
	@Column
	private String message;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "rmg_specific_map", joinColumns = {
			@JoinColumn(name = "bookingId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "specificId", nullable = false) })
	@NotNull
	@JsonIgnoreProperties("rmg")
	private Set<RmgSpecific> rmgspecific = new HashSet<>();
	
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "rmg_generic_map", joinColumns = {
			@JoinColumn(name = "bookingId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "genericId", nullable = false) })
	@NotNull
	@JsonIgnoreProperties("rmg")
	private Set<RmgGeneric> rmggeneric = new HashSet<>();


	public int getBookingId() {
		return bookingId;
	}


	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}


	public String getResourceType() {
		return resourceType;
	}


	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Boolean getFlag() {
		return flag;
	}


	public void setFlag(Boolean flag) {
		this.flag = flag;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Set<RmgSpecific> getRmgspecific() {
		return rmgspecific;
	}


	public void setRmgspecific(Set<RmgSpecific> rmgspecific) {
		this.rmgspecific = rmgspecific;
	}


	public Set<RmgGeneric> getRmggeneric() {
		return rmggeneric;
	}


	public void setRmggeneric(Set<RmgGeneric> rmggeneric) {
		this.rmggeneric = rmggeneric;
	}


	@Override
	public String toString() {
		return "RMG [bookingId=" + bookingId + ", resourceType=" + resourceType + ", projectId=" + projectId
				+ ", status=" + status + ", flag=" + flag + ", message=" + message + ", rmgspecific=" + rmgspecific
				+ ", rmggeneric=" + rmggeneric + "]";
	}


	

	
  
}
