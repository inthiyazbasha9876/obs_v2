package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.BankDetails;
import com.ojas.obs.request.BankDetailsRequest;
/**
 * 
 * @author akrishna
 *
 */
public interface BankDetailsDAO {

	boolean saveEmployeeBankDetails(BankDetailsRequest employeeBankDetailsRequest) throws SQLException;

	boolean updateEmployeeBankDetails(BankDetailsRequest employeeBankDetailsRequest) throws SQLException;

	boolean deleteEmployeeBankDetails(BankDetailsRequest employeeBankDetailsRequest) throws SQLException;

	List<BankDetails> getAllBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException;

}
