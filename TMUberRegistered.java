import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws IOException {
        ArrayList<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get(filename)))){
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                String address = scanner.nextLine().trim();
                double wallet = Double.parseDouble(scanner.nextLine().trim());
                users.add(new User(generateUserAccountId(users), name, address, wallet));
            }
        }
        return users;
    }
    
    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws IOException {
        ArrayList<Driver> drivers = new ArrayList<>();
        try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get(filename)))){
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                String carModel = scanner.nextLine().trim();
                String licensePlate = scanner.nextLine().trim();
                String address = scanner.nextLine().trim();
                drivers.add(new Driver(generateDriverId(drivers), name, carModel, licensePlate, address));
            }
        }
        return drivers;
    } 
}

