insert_ProjectDetails = insert into obs_projectdetails (project_name,contract_id,rate_id,start_date,end_date,Billing_id,employee_id,project_tech_stack,customer,location,gst_applicable,project_type,project_status,bdm_contact,is_internal,flag,created_by,created_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())
update_ProjectDetails= update obs_projectdetails set project_name=?,contract_id=?,rate_id=?,start_date=?,end_date=?,Billing_id=?,employee_id=?,project_tech_stack=?,customer=?,location=?,gst_applicable=?,project_type=?,project_status=?,bdm_contact=?,Is_internal=?,updated_by=?,updated_date=now() where id=?
getAll_projectDetails = select * from obs_projectdetails where flag = '1'
getById_projectDetails =  select * from obs_projectdetails where id =?
delete_ProjectDetails = update obs_projectdetails set flag =?,updated_by=?,updated_date=now() where id=?
getByEmpId_projectDetails =  select * from obs_projectdetails where employee_id =?