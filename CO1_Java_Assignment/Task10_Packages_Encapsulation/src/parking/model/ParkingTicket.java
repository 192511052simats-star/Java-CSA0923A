package parking.model;

public class ParkingTicket {
    private final int ticketNumber;
    private final String vehicleNumber;
    private int floorNumber;
    private int slotNumber;
    private Integer durationMinutes;

    public ParkingTicket(int ticketNumber, String vehicleNumber,
                         int floorNumber, int slotNumber) {
        if (ticketNumber <= 0) {
            throw new IllegalArgumentException("Ticket number must be positive.");
        }
        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            throw new IllegalArgumentException("Vehicle number is required.");
        }
        this.ticketNumber = ticketNumber;
        this.vehicleNumber = vehicleNumber.trim();
        setFloorNumber(floorNumber);
        setSlotNumber(slotNumber);
        this.durationMinutes = Integer.valueOf(0); // autoboxing
    }

    public int getTicketNumber() { return ticketNumber; }
    public String getVehicleNumber() { return vehicleNumber; }
    public int getFloorNumber() { return floorNumber; }
    public int getSlotNumber() { return slotNumber; }
    public Integer getDurationMinutes() { return durationMinutes; }

    public void setFloorNumber(int floorNumber) {
        if (floorNumber < 1) {
            throw new IllegalArgumentException("Invalid floor number.");
        }
        this.floorNumber = floorNumber;
    }

    public void setSlotNumber(int slotNumber) {
        if (slotNumber < 1) {
            throw new IllegalArgumentException("Invalid slot number.");
        }
        this.slotNumber = slotNumber;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        if (durationMinutes == null || durationMinutes < 0) {
            throw new IllegalArgumentException("Duration must be zero or greater.");
        }
        this.durationMinutes = durationMinutes; // unboxing/assignment
    }

    public int durationAsPrimitive() {
        return durationMinutes; // unboxing
    }
}
