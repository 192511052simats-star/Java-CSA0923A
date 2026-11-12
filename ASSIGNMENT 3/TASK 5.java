
class TASK_5 {
    private final boolean[] booked;
    private int bookingsMade = 0;

    TASK_5(int seats) {
        booked = new boolean[seats];
    }

    // Synchronized method prevents double booking
    synchronized void book(int seat) {
        if (!booked[seat]) {
            booked[seat] = true;
            bookingsMade++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        TASK_5 screen = new TASK_5(10);

        Thread[] users = new Thread[100];

        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;

            users[i] = new Thread(() -> screen.book(seat));
            users[i].start();
        }

        for (Thread t : users) {
            t.join();
        }

        System.out.println("Seats sold: " + screen.bookingsMade);
    }
}
