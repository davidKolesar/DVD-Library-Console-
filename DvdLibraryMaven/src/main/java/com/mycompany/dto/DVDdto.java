/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dto;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author David Kolesar
 */
public class DVDdto {

    private Integer ID;
    private Integer releaseYear;
    private String title;
    private String mpaaRating;
    private String directorName;
    private String userRating;
    private ArrayList<String> userNote = new ArrayList<String>();
    private String studio;
    private Integer counter = 1;

    public void setID(Integer nextID) {
        this.ID = nextID;
    }

    public int getID() {
        return ID;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getStudio() {
        return studio;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setUserNote(ArrayList<String> userNote) {
        this.userNote = userNote;
    }

    public void setNoteDecode(String noteString) {
        ArrayList<String> userNote = new ArrayList<String>(Arrays.asList(noteString));
        this.userNote = userNote;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public ArrayList<String> getUserNote() {
        return userNote;
    }

    public Integer getCounter() {
        return counter;
    }

}
