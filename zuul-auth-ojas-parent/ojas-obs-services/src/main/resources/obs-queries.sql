
		 INSERT_SERVICES =insert into obs_services(serviceName,createdBy,updatedBy,createdDate,updatedDate,flag) values(?,?,?,?,?,?)
		 UPDATE_SERVICES =update obs_services set serviceName= ? ,createdBy=?,updatedBy=?,createdDate=?,updatedDate=? ,flag=? where id= ?
		 DELETE_SERVICES =update obs_services set flag=0 where id = ?
		SELECT_SERVICES = select * from obs_services where flag=1
		 SELECT_SERVICES_BY_ID = select * from obs_services where flag=1
		SERVICESCOUNT = select count(*) from obs_services
		