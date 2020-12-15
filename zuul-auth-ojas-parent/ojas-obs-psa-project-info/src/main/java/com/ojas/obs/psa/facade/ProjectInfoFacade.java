package com.ojas.obs.psa.facade;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.psa.request.ProjectInfoRequest;

public interface ProjectInfoFacade {
	public ResponseEntity<Object> setProjectInfo(ProjectInfoRequest infoRequest) throws SQLException, IOException;
	public ResponseEntity<Object> getProjectInfo(ProjectInfoRequest infoRequest) throws SQLException;
}
