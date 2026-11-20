package gui;

import model.Customer;
import service.BankingService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final BankingService bank;
    private final JTextField id = new JTextField();
    private final JPasswordField pin = new JPasswordField();

    public LoginFrame(BankingService bank) {
        this.bank = bank;
        setTitle("Pearl & Co. Bank - Secure Login");
        setSize(480, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(15,15));
        root.setBorder(BorderFactory.createEmptyBorder(25,35,25,35));

        JLabel title = new JLabel("PEARL & CO. BANK", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4,1,8,8));
        form.add(new JLabel("Customer ID"));
        form.add(id);
        form.add(new JLabel("PIN"));
        form.add(pin);
        root.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton login = new JButton("Login");
        JButton register = new JButton("New Customer");
        buttons.add(login); buttons.add(register);
        root.add(buttons, BorderLayout.SOUTH);

        login.addActionListener(e -> doLogin());
        register.addActionListener(e -> new RegistrationFrame(bank).setVisible(true));
        setContentPane(root);
    }

    private void doLogin() {
        try {
            Customer c = bank.login(id.getText(), new String(pin.getPassword()));
            new DashboardFrame(bank, c).setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
