import java.io.*;
import java.util.*;

class Patient {
    final String name; final int severity; final long arrival;
    Patient(String n,int s,long a){name=n;severity=s;arrival=a;}
    public String toString(){return name+"(S"+severity+")";}
}
class Triage {
    private long counter=0;
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(
        Comparator.comparingInt((Patient p)->p.severity)
                  .thenComparingLong(p->p.arrival));
    void admit(String name,int severity){
        if(severity<1||severity>5)throw new IllegalArgumentException("Severity must be 1 to 5.");
        queue.add(new Patient(name,severity,counter++));
    }
    Patient next(){return queue.poll();}
    int size(){return queue.size();}
}
public class TriageDemo {
    static volatile long sink;
    static long time(Runnable r){long s=System.nanoTime();r.run();return System.nanoTime()-s;}
    public static void main(String[] args)throws Exception{
        System.out.println("=== Q6 EMERGENCY TRIAGE ===");
        Triage er=new Triage();
        er.admit("Ravi",3);er.admit("Meena",1);er.admit("John",3);er.admit("Asha",5);
        er.admit("Kumar",1);er.admit("Lata",2);er.admit("Nila",4);er.admit("Vijay",3);
        StringBuilder order=new StringBuilder();Patient p;
        while((p=er.next())!=null)order.append(p).append(' ');
        System.out.println("Treatment order: "+order.toString().trim());
        System.out.println("Rule: lower severity number first; arrival counter breaks ties.");
        System.out.println("Starvation risk: severity-5 patients may wait if more urgent patients keep arriving.");
        System.out.println("Fairness option: increase effective priority gradually after a defined waiting threshold (aging).");
        System.out.println("Deployment: PriorityQueue gives O(log n) admission/removal and O(n) storage; capacity, concurrency, monitoring and clinical policy still require production design.");

        int[] sizes={1000,10000,50000};
        try(PrintWriter out=new PrintWriter(new FileWriter("q6_results.csv"))){
            out.println("patients,add_all_ns,remove_all_ns");
            for(int n:sizes){
                final int count=n;
                long add=time(()->{PriorityQueue<Patient> q=new PriorityQueue<>(
                    Comparator.comparingInt((Patient x)->x.severity).thenComparingLong(x->x.arrival));
                    for(int i=0;i<count;i++)q.add(new Patient("P"+i,(i%5)+1,i));
                    sink=q.size();
                });
                PriorityQueue<Patient> q=new PriorityQueue<>(
                    Comparator.comparingInt((Patient x)->x.severity).thenComparingLong(x->x.arrival));
                for(int i=0;i<n;i++)q.add(new Patient("P"+i,(i%5)+1,i));
                long remove=time(()->{long c=0;while(!q.isEmpty()){q.poll();c++;}sink=c;});
                System.out.printf("n=%d add-all=%d ns remove-all=%d ns%n",n,add,remove);
                out.printf("%d,%d,%d%n",n,add,remove);
            }
        }
        System.out.println("Raw data saved to q6_results.csv");
    }
}
