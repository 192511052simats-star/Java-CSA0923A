import java.time.LocalTime;

interface Pass {
    boolean isValidForParking();
    double discount(double normalFee);
}

class OneDayVisitorPass implements Pass {
    private final String visitorName;
    private final LocalTime validUntil;

    OneDayVisitorPass(String visitorName, LocalTime validUntil) {
        if (visitorName == null || visitorName.isBlank()) {
            throw new IllegalArgumentException("Visitor name is required.");
        }
        this.visitorName = visitorName;
        this.validUntil = validUntil;
    }

    public boolean isValidForParking() {
        return LocalTime.now().isBefore(validUntil);
    }

    public double discount(double normalFee) {
        return isValidForParking() ? 0.0 : normalFee;
    }

    public String getVisitorName() { return visitorName; }
}

abstract class Vehicle {
    private final String registrationNumber;

    Vehicle(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number is required.");
        }
        this.registrationNumber = registrationNumber;
    }

    String getRegistrationNumber() { return registrationNumber; }
    abstract String category();
}

class Car extends Vehicle {
    Car(String reg) { super(reg); }
    String category() { return "Car"; }
}

class ElectricCar extends Vehicle {
    ElectricCar(String reg) { super(reg); }
    String category() { return "ElectricCar"; }
}

class EBike extends Vehicle {
    EBike(String reg) { super(reg); }
    String category() { return "EBike"; }
}

class DeliveryVan extends Vehicle {
    DeliveryVan(String reg) { super(reg); }
    String category() { return "DeliveryVan"; }
}

interface PricingRule {
    double calculate(Vehicle vehicle, int minutes, LocalTime entryTime);
}

class StandardPricingRule implements PricingRule {
    public double calculate(Vehicle vehicle, int minutes, LocalTime entryTime) {
        int hours = (minutes + 59) / 60;
        double rate = vehicle instanceof Car || vehicle instanceof ElectricCar ? 30.0 : 10.0;
        return hours * rate;
    }
}

class EBikeFlatPricingRule implements PricingRule {
    private final double flatFee;
    EBikeFlatPricingRule(double flatFee) { this.flatFee = flatFee; }

    public double calculate(Vehicle vehicle, int minutes, LocalTime entryTime) {
        return flatFee;
    }
}

class DeliveryVanPricingRule implements PricingRule {
    private final double carRate;
    DeliveryVanPricingRule(double carRate) { this.carRate = carRate; }

    public double calculate(Vehicle vehicle, int minutes, LocalTime entryTime) {
        int hours = (minutes + 59) / 60;
        boolean peakWindow = !entryTime.isBefore(LocalTime.of(9, 0))
                && entryTime.isBefore(LocalTime.of(17, 0));
        double rate = peakWindow ? carRate * 2 : carRate;
        return hours * rate;
    }
}

class ParkingFacility {
    private final String[][] slots;

    ParkingFacility(int[] slotsPerFloor) {
        slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] <= 0) {
                throw new IllegalArgumentException("Each floor must have at least one slot.");
            }
            slots[f] = new String[slotsPerFloor[f]];
        }
    }

    int[] allocate(String registrationNumber) {
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = registrationNumber;
                    return new int[]{f + 1, s + 1};
                }
            }
        }
        return null;
    }

    int totalSlots() {
        int total = 0;
        for (String[] floor : slots) total += floor.length;
        return total;
    }

    void printFloorCapacities() {
        for (int i = 0; i < slots.length; i++) {
            System.out.println("Floor " + (i + 1) + " slots: " + slots[i].length);
        }
    }
}

public class DesignChallengeDemo {
    public static void main(String[] args) {
        System.out.println("=== TASK 12: DESIGN ADAPTATION ===");

        ParkingFacility facility = new ParkingFacility(
                new int[]{20, 25, 30, 35, 40});
        facility.printFloorCapacities();
        System.out.println("Total slots: " + facility.totalSlots());

        Vehicle eBike = new EBike("TN10EB1001");
        Vehicle van = new DeliveryVan("TN10DV2002");
        Vehicle car = new Car("TN10CA3003");

        PricingRule eBikeRule = new EBikeFlatPricingRule(15.0);
        PricingRule vanRule = new DeliveryVanPricingRule(30.0);
        PricingRule standardRule = new StandardPricingRule();

        System.out.println("E-bike flat fee: Rs. "
                + eBikeRule.calculate(eBike, 180, LocalTime.of(10, 0)));
        System.out.println("Delivery van fee during 09:00-17:00: Rs. "
                + vanRule.calculate(van, 60, LocalTime.of(10, 0)));
        System.out.println("Delivery van fee outside 09:00-17:00: Rs. "
                + vanRule.calculate(van, 60, LocalTime.of(18, 0)));
        System.out.println("Standard car fee for 2 hours: Rs. "
                + standardRule.calculate(car, 120, LocalTime.of(10, 0)));

        OneDayVisitorPass pass = new OneDayVisitorPass(
                "Campus Visitor", LocalTime.MAX);
        System.out.println("One-day visitor pass valid: "
                + pass.isValidForParking());
        System.out.println("Visitor fee after pass discount on Rs. 60: Rs. "
                + pass.discount(60.0));

        System.out.println("E-bike allocated at floor/slot: "
                + java.util.Arrays.toString(
                    facility.allocate(eBike.getRegistrationNumber())));
        System.out.println("Delivery van allocated at floor/slot: "
                + java.util.Arrays.toString(
                    facility.allocate(van.getRegistrationNumber())));

        System.out.println("Decision: MODIFY original design.");
        System.out.println("Existing Vehicle hierarchy retained; new categories and pricing rules added.");
        System.out.println("Unequal floor capacities are represented by a jagged array.");
    }
}
