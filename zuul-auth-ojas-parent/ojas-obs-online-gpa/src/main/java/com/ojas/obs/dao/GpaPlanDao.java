package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.GpaPlan;
import com.ojas.obs.request.GpaRequest;

public interface GpaPlanDao {
	
		/**
		 * 
		 * @param gpaPlan
		 * @return
		 * @throws SQLException
		 */
		public boolean saveGpaPlan(GpaRequest gpaPlan) throws SQLException;

		/**
		 * 
		 * @param gpaRequest
		 * @return
		 * @throws SQLException
		 */
		public boolean updateGpa(GpaRequest gpaRequest) throws SQLException;

		/**
		 * 
		 * @param gpaRequest
		 * @return
		 * @throws SQLException
		 */
		public List<GpaPlan> getAllGpaDetails(GpaRequest gpaRequest) throws SQLException;

		/**
		 * 
		 * @param courseId
		 * @return
		 * @throws SQLException
		 */
	//	public boolean deleteGpaRecord(int courseId) throws SQLException;

		/**
		 * 
		 * @param allGpaDetails
		 * @param pageSize
		 * @param pageNum
		 * @return
		 */
		public List<GpaPlan> getPageRecords(List<GpaPlan> allGpaDetails, int pageSize, int pageNum);

		/**
		 * 
		 * @return
		 * @throws SQLException
		 */
		public int getAllGpaDetailsCount() throws SQLException;
		
		//public List<GpaPlan> getByGpaId(Integer gpaPlanid) throws SQLException;
		public List<GpaPlan> getById(GpaRequest gpaRequest) throws SQLException;

	

}
