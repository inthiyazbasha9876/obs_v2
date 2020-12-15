package com.ojas.response;

import java.util.List;

import com.ojas.obs.model.Model;
/**
 * 
 * @author uyashwanth
 *
 */
public class Response {
	private List<Model> listCourse;
	private String message;
	private String statusCode;
	public List<Model> getListCourse() {
		return listCourse;
	}
	public void setListCourse(List<Model> listCourse) {
		this.listCourse = listCourse;
	}
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
	@Override
	public String toString() {
		return "Response [listCourse=" + listCourse + ", message=" + message + ", statusCode=" + statusCode + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listCourse == null) ? 0 : listCourse.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
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
		Response other = (Response) obj;
		if (listCourse == null) {
			if (other.listCourse != null)
				return false;
		} else if (!listCourse.equals(other.listCourse))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}

	

}
