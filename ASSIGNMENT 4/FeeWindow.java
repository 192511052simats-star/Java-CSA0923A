
import java.awt.*;
import java.awt.event.*;

class FeeService {

    public String pay(String reg, String amount) {

        if (reg.trim().isEmpty() || amount.trim().isEmpty())
            return "Enter all details";

        try {
            double a = Double.parseDouble(amount);

            if (a <= 0)
                return "Invalid Amount";

            return "Payment Successful";

        } catch (NumberFormatException e) {
            return "Invalid Amount";
        }
    }
}

public class FeeWindow extends Frame {

    TextField regNo = new TextField();
    TextField amount = new TextField();

    FeeService service = new FeeService();
    String receipt = "No payment yet";

    FeeWindow() {

        setLayout(new GridLayout(4, 2));

        Button pay = new Button("Pay");
        Button clear = new Button("Clear");
        Button print = new Button("Receipt");

        add(new Label("Register Number"));
        add(regNo);
        add(new Label("Amount"));
        add(amount);
        add(pay);
        add(clear);
        add(print);

        pay.addActionListener(e -> {
            String reg = regNo.getText();
            String amt = amount.getText();

            String result = service.pay(reg, amt);
            receipt = result;

            if (result.equals("Payment Successful")) {
                receipt += "\nRegister Number: " + reg;
                receipt += "\nAmount: " + amt;
            }

            showMessage(result);
        });

        clear.addActionListener(e -> {
            regNo.setText("");
            amount.setText("");
        });

        print.addActionListener(e -> showMessage(receipt));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setTitle("Student Fee Payment");
        setSize(350, 200);
        setVisible(true);
    }

    void showMessage(String msg) {
        Dialog d = new Dialog(this, "Message", true);
        d.setLayout(new FlowLayout());

        d.add(new Label(msg));
        

        Button ok = new Button("OK");
        ok.addActionListener(e -> d.dispose());

        d.add(ok);
        d.setSize(300, 120);
        d.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FeeWindow());
    }
}
