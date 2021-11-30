/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roomschedulermatthewwan926546922;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mzwonton
 */
public class DBConnection {

    private static Connection connection;
    private static final String user = "java";
    private static final String password = "java";
    private static final String database = "jdbc:derby://localhost:1527/RoomSchedulerDBMatthewWan926546922";

    public static Connection getConnection() {
        
        if (connection == null) {
            
            try {
                
                connection = DriverManager.getConnection(database, user, password);
                
            } 
            
            catch (SQLException e) {
                
                e.printStackTrace();
                System.out.println("Could not open database.");
                System.exit(1);

            }
            
        }
        
        return connection;
        
    }

}
