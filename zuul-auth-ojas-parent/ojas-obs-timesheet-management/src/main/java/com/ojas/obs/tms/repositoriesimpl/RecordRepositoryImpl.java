package com.ojas.obs.tms.repositoriesimpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.repositories.RecordRepository;

public class RecordRepositoryImpl {

	@Autowired
	private RecordRepository recordRepository;

	public Boolean saveRecord(Set<Record> record) {
		Boolean saved = false;
		List<Record> savedRecord = recordRepository.saveAll(record);
		if (savedRecord != null) {
			saved = true;
		}

		return saved;
	}
	public Boolean updateRecord(Record rec) {
		Boolean updated = false;
		Record updatedRecord = recordRepository.save(rec);
		if (updatedRecord.getRecordId() != null) {
			updated = true;
		}
		return updated;
	}
	
	
}
