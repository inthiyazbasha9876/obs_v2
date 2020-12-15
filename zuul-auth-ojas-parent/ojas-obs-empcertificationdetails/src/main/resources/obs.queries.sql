///Queries
     INSERTCertificationDetails = insert into obs_certificationdetails( certification_name, Issued_by, Date_of_issue, employee_id,created_by,flag,created_date) values(?,?,?,?,?,?,now())
	 UPDATECertificationDetailsBYID = update obs_CertificationDetails set certification_name=?, Issued_by=?, Date_of_issue=? ,created_by=?, updated_by=?, created_date=now(),updated_date=now() where id = ?
	 DELETECertificationDetails = update obs_CertificationDetails set flag = false ,updated_by=? where id = ?
     GETCertificationDetailsCOUNT = select count(*) from obs_CertificationDetails where flag= true
	 GETCertificationDetails = Select * from obs_CertificationDetails where flag=1