package com.obs.employeeCertificationDetails.dao;

import java.sql.SQLException;
import java.util.List;

import com.obs.employeeCertificationDetails.model.CertificationDetails;
import com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;


public interface EmployeeCertificationDAO {
  public Boolean saveCertificationDetails(CertificationDetailsRequest  certificationDetailsRequest) throws SQLException;
  public Boolean updateCertificationDetails(CertificationDetailsRequest  certificationDetailsRequest)throws SQLException;
  public Boolean deleteCertificationDetails(CertificationDetailsRequest  certificationDetailsRequest)throws SQLException;
  public List<CertificationDetails> getAllCertificationDetails()throws SQLException;
  public int getAllCertificationDetailsCount()throws SQLException;
  public List<CertificationDetails> getDetailById(CertificationDetailsRequest  certificationDetailsRequest) throws SQLException;
List<CertificationDetails> getDetailByEmpId(CertificationDetailsRequest certificationDetailsRequest)
		throws SQLException;
}
