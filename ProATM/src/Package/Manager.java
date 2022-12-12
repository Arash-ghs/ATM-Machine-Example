package Package;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static Package.ATM.*;


public class Manager {

    private File file = new File("ManInfo.txt");
    private String username, pass;
    public Manager(String username, String password){
        this.username=username;
        this.pass=password;
    }

    public String getUsername(){
        return username;
    }

    public String getPass() {
        return pass;
    }

    private String correctStringInput(String alert){
        boolean validation = true;
        String str = null;
        int num = 0;
        while (validation){
            num = 0;
            Scanner input = new Scanner(System.in);
            System.out.print(alert);
            str = input.nextLine();
            for(int i=0; i<str.length(); i++){
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57){
                    num ++;
                }
            }
            if (num == 0){
                validation = false;
            }
            else {
                System.out.println("Use A-Z or a-z, please!");
                validation = true;
            }
        }
        return str;
    }

    private int correctIntInput(String alert){
        boolean validation = true;
        int num = 0;
        while (validation){
            Scanner input = new Scanner(System.in);
            try {
                System.out.print(alert);
                validation = false;
                num = input.nextInt();
                for (int i=0; i<String.valueOf(num).length(); i++){
                    if(String.valueOf(num).charAt(i)==' '){
                        validation=true;
                    }
                }
            }catch (Exception e){
                System.out.println("digits, please!");
                validation = true;
            }
        }
        return num;
    }

    private void changeInfo() {
        System.out.println("Change username and password:");
        username = correctStringInput("Enter new username, please: ");
        pass = correctPass("Enter new password, please: ");
        System.out.println("Information changed successfully.");
    }

    public boolean fileExistence(){
        if (file.exists()){
            return true;
        }
        return false;
    }

    public void saveToFile(){
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(username+"|"+pass);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFromFile(){
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            if(file.length()!=0) {
                int x;
                String str = new String();
                while ((x = reader.read()) != -1) {
                    str += (char) x;
                }
                String[] parts = str.split("\\|");
                manager.username=parts[0];
                manager.pass=parts[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void abilities() {
        int in;
        System.out.printf("Welcome %s\n", username);
        do {
            System.out.println("Your Manager's section:");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Change username and password");
            System.out.println("4. Back");
            in = correctIntInput("Option: ");
            switch (in) {
                case 1: // Add Employee
                    addEmployee();
                    break;
                case 2: // Delete Employee
                    deleteEmployee(correctIntInput("Enter Employment ID: "));
                    break;
                case 3: // CHenge username and password
                    changeInfo();
                    break;
            }
        } while (in != 4);
    }

    private String correctPass(String alert){
        boolean validation = true;
        String str = null;
        int num = 0;
        while (validation){
            num = 0;
            Scanner input = new Scanner(System.in);
            System.out.print(alert);
            str = input.nextLine();
            for(int i=0; i<str.length(); i++){
                if (str.charAt(i)==' '){
                    num ++;
                }
            }
            if (num == 0){
                validation = false;
            }
            else {
                System.out.println("Don't use space!");
                validation = true;
            }
        }
        return str;
    }

    private void addEmployee() {
        String firstName, lastName, pass;
        System.out.println("Add Employee:");
        firstName = correctStringInput("Enter Employee's First name, please: ");
        lastName = correctStringInput("Enter Employee's Last name, please: ");
        pass = correctPass("Enter Employee's Password, please: ");
        empLinkedList.insertToLast(new Employee(empId, firstName, lastName, pass));
        System.out.println("Employee created successfully.");
        System.out.printf("Username: %s\nPassword: %s\n", empId, pass);
        empId++;
    }

    private void deleteEmployee(int empId) {
        int search = 0; // to check if it found Employee or not
        if(empLinkedList.search(empId)!=null){
            empLinkedList.remove(empLinkedList.search(empId));
            System.out.println("Employee deleted successfully.ِ");
            search=1;
        }
        if(search == 0) {
            System.out.println("Employee not found!");
        }
    }
}
