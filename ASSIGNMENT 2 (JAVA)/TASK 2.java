
import java.util.*;

public class Main {

    static void test(int size) {

        ArrayList<Integer> a = new ArrayList<Integer>();
        LinkedList<Integer> l = new LinkedList<Integer>();

        long start, end;

        System.out.println("\nPLAYLIST SIZE: " + size);

        // Add at end
        start = System.nanoTime();
        for (int i = 0; i < size; i++)
            a.add(i);
        end = System.nanoTime();
        System.out.println("ArrayList Add End: " + (end-start) + " ns");

        start = System.nanoTime();
        for (int i = 0; i < size; i++)
            l.add(i);
        end = System.nanoTime();
        System.out.println("LinkedList Add End: " + (end-start) + " ns");

        // Insert at front
        start = System.nanoTime();
        a.add(0, -1);
        end = System.nanoTime();
        System.out.println("ArrayList Front Insert: " + (end-start) + " ns");

        start = System.nanoTime();
        l.add(0, -1);
        end = System.nanoTime();
        System.out.println("LinkedList Front Insert: " + (end-start) + " ns");

        // Insert in middle
        start = System.nanoTime();
        a.add(a.size()/2, 100);
        end = System.nanoTime();
        System.out.println("ArrayList Middle Insert: " + (end-start) + " ns");

        start = System.nanoTime();
        l.add(l.size()/2, 100);
        end = System.nanoTime();
        System.out.println("LinkedList Middle Insert: " + (end-start) + " ns");

        // Access k-th element
        start = System.nanoTime();
        int x = a.get(size/2);
        end = System.nanoTime();
        System.out.println("ArrayList Access: " + (end-start) + " ns");

        start = System.nanoTime();
        int y = l.get(size/2);
        end = System.nanoTime();
        System.out.println("LinkedList Access: " + (end-start) + " ns");

        // Remove front
        start = System.nanoTime();
        a.remove(0);
        end = System.nanoTime();
        System.out.println("ArrayList Front Remove: " + (end-start) + " ns");

        start = System.nanoTime();
        l.remove(0);
        end = System.nanoTime();
        System.out.println("LinkedList Front Remove: " + (end-start) + " ns");

        // Iteration
        start = System.nanoTime();
        for (Integer value : a) {
            x = value;
        }
        end = System.nanoTime();
        System.out.println("ArrayList Iteration: " + (end-start) + " ns");

        start = System.nanoTime();
        for (Integer value : l) {
            y = value;
        }
        end = System.nanoTime();
        System.out.println("LinkedList Iteration: " + (end-start) + " ns");
    }

    public static void main(String[] args) {

        test(100);
        test(1000);
        test(10000);

        // Up Next Queue using ArrayDeque
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

        queue.addFirst(10);
        queue.addLast(20);
        queue.addFirst(5);

        System.out.println("\nUP NEXT QUEUE");
        System.out.println("Queue: " + queue);

        queue.removeFirst();
        System.out.println("After removing first: " + queue);
    }
}
