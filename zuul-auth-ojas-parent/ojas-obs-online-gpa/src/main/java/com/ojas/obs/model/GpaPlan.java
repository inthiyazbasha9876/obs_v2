package com.ojas.obs.model;

/**
 * 
 * @author pnaveen
 *
 */

public class GpaPlan {

	private Integer id;
	//private Integer gpaPlanId;
	private String gpaPlanType;
	private Double gpaPremium;
	private Double totalPremium;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGpaPlanType() {
		return gpaPlanType;
	}
	public void setGpaPlanType(String gpaPlanType) {
		this.gpaPlanType = gpaPlanType;
	}
	public Double getGpaPremium() {
		return gpaPremium;
	}
	public void setGpaPremium(Double gpaPremium) {
		this.gpaPremium = gpaPremium;
	}
	public Double getTotalPremium() {
		return totalPremium;
	}
	public void setTotalPremium(Double totalPremium) {
		this.totalPremium = totalPremium;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gpaPlanType == null) ? 0 : gpaPlanType.hashCode());
		result = prime * result + ((gpaPremium == null) ? 0 : gpaPremium.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((totalPremium == null) ? 0 : totalPremium.hashCode());
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
		GpaPlan other = (GpaPlan) obj;
		if (gpaPlanType == null) {
			if (other.gpaPlanType != null)
				return false;
		} else if (!gpaPlanType.equals(other.gpaPlanType))
			return false;
		if (gpaPremium == null) {
			if (other.gpaPremium != null)
				return false;
		} else if (!gpaPremium.equals(other.gpaPremium))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (totalPremium == null) {
			if (other.totalPremium != null)
				return false;
		} else if (!totalPremium.equals(other.totalPremium))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GpaPlan [id=" + id + ", gpaPlanType=" + gpaPlanType + ", gpaPremium=" + gpaPremium + ", totalPremium="
				+ totalPremium + "]";
	}
	
	// private String flag;
	/**
	 * @return the gpaPlanId
	 */
	
}
