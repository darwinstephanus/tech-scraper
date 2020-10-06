package com.darwin.techscraper.database;


import java.io.IOException;
import java.text.ParseException;

public interface DataLoader {

    String loadDataComputerWorld() throws IOException, ParseException;

    String loadDataTechMeme() throws IOException, ParseException;

    String loadAllData() throws IOException, ParseException;
}
