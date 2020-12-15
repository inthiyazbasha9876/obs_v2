
package com.ojas.obs.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.obs.tms.model.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
	

}
