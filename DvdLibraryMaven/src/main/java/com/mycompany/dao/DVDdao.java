/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao;

import com.mycompany.dto.DVDdto;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author apprentice
 */
public interface DVDdao {

    DVDdto add(DVDdto dvd);

    void delete(DVDdto dvd);

    List<DVDdto> list();

    DVDdto read(Integer id);

    void update(DVDdto dvd);

    List<DVDdto> findEntryByName(String title);

    public List<DVDdto> findEntryWithinYear(Integer searchYear);

    public List<DVDdto> findEntryByMpaa(String rating);

    public Stream<DVDdto> findEntryByDirector(String director);

    public Stream<DVDdto> findEntryByStudio(String studio);

    public double averageAgeOfMovies();

    public double newestYear();

    public Stream<DVDdto> newestMovie(int newestYear);

    public double oldestYear();

    public Stream<DVDdto> oldestMovie(int oldestYear);

    public Integer movieSum();

}
