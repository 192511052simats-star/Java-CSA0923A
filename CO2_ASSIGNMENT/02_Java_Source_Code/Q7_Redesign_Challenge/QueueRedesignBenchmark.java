import java.io.*;
import java.util.*;

public class QueueRedesignBenchmark {
    static final int REPEATS=5;
    static volatile long sink;
    static long arrayListQueue(int n){
        ArrayList<Integer> q=new ArrayList<>(n);
        for(int i=0;i<n;i++)q.add(i);
        long sum=0;
        while(!q.isEmpty())sum+=q.remove(0);
        sink=sum;return sum;
    }
    static long arrayDequeQueue(int n){
        ArrayDeque<Integer> q=new ArrayDeque<>(n);
        for(int i=0;i<n;i++)q.addLast(i);
        long sum=0;
        while(!q.isEmpty())sum+=q.removeFirst();
        sink=sum;return sum;
    }
    static long measure(boolean useDeque,int n){
        long start=System.nanoTime();
        if(useDeque)arrayDequeQueue(n);else arrayListQueue(n);
        return System.nanoTime()-start;
    }
    static double average(boolean deque,int n){
        for(int i=0;i<2;i++)measure(deque,Math.min(n,1000));
        long total=0;
        for(int i=0;i<REPEATS;i++)total+=measure(deque,n);
        return total/(double)REPEATS;
    }
    public static void main(String[] args)throws Exception{
        int[] sizes={2000,10000,30000};
        System.out.println("=== Q7 REDESIGN VALIDATION ===");
        System.out.println("Original: ArrayList queue with remove(0); redesigned: ArrayDeque with removeFirst().");
        System.out.println("Each measurement includes filling and draining the same number of items.");
        try(PrintWriter out=new PrintWriter(new FileWriter("q7_results.csv"))){
            out.println("n,repeats,arraylist_avg_ns,arraydeque_avg_ns");
            for(int n:sizes){
                double a=average(false,n),d=average(true,n);
                System.out.printf(Locale.US,"n=%d ArrayList avg=%.0f ns; ArrayDeque avg=%.0f ns%n",n,a,d);
                out.printf(Locale.US,"%d,%d,%.0f,%.0f%n",n,REPEATS,a,d);
            }
        }
        System.out.println("Diagnosis: ArrayList front removal shifts remaining elements.");
        System.out.println("Objective: reduce repeated shifting while preserving FIFO behaviour.");
        System.out.println("Prediction: ArrayDeque should scale better for repeated front removals.");
        System.out.println("Trade-off: ArrayDeque has no indexed access; ArrayList remains useful for random access.");
        System.out.println("Raw data saved to q7_results.csv");
    }
}
