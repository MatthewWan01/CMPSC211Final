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
import java.util.Calendar;

/**
 *
 * @author mzwonton
 */
public class WaitlistQueries {
    
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement getAllWaitlist;
    private static PreparedStatement addNewWaitlist;
    private static PreparedStatement cancelWaitlist;
    private static PreparedStatement WaitlistByDate;
    private static PreparedStatement WaitlistByFaculty;
    
    public static ArrayList<WaitlistEntry> getAllWaitlist() {
        
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        
        try {
            
            getAllWaitlist = connection.prepareStatement("select faculty, date, seats, timestamp from waitlist order by timestamp");
            resultSet = getAllWaitlist.executeQuery();
            
            while(resultSet.next()) {
                
                WaitlistEntry Waitlists = new WaitlistEntry(resultSet.getString(1), resultSet.getDate(2), resultSet.getInt(3), resultSet.getTimestamp(4));
                waitlist.add(Waitlists);
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return waitlist;
        
    }
    
    public static void addWaitlistEntry(String faculty, Date date, int seats) {
        
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = RoomQueries.getAllPossibleRooms();
        
        try {
            
            addNewWaitlist = connection.prepareStatement("insert into waitlist (faculty, date, seats, timestamp) values (?,?,?,?)");
            addNewWaitlist.setString(1, faculty);
            addNewWaitlist.setDate(2, date);
            addNewWaitlist.setInt(3, seats);
            addNewWaitlist.setTimestamp(4, currentTimestamp);
            addNewWaitlist.executeUpdate();
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
         
    }
     public static ArrayList<WaitlistEntry> getWaitlistByFaculty(String faculty) {
         
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlistBF = new ArrayList<>();
        
        try {
            
            WaitlistByFaculty = connection.prepareStatement("select date, seats, timestamp from waitlist where faculty = ?");
            WaitlistByFaculty.setString(1, faculty);
            resultSet = WaitlistByFaculty.executeQuery();
            
            while(resultSet.next()) {
                
                WaitlistEntry entry = new WaitlistEntry(faculty, resultSet.getDate(1), resultSet.getInt(2), resultSet.getTimestamp(3));
                waitlistBF.add(entry);
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return waitlistBF;
        
    }
     
    public static void cancelWaitlistEntry(String faculty, Date date) {
        
        connection = DBConnection.getConnection();
        
        try {
            
            cancelWaitlist = connection.prepareStatement("delete from waitlist where faculty = ? and date = ?");
            cancelWaitlist.setString(1, faculty);
            cancelWaitlist.setDate(2, date);
            cancelWaitlist.executeUpdate();
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
    }
    
    public static ArrayList<WaitlistEntry> getWaitlistByDate(Date date) {
        
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlistBD = new ArrayList<>();
        
        try {
            
            WaitlistByDate = connection.prepareStatement("select faculty, seats, timestamp from waitlist where date = ?");
            WaitlistByDate.setDate(1, date);
            resultSet = WaitlistByDate.executeQuery();
            
            while(resultSet.next()) {
                
                WaitlistEntry entry = new WaitlistEntry(resultSet.getString(1), date, resultSet.getInt(2), resultSet.getTimestamp(3));
                waitlistBD.add(entry);
                
            }
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return waitlistBD;
        
    }
    
}
