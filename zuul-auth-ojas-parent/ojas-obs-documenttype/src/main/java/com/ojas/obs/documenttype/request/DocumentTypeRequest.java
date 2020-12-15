package com.ojas.obs.documenttype.request;

import java.util.List;

import com.ojas.obs.documenttype.model.DocumentType;

public class DocumentTypeRequest {

	private List<DocumentType> documenttypelist;
	private String transactionType;

	public List<DocumentType> getDocumenttypelist() {
		return documenttypelist;
	}

	public void setDocumenttypelist(List<DocumentType> documenttypelist) {
		this.documenttypelist = documenttypelist;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "DocumentTypeRequest [documenttypelist=" + documenttypelist + ", transactionType=" + transactionType
				+ "]";
	}

}
