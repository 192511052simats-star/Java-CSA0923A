import java.sql.*;
import java.nio.file.*;
import java.util.*;

public class B4Migration {
    public static void main(String[] args) throws Exception {
        Path input = Path.of("passes_to_migrate.csv");
        if (!Files.exists(input)) {
            try (var w=Files.newBufferedWriter(input)) {
                for(int i=1;i<=100;i++)
                    w.write("M"+i+",REG"+i+",Student"+i+","+(i%12+1)+","+(3000+i)+"\n");
                w.write("BAD1,REGX,Invalid,not-route,3000\n");
                w.write("BAD2,REGY,Invalid,3,not-amount\n");
            }
            System.out.println("Generated input file: "+input.toAbsolutePath());
        }
        List<String[]> good=new ArrayList<>();
        List<String> failures=new ArrayList<>();
        int lineNo=0;
        for(String line:Files.readAllLines(input)) {
            lineNo++; String[] f=line.split(",",-1);
            try {
                if(f.length!=5 || f[0].isBlank() || f[1].isBlank() || f[2].isBlank())
                    throw new IllegalArgumentException("wrong field count/empty field");
                Integer.parseInt(f[3]); Double.parseDouble(f[4]);
                good.add(f);
            } catch(RuntimeException ex) { failures.add("Line "+lineNo+": "+line+" ("+ex.getMessage()+")"); }
        }
        try(Connection c=CO5Config.getConnection()) {
            try(Statement s=c.createStatement()) {
                s.executeUpdate("CREATE TABLE IF NOT EXISTS bus_pass (pass_id VARCHAR(30) PRIMARY KEY, reg_no VARCHAR(30) NOT NULL, name VARCHAR(100) NOT NULL, route_no INT NOT NULL, amount DOUBLE NOT NULL)");
            }
            c.setAutoCommit(false);
            String sql="INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)";
            int imported=0;
            try(PreparedStatement ps=c.prepareStatement(sql)) {
                for(String[] f:good) {
                    try {
                        ps.setString(1,f[0]);ps.setString(2,f[1]);ps.setString(3,f[2]);
                        ps.setInt(4,Integer.parseInt(f[3]));ps.setDouble(5,Double.parseDouble(f[4]));
                        ps.addBatch(); imported++;
                        if(imported%1000==0) ps.executeBatch();
                    } catch(SQLException ex) { failures.add("Database row "+f[0]+": "+ex.getMessage()); }
                }
                ps.executeBatch(); c.commit();
            } catch(Exception ex) { c.rollback(); throw ex; }
            finally { c.setAutoCommit(true); }
            System.out.println("Valid rows submitted: "+imported);
            System.out.println("Validation/database failures: "+failures.size());
            for(String failure:failures) System.out.println(failure);

            DatabaseMetaData md=c.getMetaData();
            System.out.println("Database product: "+md.getDatabaseProductName()+" "+md.getDatabaseProductVersion());
            System.out.println("JDBC driver: "+md.getDriverName()+" "+md.getDriverVersion());
            System.out.println("Tables:");
            try(ResultSet rs=md.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})) {
                while(rs.next()) System.out.println(" - "+rs.getString("TABLE_NAME"));
            }
        }
    }
}
