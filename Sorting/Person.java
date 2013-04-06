/* Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Course: COMSW3134
 * Assignment 3: Sorting
 * File Description: Person.java defines a class of objects that represent persons
 */
 
package Sorting;
import io.*;

public class Person implements Comparable<Person>{
    private String first;
    private String last;
    private int code;
    
    public final static Person NIL = new Person("", "");
    
    /* constructor */
    public Person(String f, String l, int c){
        first = f;
        last = l;
        code = c;
    }
    
    public Person(String f, String l){
        first = f;
        last = l;
        code = 1000;
    }
    
    /* getters */
    public String getFirst(){
        return first;
    }
    
    public String getLast(){
        return last;
    }
    
    public int getCode(){
        return code;
    }
    
    /* setters */
    public void setFirst(String str){
        first = str;
    }
    
    public void setLast(String str){
        last = str;
    }
    
    public void setCode(int i){
        code = i;
    }
    
    /* compareTo (returns negative if this<that, positive if this>that)
     * 1st - alphabetically by last name
     * 2nd - if equal, by first name
     * 3rd - id number to resolve identical names */
    public int compareTo(Person that){
        int c = this.getLast().compareToIgnoreCase(that.getLast());
        if (c != 0){
            return c;
        }
        else {
            c = this.getFirst().compareToIgnoreCase(that.getFirst());
            if (c != 0){
                return c;
            }
            else{
                if (this.getCode() == that.getCode()){
                    c = 0;
                }
                if (this.getCode() < that.getCode()){
                    c = -1;
                }
                if (this.getCode() > that.getCode()){
                    c = 1;
                }
            }
        }
        return c;
    }
    
    public String toString(){
        return last + ", " + first + ", " + code;
    }
    
    public void print(){
        IO.stdout.println(this.toString());
    }
    
    public static void main(String [] args){
    }
}