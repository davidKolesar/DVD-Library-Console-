/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.consoleio.ConsoleIO;
import com.mycompany.dao.DVDdao;
import com.mycompany.dto.DVDdto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
import javax.imageio.ImageIO;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author David Kolesar
 */
public class DVDLibraryController {

    private ConsoleIO IO;
    private DVDdao dvdDao;
    public ArrayList<String> notes = new ArrayList<String>();
    private double noteCount = 0;
    int second;
    int minute;
    int hour;
    int day;
    int month;
    int year;
    boolean autoTime = true;

    public DVDLibraryController(DVDdao dao, ConsoleIO consoleio) {

        this.dvdDao = dao;
        this.IO = consoleio;
    }

    public void music() {

        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;

        ContinuousAudioDataStream loop = null;

        try {
            InputStream test = new FileInputStream("music.wav");

            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);

        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        MGP.start(loop);

        run();
    }

    public void getTime() {

        GregorianCalendar DVDdate = new GregorianCalendar();

        day = DVDdate.get(Calendar.DAY_OF_MONTH);
        month = DVDdate.get(Calendar.MONTH);
        year = DVDdate.get(Calendar.YEAR);

        second = DVDdate.get(Calendar.SECOND);
        minute = DVDdate.get(Calendar.MINUTE);
        hour = DVDdate.get(Calendar.HOUR);

        IO.displayString("");
        IO.displayString("Welcome to the Video Vault!");
        IO.displayString("");
        IO.displayString("For optimal performance, your computer's time and date should be accurately set.");
        IO.displayString("-------------------------------------------------------------+");
        IO.displayString("Current date is  " + day + "/" + (month + 1) + "/" + year + "    |");
        IO.displayString("-------------------------------------------------------------+");
        IO.displayString("Current time is  " + hour + " : " + minute + " : " + second + "  |");
        IO.displayString("-------------------------------------------------------------+");
        IO.displayString("");
    }

    public void run() {

        boolean playAgain = true;

        while (playAgain) {

            if (autoTime == true) {
                getTime();
            }

            IO.displayString("Please choose from the following options:");
            IO.displayString("========================================================================");
            IO.displayString("1.  List titles within my library");
            IO.displayString("2.  Add title");
            IO.displayString("3.  edit information about title");
            IO.displayString("4.  Remove title");
            IO.displayString("5.  Search Titles");
            IO.displayString("6.  Find within year");
            IO.displayString("7.  Find within rating");
            IO.displayString("8.  Find by director");
            IO.displayString("9.  Find by Studio");
            IO.displayString("10. Average age of movies");
            IO.displayString("11  Display the newest movie in your library");
            IO.displayString("12  Display the oldest movie in your library");
            IO.displayString("13. Display average notes per movie in your library");
            IO.displayString("14  Disable automatic date and time");
            IO.displayString("15. Enable automatic date and time");
            IO.displayString("16. Exit");
            IO.displayString("========================================================================");

            int choice = IO.getIntRange("", 16, 1);

            //This takes the user's input and sends it to console IO, which evaluates it and sends it back. 
            switch (choice) {

                //This is the menu prompt    
                case 1:
                    listTitles();
                    break;
                case 2:
                    addTitle();
                    break;
                case 3:
                    editTitle();
                    break;
                case 4:
                    removeTitle();
                    break;
                case 5:
                    searchTitles();
                    break;
                case 6:
                    sinceYear();
                    break;
                case 7:
                    searchRating();
                    break;
                case 8:
                    searchDirector();
                    break;
                case 9:
                    searchStudio();
                    break;
                case 10:
                    averageAge();
                    break;
                case 11:
                    newestMovie();
                    break;
                case 12:
                    oldestMovie();
                    break;
                case 13:
                    countNotes();
                    break;
                case 14:
                    killAutoTime();
                    break;
                case 15:
                    enableAutoTime();
                    break;
                case 16:
                    System.exit(0);
            }
        }

    }

    private void removeTitle() {

        boolean again = false;

        while (again == false) {

            Integer id = IO.getInt("Please enter the listing number.");

            DVDdto dvd = dvdDao.read(id);

            dvdDao.delete(dvd);

            int choice = IO.getIntRange("Would you like to remove another entry? Input 1 for yes or 2 for no", 2, 1);

            if (choice == 2) {
                again = true;
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editTitle() {

        int entryNumber = IO.getInt("Please enter the Id number.");

        String title = IO.getString("Please edit DVD's name.");
        String director = IO.getString("Please enter the director's name.");
        String mpaaRating = IO.getString("What is this movie rated?");
        String studio = IO.getString("Please enter the studio name.");
        String userNote = IO.getString("Please rewrite the user note.");
        IO.displayString("Your note has been set.");
        Integer releaseYear = IO.getInt("Please enter the release year.");
        IO.displayString("This movie was released in " + releaseYear);
        String userRating = IO.getString("What would you rate this movie?");
        IO.displayString("You gave this a user rating of " + userRating);

        DVDdto myDVD = new DVDdto();

        
        
            
        
        myDVD.setNoteDecode(userNote);
        myDVD.setID(entryNumber);
        myDVD.setTitle(title);
        myDVD.setDirectorName(director);
        myDVD.setStudio(studio);
        myDVD.setMpaaRating(mpaaRating);
        myDVD.setReleaseYear(releaseYear);
        myDVD.setUserRating(userRating);

        dvdDao.update(myDVD);

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addTitle() {

        boolean again = false;

        int noteChoice = 1;

        while (again == false) {

            String titleName = IO.getString("Please enter title of the entry.");

            DVDdto myDVD = new DVDdto();

            myDVD.setTitle(titleName);

            String directorName = IO.getString("Please enter the director.");

            myDVD.setDirectorName(directorName);

            String studio = IO.getString("Please enter the studio name.");

            myDVD.setStudio(studio);

            String mpaaRating = IO.getString("What is this movie rated?");

            myDVD.setMpaaRating(mpaaRating);

            noteChoice = IO.getInt("Would you like to add a note to this movie? Input 1 for yes or 2 for no.");

            while (noteChoice == 1) {

                String userNote = IO.getString("Please enter any notes you have about the movie.");

                myDVD.getUserNote().add(userNote);

                noteCount++;

                IO.displayString("Your note has been set.");

                noteChoice = IO.getInt("Would you like to add another note to this movie? Input 1 for yes or 2 for no.");

            }

            Integer releaseYear = IO.getInt("Please enter the release year.");

            myDVD.setReleaseYear(releaseYear);

            IO.displayString("This movie was released in " + releaseYear);

            String userRating = IO.getString("What would you rate this movie?");

            myDVD.setUserRating(userRating);

            IO.displayString("You gave this a user rating of " + userRating);

            dvdDao.add(myDVD);

            int choice = IO.getIntRange("Would you like to add another entry? Input 1 for yes or 2 for no", 2, 1);

            if (choice == 2) {
                again = true;

            }
        }
    }

    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    private void listTitles() {

        //This declares the list of DVDdtos "myDVDs" a value that equals the list method in dvdDao.
        List<DVDdto> myDVDs = dvdDao.list();

        for (DVDdto d : myDVDs) {

            printString(d);

        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void searchTitles() {

        String title = IO.getString("Please enter the title you wish to search:");

        List<DVDdto> searchResults = dvdDao.findEntryByName(title);

        for (DVDdto d : searchResults) {

            printString(d);
        }
    }

    public void sinceYear() {

        int currentActualYear;
        currentActualYear = year;

        Integer releaseYear = IO.getInt("How many years back would you like to search?");

        Integer searchYear = 0;

        if (autoTime = true) {

            searchYear = (currentActualYear - releaseYear);

            for (Integer i = currentActualYear + 1; i > searchYear; searchYear++) {

                List<DVDdto> dvdsWithinYear = dvdDao.findEntryWithinYear(searchYear);

                printList(dvdsWithinYear);
            }
        }
        if (autoTime = false) {

            int currentYear = 2016;

            searchYear = (currentYear - releaseYear);

            for (Integer i = 2017; i > searchYear; searchYear++) {

                List<DVDdto> dvdsWithinYear = dvdDao.findEntryWithinYear(searchYear);

                printList(dvdsWithinYear);

            }

        }
    }

    public void searchRating() {

        String rating = IO.getString("What rating are you searching for?");

        List<DVDdto> mpaaList = dvdDao.findEntryByMpaa(rating);

        printList(mpaaList);

    }

    public void printList(List<DVDdto> dvdList) {

        for (DVDdto d : dvdList) {
            IO.displayString(
                    String.format("||  VideoVault Number: %s  ||  Title: %s  ||  Directed by: %s  ||  Studio : %s  ||  MPAA Rating: %s  ||  Release Date: %s  ||  You said:  %s  ||  Your rating:  %s  ||",
                            d.getID(),
                            d.getTitle(),
                            d.getDirectorName(),
                            d.getStudio(),
                            d.getMpaaRating(),
                            d.getReleaseYear(),
                            d.getUserNote(),
                            d.getUserRating()));

           

        }
         soundEffect();
    }

    public void searchDirector() {

        String director = IO.getString("What director do you wish to search for?");

        Map<String, List<DVDdto>> dvdsByDirector
                = dvdDao
                .findEntryByDirector(director)// returning a stream
                .collect(Collectors.groupingBy(DVDdto::getMpaaRating));

        for (String mpaa : dvdsByDirector.keySet()) {

            IO.displayString(String.format("MPAA: %s", mpaa));

            for (DVDdto d : dvdsByDirector.get(mpaa)) {

                printString(d);

            }

        }
    }

    public void searchStudio() {

        String studio = IO.getString("What studio are you searching for?");

        Stream<DVDdto> studioList = dvdDao.findEntryByStudio(studio);

        List listOfStream = studioList.collect(Collectors.toList());

        printList(listOfStream);

    }

    public void averageAge() {

        double average = dvdDao.averageAgeOfMovies();

        if (autoTime = false) {
            average = 2016 - average;
            IO.displayString("Of all movies in your library, they are an average of " + average + " years old.");
            IO.displayString("");
        }

        if (autoTime = true) {
            average = year - average;
            IO.displayString("Of all movies in your library, they are an average of " + average + " years old.");
            IO.displayString("");

        }

    }

    public void newestMovie() {

        double newestYearDouble = dvdDao.newestYear();

        int newestYear = (int) (newestYearDouble);

        Stream<DVDdto> newestMovieStream = dvdDao.newestMovie(newestYear);

        List newestMovieList = newestMovieStream.collect(Collectors.toList());

        printList(newestMovieList);

    }

    public void oldestMovie() {

        double oldestYearDouble = dvdDao.oldestYear();

        int oldestYear = (int) (oldestYearDouble);

        Stream<DVDdto> oldestMovieStream = dvdDao.oldestMovie(oldestYear);

        List oldestMovieList = oldestMovieStream.collect(Collectors.toList());

        printList(oldestMovieList);

    }

    public void countNotes() {

        int movieTotal = dvdDao.movieSum();

        double movieTotalDouble = (double) movieTotal;

        DVDdto dto = new DVDdto();

        double average = dto.getUserNote().size();

        average = (noteCount /= movieTotalDouble);

        IO.displayString("the average number of notes associated with movies in your collection is " + average + ".");

    }

    public void killAutoTime() {

        if (autoTime == true) {

            autoTime = false;

            IO.displayString((char) 27 + "[31mYou have disabled the automatic time detection of VideoVault." + (char) 27 + "[0m");
            IO.displayString("All features of VideoVault will still operate, but you should have automatic time detection enabled to experience optimal performance.");

        } else {

            IO.displayString("Automatic display has already been disabled. ");
        }
    }

    public void enableAutoTime() {
        if (autoTime == false) {

            autoTime = true;
            IO.displayString((char) 27 + "[31mYou have enabled the automatic time detection of VideoVault." + (char) 27 + "[0m");

        } else {

            IO.displayString("Automatic display has already been enabled. ");
        }
    }

    public void printString(DVDdto d) {

        IO.displayString(
                String.format("||  VideoVault Number: %s  ||  Title: %s  ||  Directed by: %s  ||  Studio : %s  ||  MPAA Rating: %s  ||  Release Date: %s  ||  You said:  %s  ||  Your rating:  %s  ||",
                        d.getID(),
                        d.getTitle(),
                        d.getDirectorName(),
                        d.getStudio(),
                        d.getMpaaRating(),
                        d.getReleaseYear(),
                        d.getUserNote(),
                        d.getUserRating()));

        soundEffect();
    }

    public void soundEffect() {

        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;

        ContinuousAudioDataStream loop = null;

        try {
            InputStream test = new FileInputStream("ding.wav");

            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);

        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        MGP.start(loop);

       
    }

}
