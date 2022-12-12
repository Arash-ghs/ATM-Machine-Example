package Package;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CustLinkedList{

    private Node first = null;
    private File file = new File("CustInfo.txt");
    private File accFile = new File("AccInfo.txt");

    public void insertToLast(Customer cust){
        Node<Customer> node = new Node(cust, null);
        if(first == null){
            first = node;
        } else {
            Node<Customer> p = first;
            while (p.getNext() != null){
                p=p.getNext();
            }
            p.setNext(node);
        }
    }

    public int search(Long natCode, String pass) {
        Node<Customer> p = first;
        while (p!=null){
            if (p.getPerson().getNatCode().equals(natCode) && p.getPerson().getPass().equalsIgnoreCase(pass)){
                p.getPerson().abilities();
                return 1;
            }
            p=p.getNext();
        }
        return 0;
    }

    public Customer search(Long natCode) {
        Node<Customer> p = first;
        while (p!= null) {
            if (p.getPerson().getNatCode().equals(natCode)) {
                return p.getPerson();
            }
            p = p.getNext();
        }
        return null;
    }

    public Account accSearch(int accNum){
        Node<Customer> p = first;
        while (p != null) {
            for (int i=0; i<p.getPerson().getAccCount(); i++) {
                if (p.getPerson().account[i].getAccNum() == accNum) {
                    return p.getPerson().account[i];
                }
            }
            p = p.getNext();
        }
        return null;
    }

    public void remove(Customer cust){
        Node<Customer> p = first, q = null;
        while(p!=null){
            if(p.getPerson().equals(cust)){
                break;
            }
            q=p;
            p=p.getNext();
        }
        if(p!=null){
            if(q==null){
                first=first.getNext();
            }else{
                q.setNext(p.getNext());
            }
        }
    }

    public boolean fileExistence(){
        return file.exists();
    }

    public void saveToFile(){
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            Node<Customer> p = first;
            while(p!=null){
                writer.write(p.getPerson().getNatCode()+"|"+p.getPerson().getFirstName()
                        +"|"+p.getPerson().getLastName()+"|"+p.getPerson().getPass()+
                        "|"+p.getPerson().getPhoneNum()+"|"+p.getPerson().getAddress()+
                        "|"+p.getPerson().getAccCount()+"\n");
                p = p.getNext();
            }

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

    public void saveAccToFile(){
        FileWriter writer = null;
        try {
            writer = new FileWriter(accFile, false);
            Node<Customer> p = first;
            while(p!=null){
                for (int i=0; i<p.getPerson().getAccCount(); i++){
                    writer.write(p.getPerson().getNatCode()+"|"+p.getPerson().account[i].getAccNum()+
                            "|"+p.getPerson().account[i].getBalance()+"|"+p.getPerson().account[i].getTransactionCount()+
                            "|"+ p.getPerson().account[i].getTransactionAmount() +
                            "|"+ p.getPerson().account[i].getTransactionStatus()+"\n");
                }
                p = p.getNext();
            }

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
        first = null;
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            if(file.length()!=0) {
                int x;
                String str = "";
                while ((x = reader.read()) != -1) {
                    str += (char) x;
                }
                String[] allCustomers = str.split("\n");
                for (String cust : allCustomers) {
                    String[] parts = cust.split("\\|");
                    Customer customer = new Customer(Long.parseLong(parts[0]), parts[1], parts[2], parts[3],
                            Long.parseLong(parts[4]), parts[5]);
                    insertToLast(customer);
                    loadAccFromFile(Long.parseLong(parts[0])); // Load Account's Information
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

    public void loadAccFromFile(long natCode) {
        FileReader reader = null;
        try {
            reader = new FileReader(accFile);
            if (file.length() != 0) {
                int x;
                String str = "";
                while ((x = reader.read()) != -1) {
                    str += (char) x;
                }
                String[] allAccounts = str.split("\n");
                for (String acc : allAccounts) {
                    String[] parts = acc.split("\\|");
                    if(natCode == Long.parseLong(parts[0])){
                        search(natCode).setAccount(Integer.parseInt(parts[1]), Long.parseLong(parts[2]));
                        ATM.accNum = Integer.parseInt(parts[1]) + 1;
                        long[] amountT = new long[Integer.parseInt(parts[3])];
                        String[] statusT = new String[Integer.parseInt(parts[3])];
                        for (int i=0; i<Integer.parseInt(parts[3]); i++) {
                            amountT[i] = Long.parseLong(parts[4+i]);
                            statusT[i] = parts[4+i+Integer.parseInt(parts[3])];
                        }
                        search(natCode).account[search(natCode).getAccCount()-1].setTransactionCount(Integer.parseInt(parts[3]));
                        search(natCode).account[search(natCode).getAccCount()-1].setTransactionStatus(statusT);
                        search(natCode).account[search(natCode).getAccCount()-1].setTransactionAmount(amountT);
                    }
                    if (Account.fileExistence()) { // Load Transaction's cache file
                        for (int i=0; i < search(natCode).getAccCount(); i++) {
                            search(natCode).account[i].loadFromFile();
                        }
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

}