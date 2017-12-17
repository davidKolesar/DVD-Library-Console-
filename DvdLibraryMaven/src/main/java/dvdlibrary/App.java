/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dvdlibrary;

import com.mycompany.dao.DVDdaoLambdaImpl;
import com.mycompany.consoleio.ConsoleIO;
import com.mycompany.controllers.DVDLibraryController;
import com.mycompany.dao.DVDdao;
import com.mycompany.dao.DVDdaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //Class Path XML Application Context reads in the XML passed from parameter (applicationContext.xml)
       
        DVDLibraryController sc = (DVDLibraryController) ctx.getBean("DVDcontroller");
        //"DVDController" parameter is the name of the Bean and casts it into the controller class
        
        sc.music();
       
        
    }
    
}
