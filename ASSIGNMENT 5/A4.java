import java.io.*;
import java.sql.Connection;
import java.util.*;

class Resident implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    String registerNumber;
    String phoneNumber;

    transient String password;
    transient Connection connection;

    Resident(String name, String registerNumber, String phoneNumber,
             String password) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}

class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    int roomNumber;
    ArrayList<Resident> residents = new ArrayList<>();

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}

class Hostel implements Serializable {
    private static final long serialVersionUID = 1L;

    String hostelName;
    ArrayList<Room> rooms = new ArrayList<>();

    Hostel(String hostelName) {
        this.hostelName = hostelName;
    }
}

public class A4 {
    public static void main(String[] args) {
        try {
            Hostel hostel = new Hostel("Saveetha Hostel");

            Room room = new Room(101);

            Resident r = new Resident(
                "Arun", "192311001", "9876543210", "pass123"
            );

            room.residents.add(r);
            hostel.rooms.add(room);

            ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream("hostel.dat"));

            out.writeObject(hostel);
            out.close();

            ObjectInputStream in =
                new ObjectInputStream(new FileInputStream("hostel.dat"));

            Hostel h = (Hostel) in.readObject();
            in.close();

            System.out.println("Hostel: " + h.hostelName);
            System.out.println("Room: " + h.rooms.get(0).roomNumber);
            System.out.println("Student: " +
                h.rooms.get(0).residents.get(0).name);
            System.out.println("Register Number: " +
                h.rooms.get(0).residents.get(0).registerNumber);
            System.out.println("Phone Number: " +
                h.rooms.get(0).residents.get(0).phoneNumber);
            System.out.println("Password: " +
                h.rooms.get(0).residents.get(0).password);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}