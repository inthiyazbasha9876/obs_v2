package com.ojas.obs.customerinfo.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
@Table(name = "obs_customer")
@SequenceGenerator(name = "seq", initialValue = 1001)
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
	
	
	@Column
	private Integer customerId;

	@NotNull
	@Column
	private String customerName;


	@NotNull
	@Column
	private float tds;

	@NotNull
	@Column
	private String pannumber;

	@NotNull
	@Column
	private String tannumber;
	
	@NotNull
	@Column
	private String geolocations;
	
	@NotNull
	@Column
	private String countries;

	@NotNull
	@Column
	private boolean customerstatus;
	
	
	@Column
	private String mailstatus;
	
	
	@Column
	private Timestamp createddate;
	
	
	@Column
	private Timestamp updateddate;
	
	
	@Column
	private String createdby;
	
	
	@Column
	private String updatedby;
	

	

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "shippingaddress_record_map", joinColumns = {
			@JoinColumn(name = "customerId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "shippingaddressId", nullable = false) })
	@NotNull
	@JsonIgnoreProperties("customer")
	private Set<ShippingAddress> shippingaddress = new HashSet<>();
	
	
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "billingaddress_record_map", joinColumns = {
			@JoinColumn(name = "customerId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "billingaddressId", nullable = false) })
	@NotNull
	@JsonIgnoreProperties("customer")
	private Set<BillingAddress> billingaddress = new HashSet<>();
	
	
	

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "customer")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "customer" })
	private ContactInfo contactinfo;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "customer")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "customer" })
	private CustomerGst customergst;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "customer")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "customer" })
	private BillingInfo billinginfo;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "customer")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "customer" })
	private RegisteredAddress registeredaddress;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "service_record_map", joinColumns = {
			@JoinColumn(name = "customerId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false) })
	@NotNull
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "customer" }) 
	private Set<ServiceType> servicetype = new HashSet<>();

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public float getTds() {
		return tds;
	}

	public void setTds(float tds) {
		this.tds = tds;
	}

	public String getPannumber() {
		return pannumber;
	}

	public void setPannumber(String pannumber) {
		this.pannumber = pannumber;
	}

	public String getTannumber() {
		return tannumber;
	}

	public void setTannumber(String tannumber) {
		this.tannumber = tannumber;
	}

	public boolean isCustomerstatus() {
		return customerstatus;
	}

	public void setCustomerstatus(boolean customerstatus) {
		this.customerstatus = customerstatus;
	}

	public Set<ShippingAddress> getShippingaddress() {
		return shippingaddress;
	}

	public void setShippingaddress(Set<ShippingAddress> shippingaddress) {
		this.shippingaddress = shippingaddress;
	}

	public Set<BillingAddress> getBillingaddress() {
		return billingaddress;
	}

	public void setBillingaddress(Set<BillingAddress> billingaddress) {
		this.billingaddress = billingaddress;
	}

	public ContactInfo getContactinfo() {
		return contactinfo;
	}

	public void setContactinfo(ContactInfo contactinfo) {
		this.contactinfo = contactinfo;
	}

	public CustomerGst getCustomergst() {
		return customergst;
	}

	public void setCustomergst(CustomerGst customergst) {
		this.customergst = customergst;
	}

	public BillingInfo getBillinginfo() {
		return billinginfo;
	}

	public void setBillinginfo(BillingInfo billinginfo) {
		this.billinginfo = billinginfo;
	}

	public RegisteredAddress getRegisteredaddress() {
		return registeredaddress;
	}

	public void setRegisteredaddress(RegisteredAddress registeredaddress) {
		this.registeredaddress = registeredaddress;
	}

	public Set<ServiceType> getServicetype() {
		return servicetype;
	}

	public void setServicetype(Set<ServiceType> servicetype) {
		this.servicetype = servicetype;
	}

	

	
	public String getGeolocations() {
		return geolocations;
	}

	public void setGeolocations(String geolocations) {
		this.geolocations = geolocations;
	}

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	public String getMailstatus() {
		return mailstatus;
	}

	public void setMailstatus(String mailstatus) {
		this.mailstatus = mailstatus;
	}



	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", tds=" + tds + ", pannumber="
				+ pannumber + ", tannumber=" + tannumber + ", geolocations=" + geolocations + ", countries=" + countries
				+ ", customerstatus=" + customerstatus + ", mailstatus=" + mailstatus + ", createddate=" + createddate
				+ ", updateddate=" + updateddate + ", createdby=" + createdby + ", updatedby=" + updatedby
				+ ", shippingaddress=" + shippingaddress + ", billingaddress=" + billingaddress + ", contactinfo="
				+ contactinfo + ", customergst=" + customergst + ", billinginfo=" + billinginfo + ", registeredaddress="
				+ registeredaddress + ", servicetype=" + servicetype + "]";
	}

	
	
	

}
