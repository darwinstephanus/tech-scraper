package com.darwin.techscraper.database;

import com.darwin.techscraper.dao.EventDao;
import com.darwin.techscraper.entity.Event;
import com.darwin.techscraper.entity.EventId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Component
public class DataLoaderImpl implements DataLoader {

    @Value("${link}")
    private String link;

    @Value("${link2}")
    private String link2;

    @Autowired
    EventDao eventDao;

    @Async
    public String loadDataComputerWorld() throws IOException, ParseException {
        Document document = Jsoup.connect(link).get();
        Element table = document.getElementsByTag("tbody").get(0);
        Elements rows = table.select("tr");
        Elements href = rows.select("a");

        for(int i=0; i<rows.size(); i++){

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

            String linkEvent = href.get(i).attr("abs:href");

            //combine into event object
            EventId eventId = new EventId();
            eventId.setName(eventName);
            eventId.setStartDate(date1);

            Event event = new Event();
            event.setEventId(eventId);
            event.setEndDate(date2);
            event.setLocation(locationEvent);
            event.setLink(linkEvent);

            eventDao.save(event);
        }
        return "Success!";
    }

    @Async
    public String loadDataTechMeme() throws IOException, ParseException {
        try{
            Document document = Jsoup.connect(link2).get();
            Elements table = document.select("div.rhov");
            Elements href = table.select("a[href]");

            int year = Year.now().getValue();;
            int month = 1;

            for(int i=0; i<table.size(); i++){
                Element firstTd = table.get(i).child(0);

                //date
                String dateBeforeSplit = firstTd.select("div:nth-of-type(1)").text();
                String[] dates = dateBeforeSplit.split("-", 2);
                String dateStartBeforeParse;
                String dateEndBeforeParse;
                if(dates.length == 0){
                    throw new ParseException("Date length is 0", 0);
                }
                else if(dates.length == 1){
                    dateStartBeforeParse = dates[0];
                    dateEndBeforeParse = dates[0];
                }
                else {
                    dateStartBeforeParse = dates[0];
                    dateEndBeforeParse = dates[1];
                }

                //update year and month before parsing startdate
                Date tempStart=new SimpleDateFormat("MMM dd").parse(dateStartBeforeParse);
                Calendar calStart = Calendar.getInstance();
                calStart.setTime(tempStart);
                //January is 0 in Calendar
                int tempStartMonth = calStart.get(Calendar.MONTH) + 1;
                if(month <= tempStartMonth){
                    month = tempStartMonth;
                }
                else{
                    month = tempStartMonth;
                    year++;
                }

                //parse start date including removing space to "-" and add year
                dateStartBeforeParse = dateStartBeforeParse.replaceAll(" ", "-");
                dateStartBeforeParse = dateStartBeforeParse.concat("-");
                dateStartBeforeParse = dateStartBeforeParse.concat(Integer.toString(year));


                //parse for date end because there is no month sometimes
                if(dateEndBeforeParse.length() < 3){
                    String temp = dateStartBeforeParse.substring(0, 3);
                    temp = temp.concat(" ");
                    temp = temp.concat(dateEndBeforeParse);
                    dateEndBeforeParse = temp;
                }

                //update year and month before parsing enddate
                Date tempEnd=new SimpleDateFormat("MMM dd").parse(dateEndBeforeParse);
                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(tempEnd);
                //January is 0 in Calendar
                int tempEndMonth = calEnd.get(Calendar.MONTH) + 1;
                if(month <= tempEndMonth){
                    dateEndBeforeParse = dateEndBeforeParse.replaceAll(" ", "-");
                    dateEndBeforeParse = dateEndBeforeParse.concat("-");
                    dateEndBeforeParse = dateEndBeforeParse.concat(Integer.toString(year));
                }
                else{
                    dateEndBeforeParse = dateEndBeforeParse.replaceAll(" ", "-");
                    dateEndBeforeParse = dateEndBeforeParse.concat("-");
                    dateEndBeforeParse = dateEndBeforeParse.concat(Integer.toString(year+1));
                }

                Date startDate=new SimpleDateFormat("MMM-dd-yyyy").parse(dateStartBeforeParse);
                Date endDate=new SimpleDateFormat("MMM-dd-yyyy").parse(dateEndBeforeParse);

                //name
                String nameBeforeSplit = firstTd.select("div:nth-of-type(2)").text();
                String[] words = nameBeforeSplit.split(":", 2);
                String nameAfterSplit;
                if(words.length == 1){
                    nameAfterSplit = words[0];
                }
                else{
                    nameAfterSplit = words[1].trim();
                }

                //location
                String location;
                if(firstTd.select("div:nth-of-type(3)").text().equals("")){
                    location = "Virtual";
                }
                else{
                    location = firstTd.select("div:nth-of-type(3)").text();
                }

                //url
                String linkEvent = href.get(i).attr("abs:href");

                //merge into object event
                EventId eventId = new EventId();
                eventId.setName(nameAfterSplit);
                eventId.setStartDate(startDate);

                Event event = new Event();
                event.setEventId(eventId);
                event.setEndDate(endDate);
                event.setLocation(location);
                event.setLink(linkEvent);

                eventDao.save(event);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return "Success!";
    }

    @Async
    public String loadAllData() throws IOException, ParseException {
        String temp = loadDataComputerWorld();
        String temp2 = loadDataTechMeme();

        if(temp == "Success!"){
            if(temp2 == "Success!"){
                return "Success scraping both website!";
            }
            else{
                return "Success scraping only Computer World!";
            }
        }
        else if(temp2 == "Success!"){
            return "Success scraping only Tech Meme!";
        }
        return "Unsuccessful!";
    }


}
