import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class node{
        Item data;
        node previous;
        node next;

        public node(Item data){
            this.data=data;
            next=previous=null;
        }
    }

    private class DequeIterator implements Iterator<Item>{

        private node current = head;

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.data;
            current = current.next;
            return item;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
        
    }

    private node head;
    private node tail;
    private int count;

    // construct an empty deque
    public Deque(){
        head=tail=null;
        count=0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        if(head==null) return true;
        else return false;
    }

    // return the number of items on the deque
    public int size(){
        return count;
    }

    // add the item to the front
    public void addFirst(Item item){
        count++;
        node temp = new node(item);
        if(head==null) head=tail=temp;
        else{
            head.previous=temp;
            temp.next=head;
            head=temp;
        }
    }

    // add the item to the back
    public void addLast(Item item){
        count++;
        node temp = new node(item);
        if(head==null) head=tail=temp;
        else{
            tail.next=temp;
            temp.previous=tail;
            tail=temp;
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(head==null) throw new NoSuchElementException();
        else if(head==tail) {
            count--;
            Item temp = head.data;
            head=tail=null;
            return temp;
        }
        else{
            count--;
            Item temp = head.data;
            head=head.next;
            head.previous=null;
            return temp;
        }
        
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(tail==null) throw new NoSuchElementException();
        else if(head==tail) {
            count--;
            Item temp = head.data;
            head=tail=null;
            return temp;
        }
        else{
            count--;
            Item temp = tail.data;
            tail=tail.previous;
            tail.next=null;
            return temp;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> o = new Deque<>();
        o.addFirst(2);
        o.addFirst(3);
        //o.addFirst(20);
        //o.addLast(50);
        //o.addLast(90);

        //for(Integer i : o) System.out.print(i + " ");

        System.out.println("\n" + o.removeFirst());
        //for(Integer i : o) System.out.print(i + " ");
        System.out.println("\n" + o.removeFirst());
        //for(Integer i : o) System.out.print(i + " ");
        //System.out.println("\n" + o.removeFirst());



    }

}
