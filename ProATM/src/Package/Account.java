package Package;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static Package.ATM.*;

enum Status {Withdrawn, Transferred, Received}

public class Account {

    private static File file = new File("TrInfo.txt");
    private int accNum, transactionCount = 0;
    private long balance;
    private String[] transactionStatus = new String[5]; // Withdrawn, Transferred, Received
    private long[] transactionAmount = new long[5];

    public Account(int accNum, long balance){
        this.accNum=accNum;
        this.balance=balance;
    }

    public void setBalance(long add) {
        balance = balance+add;
    }

    public long getBalance(){
        return balance;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public void setTransactionStatus(String[] statusT) {
        for (int i=0; i<transactionCount; i++){
            transactionStatus[i] = statusT[i];
        }
    }

    public void setTransactionAmount(long[] amountT) {
        for (int i=0; i<transactionCount; i++){
            transactionAmount[i] = amountT[i];
        }
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public String getTransactionStatus() {
        String turnOver = "";
        for (int i = 0; i< transactionCount; i++) {
            if (i == transactionCount - 1) {
                turnOver = turnOver + transactionStatus[i];
            } else {
                turnOver = turnOver + transactionStatus[i] + "|";
            }
        }
        return turnOver;
    }

    public String getTransactionAmount() {
        String turnOver = "";
        for (int i = 0; i< transactionCount; i++){
            if (i== transactionCount -1) {
                turnOver = turnOver + transactionAmount[i];
            }
            else {
                turnOver = turnOver + transactionAmount[i] + "|";
            }        }
        return turnOver;
    }

    public int getAccNum() {
        return accNum;
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
            }catch (Exception e){
                System.out.println("Use 0-9, please!");
                validation = true;
            }
        }
        return num;
    }

    private long correctLongInput(String alert){
        boolean validation = true;
        long num = 0;
        while (validation){
            Scanner input = new Scanner(System.in);
            try {
                System.out.print(alert);
                validation = false;
                num = input.nextLong();
            }catch (Exception e){
                System.out.println("Use 0-9, please!");
                validation = true;
            }
        }
        return num;
    }

    public static boolean fileExistence(){
        return file.exists();
    }

    private void saveTransaction(long cash, String status) {
        if (transactionCount > 4) { // to Save this Transaction in last Five Turnover
            System.out.println("if");
            transactionCount = 4;
            for (int i = 0; i <= 3; i++) {
                transactionAmount[i] = transactionAmount[i+1];
                transactionStatus[i] = transactionStatus[i+1];
            }
        }
        transactionAmount[transactionCount] = cash;
        transactionStatus[transactionCount] = status;
        transactionCount++;
    }

    private void saveToCacheFile(int accNum){
        FileWriter writer = null;
        try {
            writer = new FileWriter(file,true);
            writer.write(accNum + "|" + transactionAmount[transactionCount-1] + "|" + transactionStatus[transactionCount-1] +
                    "|" + balance + "|" + (transactionCount-1) +"\n");
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
                String str = "";
                while ((x = reader.read()) != -1) {
                    str += (char) x;
                }
                String[] allTransactions = str.split("\n");
                for (String tr : allTransactions) {
                    String[] parts = tr.split("\\|");
                    if (accNum == Integer.parseInt(parts[0])) {
                        transactionAmount[Integer.parseInt(parts[4])] = Long.parseLong(parts[1]);
                        transactionStatus[Integer.parseInt(parts[4])] = parts[2];
                        setBalance(Long.parseLong(parts[3]));
                        transactionCount=Integer.parseInt(parts[4])+1;
                    }
                }
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

    public static void deleteTrCache(){
        file.delete();
    }

    public void withdraw() {
        long cash;
        cash = correctLongInput("Enter the amount of money you want to Withdrawn: ");
        if (cash <= balance && cash != 0) {
            balance -= cash;
            saveTransaction(cash, String.valueOf(Status.Withdrawn));
            System.out.println("Receive your cash.");
        } else {
            System.out.println("You don't have enough Money!");
        }
    }

    public void transfer(int accNum){
        transfer:
        {
            long cash;
            int number;
            cash = correctLongInput("Enter the amount of money you want to Transfer: ");
            if (cash > balance) {
                System.out.println("You don't have enough money!");
                break transfer; // If doesn't have enough money
            }
            number = correctIntInput("Enter Account Number you want to Transfer to: ");
            if (number != accNum) { // Shouldn't able to transfer to it's account
                if(custLinkedList.accSearch(number)!=null) {
                    balance -= cash;
                    custLinkedList.accSearch(number).setBalance(cash);

                    saveTransaction(cash, String.valueOf(Status.Transferred));
                    saveToCacheFile(accNum);

                    custLinkedList.accSearch(number).saveTransaction(cash, String.valueOf(Status.Received));
                    custLinkedList.accSearch(number).saveToCacheFile(number);

                    System.out.println("Transferred successfully.");
                } else {
                    System.out.println("Account Number not found!");
                }
            } else {
                System.out.println("You can't transfer money to your Account!");
            }

        }
    }

    public void fiveTransaction() {
        if (transactionCount >0) {
            for (int i = 0; i < transactionCount; i++) {
                System.out.printf("%d) %s: %d\n", i + 1, transactionStatus[i], transactionAmount[i]);
            }
        }
        else {
            System.out.println("There is no Transaction!");
        }
    }

    public void printBalance() {
        System.out.printf("your Account Balance: %d\n", balance);
    }
}
