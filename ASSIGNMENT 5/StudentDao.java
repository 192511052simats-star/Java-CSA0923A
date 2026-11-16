import java.sql.*;
import java.util.*;

public class StudentDao implements Dao<Student, Integer> {

    Connection con;

    public StudentDao(Connection con) {
        this.con = con;
    }

    public void insert(Student s) throws SQLException {
        String sql = "INSERT INTO student VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, s.id);
        ps.setString(2, s.name);
        ps.setString(3, s.email);
        ps.executeUpdate();
    }

    public Student findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM student WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email")
            );
        }
        return null;
    }

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM student");

        while (rs.next()) {
            list.add(new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email")
            ));
        }
        return list;
    }

    public void update(Student s) throws SQLException {
        String sql = "UPDATE student SET name=?, email=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, s.name);
        ps.setString(2, s.email);
        ps.setInt(3, s.id);
        ps.executeUpdate();
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM student WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}