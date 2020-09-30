package com.darwin.techscraper.database;

import com.darwin.techscraper.dao.EventDao;
import com.darwin.techscraper.model.Event;
import com.darwin.techscraper.model.EventId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataLoaderImpl implements DataLoader {

    @Value("${link}")
    private String link;

    @Autowired
    EventDao eventDao;

    private Map<String,List<Event>> eventList = new HashMap<>();

    public Map<String, List<Event>> loadData() throws IOException, ParseException {
        Document document = Jsoup.connect(link).get();
        Element table = document.getElementsByTag("tbody").get(0);
        Elements rows = table.select("tr");

        for(int i=0; i<rows.size(); i++){
            Element firstTd = rows.get(i).child(0);
            String eventName;
            eventName = firstTd.text();

            Element secondTd = rows.get(i).child(2);
            String startDate;
            startDate = secondTd.text();
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);


            Element thirdTd = rows.get(i).child(3);
            String endDate;
            endDate = thirdTd.text();
            Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

            Element fourthTd = rows.get(i).child(4);
            String locationEvent;
            locationEvent = fourthTd.text();

            System.out.println(eventName);
//            System.out.println(startDate);
//            System.out.println(endDate);
//            System.out.println(locationEvent);

            EventId eventId = new EventId();
            eventId.setName(eventName);
            eventId.setStartDate(date1);

            Event event = new Event();
            event.setEventId(eventId);
//            event.setName(eventName);
//            event.setStartDate(date1);
            event.setEndDate(date2);
            event.setLocation(locationEvent);

            eventDao.save(event);
        }


//        countryList.forEach((k, v) -> System.out.println(k + " " + v.toString()));
//        System.out.println("list size = " + countryList.size());
        return eventList;
    }
}
