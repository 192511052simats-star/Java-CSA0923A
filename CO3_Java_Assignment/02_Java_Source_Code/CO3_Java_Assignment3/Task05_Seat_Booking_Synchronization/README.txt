Q5 – Race Conditions in a Cinema Seat-Booking System
Compile: javac SeatBooking.java
Run:     java SeatBooking

The supplied program completes TODO 1 using a synchronized method and uses a start gate to make 100 threads compete for 10 seats.
Expected invariant: Seats sold: 10.
For the required experiment, create an unsynchronized version (remove synchronized), run it at least ten times, record double-booking outcomes, then compare synchronized method and synchronized-block versions. Do not assume every unsynchronized run will reproduce the race; timing/interleaving matters.
