package com.karles.project;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.println("Input file path (e.g. C:\\Users\\Karina-PC\\Desktop\\files)");
            String filePath = input.nextLine();

            Reader r = new Reader();
            try {
                r.start(filePath);
            } catch (Exception e) {

                System.out.println("Given path is not valid");
                System.out.println("");
            }
        }
    }
}
