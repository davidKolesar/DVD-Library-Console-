/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//DVD Library class
package com.mycompany.dao;

import com.mycompany.dto.DVDdto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author David Kolesar
 */
public class DVDdaoImpl implements DVDdao {

    private static final String FILENAME = "DVDLibraryStorage.txt";
    private static final String TOKEN = "::";
    public List<DVDdto> DVDs = null;
    private Integer nextID = 1;
    private DVDdto myDVD = new DVDdto();

    public DVDdaoImpl() {

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

        for (DVDdto d : DVDs) {

            if (d.getID() == dvd.getID()) {
                d = dvd;
            }

        }

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

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(FILENAME));

            for (DVDdto s : DVDs) {
                out.print(s.getID());
                out.print(TOKEN);

                out.print(s.getTitle());
                out.print(TOKEN);

                out.print(s.getMpaaRating());
                out.print(TOKEN);

                out.print(s.getReleaseYear());
                out.print(TOKEN);

                out.print(s.getDirectorName());
                out.print(TOKEN);

                out.print(s.getUserNote());
                out.print(TOKEN);

                out.print(s.getUserRating());
                out.print(TOKEN);
                out.println("");

            }

            out.flush();

        } catch (IOException ex) {

        } finally {
            out.close();
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

                int dvdId = Integer.parseInt(stringParts[0]);
                int releaseYear = Integer.parseInt(stringParts[4]);

                myDVD.setID(dvdId);
                myDVD.setTitle(stringParts[1]);
                myDVD.setDirectorName(stringParts[2]);
                myDVD.setMpaaRating(stringParts[3]);
                myDVD.setReleaseYear(releaseYear);
//                myDVD.setUserNote(stringParts[5]);
                myDVD.setUserRating(stringParts[6]);

                tempDVDList.add(myDVD);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DVDdao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tempDVDList;
    }

    @Override
    public List<DVDdto> findEntryByName(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVDdto> findEntryWithinYear(Integer year) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVDdto> findEntryByMpaa(String rating) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<DVDdto> findEntryByDirector(String director) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<DVDdto> findEntryByStudio(String studio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double averageAgeOfMovies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double newestYear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<DVDdto> newestMovie(int newestYear) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double oldestYear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<DVDdto> oldestMovie(int oldestYear) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer movieSum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
