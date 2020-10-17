package com.darwin.techscraper.database;


import com.darwin.techscraper.entity.Event;
import com.darwin.techscraper.entity.EventId;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DataLoader {

    CompletableFuture<List<Event>> loadDataComputerWorld() throws IOException, ParseException;

    CompletableFuture<List<Event>> loadDataTechMeme() throws IOException, ParseException;

    List<EventId> loadAllData() throws Throwable;
}
