/**
 * @author Brian LeProwse
 *  
 *  
 *  This program reads user entered strings from the console and a file. If 
 *  the string is 8 bits in length and is in binary, it will convert the binary 
 *  string to its decimal value.
 *  
 */ 
 
import java.util.*;
import java.io.*;
import java.util.ArrayList;
public class BinaryToDecimal {
	// ints for number of strings entered and decimal calculations 
	private int numberOfStrings, base, power, posValue, decimalValue;
	// strings for user and file input, program description, and output
	private String num, fileName, outFile, description, validOutput, invalidOutput;
	private String[] outputArray, outputFileArray;	// output messages stored in array	
	private Scanner in;				// console and file input
	private boolean confirm;			// confirm correct input file name
	private File inputFile;				// input file of strings
	private FileWriter write;			// write to file
	private PrintWriter writeFile;			// output to file					
	private ArrayList<String> stringsInFile;	// add strings in file here

/**
 * default constructor
 */
public BinaryToDecimal() {
	// initialize scanner
	in = new Scanner(System.in); 
	 
}

/**
 * outputs program description to console and moves to processUserNumbers()
 */
public void printInfo() {
	// description of program in one string
	description = "This program reads user entered strings from " +
				 "the console and a file. If the string is 8 bits in " +
				 "length and is in binary, it will convert the binary string " +
				 "to decimal value.";
		
	// output to console
	System.out.println(description);
		
	// process console entered numbers method
	processUserNumbers();
}
/**
 * prompts user for strings to enter and sends those strings to following methods
 * for validation and evaluation. Valid and invalid strings are added to a String 
 * array for message output.
 * 
 */

public void processUserNumbers() {
	// prompt user for number of strings to be evaluated 
	System.out.println("\nEnter number of strings to be evaluated: ");
	
	numberOfStrings = in.nextInt();		// set number of strings
				
	in.nextLine();						// skip line
	
	// set output message array to number of strings entered
	outputArray = new String[numberOfStrings];
		
	for (int i = 0; i < numberOfStrings; i++) {
			
		// prompt user to enter next string
		System.out.print("Enter next string: " + "\n>>> ");
		
		num = in.nextLine();				// store user entered string
		
		// if return is true, calculate decimal value in evaluateNumber()
		if (validateNumber(num) == true) {
			evaluateNumber(num);
				
			// valid output message
			validOutput = "\nEntered string: "  + num +
				      	  "\nStatus: " + "valid" +
					      "\nDecimal value: " + " " + decimalValue;
				
			// add valid output string to array
			outputArray[i] = validOutput;
				
		} else {
			// invalid output message 
			invalidOutput = "\nEntered string: " + num +
							"\nStatus: " + " invalid";
				
			// add invalid output string to array
			outputArray[i] = invalidOutput;
		}	
	}
	
	outputInfoConsole();		// method outputs summary info to console
}

/**
 * confirms if string number is length of 8 and contains either 1s or 0s
 * only. 
 * @param test - string passed from processUserNumbers()
 * @return - returns true if string passes criteria, false if not.
 */
public boolean validateNumber(String num) {
	int count = 0;				 // count of 0s or 1s in string
								
	if (num.length() != 8) {	 // check that string length is 8
		return false;
	}
	// while count is less than 8, count occurrences of 1s or 0s
	// if string at charAt(count) fails, return false.
	while (count < 8) {
		if (num.charAt(count) != '0' && num.charAt(count) != '1') 
			return false;
			count++;
	}
			return true;
}

/**
 * calculates the decimal value of the user entered string using the base 2
 * system. Calculations begin at character 0 of the string and move left to
 * right. 
 * @param num - string to be converted to decimal
 */
public void evaluateNumber(String num) {
	base = 2;			// set base to 2
	power = 7;			// set power to 7
	decimalValue = 0;	// set and reset decimalValue to 0 each time method called
	
	//loop through each char in string and calculate decimal value
	for(int i = 0; i  < 8; i++) {
			
		if(num.charAt(i) == '0') {		// check if a 0 is ith position
			power--;						// decrement power for calculations
		} else {
			// posValue holds value of the base raised to the ith power, cast to an int
			posValue = (int)Math.pow(base, power); 	
			decimalValue += posValue;	// add calculated value to get decimal value
			power--; 					// decrement power for calculations 
		}
	}	
}

/**
 * reads and processes strings from file and sends them to above methods for
 * evaluation and/or validation.
 */
public void processFileNumber() {
	int count = 0;								// count of strings in file
	stringsInFile = new ArrayList<String>();		// instantiate ArrayList
	outFile = new String("Output.txt");			// instantiate output file name
	
	try {			
		// while false, prompt use to enter file name, if file name cannot be found, 
		// prompt user to re-enter. 
		while(confirm == false) {
			System.out.println("\nEnter file name: ");
			fileName = in.next(); 				// string of file name
			inputFile = new File(fileName);		// pass file name to new File
				
			write = new FileWriter(outFile);		// new FileWriter with output file name
			writeFile = new PrintWriter(write);	// pass FileWriter to new PrintWriter
			
			// if file can't be found, prompt to try again
			if(!inputFile.exists()) {
				System.out.println("File not found! Try again. ");
			} else {
				in = new Scanner(inputFile);		// scan file
				confirm = true;					// end loop on correct file name
				
			}
		}
		// count number of strings in the file and add them to ArrayList
		while(in.hasNextLine()) {
			num = in.nextLine();			// store string from each line in file
			stringsInFile.add(num);		// add string to ArrayList
			count++;						// increment counter
		}
		// create output array to number of strings within file
		outputFileArray = new String[count];		
		
		for(int i = 0; i < count; i++) {
			
			// if return true, calculate decimal value of string in ith position
			if(validateNumber(stringsInFile.get(i))) {	 
			   evaluateNumber(stringsInFile.get(i));
				
			   // valid output message. stringsInFile.get(i) is string in ArrayList
			   // at ith position 
			   validOutput = "\nEntered string: "  + stringsInFile.get(i) +
						 	"\nStatus: " + "valid" +
						 	"\nDecimal value: " + " " + decimalValue;
			
			outputFileArray[i] = validOutput;			// add valid output to array
			
			} else {
				// invalid output message. stringsInFile.get(i) is string in ArrayList
				// at ith position 
				invalidOutput = "\nEntered string: " + stringsInFile.get(i) +
								"\nStatus:" + " invalid";
				
				outputFileArray[i] = invalidOutput;	// add invalid output to array
			}
		}	
		
	} catch (Exception FileNotFound) {		// catch FileNotFound Exception
		FileNotFound.printStackTrace();		// print detailed exception info
	}
	
	outputInfoToFile();			// output summary information to file
	in.close();					// close Scanner
	writeFile.close();			// close PrintWriter
	
}

/**
 * method outputs summary info contained in array to console
 */
public void outputInfoConsole() {
	
	for(String s: outputArray) {
		System.out.println(s);	
	}		
}

/**
 * method prints summary info contained in array to file
 */
public void outputInfoToFile() {
	// name header, project, and date header
	writeFile.println("Brian LeProwse" + 
					  "\nBinary Numbers");
	
	for(String s: outputFileArray) {
		writeFile.println(s);	
	}
}

/**
 * instantiates BinaryToDecimal class. Calls printInfo() and 
 * processfileNumbers().
 * @param args
 * @throws FileNotFoundException 
 * 
 */
public static void main(String[] args) throws FileNotFoundException {
	BinaryToDecimal convert = new BinaryToDecimal();
	convert.printInfo();				// process strings from user
	
	// ******* method call to read strings from file commented *******
	convert.processFileNumber();		// process strings from file
	
	}
}
