package com.obs.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LeaveBalance")
public class LeaveBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String empId; 
	
	@Column(columnDefinition = "float default 0.0")
	private Float totalCasualLeave;
	@Column(columnDefinition = "float default 0.0")
	private Float lossOfPay;
	@Column(columnDefinition = "float default 0.0")
	private Float totalCompOff;
	@Column(columnDefinition = "float default 0.0")
	private Float totalSickLeave;
	@Column(columnDefinition = "float default 0.0")
	private Float totalMaternityLeave;
	@Column(columnDefinition = "float default 0.0")
	private Float consumedCasualLeave;
	@Column(columnDefinition = "float default 0.0")
	private Float consumedCompOff;
	@Column(columnDefinition = "float default 0.0")
	private Float consumedSickLeave;
	@Column(columnDefinition = "float default 0.0")
	private Float consumedMaternityLeave;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Float getTotalCasualLeave() {
		return totalCasualLeave;
	}

	public void setTotalCasualLeave(Float totalCasualLeave) {
		this.totalCasualLeave = totalCasualLeave;
	}

	public Float getLossOfPay() {
		return lossOfPay;
	}

	public void setLossOfPay(Float lossOfPay) {
		this.lossOfPay = lossOfPay;
	}

	public Float getTotalCompOff() {
		return totalCompOff;
	}

	public void setTotalCompOff(Float totalCompOff) {
		this.totalCompOff = totalCompOff;
	}

	public Float getTotalSickLeave() {
		return totalSickLeave;
	}

	public void setTotalSickLeave(Float totalSickLeave) {
		this.totalSickLeave = totalSickLeave;
	}

	public Float getTotalMaternityLeave() {
		return totalMaternityLeave;
	}

	public void setTotalMaternityLeave(Float totalMaternityLeave) {
		this.totalMaternityLeave = totalMaternityLeave;
	}

	public Float getConsumedCasualLeave() {
		return consumedCasualLeave;
	}

	public void setConsumedCasualLeave(Float consumedCasualLeave) {
		this.consumedCasualLeave = consumedCasualLeave;
	}

	public Float getConsumedCompOff() {
		return consumedCompOff;
	}

	public void setConsumedCompOff(Float consumedCompOff) {
		this.consumedCompOff = consumedCompOff;
	}

	public Float getConsumedSickLeave() {
		return consumedSickLeave;
	}

	public void setConsumedSickLeave(Float consumedSickLeave) {
		this.consumedSickLeave = consumedSickLeave;
	}

	public Float getConsumedMaternityLeave() {
		return consumedMaternityLeave;
	}

	public void setConsumedMaternityLeave(Float consumedMaternityLeave) {
		this.consumedMaternityLeave = consumedMaternityLeave;
	}

	@Override
	public String toString() {
		return "LeaveBalance [id=" + id + ", empId=" + empId + ", totalCasualLeave=" + totalCasualLeave + ", lossOfPay="
				+ lossOfPay + ", totalCompOff=" + totalCompOff + ", totalSickLeave=" + totalSickLeave
				+ ", totalMaternityLeave=" + totalMaternityLeave + ", consumedCasualLeave=" + consumedCasualLeave
				+ ", consumedCompOff=" + consumedCompOff + ", consumedSickLeave=" + consumedSickLeave
				+ ", consumedMaternityLeave=" + consumedMaternityLeave + "]";
	}

}
