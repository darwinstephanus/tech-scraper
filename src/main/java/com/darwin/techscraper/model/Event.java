package com.darwin.techscraper.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="event")
@EqualsAndHashCode
public class Event {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="event_id")
//    private long event_id;

    @EmbeddedId
    private EventId eventId;
    private Date endDate;
    private String location;
}
