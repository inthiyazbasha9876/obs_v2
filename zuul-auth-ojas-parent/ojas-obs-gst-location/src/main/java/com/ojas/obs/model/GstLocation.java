package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gst_Location")
public class GstLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer gstlocationId;

	@Column(unique = true)
	private String gstlocationName;
	
	@Column(unique = true)
	private String gstcode;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getGstlocationId() {
		return gstlocationId;
	}

	public void setGstlocationId(Integer gstlocationId) {
		this.gstlocationId = gstlocationId;
	}

	public String getGstlocationName() {
		return gstlocationName;
	}

	public void setGstlocationName(String gstlocationName) {
		this.gstlocationName = gstlocationName;
	}

	public String getGstcode() {
		return gstcode;
	}

	public void setGstcode(String gstcode) {
		this.gstcode = gstcode;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GstLocation [gstlocationId=" + gstlocationId + ", gstlocationName=" + gstlocationName + ", gstcode="
				+ gstcode + ", status=" + status + "]";
	}

	
	

}
