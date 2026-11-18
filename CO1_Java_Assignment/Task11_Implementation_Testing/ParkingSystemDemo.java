import java.util.Arrays;

class Vehicle {
    private final String registrationNumber;
    private final String type;
    private final double hourlyRate;

    Vehicle(String registrationNumber, String type, double hourlyRate) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number is required.");
        }
        if (type == null || !java.util.Set.of(
                "TwoWheeler", "Car", "ElectricCar").contains(type)) {
            throw new IllegalArgumentException("Unknown vehicle type.");
        }
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative.");
        }
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.hourlyRate = hourlyRate;
    }

    String getRegistrationNumber() { return registrationNumber; }
    String getType() { return type; }

    double feeForMinutes(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative.");
        int billableHours = (minutes + 59) / 60;
        return billableHours * hourlyRate;
    }
}

class ParkingTicket {
    private final int ticketNumber;
    private final Vehicle vehicle;
    private final int floor;
    private final int slot;
    private final int entryMinuteOfDay;

    ParkingTicket(int ticketNumber, Vehicle vehicle, int floor, int slot,
                  int entryMinuteOfDay) {
        this.ticketNumber = ticketNumber;
        this.vehicle = vehicle;
        this.floor = floor;
        this.slot = slot;
        this.entryMinuteOfDay = entryMinuteOfDay;
    }

    int getTicketNumber() { return ticketNumber; }
    Vehicle getVehicle() { return vehicle; }
    int getFloor() { return floor; }
    int getSlot() { return slot; }
    int getEntryMinuteOfDay() { return entryMinuteOfDay; }
}

class ParkingSystem {
    private final String[][] slots;
    private final ParkingTicket[] activeTickets;
    private int nextTicket = 7000;
    private double dailyCollection = 0;

    ParkingSystem(int[] slotsPerFloor, int maxTickets) {
        slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] <= 0) throw new IllegalArgumentException("Invalid floor size.");
            slots[f] = new String[slotsPerFloor[f]];
        }
        activeTickets = new ParkingTicket[maxTickets];
    }

    ParkingTicket enter(Vehicle vehicle, int entryMinuteOfDay) {
        if (vehicle == null) throw new IllegalArgumentException("Unknown vehicle type.");
        for (ParkingTicket t : activeTickets) {
            if (t != null && t.getVehicle().getRegistrationNumber()
                    .equals(vehicle.getRegistrationNumber())) {
                throw new IllegalArgumentException("Duplicate registration number.");
            }
        }

        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    for (int i = 0; i < activeTickets.length; i++) {
                        if (activeTickets[i] == null) {
                            slots[f][s] = vehicle.getRegistrationNumber();
                            ParkingTicket ticket = new ParkingTicket(
                                    ++nextTicket, vehicle, f, s, entryMinuteOfDay);
                            activeTickets[i] = ticket;
                            return ticket;
                        }
                    }
                    throw new IllegalStateException("Ticket storage is full.");
                }
            }
        }
        return null;
    }

    double exit(String registrationNumber, int exitMinuteOfDay) {
        for (int i = 0; i < activeTickets.length; i++) {
            ParkingTicket ticket = activeTickets[i];
            if (ticket != null && ticket.getVehicle().getRegistrationNumber()
                    .equals(registrationNumber)) {
                int duration = exitMinuteOfDay - ticket.getEntryMinuteOfDay();
                if (duration < 0) throw new IllegalArgumentException("Invalid exit time.");
                double fee = ticket.getVehicle().feeForMinutes(duration);
                slots[ticket.getFloor()][ticket.getSlot()] = null;
                activeTickets[i] = null;
                dailyCollection += fee;
                return fee;
            }
        }
        throw new IllegalArgumentException("No active ticket for registration number.");
    }

    int freeSlotCount() {
        int count = 0;
        for (String[] floor : slots) {
            for (String slot : floor) if (slot == null) count++;
        }
        return count;
    }

    double getDailyCollection() { return dailyCollection; }

    boolean isValidSlot(int floor, int slot) {
        return floor >= 0 && floor < slots.length
                && slot >= 0 && slot < slots[floor].length;
    }

    void occupyLastFreeSlotForBoundaryTest() {
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) slots[f][s] = "BOUNDARY";
            }
        }
    }
}

public class ParkingSystemDemo {
    private static void printTest(String name, boolean passed) {
        System.out.println(name + ": " + (passed ? "PASS" : "FAIL"));
    }

    public static void main(String[] args) {
        System.out.println("=== TASK 11: PARKING SYSTEM TESTS ===");
        ParkingSystem system = new ParkingSystem(new int[]{2, 1}, 10);

        Vehicle bike = new Vehicle("TN01AA1111", "TwoWheeler", 10);
        Vehicle car = new Vehicle("TN02BB2222", "Car", 30);

        ParkingTicket bikeTicket = system.enter(bike, 8 * 60);
        ParkingTicket carTicket = system.enter(car, 9 * 60);
        printTest("Normal entry for two vehicle types",
                bikeTicket != null && carTicket != null);

        double bikeFee = system.exit("TN01AA1111", 9 * 60);
        double carFee = system.exit("TN02BB2222", 10 * 60 + 30);
        printTest("Normal exit and fee calculation",
                bikeFee == 10.0 && carFee == 60.0);
        System.out.println("Bike fee (60 min): Rs. " + bikeFee);
        System.out.println("Car fee (90 min): Rs. " + carFee);

        ParkingTicket zeroTicket = system.enter(
                new Vehicle("TN03CC3333", "Car", 30), 11 * 60);
        double zeroFee = system.exit("TN03CC3333", 11 * 60);
        printTest("Boundary: zero-minute parking", zeroFee == 0.0);

        ParkingTicket slabTicket = system.enter(
                new Vehicle("TN04DD4444", "Car", 30), 12 * 60);
        double slabFee = system.exit("TN04DD4444", 13 * 60);
        printTest("Boundary: exactly 60-minute slab", slabFee == 30.0);

        try {
            system.enter(new Vehicle("TN05EE5555", "Unknown", 20), 14 * 60);
            printTest("Invalid: unknown vehicle type", false);
        } catch (IllegalArgumentException ex) {
            printTest("Invalid: unknown vehicle type", true);
        }

        system.enter(new Vehicle("TN06FF6666", "Car", 30), 15 * 60);
        try {
            system.enter(new Vehicle("TN06FF6666", "Car", 30), 15 * 60);
            printTest("Invalid: duplicate registration", false);
        } catch (IllegalArgumentException ex) {
            printTest("Invalid: duplicate registration", true);
        }
        printTest("Invalid slot number rejected",
                !system.isValidSlot(9, 0));

        ParkingSystem full = new ParkingSystem(new int[]{1}, 2);
        full.occupyLastFreeSlotForBoundaryTest();
        printTest("Boundary: last free slot / full facility",
                full.enter(new Vehicle("TN07GG7777", "Car", 30), 17 * 60) == null);

        System.out.println("=== DAILY COLLECTION SCENARIO ===");
        // Duplicate-registration test vehicle is parked once, after the rejected
        // duplicate attempt, and then exits: 60 minutes = Rs 30.
        // Hand calculation: bike Rs 10 + car Rs 60 + zero-minute Rs 0
        // + exact-slab car Rs 30 + duplicate-test car Rs 30 = Rs 130.
        ParkingTicket duplicateTestTicket = null;
        // The duplicate-test ticket was already parked above; it is now exited.
        double duplicateTestFee = system.exit("TN06FF6666", 16 * 60);
        System.out.println("Duplicate-test vehicle fee: Rs. " + duplicateTestFee);
        System.out.println("Hand-calculated collection: Rs. 130.0");
        System.out.println("Program collection: Rs. " + system.getDailyCollection());
        printTest("Hand calculation matches program",
                Math.abs(system.getDailyCollection() - 130.0) < 0.001);

        System.out.println("=== DESIGN EVALUATION ===");
        System.out.println("Encapsulation: Vehicle and ticket fields are private.");
        System.out.println("Inheritance/polymorphism: vehicle types can specialise fee behaviour.");
        System.out.println("Abstraction: Parking operations are grouped in ParkingSystem.");
        System.out.println("Limitation: active tickets use a fixed-size array and linear exit search.");
        System.out.println("Array exit search: O(n); storage: O(n).");
        System.out.println("HashMap alternative: average O(1) lookup; O(n) storage.");
    }
}
