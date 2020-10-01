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

    @Value("${link2}")
    private String link2;

    @Autowired
    EventDao eventDao;

//    private Map<String,List<Event>> eventList = new HashMap<>();

    public void loadDataComputerWorld() throws IOException, ParseException {
        Document document = Jsoup.connect(link).get();
        Element table = document.getElementsByTag("tbody").get(0);
//        System.out.println(table);
        Elements rows = table.select("tr");
        Elements href = rows.select("a");

        //key
//        Elements href = document.select("a[href]");
//        for(Element temp : href) {
//            System.out.println("AA: " + temp.attr("abs:href"));
//        }

        for(int i=0; i<rows.size(); i++){


//            for(Element temp : href) {
//                System.out.println("AA: " + temp.attr("abs:href"));
//            }
//            System.out.println(href.get(0).attr("abs:href"));

//            for(Element temp : href) {
//                System.out.println("AA: " + temp);
//            }
//            System.out.println(href.get(0));
//            String test = rows.get(i).text();
//            String test = rows.get(i).select("a.href").text();
//            System.out.println(test);

            //parse each information into strings
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
            Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

            Element fourthTd = rows.get(i).child(4);
            String locationEvent;
            locationEvent = fourthTd.text();

            String link = href.get(i).attr("abs:href");

            //combine into event object
            EventId eventId = new EventId();
            eventId.setName(eventName);
            eventId.setStartDate(date1);

            Event event = new Event();
            event.setEventId(eventId);
            event.setEndDate(date2);
            event.setLocation(locationEvent);
            event.setLink(link);

            eventDao.save(event);
        }


//        countryList.forEach((k, v) -> System.out.println(k + " " + v.toString()));
//        System.out.println("list size = " + countryList.size());
    }

    public void loadDataTechMeme() throws IOException, ParseException {
        try{
            Document document = Jsoup.connect(link2).get();
            Elements table = document.select("div.rhov");
            Elements href = table.select("a[href]");

            for(Element row : document.select("div.rhov")) {
//                System.out.println(row);
                String date = row.select("div:nth-of-type(1)").text();
//                System.out.println(row.select("div:nth-of-type(1)"));
//                String name = row.select("div:nth-of-type(2)").text();
//                System.out.println(row.select("div:nth-of-type(2)"));
//                String location = row.select("div:nth-of-type(3)").text();
//                System.out.println(row.select("div:nth-of-type(3)"));
//                String link = row.select("div:nth-of-type(1)").attr("abs:href");
//                System.out.println(link);

                String link = href.get(0).attr("abs:href");
                System.out.println(link);
//                System.out.println(row.attr("abs:href"));

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }



//        Element table = document.getElementsByTag("events").get(0);
//        System.out.println(table);
//        Elements rows = table.select(".rhov");
//
//        Element firstTd = rows.get(0).child(0);
//        String eventName;
//        eventName = firstTd.text();
//        System.out.println(eventName);

    }
}
