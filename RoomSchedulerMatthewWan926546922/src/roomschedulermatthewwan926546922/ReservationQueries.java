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
public class ReservationQueries {
    
    private static ArrayList<RoomEntry> rooms;
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement getReservationsByDate;
    private static PreparedStatement Reservations;
    private static PreparedStatement getRooms;
    private static PreparedStatement getReservationsByRoom;
    private static PreparedStatement getReservationsByFaculty;
    private static PreparedStatement canceledReservations;
    private static PreparedStatement deleteReservations;
    private static ArrayList<ReservationEntry> reservation;
    
    public static ArrayList<ReservationEntry> getReservationsByDate(Date date) {
        
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservation = new ArrayList<>();
        
        try {
            
            getReservationsByDate = connection.prepareStatement("select faculty, room, seats, timestamp from reservations where date = ? order by timestamp");
            getReservationsByDate.setDate(1, date);
            resultSet = getReservationsByDate.executeQuery();
            
            while(resultSet.next()) {
                
                ReservationEntry reservations = new ReservationEntry(resultSet.getString(1), resultSet.getString(2), date, resultSet.getInt(3), resultSet.getTimestamp(4));
                reservation.add(reservations);
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return reservation;
        
    }
    
    public static ArrayList<RoomEntry> getRoomsReservedByDate(Date date) {
        
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = new ArrayList<>();
        System.out.println("DATE " + date);
        
        try {
            
            getRooms = connection.prepareStatement("select room, seats from reservations where date = ?");
            getRooms.setDate(1, date);
            resultSet = getRooms.executeQuery();
            
            while(resultSet.next()) {
                
                RoomEntry room = new RoomEntry(resultSet.getString(1), resultSet.getInt(2));
                rooms.add(room);
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return rooms;
        
    }
    
    public static ArrayList<ReservationEntry> getReservationsByRoom(String room) {
        
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservation = new ArrayList<>();
        
        try {
            
            getReservationsByRoom = connection.prepareStatement("select faculty, date, seats, timestamp from reservations where room = ?");
            getReservationsByRoom.setString(1, room);
            resultSet = getReservationsByRoom.executeQuery();
            
            while(resultSet.next()) {
                
                ReservationEntry reservations = new ReservationEntry(resultSet.getString(1), room, resultSet.getDate(2), resultSet.getInt(3), resultSet.getTimestamp(4));
                reservation.add(reservations);
                
            }
            
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return reservation;
        
    }
    
    public static boolean addReservationEntry(String faculty, Date date, int seats) {
        
        connection = DBConnection.getConnection();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        ArrayList<RoomEntry> roomlist = RoomQueries.getAllPossibleRooms();
        
        for (int i = 0; i < roomlist.size(); i++) {
            
            RoomEntry room = roomlist.get(i);
            
            if (room.getSeats() >= seats) {
                
                ArrayList<RoomEntry> roomsReserved = getRoomsReservedByDate(date);
                ArrayList<String> roomNames = new ArrayList<>();
                
                for(int j = 0; j < roomsReserved.size(); j++ ){
                    
                    roomNames.add(roomsReserved.get(j).getName());
                    
                }
                
                if ((roomsReserved.isEmpty()) || !roomNames.contains(room.getName())) {
                    
                    try {
                        
                        Reservations = connection.prepareStatement("insert into reservations (faculty, room, date, seats, timestamp) values (?,?,?,?,?)");
                        Reservations.setString(1, faculty);
                        Reservations.setString(2, room.getName());
                        Reservations.setDate(3, date);
                        Reservations.setInt(4, room.getSeats());
                        Reservations.setTimestamp(5, currentTimestamp);
                        Reservations.executeUpdate();
                        
                        return true;
                        
                    }
                    
                    catch(SQLException sqlException) {
                        
                        sqlException.printStackTrace();
                        
                    }
                    
                    return true;
                    
                }
                
            }
            
        }
        
        return false;
        
    }
    
    public static ArrayList<ReservationEntry> getReservationsByFaculty(String faculty) {
        
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservationbyF = new ArrayList<>();
        
        try {
            
            getReservationsByFaculty = connection.prepareStatement("select room, date, seats, timestamp from reservations where faculty = ?");
            getReservationsByFaculty.setString(1, faculty);
            resultSet = getReservationsByFaculty.executeQuery();
            
            while(resultSet.next()) {
                
                ReservationEntry reservations = new ReservationEntry(faculty, resultSet.getString(1), resultSet.getDate(2), resultSet.getInt(3), resultSet.getTimestamp(4));
                reservationbyF.add(reservations);
                
            }
        }
        
        catch(SQLException sqlException) {
            
            sqlException.printStackTrace();
            
        }
        
        return reservationbyF;
    }
    
    public static void cancelReservation(String faculty, Date date) {
        
        connection = DBConnection.getConnection();
        
        try {
            
            canceledReservations = connection.prepareStatement("delete from reservations where faculty = ? and date = ?");
            canceledReservations.setString(1, faculty);
            canceledReservations.setDate(2, date);
            canceledReservations.executeUpdate();
            
        }
        
        catch(SQLException sqlException) {
            
                sqlException.printStackTrace();
                
        }
        
    }
    
    public static void deleteReservation(String room) {
        
        connection = DBConnection.getConnection();
        
        try {
            
            deleteReservations = connection.prepareStatement("delete from reservations where room = ?");
            deleteReservations.setString(1, room);
            deleteReservations.executeUpdate();   
            
        }
        
        catch(SQLException sqlException) {
            
                sqlException.printStackTrace();
                
        }
        
    }
    
}
