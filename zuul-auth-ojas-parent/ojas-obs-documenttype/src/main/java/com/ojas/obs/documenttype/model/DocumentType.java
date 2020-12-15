package com.ojas.obs.documenttype.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DocumentType")
public class DocumentType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer documenttypeId;

	@Column(unique = true)
	private String documenttype;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getDocumenttypeId() {
		return documenttypeId;
	}

	public void setDocumenttypeId(Integer documenttypeId) {
		this.documenttypeId = documenttypeId;
	}

	public String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DocumentType [documenttypeId=" + documenttypeId + ", documenttype=" + documenttype + ", status="
				+ status + "]";
	}

}
