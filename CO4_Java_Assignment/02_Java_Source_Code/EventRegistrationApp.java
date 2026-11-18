import javax.swing.*; import javax.swing.table.*; import java.awt.*; import java.io.*; import java.sql.*; import java.util.*;
public class EventRegistrationApp extends JFrame {
 private static final String URL="jdbc:mysql://localhost:3306/campus_events",USER="root",PASSWORD="CHANGE_ME";
 private final JTextField id=new JTextField(10),name=new JTextField(14);
 private final JComboBox<String> event=new JComboBox<>(new String[]{"Cultural Fest","Tech Symposium","Sports Day"});
 private final JComboBox<String> filter=new JComboBox<>(new String[]{"All","Cultural Fest","Tech Symposium","Sports Day"});
 private final DefaultTableModel model=new DefaultTableModel(new String[]{"Student ID","Name","Event"},0){public boolean isCellEditable(int r,int c){return false;}};
 private final JTable table=new JTable(model); private final JLabel status=new JLabel("Ready.");
 public EventRegistrationApp(){super("Campus Event Registration");setDefaultCloseOperation(EXIT_ON_CLOSE);setLayout(new BorderLayout(6,6));
  JMenuBar mb=new JMenuBar();JMenu file=new JMenu("File");JMenuItem refresh=new JMenuItem("Refresh"),export=new JMenuItem("Export CSV"),exit=new JMenuItem("Exit");
  refresh.addActionListener(e->load());export.addActionListener(e->exportCsv());exit.addActionListener(e->dispose());file.add(refresh);file.add(export);file.add(exit);mb.add(file);setJMenuBar(mb);
  JPanel form=new JPanel(new FlowLayout(FlowLayout.LEFT));form.add(new JLabel("Student ID"));form.add(id);form.add(new JLabel("Name"));form.add(name);form.add(new JLabel("Event"));form.add(event);JButton register=new JButton("Register");form.add(register);
  JPanel f=new JPanel(new FlowLayout(FlowLayout.LEFT));f.add(new JLabel("Filter"));f.add(filter);JButton apply=new JButton("Apply");f.add(apply);
  JPanel north=new JPanel(new BorderLayout());north.add(form,BorderLayout.NORTH);north.add(f,BorderLayout.SOUTH);add(north,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);add(status,BorderLayout.SOUTH);
  register.addActionListener(e->register());apply.addActionListener(e->load());setSize(850,520);setLocationRelativeTo(null);load();
 }
 private Connection connect()throws SQLException{return DriverManager.getConnection(URL,USER,PASSWORD);}
 private void register(){String sid=id.getText().trim(),student=name.getText().trim(),ev=(String)event.getSelectedItem();if(sid.isEmpty()||student.isEmpty()){JOptionPane.showMessageDialog(this,"Student ID and name are required.");return;}
  new SwingWorker<Void,Void>(){protected Void doInBackground()throws Exception{try(Connection c=connect();PreparedStatement p=c.prepareStatement("INSERT INTO registrations(student_id,student_name,event_name) VALUES(?,?,?)")){p.setString(1,sid);p.setString(2,student);p.setString(3,ev);p.executeUpdate();}return null;}
   protected void done(){try{get();status.setText("Registration saved.");id.setText("");name.setText("");load();}catch(Exception x){Throwable t=x.getCause();String m=t==null?x.getMessage():t.getMessage();JOptionPane.showMessageDialog(EventRegistrationApp.this,"Registration failed (possibly duplicate): "+m);}}}.execute();
 }
 private void load(){String ev=(String)filter.getSelectedItem();new SwingWorker<java.util.List<Object[]>,Void>(){protected java.util.List<Object[]> doInBackground()throws Exception{java.util.List<Object[]> rows=new ArrayList<>();String sql="All".equals(ev)?"SELECT student_id,student_name,event_name FROM registrations ORDER BY student_id":"SELECT student_id,student_name,event_name FROM registrations WHERE event_name=? ORDER BY student_id";
  try(Connection c=connect();PreparedStatement p=c.prepareStatement(sql)){if(!"All".equals(ev))p.setString(1,ev);try(ResultSet r=p.executeQuery()){while(r.next())rows.add(new Object[]{r.getString(1),r.getString(2),r.getString(3)});}}return rows;}
  protected void done(){try{model.setRowCount(0);for(Object[] row:get())model.addRow(row);status.setText(model.getRowCount()+" registration(s) loaded.");}catch(Exception e){status.setText("Load failed: "+e.getMessage());}}}.execute();
 }
 private void exportCsv(){JFileChooser fc=new JFileChooser();if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;try(PrintWriter out=new PrintWriter(new FileWriter(fc.getSelectedFile()))){out.println("Student ID,Name,Event");for(int r=0;r<model.getRowCount();r++)out.printf("\"%s\",\"%s\",\"%s\"%n",model.getValueAt(r,0),model.getValueAt(r,1),model.getValueAt(r,2));status.setText("CSV exported.");}catch(IOException e){JOptionPane.showMessageDialog(this,e.getMessage());}}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new EventRegistrationApp().setVisible(true));}
}