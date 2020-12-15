package com.ojas.obs.customerinfo.response;

import java.util.List;

import com.ojas.obs.customerinfo.model.ShippingAddress;
import com.ojas.obs.customerinfo.model.BillingAddress;
import com.ojas.obs.customerinfo.model.ContactInfo;
import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.model.CustomerGst;



public class CustomerinfoResponse
{
	private String message;
	private String statusCode;
	private List<Customer> customerList;
	private List<CustomerGst> customergstList;
	private List<ShippingAddress> shippingaddressList;
	private List<BillingAddress> billingaddressList;
	private List<ContactInfo> contactinfoList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public List<CustomerGst> getCustomergstList() {
		return customergstList;
	}
	public void setCustomergstList(List<CustomerGst> customergstList) {
		this.customergstList = customergstList;
	}
	public List<ShippingAddress> getShippingaddressList() {
		return shippingaddressList;
	}
	public void setShippingaddressList(List<ShippingAddress> shippingaddressList) {
		this.shippingaddressList = shippingaddressList;
	}
	public List<BillingAddress> getBillingaddressList() {
		return billingaddressList;
	}
	public void setBillingaddressList(List<BillingAddress> billingaddressList) {
		this.billingaddressList = billingaddressList;
	}
	public List<ContactInfo> getContactinfoList() {
		return contactinfoList;
	}
	public void setContactinfoList(List<ContactInfo> contactinfoList) {
		this.contactinfoList = contactinfoList;
	}
	
	
	@Override
	public String toString() {
		return "CustomerinfoResponse [message=" + message + ", statusCode=" + statusCode + ", customerList="
				+ customerList + ", customergstList=" + customergstList + ", shippingaddressList=" + shippingaddressList
				+ ", billingaddressList=" + billingaddressList + ", contactinfoList=" + contactinfoList + "]";
	}
	
	
	
	
	
}
