import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    String[] parts = getParts(address);

    if (parts.length != 3){
      return false;
    }

    String resNum = parts[0];
    String nTH = parts[1];
    String StAve = parts[2];

    int resNum_check = Integer.parseInt(resNum);

    if (resNum.length() != 2 || resNum_check <= 0){
      return false;
    }

    if (Character.isDigit(nTH.charAt(0)) == false || !(nTH.substring(1).equals("st") || nTH.substring(1).equals("nd") || nTH.substring(1).equals("rd") || nTH.substring(1).equals("th"))) {
      return false;
  }

    if (!StAve.equalsIgnoreCase("avenue") && !StAve.equalsIgnoreCase("street")){
      return false;
    }

    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3
    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    if (validAddress(address) == false){
      return block;
    }

    String[] parts = getParts(address);
    String resNum = parts[0];
    String nth = parts[1];
    String StAve = parts[2];

    int ave = 0;
    int st = 0;

    if (StAve.equalsIgnoreCase("avenue")){
      ave = Character.getNumericValue(resNum.charAt(0));
      st = Character.getNumericValue(nth.charAt(0));
    } else {
      st = Character.getNumericValue(resNum.charAt(0));
      ave = Character.getNumericValue(nth.charAt(0));
    }

    block[0] = ave;
    block[1] = st;

    // Fill in the code
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    
    // Math.random() generates random number from 0.0 to 0.999
    // Hence, Math.random()*17 will be from 0.0 to 16.999
    // double doubleRandomNumber = Math.random() * 17;
    // cast the double to whole number
    // int randomNumber = (int)doubleRandomNumber;
    // return (randomNumber);

    int[] block1 = getCityBlock(from);
    int[] block2 = getCityBlock(to);

    int streetDiff = Math.abs(block2[1] - block1[1]);
    int aveDiff = Math.abs(block2[0] - block1[0]);

    int distance = streetDiff + aveDiff;

    return distance;
  }

  public static int getCityZone(String address){

    int zone = -1;
    
    if (!validAddress(address)) {
        return zone; 
    }

    int[] num = getCityBlock(address);

    int avenue = num[1];
    int street = num[0];

    if (avenue >= 1 && avenue <= 5 && street >= 6 && street <= 9) {
        zone = 0; 
    } else if (avenue >= 6 && avenue <= 9 && street >= 6 && street <= 9) {
        zone = 1;
    } else if (avenue >= 6 && avenue <= 9 && street >= 1 && street <= 5) {
        zone = 2; 
    } else if (avenue >= 1 && avenue <= 5 && street >= 1 && street <= 5) {
        zone = 3; 
    }
    
    return zone; 
  }
}


