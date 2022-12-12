package Package;

import java.util.Scanner;

public class Customer extends Person{

    protected Account[] account = new Account[5];
    private int accCount = 0;
    private String pass, address;
    private Long natCode, phoneNum;

    public Customer(Long natCode, String firstName, String lastName, String pass, long phoneNum, String address) {
        super(firstName,lastName);
        this.pass = pass;
        this.phoneNum = phoneNum;
        this.address = address;
        this.natCode=natCode;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getAccCount(){
        return accCount;
    }

    public void setAccCount(int accCount) {
        this.accCount = accCount;
    }

    public String getPass() {
        return pass;
    }

    public Long getNatCode() {
        return natCode;
    }

    public String getAddress() {
        return address;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setAccount(int accNum, long balance) {
        account[accCount]=new Account(accNum, balance);
        accCount++;
        ATM.accNum++;
    }

    private int search(int accNum){
        int found = 5;
            for (int i=0; i<accCount; i++) {
                if (account[i].getAccNum() == accNum) {
                    found = i;
                }
            }
        return found;
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
                        System.out.println("Use digits, please!");
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

    private void showPersonalInfo() {
        System.out.println("Personal Information:");
        System.out.printf("National Code: 0%d\n", natCode);
        System.out.printf("First name: %s\n", firstName);
        System.out.printf("Last name: %s\n", lastName);
        System.out.printf("Password: %s\n", pass);
        System.out.printf("Phone Number: 0%s\n", phoneNum);
        System.out.printf("Address: %s\n", address);
        for (int i=0; i<accCount; i++){
            System.out.printf("%d. Account Number: %d    Balance: %d\n", i+1, account[i].getAccNum(), account[i].getBalance());
        }
    }

    public void abilities() {
        int in;
        int accNum;
        System.out.printf("Welcome %s %s\n", firstName, lastName);
        do {
            System.out.println("Your Customer's section:");
            System.out.println("1. Withdraw");
            System.out.println("2. Transfer");
            System.out.println("3. Last Five Turnover");
            System.out.println("4. Account Balance");
            System.out.println("5. Personal Information");
            System.out.println("6. Back");
            in = correctIntInput("Option: ");
            int accFound;
            switch (in) {
                case 1: // Withdrawn
                    System.out.println("Withdrawn:");
                    accNum = correctIntInput("Enter your account number, please: ");
                    accFound = search(accNum);
                    if(accFound!=5){
                        account[accFound].withdraw();
                    }
                    else {
                        System.out.println("Account number doesn't found.");
                    }
                    break;
                case 2: // Transfer
                    System.out.println("Transfer:");
                    accNum = correctIntInput("Enter your account number, please: ");
                    accFound = search(accNum);
                    if(accFound!=5){
                        account[accFound].transfer(accNum);
                    }
                    else {
                        System.out.println("Account number doesn't found.");
                    }
                    break;
                case 3: // Last Five Transaction
                    System.out.println("Last five Transaction:");
                    accNum = correctIntInput("Enter your account number, please: ");
                    accFound = search(accNum);
                    if(accFound!=5){
                        account[accFound].fiveTransaction();
                    }
                    else {
                        System.out.println("Account number doesn't found.");
                    }
                    break;
                case 4: // Account Balance
                    accNum = correctIntInput("Enter your account number, please: ");
                    accFound = search(accNum);
                    if(accFound!=5){
                        account[accFound].printBalance();
                    }
                    else {
                        System.out.println("Account number doesn't found.");
                    }
                    break;
                case 5: // Show personal Information
                    showPersonalInfo();
                    break;
            }
        } while (in != 6);
    }

}
