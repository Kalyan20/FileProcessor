package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try( BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            String input = "";
            String fileName = "";
            String fileLocation = "";
            String equation = "";
            while (!"q".equalsIgnoreCase(input)) {

                System.out.print("Enter File Name : ");
                fileName = br.readLine();
                System.out.println("Entered File Name : " + fileName);

                System.out.print("Enter File Location : ");
                fileLocation = br.readLine();
                System.out.println("Entered File Location : " + fileLocation);
                //parsing a CSV file into Scanner class constructor
                File file = new File(fileLocation +"/" + fileName);
                Scanner sc = new Scanner(file);
                sc.useDelimiter(",");   //sets the delimiter pattern
                while (sc.hasNext())  //returns a boolean value
                {
                    System.out.print(sc.next());  //find and returns the next complete token from this scanner
                }
                sc.close();  //closes the scanner
                System.out.print("Continue  : ");
                input = br.readLine();
        }

            System.out.println("bye bye!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           /* if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}	// write your code here

