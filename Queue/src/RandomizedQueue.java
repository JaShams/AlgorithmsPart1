import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item array[];
    private int size;

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        for(int i=0; i<size ; i++) copy[i]=array[i];
        array=copy;
        copy=null;
    }

    private class RandomizedQueueIterator implements Iterator<Item>{

        private RandomizedQueue<Item> obj;

        public RandomizedQueueIterator(){
            obj = new RandomizedQueue<>(RandomizedQueue.this);
            //obj.size = size;
            //for(int i=0; i<size ; i++) obj.array[i]=array[i];
        }


        @Override
        public boolean hasNext() {
            return !(obj.isEmpty());
        }

        @Override
        public Item next() {
            return obj.dequeue();
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue(){
        array = (Item[]) new Object[1];
        size = 0;
    }

    private RandomizedQueue(RandomizedQueue<Item> obj){
        array = (Item[]) new Object[obj.array.length];
        for(int i=0; i<obj.array.length ; i++) array[i]=obj.array[i];
        size = obj.size;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size==0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if(item==null) throw new IllegalArgumentException();
        if(size==array.length) resize(2*size);
        array[size++]=item;
    }

    // remove and return a random item
    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        int rand = StdRandom.uniform(size);
        Item temp = array[rand];

        array[rand]=array[size-1];
        array[size-1]=null;
        size--;

        if(size>0 && size==array.length/4) resize(array.length/2);

        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        int rand = StdRandom.uniform(size);
        return array[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> obj = new RandomizedQueue<>();
        obj.enqueue(12);
        obj.enqueue(34);
        obj.enqueue(65);
        obj.enqueue(31);
        obj.enqueue(90);

        System.out.println(obj.sample());
        
        for(Integer i : obj) System.out.print(i + " ");

        System.out.println("\n" + obj.dequeue());

        for(Integer i : obj) System.out.print(i + " ");

        System.out.println("\n" + obj.dequeue());

        for(Integer i : obj) System.out.print(i + " ");

        System.out.println("\n" + obj.dequeue());

        for(Integer i : obj) System.out.print(i + " ");

    }

}
