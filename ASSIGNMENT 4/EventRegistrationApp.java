
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class EventRegistrationApp extends JFrame {

    JTextField id, name, email;
    JComboBox<String> event, filter;
    JTable table;
    DefaultTableModel model;

    ArrayList<String[]> registrations = new ArrayList<>();

    public EventRegistrationApp() {

        setTitle("Campus Event Registration");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem registerItem = new JMenuItem("Registration");
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        JMenuItem exitItem = new JMenuItem("Exit");

        menu.add(registerItem);
        menu.add(dashboardItem);
        menu.add(exitItem);
        bar.add(menu);

        setJMenuBar(bar);

        // Registration Form
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        id = new JTextField();
        name = new JTextField();
        email = new JTextField();

        event = new JComboBox<>(new String[]{
            "Dance", "Music", "Drama", "Sports"
        });

        JButton addButton = new JButton("Register");
        JButton clearButton = new JButton("Clear");

        form.add(new JLabel("Student ID:"));
        form.add(id);

        form.add(new JLabel("Student Name:"));
        form.add(name);

        form.add(new JLabel("Email:"));
        form.add(email);

        form.add(new JLabel("Event:"));
        form.add(event);

        form.add(addButton);
        form.add(clearButton);

        // Dashboard Table
        model = new DefaultTableModel(
            new String[]{"Student ID", "Name", "Email", "Event"}, 0
        );

        table = new JTable(model);

        filter = new JComboBox<>(new String[]{
            "All Events", "Dance", "Music", "Drama", "Sports"
        });

        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter Event:"));
        filterPanel.add(filter);

        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.add(filterPanel, BorderLayout.NORTH);
        dashboardPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Export Button
        JButton exportButton = new JButton("Export CSV");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(exportButton);

        dashboardPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(form, BorderLayout.NORTH);
        add(dashboardPanel, BorderLayout.CENTER);

        // Button Actions
        addButton.addActionListener(e -> registerStudent());

        clearButton.addActionListener(e -> clearFields());

        filter.addActionListener(e -> showRecords());

        registerItem.addActionListener(e -> id.requestFocus());

        dashboardItem.addActionListener(e -> showRecords());

        exitItem.addActionListener(e -> dispose());

        exportButton.addActionListener(e -> exportCSV());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Register Student
    void registerStudent() {

        String studentId = id.getText().trim();
        String studentName = name.getText().trim();
        String studentEmail = email.getText().trim();
        String selectedEvent = (String) event.getSelectedItem();

        if (studentId.isEmpty() ||
            studentName.isEmpty() ||
            studentEmail.isEmpty()) {

            JOptionPane.showMessageDialog(
                this, "All fields are required."
            );
            return;
        }

        if (!studentEmail.matches(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                this, "Enter a valid email address."
            );
            return;
        }

        // Duplicate Check
        for (String[] record : registrations) {

            if (record[0].equals(studentId) &&
                record[3].equals(selectedEvent)) {

                JOptionPane.showMessageDialog(
                    this,
                    "Student already registered for this event."
                );
                return;
            }
        }

        registrations.add(new String[]{
            studentId, studentName, studentEmail, selectedEvent
        });

        JOptionPane.showMessageDialog(
            this, "Registration successful!"
        );

        clearFields();
        showRecords();
    }

    // Display and Filter Records
    void showRecords() {

        model.setRowCount(0);

        String selected = (String) filter.getSelectedItem();

        for (String[] record : registrations) {

            if (selected.equals("All Events") ||
                selected.equals(record[3])) {

                model.addRow(record);
            }
        }
    }

    // Clear Fields
    void clearFields() {

        id.setText("");
        name.setText("");
        email.setText("");
        event.setSelectedIndex(0);
    }

    // Export CSV
    void exportCSV() {

        try (PrintWriter out =
                 new PrintWriter(new FileWriter("registrations.csv"))) {

            out.println("Student ID,Name,Email,Event");

            for (String[] record : registrations) {

                out.println(
                    "\"" + record[0] + "\"," +
                    "\"" + record[1] + "\"," +
                    "\"" + record[2] + "\"," +
                    "\"" + record[3] + "\""
                );
            }

            JOptionPane.showMessageDialog(
                this, "CSV exported successfully!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this, "Export failed."
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
            new EventRegistrationApp()
        );
    }
}
