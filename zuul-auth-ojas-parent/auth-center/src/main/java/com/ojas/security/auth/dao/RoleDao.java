package com.ojas.security.auth.dao;

import java.util.List;

public interface RoleDao {

	List<String> getRoleNames(String userId);

}
