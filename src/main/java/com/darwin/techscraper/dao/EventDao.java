package com.darwin.techscraper.dao;

import com.darwin.techscraper.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Long> {

}
