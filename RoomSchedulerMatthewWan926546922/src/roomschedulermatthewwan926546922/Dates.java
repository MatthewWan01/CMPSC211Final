/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roomschedulermatthewwan926546922;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mzwonton
 */
public class Dates {
    
    private static ArrayList<Date> Dates = new ArrayList<Date>();
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement getAllDate;
    private static PreparedStatement addDate;
    
    public static ArrayList<Date> getAllDates() {
        
        connection = DBConnection.getConnection();
        ArrayList<Date> dates = new ArrayList<Date>();
        
        try {
            
            getAllDate = connection.prepareStatement("select date from dates");
            resultSet = getAllDate.executeQuery();
            
            while(resultSet.next()) {
                
                dates.add(resultSet.getDate(1));
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return dates;
        
    }
    
    public static boolean addDate(Date date) {
        
        connection = DBConnection.getConnection();
        
        try {
            
            addDate = connection.prepareStatement("insert into dates (date) values (?)");
            addDate.setDate(1, date);
            addDate.executeUpdate();
            
            return true;
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return false;
        
    }
   
}
