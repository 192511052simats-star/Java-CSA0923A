import java.sql.*;
import java.nio.file.*;
import java.util.*;

public class B2SecurityDemo {
    static boolean unsafeLogin(String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        try (Connection c = CO5Config.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next();
        }
    }
    static boolean safeLogin(String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        try (Connection c = CO5Config.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id); ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Valid login unsafe: " + unsafeLogin("student1", "pass123"));
        System.out.println("Valid login prepared: " + safeLogin("student1", "pass123"));
        String attack = "' OR '1'='1' -- ";
        System.out.println("Adversarial unsafe login: " + unsafeLogin("student1", attack));
        System.out.println("Adversarial prepared login: " + safeLogin("student1", attack));

        Path damaged = Files.createTempFile("co5-damaged", ".csv");
        Files.write(damaged, List.of(
            "P1,R1,Name,4,100.0",
            "P2,TOO,FEW",
            "P3,R3,Name,4,not-a-number",
            "",
            "P4,R4,Valid,5,250.0"
        ));
        int valid = 0, rejected = 0, lineNo = 0;
        for (String line : Files.readAllLines(damaged)) {
            lineNo++;
            String[] f = line.split(",", -1);
            try {
                if (f.length != 5 || f[0].isBlank() || f[1].isBlank()) throw new IllegalArgumentException();
                Integer.parseInt(f[3]); Double.parseDouble(f[4]); valid++;
            } catch (RuntimeException ex) {
                rejected++; System.out.println("Rejected damaged line " + lineNo + ": " + line);
            }
        }
        System.out.println("Valid rows: " + valid + ", rejected rows: " + rejected);
        Files.deleteIfExists(damaged);
    }
}
