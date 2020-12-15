

INSERT_EMPLOYMENT_DETAILS_STMT = INSERT into obs_employmentdetails(employee_id,joining_date,resource_type,bond_status,bond_tenure,resource_cat,confirmation_date,cost_center_id,bu_id,sbu_id,flag,created_by,created_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?);

 GET_EMPLOYMENT_DETAILS_STMT = select * from obs_employmentdetails where flag=1;

 UPDATE_EMPLOYMENT_DETAILS_STMT = UPDATE obs_employmentdetails set resource_type=?,joining_date=?,bond_status=?,bond_tenure=?,resource_cat=?,confirmation_date=?,cost_center_id=?,bu_id=?,sbu_id=?,updated_by=?,updated_date=? where id=?; 

DELETE_EMPLOYMENT_DETAILS_STMT = UPDATE obs_employmentdetails set flag=? where id=?

GET_EMPLOYMENT_DETAILS_BY_EMPLOYEE_ID_STMT= select * from obs_employmentdetails where  flag !=false 

GET_EMPLOYMENT_DETAILS_BY_ID_STMT=select * from obs_employmentdetails where id=? and flag=1

UPDATERMGEMPINFO = Update obs_rmg.rmg_employee_list set resource_category=?, employment_status=?, status_date=? where employee_id=?