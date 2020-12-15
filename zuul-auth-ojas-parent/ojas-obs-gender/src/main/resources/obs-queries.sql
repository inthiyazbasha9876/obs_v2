
		 INSERT_GENDER =insert into obs_genders(gender,createdBy,updatedBy,createdDate,updatedDate,flag) values(?,?,?,?,?,?)
		 UPDATE_GENDER =update obs_genders set gender= ? ,createdBy=?,updatedBy=?,createdDate=?,updatedDate=? ,flag=? where id= ?
		 DELETE_GENDER =update obs_genders set flag=0 where id = ?
		 SELECT_GENDER = select * from obs_genders where flag=1
		 GENDERCOUNT = select count(*) from obs_genders where flag=1
		