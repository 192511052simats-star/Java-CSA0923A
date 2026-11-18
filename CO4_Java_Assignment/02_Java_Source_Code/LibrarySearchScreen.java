import javax.swing.*; import java.awt.*;
public class LibrarySearchScreen extends JFrame {
 private final JTextField query=new JTextField(20);
 private final JComboBox<String> category=new JComboBox<>(new String[]{"All","Books","Journals","Theses"});
 private final DefaultListModel<String> model=new DefaultListModel<>();
 private final JList<String> results=new JList<>(model);
 private final JLabel status=new JLabel("Enter a keyword.");
 public LibrarySearchScreen(){
  super("Campus Library Search"); setDefaultCloseOperation(EXIT_ON_CLOSE); setLayout(new BorderLayout(8,8));
  JLabel title=new JLabel("Campus Library Search",SwingConstants.CENTER); title.setFont(title.getFont().deriveFont(Font.BOLD,20f)); add(title,BorderLayout.NORTH);
  JPanel search=new JPanel(new FlowLayout(FlowLayout.LEFT)); search.add(new JLabel("Search:")); search.add(query); search.add(category);
  JButton go=new JButton("Search"); search.add(go);
  JPanel center=new JPanel(new BorderLayout()); center.add(search,BorderLayout.NORTH); center.add(new JScrollPane(results),BorderLayout.CENTER);
  JPanel nav=new JPanel(new FlowLayout()); JButton prev=new JButton("Prev"), next=new JButton("Next"), reserve=new JButton("Reserve");
  nav.add(prev); nav.add(next); nav.add(reserve); center.add(nav,BorderLayout.SOUTH);
  add(center,BorderLayout.CENTER); add(status,BorderLayout.SOUTH);
  go.addActionListener(e->search()); query.addActionListener(e->search());
  prev.addActionListener(e->status.setText("Previous page selected."));
  next.addActionListener(e->status.setText("Next page selected."));
  reserve.addActionListener(e->{String s=results.getSelectedValue();status.setText(s==null?"Select a result first.":"Reservation requested: "+s);});
  setMinimumSize(new Dimension(540,340)); setSize(760,520); setLocationRelativeTo(null);
 }
 private void search(){String q=query.getText().trim();model.clear();if(q.isEmpty()){status.setText("Please enter a keyword.");return;}
  for(int i=1;i<=3;i++)model.addElement(q+" — "+category.getSelectedItem()+" result "+i);
  status.setText("3 sample results. Connect a catalogue service for live data.");
 }
 public static void main(String[] a){SwingUtilities.invokeLater(()->new LibrarySearchScreen().setVisible(true));}
}