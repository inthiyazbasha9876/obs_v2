package com.ojas.obs.documenttype.response;

import java.util.List;

import com.ojas.obs.documenttype.model.DocumentType;

public class DocumentTypeResponse {

	private String message;
	private String statusCode;
	private List<DocumentType> documenttypelist;

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

	public List<DocumentType> getDocumenttypelist() {
		return documenttypelist;
	}

	public void setDocumenttypelist(List<DocumentType> documenttypelist) {
		this.documenttypelist = documenttypelist;
	}

	@Override
	public String toString() {
		return "DocumentTypeResponse [message=" + message + ", statusCode=" + statusCode + ", documenttypelist="
				+ documenttypelist + "]";
	}

}
