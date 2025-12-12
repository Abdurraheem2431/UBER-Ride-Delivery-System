import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private ArrayList<User>   users;
  private ArrayList<Driver> drivers;

  private ArrayList<TMUberService> serviceRequests; 
  
  private Queue<TMUberService>[] que;

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 9000;
  int driverId = 7000;

  public TMUberSystemManager()
  {
    users   = new ArrayList<User>();
    drivers = new ArrayList<Driver>();
    serviceRequests = new ArrayList<>(); 
  
    totalRevenue = 0;

    que = new Queue[4]; // Initialize with 4 queues
        for (int i = 0; i < 4; i++) {
            que[i] = new ArrayDeque<>();
        }
  }

  public void addServiceRequest(TMUberService service, int zone) {
    if (zone >= 0 && zone <= 3) {
      que[zone].offer(service); 
    } else {
      System.out.println("Invalid zone number: " + zone);
  }
}

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  String errMsg = null;

  public String getErrorMessage()
  {
    return errMsg;
  }
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    for (int i = 0; i < users.size();i++){
      if (users.get(i).getAccountId().equals(accountId)){
        return users.get(i);
      }
    }
    // Fill in the code
    return null;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    for (int i = 0; i < users.size();i++){
      if (users.get(i).equals(user)){
        return true;
      }
    }
    // Fill in the code
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
  for (int i = 0; i < drivers.size();i++){
    if (drivers.get(i).equals(driver)){
      return true;
    }
  }
   // Fill in the code
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    for (Queue<TMUberService> queue : que) {
      if (queue.contains(req)) {
          return true;
      }
  }
  return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    for (int i = 0; i < drivers.size();i++){
      Driver driver = drivers.get(i);
      if (driver.getStatus() == Driver.Status.AVAILABLE){
        return driver;
      }
    }
    // Fill in the code
    return null;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    
    for (int i = 0; i < users.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      users.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    // Fill in the code
    System.out.println();
    
    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    System.out.println("All Current Service Requests:");
        for (int i = 0; i < 4; i++) {
            Queue<TMUberService> queue = que[i];
            System.out.println("");
            System.out.println("Zone " + (i) + ":");
            System.out.println("");
            if (queue.isEmpty()) {
                System.out.println("No service requests in this zone.");
            } else {
                int index = 1;
                for (TMUberService service : queue) {
                    System.out.printf("%d. ", index++);
                    service.printInfo();
                    System.out.println();

                }
            }
        }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply
    // Write the code like (for example):
    // if (address is *not* valid)
    // {
    //    set errMsg string variable to "Invalid Address "
    //    return false
    // }
    // If all parameter checks pass then create and add new user to array list users
    // Make sure you check if this user doesn't already exist!
    
    if (name.equals("") || name == null){
      throw new IllegalArgumentException("Invalid user Name"); 
    }
    
    if (CityMap.validAddress(address) == false){
      throw new IllegalArgumentException("Invalid user Address"); 
    }

    if (wallet < 0){
      throw new IllegalArgumentException("Invalid money in wallet"); 
    }

    User user = new User(Integer.toString(userAccountId + users.size()), name, address, wallet);

    if (userExists(user)){
      throw new IllegalArgumentException("User already exists in system"); 
    }

    users.add(user);
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser
    if (name.equals("") || name == null){
      throw new IllegalArgumentException("Invalid user Name"); 
    }

    if (carModel.equals("") || carModel == null){
      throw new IllegalArgumentException("Invalid car model"); 
    }
    
    if (carLicencePlate.equals("") || carLicencePlate == null){
      throw new IllegalArgumentException("Invalid license plate"); 
    }

    if (!CityMap.validAddress(address)){
      throw new IllegalArgumentException("Invalid address"); 
    }

    Driver driver = new Driver(Integer.toString(driverId + drivers.size()), name, carModel, carLicencePlate, address);

    if (driverExists(driver)){
      throw new IllegalArgumentException("User already exists in system"); 
    }

    drivers.add(driver);
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Check for valid parameters
	  // Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user

    User user = getUser(accountId);


    if (accountId == "" || accountId == null || user == null){
      throw new IllegalArgumentException("User account not found"); 
    }
    
    if (CityMap.validAddress(from) == false || CityMap.validAddress(to) == false){
      throw new IllegalArgumentException("Invalid address"); 
    }

    int distance = CityMap.getDistance(from, to);
    if (distance < 1){
      throw new IllegalArgumentException("Insufficient Travel Distance"); 
    }

    Driver driver = getAvailableDriver();
    if (driver == null){
      throw new IllegalArgumentException("No drivers available");
    }

    double deliveryCost = getRideCost(distance);
    if (user.getWallet() < deliveryCost) {
        throw new IllegalArgumentException("Insufficient Funds");
    }

    int zone = CityMap.getCityZone(from);
    TMUberRide ride = new TMUberRide(driver, from, to, getUser(accountId), distance, getRideCost(distance), "RIDE");

    if (existingRequest(ride)){
      throw new IllegalArgumentException("User already has ride request");
    }

    getUser(accountId).addRide();
    driver.setStatus(Driver.Status.DRIVING);
    addServiceRequest(ride, zone);
    user.addRide();

    System.out.println("\nRIDE for: " + user.getName() + "     From: " + from + "     To: " + to);

  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had

    User user = getUser(accountId);

    if (accountId == "" || accountId == null || user == null) {
      throw new IllegalArgumentException("User account not found");
    }
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) {
        throw new IllegalArgumentException("Invalid Address");
    }
    int distance = CityMap.getDistance(from, to);
    if (distance <= 1) {
        throw new IllegalArgumentException("Insufficient Travel Distance");
    }
    Driver driver = getAvailableDriver();
    if (driver == null) {
        throw new IllegalArgumentException("No drivers available");
    }
    double deliveryCost = distance * 1.2; 
    if (user.getWallet() < deliveryCost) {
        throw new IllegalArgumentException("Insufficient Funds");
    }
    TMUberDelivery delivery = new TMUberDelivery(driver, from, to, user, distance, deliveryCost, restaurant, foodOrderId, "DELIVERY");
    int zone = CityMap.getCityZone(from);

    if (existingRequest(delivery)) {
        throw new IllegalArgumentException("User Already Has Delivery Request at Restaurant with this Food Order");
    }

    System.out.println("\nDELIVERY for: " + user.getName() + "     From: " + from + "     To: " + to);

    getUser(accountId).addDelivery();
    addServiceRequest(delivery, zone);
    driver.setStatus(Driver.Status.DRIVING);
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    request--;

    if (serviceRequests.isEmpty()){
       throw new IllegalArgumentException("No active requests");
    }
    if (request < 0 || serviceRequests.size() <= request){
      throw new IllegalArgumentException("Invalid request number");
    }

    TMUberService cancel = serviceRequests.get(request);
    User user = cancel.getUser();
    serviceRequests.remove(request);

    if (cancel instanceof TMUberRide){
      user.minRide();
    } else {
      user.minDelivery();
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String id)
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user
    Driver driver = null;
    for (Driver d : drivers) {
        if (d.getId().equals(id)) {
            driver = d;
            break;
        }
    }

    // Check if driver is found and is driving
    if (driver == null) {
        throw new IllegalArgumentException("Driver not found");
    } else if (driver.getStatus() != Driver.Status.DRIVING) {
        throw new IllegalArgumentException("Driver is not driving");
    }

    // Get the TMUberService reference from the driver
    TMUberService service = driver.getService();
    if (service == null) {
        throw new IllegalArgumentException("Driver has no ongoing service");
    }

    // Get the cost for the service and add to total revenues
    double price = service.getCost();
    totalRevenue += price;

    // Pay the driver
    double fee = price * PAYRATE;
    driver.getMoney(fee);

    // Deduct driver fee from total revenues
    totalRevenue -= fee;

    // Change driver status back to AVAILABLE
    driver.setStatus(Driver.Status.AVAILABLE);

    // Set the reference to the TMUberService object inside the Driver object to null
    driver.setService(null);

    // Set the new driver address to the drop-off address and update the driver zone
    String drop = service.getTo();
    driver.setAddress(drop);
    int zone = CityMap.getCityZone(drop);
    driver.setZone(zone);

    // Deduct cost of service from user
    User user = service.getUser();
    user.payForService(price);

  }

  public void pickup(String driverID){

    Driver driver = null;
    for (Driver d : drivers) {
        if (d.getId().equals(driverID)) {
            driver = d;
            break;
        }
    }

    if (driver == null) {
        throw new IllegalArgumentException("Driver not found");
    }


    int zone = CityMap.getCityZone(driver.getAddress());

    if (zone < 0 || zone >= que.length){
      throw new IllegalArgumentException("\nInvalid address, no zone");
    }

    Queue<TMUberService> queue = que[zone];
    if (queue.isEmpty()) {
        throw new IllegalArgumentException("No service requests in this zone");
    }

    TMUberService service = queue.poll();
    driver.setService(service);
    driver.setStatus(Driver.Status.DRIVING);
    driver.setAddress(service.getFrom());

    System.out.println("complete");
  }
  public void driveTo(String driverId, String address) {
    
    Driver driver = null;
    for (Driver d : drivers) {
        if (d.getId().equals(driverId)) {
            driver = d;
            break;
        }
    }

    if (driver == null) {
        throw new IllegalArgumentException("Driver not found");
    }
    if (!CityMap.validAddress(address)) {
        throw new IllegalArgumentException("Invalid address");
    }
    if (driver.getStatus() != Driver.Status.AVAILABLE) {
        throw new IllegalStateException("Driver is not available");
    }
    driver.setAddress(address);
    System.out.println(driver.getAddress());
    // Check if the new address triggers a zone change
    int newZone = CityMap.getCityZone(address);
    if (newZone != CityMap.getCityZone(driver.getAddress())) {
        // If the zone changes, update the driver's current zone
        driver.setZone(newZone);
    }
    }

  // Sort users by name
  // Then list all users
  public void sortByUserName()
{
    Collections.sort(users, new NameComparator());
    listAllUsers();
}

// Helper class for method sortByUserName
private class NameComparator implements Comparator<User>
{
    public int compare(User one, User two) {
        return one.getName().compareTo(two.getName());
    }
}

  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {
    Collections.sort(users, new UserWalletComparator());
    listAllUsers();
}

// Helper class for use by sortByWallet
private class UserWalletComparator implements Comparator<User>
{
    public int compare(User one, User two) {
        return Double.compare(one.getWallet(), two.getWallet());
    }
}

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    Collections.sort(serviceRequests, new DistanceComparator());
    listAllServiceRequests();
}

// Helper class for sorting by distance
private class DistanceComparator implements Comparator<TMUberService>
{
    public int compare(TMUberService one, TMUberService two) {
        return Integer.compare(one.getDistance(), two.getDistance());
    }
}
}
