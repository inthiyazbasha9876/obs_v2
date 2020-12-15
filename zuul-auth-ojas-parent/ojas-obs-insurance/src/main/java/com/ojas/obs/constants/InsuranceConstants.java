package com.ojas.obs.constants;

public class InsuranceConstants {
	// ==============transactionType==========
	
	public static final String SAVE = "save";

	public static final String UPDATE = "update";

	public static final String DELETE = "delete";

	public static final String FAILED = "failed";

	// ==============flag values====================
	
	public static final String SAVED = "saved";

	public static final String UPDATED = "updated";

	public static final String DELETED = "deleted";

	// ===========Insurance Queries===========

	public static final String SAVEINSURANCE = "insert into ojas_insurance (age_id,age_band,plan1,plan2,flag,created_on,created_by) values (?,?,?,?,?,?,?)";

	public static final String UPDATEINSURANCEDETAILS = "update ojas_insurance set  age_id =?,age_band=?,plan1=? ,plan2=?, flag=?,updated_on =? ,updated_by=? where id =?";

	public static final String DELETEINSURANCEDETAILS = "update ojas_obs.ojas_insurance set flag = ?,updated_on = ? where id =?";

	public static final String GETINSURANCEDETAILS = "select * from ojas_insurance";

	public static final String GETINSURANCEDETAILSCOUNT = "select count(*) from ojas_insurance";

}
