package com.ojas.obs.model;

public class Genders {
	private Integer id;
	private String gender;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Genders [id=" + id + ", gender=" + gender + "]";
	}

	
	
	
}
