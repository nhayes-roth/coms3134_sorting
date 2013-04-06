/* Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Course: COMSW3134
 * Assignment 3: Sorting
 * File Description: MakeNames.java takes two text files 
 * (firstNames and lastNames) and writes a list of 1000 people, 
 * each with a randomly selected first name and last name, 
 * but each with a unique, four-digit identification code. 
 */
 
package Sorting;
import io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;


public class MakeNames{   
    private static final int firstID = 1000;
    private static final int number = 1000;
    public static Person[] people = new Person[number];
    public static String firstNamesFile;
    public static String lastNamesFile;
    
    /* fileToString()
     * read a file into a String
     */
    public static String fileToString(String first){
        String toReturn = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(first));
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            in.close();
            toReturn = builder.toString();
        }
        catch (Exception e){
            IO.stderr.println("Error: " + e);
        }
        return toReturn;
    }
    
    /* toList()
     * converts a String into a String[]
     */
    public static String[] toList(String toSplit){
        return toSplit.split("\n");
    }
    
    /* makePeople()
     * builds a list of "number" of people from two arrays of names 
     */
    public static void makePeople(String[] firstNames, String[] lastNames){
        int ID = firstID;
        while(ID < firstID + number){
            Random randomGenerator = new Random();
            int f = randomGenerator.nextInt(firstNames.length);
            int l = randomGenerator.nextInt(lastNames.length);
            people[ID - firstID] = new Person(firstNames[f],
                                              lastNames[l],
                                              ID);
            ID++;;
        }
    }
    
    
    /* printPeople()
     * prints anarray of people
     */
    public static void printPeople(){
        for (int i = 0; i<people.length; i++){
            IO.stdout.println(people[i].toString());
        }
    }
    
    /* checkArgs()
     * appends ".txt" to the filename argument if the string ends with 
     * something else
     */
    public static String checkArgs(String fileName){
        int i = fileName.length()-4;
        
        if (!fileName.substring(i).equals(".txt")){
            return fileName + ".txt";
        }
        return fileName;
    }
    
    public static void main(String [] args){
        firstNamesFile = checkArgs(args[0]);
        lastNamesFile = checkArgs(args[1]);
        String[] firstNames = toList(fileToString(firstNamesFile));
        String[] lastNames = toList(fileToString(lastNamesFile));
        makePeople(firstNames, lastNames);
        printPeople();
        
    }
}