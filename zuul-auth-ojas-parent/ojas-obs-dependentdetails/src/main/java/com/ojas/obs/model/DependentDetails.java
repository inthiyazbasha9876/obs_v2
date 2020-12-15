package com.ojas.obs.model;

import java.sql.Date;
import java.sql.Timestamp;

public class DependentDetails {

		private int id;
		private String dependent_Name;
		private String relation;
		private String date_Of_Birth;
		private String employee_Id;
		private String created_By;
		private Timestamp created_Date;
		private String updated_By;
		private Timestamp updated_Date;
		private boolean flag;
		
		public DependentDetails() {}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDependent_Name() {
			return dependent_Name;
		}

		public void setDependent_Name(String dependent_Name) {
			this.dependent_Name = dependent_Name;
		}

		public String getRelation() {
			return relation;
		}

		public void setRelation(String relation) {
			this.relation = relation;
		}

		public String getDate_Of_Birth() {
			return date_Of_Birth;
		}

		public void setDate_Of_Birth(String date_Of_Birth) {
			this.date_Of_Birth = date_Of_Birth;
		}

		public String getEmployee_Id() {
			return employee_Id;
		}

		public void setEmployee_Id(String employee_Id) {
			this.employee_Id = employee_Id;
		}

		public String getCreated_By() {
			return created_By;
		}

		public void setCreated_By(String created_By) {
			this.created_By = created_By;
		}

		public Timestamp getCreated_Date() {
			return created_Date;
		}

		public void setCreated_Date(Timestamp created_Date) {
			this.created_Date = created_Date;
		}

		public String getUpdated_By() {
			return updated_By;
		}

		public void setUpdated_By(String updated_By) {
			this.updated_By = updated_By;
		}

		public Timestamp getUpdated_Date() {
			return updated_Date;
		}

		public void setUpdated_Date(Timestamp updated_Date) {
			this.updated_Date = updated_Date;
		}

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((created_By == null) ? 0 : created_By.hashCode());
			result = prime * result + ((created_Date == null) ? 0 : created_Date.hashCode());
			result = prime * result + ((date_Of_Birth == null) ? 0 : date_Of_Birth.hashCode());
			result = prime * result + ((dependent_Name == null) ? 0 : dependent_Name.hashCode());
			result = prime * result + ((employee_Id == null) ? 0 : employee_Id.hashCode());
			result = prime * result + (flag ? 1231 : 1237);
			result = prime * result + id;
			result = prime * result + ((relation == null) ? 0 : relation.hashCode());
			result = prime * result + ((updated_By == null) ? 0 : updated_By.hashCode());
			result = prime * result + ((updated_Date == null) ? 0 : updated_Date.hashCode());
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
			DependentDetails other = (DependentDetails) obj;
			if (created_By == null) {
				if (other.created_By != null)
					return false;
			} else if (!created_By.equals(other.created_By))
				return false;
			if (created_Date == null) {
				if (other.created_Date != null)
					return false;
			} else if (!created_Date.equals(other.created_Date))
				return false;
			if (date_Of_Birth == null) {
				if (other.date_Of_Birth != null)
					return false;
			} else if (!date_Of_Birth.equals(other.date_Of_Birth))
				return false;
			if (dependent_Name == null) {
				if (other.dependent_Name != null)
					return false;
			} else if (!dependent_Name.equals(other.dependent_Name))
				return false;
			if (employee_Id == null) {
				if (other.employee_Id != null)
					return false;
			} else if (!employee_Id.equals(other.employee_Id))
				return false;
			if (flag != other.flag)
				return false;
			if (id != other.id)
				return false;
			if (relation == null) {
				if (other.relation != null)
					return false;
			} else if (!relation.equals(other.relation))
				return false;
			if (updated_By == null) {
				if (other.updated_By != null)
					return false;
			} else if (!updated_By.equals(other.updated_By))
				return false;
			if (updated_Date == null) {
				if (other.updated_Date != null)
					return false;
			} else if (!updated_Date.equals(other.updated_Date))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "DependentDetails [id=" + id + ", dependent_Name=" + dependent_Name + ", relation=" + relation
					+ ", date_Of_Birth=" + date_Of_Birth + ", employee_Id=" + employee_Id + ", created_By=" + created_By
					+ ", created_Date=" + created_Date + ", updated_By=" + updated_By + ", updated_Date=" + updated_Date
					+ ", flag=" + flag + "]";
		}

		


	

		

}
