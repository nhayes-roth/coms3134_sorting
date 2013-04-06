/* Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Course: COMSW3134
 * Assignment 3: Sorting
 * File Description: Sort.java sorts a database of People objects alphabetically.
    -- Keep track of each comparison of Persons, and all swaps
         (swaps should be counted for each Person object set to a value during 
         a sort; thus, if a swap sets a temp Person to a, then a to b, and
         b to temp, this counts as 3 swaps).
    -- Your main program, Sort, should use either quicksort or heapSort based on 
       the command line argument
    -- Either print the sorted array or the statistics, also based on the command
       line argument.
 */

package Sorting;
import io.*;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class Sort{   
  public static String sortType;
  public static String printType;
  private static String[] sortTypes = {"h", "heap", "heapSort", "q", "quick", "quicksort"};
  private static String[] printTypes = {"s", "stat", "stats", "p", "print"};
  private static final int number = 1000;
  private static int comparisons = 0;
  private static int swaps = 0;
  private static Person[] sorted;

  
  /* checkArgs()
   * checks a command line argument to make sure it makes sense 
   * returns true if the argument works
   * returns false if the argument doesn't work
   */
  public static boolean checkArgs(String argument, String[] ok){
    for (int i = 0; i<ok.length; i++){
      if (argument.equals(ok[i]) || argument.equals("-" + ok[i])){
        return true;
      }
    }
      return false;
  }

  /* fileToString()
   * reads a text file and creates a very long string
   */
  public static String fileToString(){
    String toReturn = "";
    int count = 0;
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      StringBuilder builder = new StringBuilder();
      while (count < number) {
        String line = in.readLine();
        builder.append(line);
        builder.append("\n");
        count++;
      }
      in.close();
      toReturn = builder.toString();
    }
    catch (Exception e){
      IO.stderr.println("Error: " + e);
    }
    return toReturn;
  }
  
  /* stringToStringArray()
   * converts a long string into an array of strings, split at \n
   */
  public static String[] toList(String toSplit){
        return MakeNames.toList(toSplit);
  }
  
  /* stringArrayToPersonArray()
   * converts an array of Strings into an array of Persons
   */
  public static Person[] stringArrayToPersonArray(String[] lines){
    Person[] toReturn = new Person[lines.length];
    for (int i = 0; i<lines.length; i++){
      String[] person = lines[i].split(", ");
      String first = person[0];
      String last = person[1];
      int code = Integer.parseInt(person[2]);
      Person temp = new Person(first, last, code);
      toReturn[i] = temp;
    }
    return toReturn;
  }
  
  /* sort()
   * Helper method that calls either quicksort or heapSort based on the command 
   * line argument sortType 
   */
  public static void sort(Person[] toSort, String sortType){
      if (sortType.equals("-h") || sortType.equals("h") || sortType.equals("heap") || sortType.equals("-heap")){
          heapSort(toSort);
      } else {
          quickSort(toSort, 0, toSort.length - 1);
      }
  }
  
  /* quicksort()
   * administers the quicksort algorithm
   */
  public static void quickSort(Person[] toSort, int low, int high){
      int index = partition(toSort, low, high);
      if (low < index - 1)
            quickSort(toSort, low, index - 1);
      if (index < high)
            quickSort(toSort, index, high);
  }
  
  /* partition()
   * divides the Person[] into sections such that they are alphabetically
   * separated on either side of the pivot Person
   */
  public static int partition(Person[] toSort, int low, int high){
      Person temp;
      Person pivot = toSort[(low + high) / 2];
      while (low <= high) {
          while (toSort[low].compareTo(pivot)<0){
                  low++;
                  comparisons++;
          }
          while (toSort[high].compareTo(pivot)>0){
                  high--;
                  comparisons++;
          }
          if (low <= high) {
                  temp = toSort[low];
                  swaps++;
                  toSort[low] = toSort[high];
                  swaps++;
                  toSort[high] = temp;
                  swaps++;
                  low++;
                  high--;
            }
      }
      return low;
  }
  
  /* heapSort()
   * administers the heapSort algorithm 
   */
  public static void heapSort(Person[] toSort){
      sorted = toSort;
      int k = sorted.length-1;
      toHeap(sorted, k);
      for(int i=k;i>0;i--){
          exchange(0, i);
          k=k-1;
          build(sorted, 0, k);
      }
  }
  
  /* toHeap()
   * calls build to build a heap of the remaining elements
   */
  public static void toHeap(Person[] sorted, int k){
        for(int i=k/2; i>=0 ;i--){
            build(sorted, i, k);
        }
  }
  
  /* build()
   * creates a max heap out of an unsorted array of elements
   */
  public static void build(Person[] sorted, int i, int k){
      int heapIndex; 
      int low = 2*i;
       int high = 2*i + 1;
        if(low <= k && sorted[low].compareTo(sorted[i])>0){
            heapIndex = low;
            comparisons++;
        }else{
            heapIndex = i;
            comparisons++;
        }
        if(high <= k && sorted[high].compareTo(sorted[heapIndex])>0){
            heapIndex = high;
            comparisons++;
        }
        if(heapIndex! = i){
            exchange(i, heapIndex);
            build(sorted, heapIndex, k);
        }
    }
  
  /* exchange()
   * handles heapSort's swaps of Person elements in the sorted array
   */
  public static void exchange(int i, int j){
        Person t = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = t; 
        swaps = swaps + 3;
        }
              
  
  /* print()
   * Either print the sorted array or the statistics, also based on the command 
   * line argument.
   */
  public static void print(Person [] sorted, String printType){
      if (printType.equals("-p") || printType.equals("p") || 
          printType.equals("print") || printType.equals("-print")){
          for(int i=0; i < sorted.length;i++){
              IO.stdout.println(sorted[i]);
          }
      }else{
          IO.stdout.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
      }
      comparisons = 0;
      swaps = 0;
  }
   
      public static void main(String [] args){
        sortType = args[0];
        printType = args[1];
        
        if (!checkArgs(args[0], sortTypes) || !checkArgs(args[1], printTypes)){
          IO.stderr.println("I'm sorry, I couldn't understand those arguments."
                              + "\n\t(h for heapSort, q for quickSort)"
                              + "\n\t(p to print list, s to see stats)");
        }
        else{
          // read into Person[]
          String input = fileToString();
          String[] array = toList(input);
          Person[] toSort = stringArrayToPersonArray(array);
          // sort
          sort(toSort, sortType);
          
          // print
          print(toSort, printType);
        }
      }
}