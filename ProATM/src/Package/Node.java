package Package;

public class Node<Type>{
    private Node next;
    private Type person;

    public Node(Type person, Node next){
        this.person = person;
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Type getPerson() {
        return person;
    }
}