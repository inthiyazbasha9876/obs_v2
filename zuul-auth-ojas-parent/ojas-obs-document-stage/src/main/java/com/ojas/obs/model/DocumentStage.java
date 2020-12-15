package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Document_Stage")
public class DocumentStage {  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer documentstageId;

	@Column(unique = true)
	private String documentstage;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getDocumentstageId() {
		return documentstageId;
	}

	public void setDocumentstageId(Integer documentstageId) {
		this.documentstageId = documentstageId;
	}

	public String getDocumentstage() {
		return documentstage;
	}

	public void setDocumentstage(String documentstage) {
		this.documentstage = documentstage;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DocumentStage [documentstageId=" + documentstageId + ", documentstage=" + documentstage + ", status="
				+ status + "]";
	}

}