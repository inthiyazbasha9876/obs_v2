package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Interview_Result")
public class InterviewResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer interviewresultId;

	@Column(unique = true)
	private String interviewResult;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getInterviewresultId() {
		return interviewresultId;
	}

	public void setInterviewresultId(Integer interviewresultId) {
		this.interviewresultId = interviewresultId;
	}

	public String getInterviewResult() {
		return interviewResult;
	}

	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InterviewResult [interviewresultId=" + interviewresultId + ", interviewResult=" + interviewResult
				+ ", status=" + status + "]";
	}

}