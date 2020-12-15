
		 INSERT_STATES =insert into obs_states(stateName,createdBy,updatedBy,createdDate,updatedDate,flag) values(?,?,?,?,?,?)
		 UPDATE_STATES =update obs_states set stateName= ? ,createdBy=?,updatedBy=?,createdDate=?,updatedDate=? ,flag=? where id= ?
		 DELETE_STATES =update obs_states set flag=0 where id = ?
		SELECT_STATES = select * from obs_states where flag=1
		 SELECT_STATES_BY_ID = select * from obs_states where flag=1
		STATESCOUNT = select count(*) from obs_states
		