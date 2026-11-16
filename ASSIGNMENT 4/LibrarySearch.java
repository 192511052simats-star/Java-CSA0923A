
import javax.swing.*;
import java.awt.*;

public class LibrarySearch extends JFrame {

    JTextField searchField;
    JComboBox<String> category;
    JTextArea results;
    JLabel status;

    LibrarySearch() {

        setTitle("Campus Library Search");
        setLayout(new BorderLayout(5, 5));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(20);

        category = new JComboBox<>(new String[]{
            "All", "Books", "Authors", "Subjects"
        });

        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField);
        searchPanel.add(category);
        searchPanel.add(searchButton);

        // Results Panel
        JPanel resultsPanel = new JPanel(new BorderLayout());

        results = new JTextArea();
        results.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(results);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton reserveButton = new JButton("Reserve");

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(reserveButton);

        status = new JLabel("Ready to search");

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(status);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        // Add panels to main window
        add(searchPanel, BorderLayout.NORTH);
        add(resultsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Search Button
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();

            if (keyword.trim().isEmpty()) {
                status.setText("Enter a search word");
            } else {
                results.setText(
                    "Search Results for: " + keyword +
                    "\n1. Java Programming\n2. Data Structures\n3. Computer Networks"
                );
                status.setText("Search completed");
            }
        });

        prevButton.addActionListener(e ->
            status.setText("Previous page selected"));

        nextButton.addActionListener(e ->
            status.setText("Next page selected"));

        reserveButton.addActionListener(e ->
            status.setText("Book reserved successfully"));

        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearch());
    }
}
