/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DVDtest;

import com.mycompany.dao.DVDdao;
import com.mycompany.dto.DVDdto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class DVDtest {

    DVDdao dao;
    DVDdto dto;

    public DVDtest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        dao = ctx.getBean("DVDdao", DVDdao.class);

    }

    private static void clearData() {

        File xx = new File("testFile.txt");
        if (xx.exists()) {
            xx.delete();
            try {
                xx.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DVDtest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @BeforeClass
    public static void setUpClass() { //This only happens before the test is run
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() { //This happens after the test is complete
    }

    @After
    public void tearDown() { //this happens after every test has ran
    }

    @Test
    public void getAll() {
        int expected = 0;
        int actual = dao.list().size();

        Assert.assertEquals(expected, actual);
    }

    public void add() {

        int expectedSize = 1;
        Integer expectedId = 1;  //Arrange
        DVDdto newDVD = new DVDdto();

        newDVD = dao.add(newDVD); // Act

        Assert.assertEquals(newDVD, expectedId); //Assert
        Assert.assertEquals(expectedSize, dao.list().size());

    }

    @Test
    public void delete() {

        int expectedSum = 0;
        int actual = dao.movieSum();

        Assert.assertEquals(actual, expectedSum);

    }

}
