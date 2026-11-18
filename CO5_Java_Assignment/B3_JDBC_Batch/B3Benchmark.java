import java.sql.*;
import java.util.*;

public class B3Benchmark {
    static List<String[]> generate(int n, String prefix) {
        List<String[]> rows = new ArrayList<>(n);
        for (int i=0;i<n;i++) rows.add(new String[]{prefix+i,"REG"+i,"Student"+i,
                String.valueOf((i%20)+1),String.valueOf(100.0+(i%500))});
        return rows;
    }
    static void clear(Connection c) throws SQLException {
        try (Statement s=c.createStatement()) { s.executeUpdate("DELETE FROM bus_pass"); }
    }
    static long statementInsert(Connection c, List<String[]> rows) throws SQLException {
        long t=System.nanoTime();
        try (Statement s=c.createStatement()) {
            for (String[] r:rows) {
                String sql="INSERT INTO bus_pass VALUES ('"+r[0]+"','"+r[1]+"','"+r[2]+"',"+r[3]+","+r[4]+")";
                s.executeUpdate(sql);
            }
        }
        return System.nanoTime()-t;
    }
    static long preparedInsert(Connection c, List<String[]> rows) throws SQLException {
        long t=System.nanoTime();
        try (PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass VALUES (?,?,?,?,?)")) {
            for(String[] r:rows) {
                p.setString(1,r[0]);p.setString(2,r[1]);p.setString(3,r[2]);
                p.setInt(4,Integer.parseInt(r[3]));p.setDouble(5,Double.parseDouble(r[4]));p.executeUpdate();
            }
        }
        return System.nanoTime()-t;
    }
    static long batchInsert(Connection c, List<String[]> rows) throws SQLException {
        long t=System.nanoTime();
        PassLoader.loadAll(c, rows);
        return System.nanoTime()-t;
    }
    public static void main(String[] args) throws Exception {
        try(Connection c=CO5Config.getConnection()) {
            try(Statement s=c.createStatement()) {
                s.executeUpdate("CREATE TABLE IF NOT EXISTS bus_pass (pass_id VARCHAR(30) PRIMARY KEY, reg_no VARCHAR(30) NOT NULL, name VARCHAR(100) NOT NULL, route_no INT NOT NULL, amount DOUBLE NOT NULL)");
            }
            for(int n:new int[]{1000,10000,100000}) {
                List<String[]> rows=generate(n,"S"+n+"-");
                clear(c); long a=statementInsert(c,rows);
                System.out.printf("%,d Statement: %.2f ms%n",n,a/1e6);
                clear(c); long b=preparedInsert(c,rows);
                System.out.printf("%,d PreparedStatement: %.2f ms%n",n,b/1e6);
                clear(c); long d=batchInsert(c,rows);
                System.out.printf("%,d PreparedStatement batch: %.2f ms%n",n,d/1e6);
            }
            try(Statement s=c.createStatement(); ResultSet rs=s.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
                rs.next(); System.out.println("Final batch row count: "+rs.getInt(1));
            }
            try(Statement s=c.createStatement(); ResultSet rs=s.executeQuery("SELECT * FROM bus_pass LIMIT 1")) {
                ResultSetMetaData md=rs.getMetaData();
                System.out.println("Metadata columns:");
                for(int i=1;i<=md.getColumnCount();i++)
                    System.out.println(md.getColumnName(i)+" : "+md.getColumnTypeName(i));
            }
            // Force a mid-transaction failure; all rows in this attempted transaction roll back.
            clear(c);
            List<String[]> bad=generate(20,"FAIL-"); bad.set(10,new String[]{"BAD-ROW","R","Bad","2","not-a-number"});
            try { PassLoader.loadAll(c,bad); } catch(RuntimeException ex) {
                System.out.println("Forced failure: "+ex.getClass().getSimpleName());
            }
            try(Statement s=c.createStatement();ResultSet rs=s.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
                rs.next(); System.out.println("Rows after failed transaction: "+rs.getInt(1));
            }
        }
    }
}
