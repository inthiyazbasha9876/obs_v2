package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.RoleManagement;

public class RoleManagementRequest {

	private List<RoleManagement> roleManagement;
	private String transactionType;

	public List<RoleManagement> getRoleManagement() {
		return roleManagement;
	}

	public void setRoleManagement(List<RoleManagement> roleManagement) {
		this.roleManagement = roleManagement;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "RoleManagementRequest [roleManagement=" + roleManagement + ", transactionType=" + transactionType + "]";
	}

}
