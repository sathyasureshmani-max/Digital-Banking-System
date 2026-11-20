package gui;

import model.Customer;
import service.BankingService;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {
    private final BankingService bank;
    private final JTextField name = new JTextField(), phone = new JTextField(), email = new JTextField();
    private final JPasswordField pin = new JPasswordField();

    public RegistrationFrame(BankingService bank) {
        this.bank = bank;
        setTitle("New Customer Registration");
        setSize(500, 390);
        setLocationRelativeTo(null);
        JPanel p = new JPanel(new GridLayout(5,2,10,10));
        p.setBorder(BorderFactory.createEmptyBorder(25,30,25,30));
        p.add(new JLabel("Full Name")); p.add(name);
        p.add(new JLabel("Phone")); p.add(phone);
        p.add(new JLabel("Email")); p.add(email);
        p.add(new JLabel("PIN (4+ digits)")); p.add(pin);
        JButton save = new JButton("Register");
        JButton cancel = new JButton("Cancel");
        p.add(save); p.add(cancel);
        save.addActionListener(e -> register());
        cancel.addActionListener(e -> dispose());
        setContentPane(p);
    }

    private void register() {
        try {
            Customer c = bank.register(name.getText(), phone.getText(), email.getText(),
                    new String(pin.getPassword()), "");
            JOptionPane.showMessageDialog(this,
                    "Registration successful!\nYour Customer ID is: " + c.getCustomerId(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
