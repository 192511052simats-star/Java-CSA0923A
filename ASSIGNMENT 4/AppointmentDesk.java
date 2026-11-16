
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AppointmentDesk extends JFrame {

    JTextField patientId;
    JComboBox<String> department;
    JComboBox<String> doctor;
    JComboBox<String> time;
    JButton book, clear;

    Map<String, String[]> doctors = new HashMap<>();

    AppointmentDesk() {

        setTitle("Hospital Appointment Desk");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        doctors.put("Cardiology",
                new String[]{"Dr. Kumar", "Dr. Ravi"});
        doctors.put("Neurology",
                new String[]{"Dr. Arun", "Dr. Priya"});
        doctors.put("Orthopedics",
                new String[]{"Dr. Raj", "Dr. Meena"});

        patientId = new JTextField();

        department = new JComboBox<>(new String[]{
            "Cardiology", "Neurology", "Orthopedics"
        });

        doctor = new JComboBox<>();

        time = new JComboBox<>(new String[]{
            "10:00 AM", "11:00 AM", "12:00 PM"
        });

        book = new JButton("Book");
        clear = new JButton("Clear");

        add(new JLabel("Patient ID:"));
        add(patientId);

        add(new JLabel("Department:"));
        add(department);

        add(new JLabel("Doctor:"));
        add(doctor);

        add(new JLabel("Time Slot:"));
        add(time);

        add(book);
        add(clear);

        updateDoctors();

        department.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateDoctors();
            }
        });

        book.addActionListener(e -> {
            if (patientId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "Please enter Patient ID");
            } else {
                JOptionPane.showMessageDialog(
                    this, "Appointment Booked Successfully!");
            }
        });

        clear.addActionListener(e -> {
            patientId.setText("");
            department.setSelectedIndex(0);
            time.setSelectedIndex(0);
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    AppointmentDesk.this,
                    "Are you sure you want to close?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void updateDoctors() {
        doctor.removeAllItems();

        String selected =
            (String) department.getSelectedItem();

        if (selected != null) {
            for (String d : doctors.get(selected)) {
                doctor.addItem(d);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppointmentDesk();
        });
    }
}
