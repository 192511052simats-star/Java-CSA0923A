import java.io.*;
import java.util.*;

public class MapBenchmark {
    static volatile int sink;
    static long time(Runnable r) { long s=System.nanoTime(); r.run(); return System.nanoTime()-s; }
    static String code(int i) { return String.format(Locale.ROOT,"MED%07d",i); }

    public static void main(String[] args) throws Exception {
        int[] sizes={1000,10000,50000};
        Random random=new Random(42);
        System.out.println("=== Q4 PHARMACY MAP EXPERIMENT ===");
        try(PrintWriter out=new PrintWriter(new FileWriter("q4_results.csv"))) {
            out.println("size,map,operation,time_ns");
            for(int n:sizes) {
                Map<String,Integer> hash=new HashMap<>();
                Map<String,Integer> tree=new TreeMap<>();
                Map<String,Integer> linked=new LinkedHashMap<>();
                for(int i=0;i<n;i++){String k=code(i);hash.put(k,i);tree.put(k,i);linked.put(k,i);}
                String[] keys=new String[100000];
                for(int i=0;i<keys.length;i++) keys[i]=code(random.nextInt(n));
                System.out.println("\nMedicines: "+n);
                Map<String,Map<String,Integer>> maps=new LinkedHashMap<>();
                maps.put("HashMap",hash); maps.put("TreeMap",tree); maps.put("LinkedHashMap",linked);
                for(Map.Entry<String,Map<String,Integer>> e:maps.entrySet()) {
                    long lookup=time(()->{int s=0;for(String k:keys)s+=e.getValue().get(k);sink=s;});
                    long iteration=time(()->{int s=0;for(Map.Entry<String,Integer>x:e.getValue().entrySet())s+=x.getValue();sink=s;});
                    long sorted=time(()->{TreeMap<String,Integer> t=new TreeMap<>(e.getValue());sink=t.size();});
                    long insertion=time(()->{LinkedHashMap<String,Integer> t=new LinkedHashMap<>(e.getValue());sink=t.size();});
                    System.out.printf("%s: 100k lookups %d ns; iterate %d ns; make code-order report %d ns; make insertion-order report %d ns%n",
                            e.getKey(),lookup,iteration,sorted,insertion);
                    out.printf("%d,%s,100000-lookups,%d%n",n,e.getKey(),lookup);
                    out.printf("%d,%s,iterate,%d%n",n,e.getKey(),iteration);
                    out.printf("%d,%s,code-order-report-copy,%d%n",n,e.getKey(),sorted);
                    out.printf("%d,%s,insertion-order-report-copy,%d%n",n,e.getKey(),insertion);
                }
                System.out.println("Natural code order: TreeMap. Natural insertion order: LinkedHashMap.");
            }
        }
        System.out.println("\nRaw data saved to q4_results.csv");
    }
}
