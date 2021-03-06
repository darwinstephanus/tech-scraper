package com.darwin.techscraper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Embeddable
public class EventId implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long event_id;

    private String name;
    private Date startDate;

//    public EventId(String name, Date startDate) {
//        this.name = name;
//        this.startDate = startDate;
//    }
}
