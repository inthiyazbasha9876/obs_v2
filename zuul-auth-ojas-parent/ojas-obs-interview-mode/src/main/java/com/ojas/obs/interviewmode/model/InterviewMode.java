package com.ojas.obs.interviewmode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "InterviewMode")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class InterviewMode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer interviewmodeId;
	@Column(unique = true)
	private String interviewMode;
	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getInterviewmodeId() {
		return interviewmodeId;
	}

	public void setInterviewmodeId(Integer interviewmodeId) {
		this.interviewmodeId = interviewmodeId;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InterviewMode [interviewmodeId=" + interviewmodeId + ", interviewMode=" + interviewMode + ", status="
				+ status + "]";
	}

}
