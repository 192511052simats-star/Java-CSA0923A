package parking.model;

public class ParkingSlot {
    private final int floorNumber;
    private final int slotNumber;
    private String vehicleNumber;

    public ParkingSlot(int floorNumber, int slotNumber) {
        if (floorNumber < 1) {
            throw new IllegalArgumentException("Floor number must be positive.");
        }
        if (slotNumber < 1) {
            throw new IllegalArgumentException("Slot number must be positive.");
        }
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
    }

    public int getFloorNumber() { return floorNumber; }
    public int getSlotNumber() { return slotNumber; }
    public String getVehicleNumber() { return vehicleNumber; }
    public boolean isFree() { return vehicleNumber == null; }

    public void park(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            throw new IllegalArgumentException("Vehicle number is required.");
        }
        if (!isFree()) {
            throw new IllegalStateException("Slot is already occupied.");
        }
        this.vehicleNumber = vehicleNumber.trim();
    }

    public void release() {
        if (isFree()) {
            throw new IllegalStateException("Slot is already free.");
        }
        vehicleNumber = null;
    }

    @Override
    public String toString() {
        return "Floor " + floorNumber + ", Slot " + slotNumber
                + ", Vehicle " + (isFree() ? "FREE" : vehicleNumber);
    }
}
