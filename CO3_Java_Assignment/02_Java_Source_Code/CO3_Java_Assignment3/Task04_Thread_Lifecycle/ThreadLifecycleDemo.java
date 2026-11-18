class ScannerStation extends Thread {
    private final int scripts;
    ScannerStation(String name, int scripts) { super(name); this.scripts = scripts; }
    public void run() {
        try {
            for (int i = 1; i <= scripts; i++) {
                Thread.sleep(80);
                System.out.println(getName() + " scanned script " + i);
            }
            System.out.println(getName() + " finished " + scripts + " scripts.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(getName() + " interrupted.");
        }
    }
}
class RunnableStation implements Runnable {
    private final String station;
    private final int scripts;
    RunnableStation(String station, int scripts) { this.station=station; this.scripts=scripts; }
    public void run() {
        try {
            for (int i=1;i<=scripts;i++) {
                Thread.sleep(60);
                System.out.println(station + " (Runnable) scanned script " + i);
            }
            System.out.println(station + " (Runnable) finished " + scripts + " scripts.");
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
public class ThreadLifecycleDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Q4 THREAD LIFE CYCLE ===");
        Thread[] stations = {
            new ScannerStation("Station-1",3),
            new ScannerStation("Station-2",3),
            new Thread(new RunnableStation("Station-3",3),"Station-3"),
            new Thread(new RunnableStation("Station-4",3),"Station-4")
        };
        for (Thread t : stations) {
            System.out.println(t.getName()+" alive before start: "+t.isAlive());
            t.start();
        }
        for (Thread t : stations) t.join();
        System.out.println("All stations completed. Final total scripts: 12");
        System.out.println("Calling run() directly would execute on the current thread, not create a new thread.");
    }
}
