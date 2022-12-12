package Package;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EmpLinkedList{

    private Node first = null;
    private File file = new File("EmpInfo.txt");

    public void insertToLast(Employee emp){
        Node<Employee> node = new Node(emp, null);
        if(first==null){
            first = node;
        } else {
            Node p = first;
            while (p.getNext() != null){
                p = p.getNext();
            }
            p.setNext(node);
        }
    }

    public int search(int empId, String pass){
        Node<Employee> p = first;
        while (p!=null){
            if (p.getPerson().getEmpId()==empId && p.getPerson().getPass().equalsIgnoreCase(pass)){
                p.getPerson().abilities();
                return 1;
            }
            p=p.getNext();
        }
        return 0;
    }

    public Employee search(int empId){
        Node<Employee> p = first;
        while (p!=null){
            if (p.getPerson().getEmpId()==empId){
                return p.getPerson();
            }
            p=p.getNext();
        }
        return null;
    }

    public void remove(Employee emp){
        Node<Employee> p = first, q = null;
        while(p!=null){
            if(p.getPerson().equals(emp)){
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
            Node<Employee> p = first;
            while(p!=null){
                writer.write(p.getPerson().getEmpId()+"|"+p.getPerson().getFirstName()
                        +"|"+p.getPerson().getLastName()+"|"+p.getPerson().getPass()+"\n");
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
                String[] allEmployees = str.split("\n");
                for (String emp : allEmployees) {
                    String[] parts = emp.split("\\|");
                    Employee employee = new Employee(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                    ATM.empId = Integer.parseInt(parts[0]) + 1;
                    insertToLast(employee);
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
