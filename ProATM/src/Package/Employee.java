package Package;

import java.util.Scanner;

import static Package.ATM.*;

public class Employee extends Person{
    private String pass;
    private int empId;
    Scanner input=new Scanner(System.in);
    Employee(int empId, String firstName, String lastName, String pass){
        super(firstName,lastName);
        this.pass=pass;
        this.empId=empId;
    }

    public String getPass() {
        return pass;
    }

    public int getEmpId() {
        return empId;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
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
        String num = null;
        while (validation){
            Scanner input = new Scanner(System.in);
            try {
                System.out.print(alert);
                validation = false;
                num = input.next();
                for (int i=0; i<num.length(); i++) {
                    if (!(num.charAt(i) >= 48 && num.charAt(i) <= 57)){
                        validation = true;
                        System.out.println("Use digits, please");
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println("Use digits, please!");
                validation = true;
            }
        }
        return Integer.parseInt(num);
    }

    private long correctLongInput(String alert){
        boolean validation = true;
        String num = "";
        while (validation){
            Scanner input = new Scanner(System.in);
            try {
                System.out.print(alert);
                validation = false;
                num = input.next();
                for (int i = 0; i < num.length(); i++) {
                    if (!(num.charAt(i) >= 48 && num.charAt(i) <= 57)){
                        validation = true;
                        System.out.println("Use digits, please!");
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println("Use digits, please!");
                validation = true;
            }
        }
        return Long.parseLong(num);
    }

    public void abilities() {
        int in;
        System.out.printf("Welcome %s %s\n", firstName, lastName);
        do {
            System.out.println("Your Employee's section:");
            System.out.println("1. Add Customer");
            System.out.println("2. Delete Customer");
            System.out.println("3. Add Account");
            System.out.println("4. Delete Account");
            System.out.println("5. Back");
            in = correctIntInput("Option: ");
            switch (in) {
                case 1: // Add customer
                    addCustomer();
                    break;
                case 2: // Delete Customer
                    deleteCustomer(correctLongInput("Enter Customer's National Number: (Without \"0\") "));
                    break;
                case 3:
                    addAccount(correctLongInput("Enter Customer's National Number: (Without \"0\") "));
                    break;
                case 4:
                    deleteAccount(correctLongInput("Enter Customer's National Number: (Without \"0\") "));
                    break;
            }
        } while (in != 5);
    }

    private void addCustomer() {
        String firstName, lastName, pass, address;
        long balance, natCode, phoneNum;
        addCustomer:
        {
            System.out.println("Add Customer:");
            natCode = correctLongInput("Enter Customer's National Code, please: ");
            if (custLinkedList.search(natCode) != null) {
                System.out.println("This Customer has already registered.");
                break addCustomer;
            }
            firstName = correctStringInput("Enter Customer's First name, please: ");
            lastName = correctStringInput("Enter Customer's Last name, please: ");
            System.out.print("Enter Customer's Password, please: ");
            pass = input.next();
            System.out.print("Enter Customer's Address, please: ");
            input.nextLine();
            address = input.nextLine();
            phoneNum = correctLongInput("Enter Customer's Phone Number, please: ");
            balance = correctLongInput("Enter Customer's Account Balance, please: ");
            custLinkedList.insertToLast(new Customer(natCode, firstName, lastName, pass, phoneNum, address));
            custLinkedList.search(natCode).setAccount(accNum,balance);
            System.out.println("Customer created successfully.");
            System.out.printf("Username: %s\nPassword: %s\nAccount number: %d\n", natCode, pass, accNum-1);
        }
    }

    private void deleteCustomer(Long natCode) {
        int search = 0; // to check if it found Customer or not
        if (custLinkedList.search(natCode)!=null){
            custLinkedList.remove(custLinkedList.search(natCode));
            System.out.println("Customer deleted successfully.ِ");
            search=1;
        }
        if (search == 0) {
            System.out.println("Customer not found!");
        }
    }

    private void addAccount(Long natCode){
        int search=0;
        long balance;
        addAccount:
        {
            if (custLinkedList.search(natCode) != null) {
                if (custLinkedList.search(natCode).getAccCount() == 5) {
                    System.out.print("You have too much Account: 5");
                    break addAccount;
                }
                balance = correctLongInput("Enter account balance: ");
                custLinkedList.search(natCode).setAccount(accNum, balance);
                System.out.println("Account created.");
                System.out.printf("Account number: %d\nBalance: %d\n",accNum-1, balance);
                search = 1;
            }
            if (search == 0) {
                System.out.println("Customer not found!");
            }
        }
    }

    private void deleteAccount(Long natCode){
        int search = 0;
        if (custLinkedList.search(natCode) != null) {
            int accNum = correctIntInput("Enter Account Number, please: ِ");
            for (int i = 0; i < custLinkedList.search(natCode).getAccCount(); i++) {
                if (custLinkedList.search(natCode).account[i].getAccNum() == accNum) {
                    for (int j = i; j < custLinkedList.search(natCode).getAccCount(); j++) {
                        custLinkedList.search(natCode).account[j] = custLinkedList.search(natCode).account[j + 1];
                    }
                    custLinkedList.search(natCode).setAccCount(custLinkedList.search(natCode).getAccCount() - 1);
                    System.out.println("Account deleted successfully.ِ");
                    search = 1;
                }
            }
        }else {
            System.out.println("Customer not found!");
            search = 1;
        }
        if (search == 0) {
            System.out.println("Account not found!");
        }
    }
}
