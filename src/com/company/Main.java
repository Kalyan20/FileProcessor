package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String input = "";
        String fileName = "";
        String fileLocation = "";
        String equation = "";
        String line = "";
        try( BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {


            while (!"exit".equalsIgnoreCase(input)) {

                System.out.print("Enter File Name : ");
                fileName = br.readLine();
                System.out.println("Entered File Name : " + fileName);

                System.out.print("Enter File Location : ");
                fileLocation = br.readLine();
                System.out.println("Entered File Location : " + fileLocation);

                System.out.print("Enter compute equation : ");
                equation = br.readLine();
                System.out.println("Entered compute equation : " + equation);
                //parsing a CSV file
                File file = new File(fileLocation +"/" + fileName);
                BufferedReader br1 = new BufferedReader(new FileReader(file));
                List<Double> collectDeltaSquares = null;
                if((line = br1.readLine()) != null) {
                    collectDeltaSquares = new ArrayList<>();
                }
                while ((line = br1.readLine()) != null) {
                    // use comma as separator
                    String[] cols = line.split(",");
                    if(cols[0].equalsIgnoreCase("x") || cols[1].equalsIgnoreCase("Y")) {
                        continue;
                    }
                    int x = Integer.parseInt(cols[0]);
                    int y = Integer.parseInt(cols[1]);

                    float z = 0 ;
                    String firstSplitEquation;
                    String secondSplitEquation;
                    String operation;
                    // split equation string capture sepearated equations and operation
                    String[] equationSplit = equation.split(" ");
                    firstSplitEquation =  equationSplit[0];
                    operation = equationSplit[1];
                    secondSplitEquation = equationSplit[2];
                    //compute split equations
                    Float firstEquationComputed = compute(firstSplitEquation, x, y);
                    Float secondEquationComputed = compute(secondSplitEquation, x, y);

                    // compute operation
                    if(operation.equals("+")) {
                        z = firstEquationComputed + secondEquationComputed ;
                    }
                    if(operation.equals("-")) {
                        z = firstEquationComputed - secondEquationComputed ;
                    }


                    System.out.println("Coulmn 1= " + cols[0] + " , Column 2=" + cols[1]);
                    System.out.println("z= " + z);

                    double square = Math.pow(z - (float) y, 2);
                    System.out.println("square= " + square);
                    if(collectDeltaSquares != null) {
                        collectDeltaSquares.add(square);
                    }
                }

                OptionalDouble average = collectDeltaSquares
                        .stream()
                        .mapToDouble(a -> a)
                        .average();
                double meanDeltaSquares = average.isPresent() ? average.getAsDouble() : 0;
                System.out.println("Mean of Delta Squares:" +meanDeltaSquares );

                System.out.print("Continue with another file : ");
                input = br.readLine();
        }

            System.out.println("System terminated by the user");

        } catch (FileNotFoundException fn){
            System.out.println("File not found :" +fileName + " in the location :" +fileLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float compute(String splitEquation, int x, int y) {
      float computedValue = 0;
      boolean extractedNumbers = false;
      boolean hasX = false;
      boolean hasY = false;
        if(splitEquation.contains("x")) {
            List<Float> numbers = extractNumbers(splitEquation);
            extractedNumbers = true;
            hasX = true;
            for( Float number: numbers) {
                if(computedValue != 0) {
                    computedValue = number * computedValue;
                } else {
                    computedValue = number;
                }
            }
            if(computedValue != 0) {
                computedValue = computedValue * x;
            } else {
                computedValue = x;
            }
        }

        if(splitEquation.contains("y")) {
            hasY = true;
            if(extractedNumbers == false) {
                List<Float> numbers = extractNumbers(splitEquation);
                for (Float number : numbers) {

                    if (computedValue != 0) {
                        computedValue = number * computedValue;
                    } else {
                        computedValue = number;
                    }
                }
            }
            if(computedValue != 0) {
                computedValue = computedValue * y;
            } else {
                computedValue = y;
            }
        }
       if(hasX == false && hasY == false) {
           computedValue = Float.parseFloat(splitEquation);
       }

       return computedValue;
    }

    private static List<Float> extractNumbers(String splitEquation) {
        String strPattern = "-?\\d+(\\.\\d+)?";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(splitEquation);
        List<Float> listNumbers = new ArrayList<>();
        while (m.find()) {
            System.out.println(m.group());
            listNumbers.add(Float.parseFloat(m.group()));
        }
        return listNumbers;
    }
}

