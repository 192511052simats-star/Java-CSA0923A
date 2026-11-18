import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;
    private final AtomicInteger doubleBookingAttempts = new AtomicInteger();

    SeatBooking(int seats) { booked = new boolean[seats]; }

    // TODO 1 completed: the synchronized method makes check-then-book atomic.
    public synchronized boolean book(int seat) {
        if (booked[seat]) {
            doubleBookingAttempts.incrementAndGet();
            return false;
        }
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        CountDownLatch ready = new CountDownLatch(users.length);
        CountDownLatch start = new CountDownLatch(1);
        for (int i=0;i<users.length;i++) {
            final int seat=i%10;
            users[i]=new Thread(() -> {
                ready.countDown();
                try { start.await(); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); return;
                }
                screen.book(seat);
            });
            users[i].start();
        }
        ready.await();
        start.countDown();
        for (Thread t:users) t.join();
        System.out.println("=== Q5 SYNCHRONIZED SEAT BOOKING ===");
        System.out.println("Seats sold: "+screen.bookingsMade);
        System.out.println("Double-booked seats: "+(screen.bookingsMade-10));
        System.out.println("Rejected competing attempts: "+screen.doubleBookingAttempts.get());
    }
}
