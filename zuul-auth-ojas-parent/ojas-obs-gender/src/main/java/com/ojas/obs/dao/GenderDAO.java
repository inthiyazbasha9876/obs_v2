package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;

public interface GenderDAO {
  public boolean saveGender(GenderRequest genderRequest) throws SQLException;
  public boolean updateGender(GenderRequest genderRequest) throws SQLException;

  public int getAllCount(GenderRequest genderRequest) throws SQLException;
  public List<Genders> getAll(GenderRequest genderRequest) throws SQLException;

  public List<Genders> getGenderById(GenderRequest genderRequest) throws SQLException;
}
