/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roomschedulermatthewwan926546922;

/**
 *
 * @author mzwonton
 */
public class RoomEntry {
    
    private String name;
    private int seats;
      
    public RoomEntry(String name, int seats) {
        
        this.name = name;
        this.seats = seats;
        
    }
    
    public String getName() {
        
        return this.name;
        
    }    
    
    public int getSeats() {
        
        return this.seats;
        
    }
    
}
