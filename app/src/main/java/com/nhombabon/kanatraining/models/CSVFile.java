package com.nhombabon.kanatraining.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Common */
public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<String[]> read() {
        List<String[]> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
        while (true) {
            try {
                String csvLine = reader.readLine();
                if (csvLine == null) {
                    try {
                        this.inputStream.close();
                        return resultList;
                    } catch (IOException e) {
                        throw new RuntimeException("Error while closing input stream: " + e);
                    }
                }
                resultList.add(csvLine.split(","));
            } catch (IOException e2) {
                throw new RuntimeException("Error in reading CSV file: " + e2);
            } catch (Throwable th) {
                try {
                    this.inputStream.close();
                } catch (IOException e22) {
                    throw new RuntimeException("Error while closing input stream: " + e22);
                }
            }
        }
    }
}
