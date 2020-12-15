
#////// ---------------    EmployeeExperienceDetails ----------------------------
# CREATE TABLE obs_employee_experience_details (Id int(20)  AUTO_INCREMENT,company_name varchar(100)  NOT NULL,joining_date timestamp  NOT NULL,exit_date timestamp  NOT NULL,salary varchar(50)   NOT NULL,location varchar(100)  NOT NULL ,is_current_company tinyint(20)  NOT NULL ,employee_id int(50)  NOT NULL,Reference_1_name  varchar(200)  NOT NULL ,Reference_1_contact varchar(15)  NOT NULL ,Reference_2_name varchar(200)  NOT NULL,Reference_2_contact varchar(15)  NOT NULL,flag tinyint(20)  NOT NULL ,created_by varchar(50)  NOT NULL,updated_by varchar(50)  NOT NULL,created_date timestamp  NOT NULL,updated_date timestamp  NOT NULL,PRIMARY KEY (Id));
 
 
 
 insert_employee_experience_details = insert into obs_experiencedetails (company_name,joining_date,exit_date,salary,location,is_current_company,employee_id,reference_1_name,reference_1_contact,reference_2_name,reference_2_contact,flag,created_by,created_date,image,experience,status,documentsstatus)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);
                                      
 #update_employee_experience_details = update obs_experiencedetails set employee_id =?,updated_by=?,image=?,documentsstatus=? where id =? 
                                         
 getAll_employee_experience_details = select * from ojas_obs.obs_experiencedetails 
   
getById_employee_experience_details =  select * from obs_experiencedetails where id =?
getByEmpId_employee_experience_detailss =  select * from obs_experiencedetails where employee_id =?
delete_employee_experience_details  = update obs_experiencedetails set flag =? ,updated_by=?, updated_date =now()  where id=?
 fileupload_employee_experience_details= update obs_experiencedetails set documentsstatus=? where employee_id =?                         
  update_employee_experience_details = update obs_experiencedetails set employee_id=?,updated_by=?,updated_date=?,image=?, documentsstatus=? where id=?                                                               