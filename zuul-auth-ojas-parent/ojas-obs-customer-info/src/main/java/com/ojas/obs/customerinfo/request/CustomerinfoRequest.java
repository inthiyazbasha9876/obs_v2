package com.ojas.obs.customerinfo.request;

import java.util.Set;

import com.ojas.obs.customerinfo.model.ShippingAddress;
import com.ojas.obs.customerinfo.model.BillingAddress;
import com.ojas.obs.customerinfo.model.BillingInfo;
import com.ojas.obs.customerinfo.model.ContactInfo;
import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.model.CustomerGst;
import com.ojas.obs.customerinfo.model.RegisteredAddress;
import com.ojas.obs.customerinfo.model.ServiceType;

public class CustomerinfoRequest {

	private String transactionType;
	private  Customer customerList;
	private CustomerGst customergstList;
	private Set<ShippingAddress> shippingaddressList;
	private Set<BillingAddress> billingaddressList;
	private ContactInfo contactinfoList;
	private BillingInfo billinginfoList;
	private RegisteredAddress registeredaddress;
	private Set<ServiceType> servicetype;
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Customer getCustomerList() {
		return customerList;
	}
	public void setCustomerList(Customer customerList) {
		this.customerList = customerList;
	}
	public CustomerGst getCustomergstList() {
		return customergstList;
	}
	public void setCustomergstList(CustomerGst customergstList) {
		this.customergstList = customergstList;
	}
	public Set<ShippingAddress> getShippingaddressList() {
		return shippingaddressList;
	}
	public void setShippingaddressList(Set<ShippingAddress> shippingaddressList) {
		this.shippingaddressList = shippingaddressList;
	}
	public Set<BillingAddress> getBillingaddressList() {
		return billingaddressList;
	}
	public void setBillingaddressList(Set<BillingAddress> billingaddressList) {
		this.billingaddressList = billingaddressList;
	}
	public ContactInfo getContactinfoList() {
		return contactinfoList;
	}
	public void setContactinfoList(ContactInfo contactinfoList) {
		this.contactinfoList = contactinfoList;
	}
	public BillingInfo getBillinginfoList() {
		return billinginfoList;
	}
	public void setBillinginfoList(BillingInfo billinginfoList) {
		this.billinginfoList = billinginfoList;
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
	
	
	@Override
	public String toString() {
		return "CustomerinfoRequest [transactionType=" + transactionType + ", customerList=" + customerList
				+ ", customergstList=" + customergstList + ", shippingaddressList=" + shippingaddressList
				+ ", billingaddressList=" + billingaddressList + ", contactinfoList=" + contactinfoList
				+ ", billinginfoList=" + billinginfoList + ", registeredaddress=" + registeredaddress + ", servicetype="
				+ servicetype + "]";
	}
	
	
	
}
