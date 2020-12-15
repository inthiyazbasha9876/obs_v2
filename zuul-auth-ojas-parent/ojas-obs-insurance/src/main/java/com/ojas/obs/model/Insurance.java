package com.ojas.obs.model;

import java.sql.Timestamp;

public class Insurance {
	private Integer id;
	private Integer age_id;
	private String age_band;
	private Double plan1;
	private Double plan2;
	private String flag;
	private Timestamp created_on;
	private Timestamp updated_on;
	private Integer created_by;
	private Integer updated_by;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge_id() {
		return age_id;
	}

	public void setAge_id(Integer age_id) {
		this.age_id = age_id;
	}

	public String getAge_band() {
		return age_band;
	}

	public void setAge_band(String age_band) {
		this.age_band = age_band;
	}

	public Double getPlan1() {
		return plan1;
	}

	public void setPlan1(Double plan1) {
		this.plan1 = plan1;
	}

	public Double getPlan2() {
		return plan2;
	}

	public void setPlan2(Double plan2) {
		this.plan2 = plan2;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Timestamp getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}

	public Timestamp getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Timestamp updated_on) {
		this.updated_on = updated_on;
	}

	public Integer getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age_band == null) ? 0 : age_band.hashCode());
		result = prime * result + ((age_id == null) ? 0 : age_id.hashCode());
		result = prime * result + ((created_by == null) ? 0 : created_by.hashCode());
		result = prime * result + ((created_on == null) ? 0 : created_on.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((plan1 == null) ? 0 : plan1.hashCode());
		result = prime * result + ((plan2 == null) ? 0 : plan2.hashCode());
		result = prime * result + ((updated_by == null) ? 0 : updated_by.hashCode());
		result = prime * result + ((updated_on == null) ? 0 : updated_on.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Insurance other = (Insurance) obj;
		if (age_band == null) {
			if (other.age_band != null)
				return false;
		} else if (!age_band.equals(other.age_band))
			return false;
		if (age_id == null) {
			if (other.age_id != null)
				return false;
		} else if (!age_id.equals(other.age_id))
			return false;
		if (created_by == null) {
			if (other.created_by != null)
				return false;
		} else if (!created_by.equals(other.created_by))
			return false;
		if (created_on == null) {
			if (other.created_on != null)
				return false;
		} else if (!created_on.equals(other.created_on))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (plan1 == null) {
			if (other.plan1 != null)
				return false;
		} else if (!plan1.equals(other.plan1))
			return false;
		if (plan2 == null) {
			if (other.plan2 != null)
				return false;
		} else if (!plan2.equals(other.plan2))
			return false;
		if (updated_by == null) {
			if (other.updated_by != null)
				return false;
		} else if (!updated_by.equals(other.updated_by))
			return false;
		if (updated_on == null) {
			if (other.updated_on != null)
				return false;
		} else if (!updated_on.equals(other.updated_on))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Insurance [id=" + id + ", age_id=" + age_id + ", age_band=" + age_band + ", plan1=" + plan1 + ", plan2="
				+ plan2 + ", flag=" + flag + ", created_on=" + created_on + ", updated_on=" + updated_on
				+ ", created_by=" + created_by + ", updated_by=" + updated_by + "]";
	}

}