package com.ojas.obs.calendarholidays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.obs.calendarholidays.model.CalendarHolidays;
@Repository
public interface CalendarHolidaysRepository extends JpaRepository<CalendarHolidays, Integer> {

}
