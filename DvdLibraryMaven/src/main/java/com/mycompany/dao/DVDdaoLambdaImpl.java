/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//DVD Library class
package com.mycompany.dao;

import com.mycompany.dto.DVDdto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author David Kolesar
 */
public class DVDdaoLambdaImpl implements DVDdao {

    private static final String FILENAME = "DVDLibraryStorage.txt";
    private static final String TOKEN = "::";
    public List<DVDdto> DVDs = null;
    private Integer nextID = 1;
    private DVDdto myDVD = new DVDdto();
    public ArrayList<String> notesWrite = new ArrayList<String>();

    public DVDdaoLambdaImpl() {

        DVDs = decode();

        for (DVDdto d : DVDs) {

            if (d.getID() == nextID) {
                nextID = d.getID() + 1;
            }
        }
    }

    @Override
    public DVDdto add(DVDdto dvd) {

        dvd.setID(nextID);

        DVDs.add(dvd);

        nextID++;

        encode();

        return dvd;

    }

    @Override
    public DVDdto read(Integer id) {

        for (DVDdto s : DVDs) {

            if (s.getID() == id) {
                return s;
            }
        }
        return null;

    }

    @Override
    public void update(DVDdto dvd) {

        DVDdto toRemove = null;

        for (DVDdto d : DVDs) {

            if (d.getID() == dvd.getID()) {
                toRemove = d;
            }

        }

        DVDs.remove(toRemove);
        DVDs.add(dvd);
        encode();

        encode();

    }

    @Override
    public void delete(DVDdto dvd) {

        for (DVDdto d : DVDs) {

            if (d.getID() == dvd.getID()) {

                DVDs.remove(d);
                break;
            }
        }
        encode();
    }

    @Override
    public List<DVDdto> list() {

        return DVDs;

        //DVDs is a variable assigned to the value of list.
    }

    public void encode() {

        try {

            File file = new File(FILENAME);

            FileWriter out = new FileWriter(file);

            for (DVDdto s : DVDs) {

                out.write("[");

                for (int i = 0; i < s.getUserNote().size(); i++) {

                    out.write(s.getUserNote().get(i));

                    if (i < s.getUserNote().size() - 1) {

                        out.write(",");
                    }
                }

                out.write("]");

                out.write(TOKEN);

                out.write(new Integer(s.getID()).toString());
                out.write(TOKEN);

                out.write(s.getTitle());
                out.write(TOKEN);

                out.write(s.getMpaaRating());
                out.write(TOKEN);

                out.write(new Integer(s.getReleaseYear()).toString());
                out.write(TOKEN);

                out.write(s.getDirectorName());
                out.write(TOKEN);

                out.write(s.getStudio());
                out.write(TOKEN);

                out.write(s.getUserRating());
                out.write(TOKEN);
                out.write("\n");

            }

            out.flush(); // enforces any outputs in bytes to be written out.
            out.close(); // tells the file that it's available to be read.

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            Logger.getLogger(DVDdaoLambdaImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }

    }

    public List<DVDdto> decode() {

        List<DVDdto> tempDVDList = new ArrayList();

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                DVDdto myDVD = new DVDdto();

                int dvdId = Integer.parseInt(stringParts[1]);
                int releaseYear = Integer.parseInt(stringParts[4]);
                String testme = stringParts[0];
                notesWrite.add(testme);

                myDVD.setNoteDecode(stringParts[0]);
                myDVD.setID(dvdId);
                myDVD.setTitle(stringParts[2]);
                myDVD.setMpaaRating(stringParts[3]);
                myDVD.setReleaseYear(releaseYear);
                myDVD.setDirectorName(stringParts[5]);
                myDVD.setStudio(stringParts[6]);
                myDVD.setUserRating(stringParts[7]);

                tempDVDList.add(myDVD);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DVDdao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tempDVDList;
    }

    @Override //?
    public List<DVDdto> findEntryByName(String title) {

//        Predicate<DVDdto> titlePredicate = new Predicate<DVDdto>() {
//
//            @Override
//            public boolean test(DVDdto t) {
//                if (t.getTitle().equals(title)) {
//                    return true;
//                }
//                return false;
//            }
//
//        };
        List<DVDdto> result = DVDs
                .stream()
                .filter(s -> s.getTitle().equals(title)) //Make 'Jones' a variable you can edit
                .collect(Collectors.toList());

        //.forEach(e -> System.out.println(e.getEntryNumber()+". " + e.getLastName() +", "+ e.getFirstName() +" "+ e.getAddress())); sout version for non-list
        return result;

    }

    public List<DVDdto> findEntryWithinYear(Integer searchYear) {
//
//        Predicate<DVDdto> searchYearPredicate = new Predicate<DVDdto>() {
//
//            @Override
//            public boolean test(DVDdto d) {
//                if (searchYear.equals(d.getReleaseYear())) {
//                    return true;
//                }
//                return false;
//            }
//
//        };

        List<DVDdto> result = DVDs
                .stream()
                .filter(d -> d.getReleaseYear().equals(searchYear))
                .collect(Collectors.toList());

        return result;

    }

    public List<DVDdto> findEntryByMpaa(String rating) {

        List<DVDdto> result = DVDs
                .stream()
                .filter(s -> s.getMpaaRating().equals(rating))
                .collect(Collectors.toList());

        //.forEach(e -> System.out.println(e.getEntryNumber()+". " + e.getLastName() +", "+ e.getFirstName() +" "+ e.getAddress())); sout version for non-list
        return result;

    }

    public Stream<DVDdto> findEntryByDirector(String director) {

        //Predicate<DVDdto> directorPredicate = new Predicate<DVDdto>() {
        return DVDs
                .stream()
                .filter(DVDdto -> DVDdto.getDirectorName().equals(director));
        //Lambda    
//            Map<String, List<Entry>> result 
//                 = addresses
//                .stream()
//                .filter(a->a.getState().equals(state))
//                .collect(Collectors.groupingBy(Address::getCity));
//
//        //.forEach(e -> System.out.println(e.getEntryNumber()+". " + e.getLastName() +", "+ e.getFirstName() +" "+ e.getAddress())); sout version for non-list
//        return result;
    }

    public Stream<DVDdto> findEntryByStudio(String studio) {

        return DVDs
                .stream()
                .filter(DVDdto -> DVDdto.getStudio().equals(studio));
        //.forEach(e -> System.out.println(e.getEntryNumber()+". " + e.getLastName() +", "+ e.getFirstName() +" "+ e.getAddress())); sout version for non-list

    }

    @Override
    public double averageAgeOfMovies() {

        OptionalDouble average = DVDs
                .stream()
                .mapToDouble(d -> d.getReleaseYear())
                .average();

        return average.getAsDouble();
    }

    public double newestYear() {

        OptionalDouble newestYear = DVDs
                .stream()
                .mapToDouble(d -> d.getReleaseYear())
                .max();

        return newestYear.getAsDouble();
    }

    @Override
    public Stream<DVDdto> newestMovie(int newestYear) {

        return DVDs
                .stream()
                .filter(d -> d.getReleaseYear().equals(newestYear));

    }

    public double oldestYear() {

        OptionalDouble newestYear = DVDs
                .stream()
                .mapToDouble(d -> d.getReleaseYear())
                .min();

        return newestYear.getAsDouble();
    }

    @Override
    public Stream<DVDdto> oldestMovie(int oldestYear) {

        return DVDs
                .stream()
                .filter(d -> d.getReleaseYear().equals(oldestYear));

    }

    public Integer movieSum() {

        Integer movieSum = DVDs
                .stream()
                .mapToInt(DVDdto::getCounter)
                .sum();

        return movieSum;
    }


}
