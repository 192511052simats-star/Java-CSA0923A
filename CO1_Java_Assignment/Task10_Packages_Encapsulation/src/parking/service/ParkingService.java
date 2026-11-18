package parking.service;

import parking.model.ParkingSlot;
import parking.model.ParkingTicket;

public class ParkingService {
    private final ParkingSlot[][] slots;
    private int nextTicketNumber = 5000;

    public ParkingService(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) {
            throw new IllegalArgumentException("At least one floor is required.");
        }
        slots = new ParkingSlot[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] <= 0) {
                throw new IllegalArgumentException("Each floor needs a slot.");
            }
            slots[f] = new ParkingSlot[slotsPerFloor[f]];
            for (int s = 0; s < slotsPerFloor[f]; s++) {
                slots[f][s] = new ParkingSlot(f + 1, s + 1);
            }
        }
    }

    public ParkingTicket park(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            throw new IllegalArgumentException("Vehicle number is required.");
        }
        for (ParkingSlot[] floor : slots) {
            for (ParkingSlot slot : floor) {
                if (slot.isFree()) {
                    slot.park(vehicleNumber);
                    return new ParkingTicket(++nextTicketNumber, vehicleNumber,
                            slot.getFloorNumber(), slot.getSlotNumber());
                }
            }
        }
        return null;
    }

    public void displaySlots() {
        for (ParkingSlot[] floor : slots) {
            for (ParkingSlot slot : floor) {
                System.out.println(slot);
            }
        }
    }
}
