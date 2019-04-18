/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package incidentrecorder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Month;

/**
 *
 * @author Shellback Software
 */
public class IncidentRecorder {
    private final String curYear;
    private final String curMonth;
    private final String curDate;
    private final String curTime;
    private  String logText;
    private final String name;
    private final String location;
    private String subjDesc;
    private final String plateNumber;
    private final String plateState;
    
    public IncidentRecorder(String cDate, String cTime, String uText, String fName,
                    String loc, String desc, String plNum, String plState) {
        curYear = cDate.split("-")[0];
        curMonth = cDate.split("-")[1];
        curDate = cDate;
        curTime = cTime;
        logText = uText;
        name = fName;
        location = loc;
        subjDesc = desc;
        plateNumber = plNum;
        plateState = plState;
    }
    
    // Builds the filepath and output string
    public void saveFile() {
        String filepath = System.getProperty("user.home") + "/Desktop";
        buildOutput();
        try {
            String monthName = Month.of(Integer.parseInt(curMonth)).name();
            monthName = monthName.substring(0,1).toUpperCase() + monthName.substring(1).toLowerCase();
            filepath += "/Logs/" + curYear + "/"+ monthName;
            // Create folder - For each level, if it doesn't exist the folder will be created.
            // Desktop/Logs/{year}/{month}/{date}.txt
            Files.createDirectories(Paths.get(filepath));
            // Create the file if it doesn't exist
            Path fpath = Paths.get(filepath + "/" + curDate + ".txt");
            if(!Files.exists(fpath)) {
                Files.createFile(fpath);
            }
            // Add to the file
            Files.write(fpath, buildOutput().getBytes(Charset.forName("UTF-8")), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println(ex);}
    }
    
    private String buildOutput() {
        // Every 60 characters add line break
        String desc = subjDesc.replaceAll("(.{60})", "$1"+System.lineSeparator());
        String notes = logText.replaceAll("(.{60})", "$1"+System.lineSeparator());
        // Replace line breaks with new lines
        desc = desc.replaceAll("\n", System.lineSeparator());
        notes = notes.replaceAll("\n", System.lineSeparator());
        String outString = 
               "Time: " + curTime + System.lineSeparator() +
               "Name: " + name + System.lineSeparator() +
               "Location: " + location + System.lineSeparator() +
               "Subject Description: " + System.lineSeparator() + desc + System.lineSeparator() +
               "License Plate Number: " + plateNumber + System.lineSeparator() +
               "License Plate State: " + plateState + System.lineSeparator() +
               "Notes: " + System.lineSeparator() + notes +
               System.lineSeparator() + System.lineSeparator(); 
        
        // Entry divider; Make sure this number matches the number above in replaceAll()
        for (int i = 0; i < 60; i++) {
            outString += "=";
        }
        outString += System.lineSeparator() + System.lineSeparator();
        return outString; 
    }
    
}
