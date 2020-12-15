package com.ojas.security.auth.dao;

import com.ojas.security.auth.model.AppUser;

public interface UserDao {

	AppUser findUserAccount(String userName);

}
