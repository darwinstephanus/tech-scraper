package com.darwin.techscraper.database;


import com.darwin.techscraper.model.Event;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DataLoader {

    void loadDataComputerWorld() throws IOException, ParseException;

    void loadDataTechMeme() throws IOException, ParseException;
}
