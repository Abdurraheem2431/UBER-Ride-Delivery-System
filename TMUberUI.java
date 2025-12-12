import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;


// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      else if (action.equalsIgnoreCase("LOADUSERS")) 
      {
        String name = "";
        System.out.print("File name: ");
        if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
        File file = new File(name);
        if (file.exists() == false){
          try {
            ArrayList<User> users = (ArrayList<User>) TMUberRegistered.loadPreregisteredUsers(name);
            tmuber.setUsers(users);
            System.out.println("Successfully Loaded");
          } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
          }
        } else {
          System.out.println("File not found");
        }
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        try {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address);
        } 
        catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }

      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        try {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
          }
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        } 
        catch (IllegalArgumentException e){
          System.out.println(e.getMessage());
        }
  
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)

        try {
          String userID = "";
          String fAddress = "";
          String tAddress = "";

          System.out.print("User Account Id: ");
          if (scanner.hasNextLine()){
            userID = scanner.nextLine();
          }

          System.out.print("From Address: ");
          if (scanner.hasNextLine()){
            fAddress = scanner.nextLine();
          }
          
          System.out.print("To Address: ");
          if (scanner.hasNextLine()){
            tAddress = scanner.nextLine();
          }
          
          tmuber.requestRide(userID,fAddress,tAddress);
        }
        catch (IllegalArgumentException e){
          System.out.println(e.getMessage());
        }
        
      }
      else if (action.equalsIgnoreCase("PICKUP"))
      {
        try {
          String id = "";
          System.out.print("Driver ID: ");
        if (scanner.hasNextLine()){
          id = scanner.nextLine();
        }
        tmuber.pickup(id);
        } catch (IllegalArgumentException e){
          System.out.println(e.getMessage());
        }
      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        try {
          String userID = "";
        String fAddress = "";
        String tAddress = "";
        String restaurant = "";
        String foodOrderID = "";

        System.out.print("User Account Id: ");
        if (scanner.hasNextLine()){
          userID = scanner.nextLine();
        }

        System.out.print("From Address: ");
        if (scanner.hasNextLine()){
          fAddress = scanner.nextLine();
        }

        System.out.print("To Address: ");
        if (scanner.hasNextLine()){
          tAddress = scanner.nextLine();
        }

        System.out.print("Restaurant: ");
        if (scanner.hasNextLine()){
          restaurant = scanner.nextLine();
        }

        System.out.print("Food Order ID: ");
        if (scanner.hasNextLine()){
          foodOrderID = scanner.nextLine();
        }

        tmuber.requestDelivery(userID, fAddress, tAddress, restaurant, foodOrderID);
        }
        catch (IllegalArgumentException e){
          System.out.println(e.getMessage());
        }
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) 
      {
        tmuber.sortByDistance();   
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        try {
          int request = -1;
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); // consume nl character
          }
          tmuber.cancelServiceRequest(request);
          System.out.println("Service request #" + request + " cancelled");
        }
        catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
        
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        try {
          String driverID = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine())
          {
              driverID = scanner.nextLine();
          }

          tmuber.dropOff(driverID);
          System.out.println("Driver + " + driverID + " is now dropping off");
        }
        
        catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
        
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      else if (action.equalsIgnoreCase("DRIVETO"))  
      {
        try {
          // Prompt the user to enter the driver ID and address
          String driverId = "";
          System.out.print("Enter Driver ID: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }
          String newAddress = "";
          System.out.print("Enter new address: ");
          if (scanner.hasNextLine())
          {
            newAddress = scanner.nextLine();
          }
  
          // Call the driveTo method with the provided information
          tmuber.driveTo(driverId, newAddress);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
      }
      
      //else if (action.equalsIgnoreCase("LOADUSERS"))  
      //{
        //tmuber.listAllUsers(); 
      //}
      
      
      System.out.print("\n>");
    }
    scanner.close();
  }
}

