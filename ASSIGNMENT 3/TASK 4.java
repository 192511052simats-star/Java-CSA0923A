
class ScannerStation extends Thread {
    int scripts;

    ScannerStation(String name, int scripts) {
        super(name);
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(getName() + " started");

            Thread.sleep(1000);

            System.out.println(getName() + " scanned " + scripts + " scripts");

        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}

class StationTask implements Runnable {
    int scripts;
    String name;

    StationTask(String name, int scripts) {
        this.name = name;
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(name + " started");
            Thread.sleep(1000);
            System.out.println(name + " scanned " + scripts + " scripts");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}

class TASK_4 {
    public static void main(String[] args) throws InterruptedException {

        ScannerStation s1 = new ScannerStation("Station 1", 10);
        ScannerStation s2 = new ScannerStation("Station 2", 20);

        Thread s3 = new Thread(new StationTask("Station 3", 30));
        Thread s4 = new Thread(new StationTask("Station 4", 40));

        s1.start();
        s2.start();
        s3.start();
        s4.start();

        System.out.println("Station 1 alive: " + s1.isAlive());

        s1.join();
        s2.join();
        s3.join();
        s4.join();

        System.out.println("All stations completed");
        System.out.println("Total scripts scanned: 100");
    }
}
