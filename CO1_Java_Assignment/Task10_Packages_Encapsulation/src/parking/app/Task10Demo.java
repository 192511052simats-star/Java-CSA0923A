package parking.app;

import parking.model.ParkingTicket;
import parking.service.ParkingService;

public class Task10Demo {
    public static void main(String[] args) {
        System.out.println("=== PACKAGE AND ENCAPSULATION DEMO ===");

        ParkingService service = new ParkingService(new int[]{2, 1});
        ParkingTicket ticket = service.park("TN10AA1234");

        System.out.println("Ticket: " + ticket.getTicketNumber());
        System.out.println("Vehicle: " + ticket.getVehicleNumber());
        System.out.println("Allocated floor: " + ticket.getFloorNumber());
        System.out.println("Allocated slot: " + ticket.getSlotNumber());

        ticket.setDurationMinutes(90); // int autoboxed to Integer
        System.out.println("Duration (Integer): " + ticket.getDurationMinutes());
        System.out.println("Duration as int: " + ticket.durationAsPrimitive());

        Integer first = 1000;
        Integer second = 1000;
        System.out.println("Integer objects compared with ==: " + (first == second));
        System.out.println("Integer objects compared with equals(): "
                + first.equals(second));

        try {
            ticket.setDurationMinutes(-5);
        } catch (IllegalArgumentException ex) {
            System.out.println("Validation: " + ex.getMessage());
        }

        try {
            ticket.setSlotNumber(0);
        } catch (IllegalArgumentException ex) {
            System.out.println("Validation: " + ex.getMessage());
        }

        System.out.println("=== SLOT STATUS ===");
        service.displaySlots();
    }
}
