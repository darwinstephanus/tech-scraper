package com.darwin.techscraper.dao;

import com.darwin.techscraper.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventDao extends PagingAndSortingRepository<Event, Long>, JpaSpecificationExecutor<Event> {

}
