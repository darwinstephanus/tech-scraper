package com.darwin.techscraper.controller;

import com.darwin.techscraper.common.CommonResponse;
import com.darwin.techscraper.common.DataLoaderControllerAdvice;
import com.darwin.techscraper.database.DataLoader;
import com.darwin.techscraper.entity.Event;
import com.darwin.techscraper.entity.utils.PagingHeaders;
import com.darwin.techscraper.entity.utils.PagingResponse;
import com.darwin.techscraper.exception.BadLinkException;
import com.darwin.techscraper.exception.BadParseException;
import com.darwin.techscraper.service.EventService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DatabaseController {

    @Autowired
    DataLoader dataLoader;

    @Autowired
    EventService eventService;

    @GetMapping("/load_data_tech_meme")
    public CommonResponse loadDataTechMeme() {

        try{
            dataLoader.loadDataTechMeme();
        } catch (BadLinkException e) {
            return new CommonResponse(400, e.getMessage());
        } catch (BadParseException e) {
            return new CommonResponse(400, e.getMessage());
        } catch (Exception e) {
            return new CommonResponse(500, e.getMessage());
        }

        return new CommonResponse(200 ,"load data tech meme success!");
    }

    @GetMapping("/load_data_computer_world")
    @ExceptionHandler({ BadLinkException.class, BadParseException.class })
    public CommonResponse loadDataComputerWorld() throws IOException, ParseException {
        try{
            dataLoader.loadDataComputerWorld();
        } catch (BadLinkException e) {
            return new CommonResponse(400, e.getMessage());
        } catch (BadParseException e) {
            return new CommonResponse(400, e.getMessage());
        } catch (Exception e) {
            return new CommonResponse(500, e.getMessage());
        }
        return new CommonResponse(200 ,"load data computer world success!");
    }

    @GetMapping("/load_all_data")
    @ExceptionHandler({ BadLinkException.class, BadParseException.class })
    public CommonResponse loadAllData() throws Throwable {
        try{
            dataLoader.loadAllData();
        } catch (BadLinkException e) {
            return new CommonResponse(400, e.getMessage());
        } catch (BadParseException e) {
            return new CommonResponse(400, e.getMessage());
        } catch (Exception e) {
            return new CommonResponse(500, e.getMessage());
        }
        return new CommonResponse(200 ,"load data computer world success!");
    }

    @Transactional
    @GetMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Event>> get(
            @And({
                    @Spec(path = "eventId.name", params = "name", spec = Like.class),
                    @Spec(path = "location", params = "location", spec = Like.class),
                    @Spec(path = "eventId.startDate", params = {"startDateGt", "startDateLt"}, spec = Between.class),
                    @Spec(path = "endDate", params = {"endDateGt", "endDateLt"}, spec = Between.class)
            }) Specification<Event> spec,
            Sort sort,
            @RequestHeader HttpHeaders headers) {
        final PagingResponse response = eventService.get(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }

}
