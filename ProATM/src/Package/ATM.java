package Package;

import java.util.Scanner;

public class ATM {

    static Scanner input=new Scanner(System.in);
    static int empId = 1000, accNum = 99000;
    static EmpLinkedList empLinkedList = new EmpLinkedList();
    static CustLinkedList custLinkedList = new CustLinkedList();
    static Manager manager=new Manager("admin", "admin");

    public static void main(String[] args) {

        if (manager.fileExistence()){
            manager.loadFromFile();
        }

        if (empLinkedList.fileExistence()){
            empLinkedList.loadFromFile();
        }

        if (custLinkedList.fileExistence()){
            custLinkedList.loadFromFile();
        }

        int search, mainIn;
        String username, password;
        do {
            System.out.println("ATM App:");
            System.out.println("1. Sign in");
            System.out.println("2. Exit");
            System.out.println("(Default Manager's username and password is admin)");
            mainIn = correctIntInput("Option: ");
            switch (mainIn) {
                case 1:
                    search = 0; // to Check if it found user
                    do {
                        System.out.print("Username: ");
                        username = input.next();
                        System.out.print("Password: ");
                        password = input.next();
                        int numcheck = 0;
                        for (int i = 0; i < username.length(); i++) { //To check if this is a number.
                            if (username.charAt(i) >= 48 && username.charAt(i) <= 57) {
                                numcheck++;
                            }
                        }
                        // Manager
                        if (username.equalsIgnoreCase(manager.getUsername()) && password.equalsIgnoreCase(manager.getPass())) {
                            manager.abilities();
                            search = 1;
                        }
                        // Employee Or Customer
                        else if (username.length() == numcheck) {
                            int intUsername = Integer.parseInt(username);
                            if (Search(intUsername, password) == 1) {
                                search = 1;
                            }
                        }
                    } while (search == 0);

                    break;

                case 2:
                    custLinkedList.saveToFile();
                    custLinkedList.saveAccToFile();
                    Account.deleteTrCache();
                    empLinkedList.saveToFile();
                    manager.saveToFile();
                    break;
            }
        } while (mainIn != 2);
    }

    private static int correctIntInput(String alert){
        boolean validation = true;
        int num = 0;
        while (validation){
            Scanner input = new Scanner(System.in);
            try {
                System.out.print(alert);
                validation = false;
                num = input.nextInt();
            }catch (Exception e){
                System.out.println("Use digits, please!");
                validation = true;
            }
        }
        return num;
    }

    private static int Search(int number, String pass) { // To Find its Customer or Employee
        int found = 0; // to check if it found --> found = 1
        if (number >= 1000 && number <= 1999) { //To check if its Employee
            found = empLinkedList.search(number, pass);
        } else{
            Long natCode = (long) number;
            found = custLinkedList.search(natCode, pass);
        }
        return found;
    }
}
