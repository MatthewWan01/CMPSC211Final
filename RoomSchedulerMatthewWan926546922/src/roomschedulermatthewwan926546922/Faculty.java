/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roomschedulermatthewwan926546922;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author mzwonton
 */
public class Faculty {
    
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addFaculty;
    private static PreparedStatement getFacultyList;
    
    public static void addFaculty(String name) {
        
        connection = DBConnection.getConnection();
        
        try {
            
            addFaculty = connection.prepareStatement("insert into faculty (name) values (?)");
            addFaculty.setString(1, name);
            addFaculty.executeUpdate();
            
        }
        
        catch(SQLException sqlException) {
            
           
            sqlException.printStackTrace();
            
        }
        
    }
    
    public static ArrayList<String> getFacultyList() {
        
        connection = DBConnection.getConnection();
        ArrayList<String> faculty = new ArrayList<String>();
        
        try {
            
            getFacultyList = connection.prepareStatement("select name from faculty");
            resultSet = getFacultyList.executeQuery();
            
            while(resultSet.next()) {
                
                faculty.add(resultSet.getString(1));
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();   
            
        }
        
        return faculty;
        
    }
    
    
}
