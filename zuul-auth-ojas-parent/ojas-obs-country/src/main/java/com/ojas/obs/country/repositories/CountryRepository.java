package com.ojas.obs.country.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.country.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	public ArrayList<Country> getByGeoId(Integer geoId);

}
