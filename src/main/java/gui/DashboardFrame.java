package gui;

import model.Account;
import model.Customer;
import service.BankingService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final BankingService bank;
    private final Customer customer;
    private final JComboBox<String> accountBox = new JComboBox<>();
    private final JLabel balance = new JLabel("Balance: ₹0.00");
    private final JTextArea history = new JTextArea();

    public DashboardFrame(BankingService bank, Customer customer) {
        this.bank = bank; this.customer = customer;
        setTitle("Pearl & Co. Bank - Dashboard");
        setSize(760, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Welcome, " + customer.getName()));
        top.add(new JLabel("Customer ID: " + customer.getCustomerId()));
        JButton logout = new JButton("Logout");
        top.add(logout);

        JPanel account = new JPanel(new FlowLayout(FlowLayout.LEFT));
        account.add(new JLabel("Account:"));
        account.add(accountBox);
        JButton refresh = new JButton("Refresh");
        account.add(refresh);
        account.add(balance);

        JPanel actions = new JPanel(new GridLayout(1,4,8,8));
        JButton create = new JButton("Open Account");
        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        JButton transfer = new JButton("Transfer");
        actions.add(create); actions.add(deposit); actions.add(withdraw); actions.add(transfer);

        history.setEditable(false);
        history.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(history);

        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        root.add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new BorderLayout(8,8));
        center.add(account, BorderLayout.NORTH);
        center.add(actions, BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);
        root.add(scroll, BorderLayout.SOUTH);
        scroll.setPreferredSize(new Dimension(700,230));
        setContentPane(root);

        refresh.addActionListener(e -> refreshAll());
        accountBox.addActionListener(e -> updateBalance());
        create.addActionListener(e -> createAccount());
        deposit.addActionListener(e -> money("Deposit"));
        withdraw.addActionListener(e -> money("Withdraw"));
        transfer.addActionListener(e -> transfer());
        logout.addActionListener(e -> { new LoginFrame(bank).setVisible(true); dispose(); });

        refreshAll();
    }

    private Account selected() {
        int i = accountBox.getSelectedIndex();
        List<Account> list = bank.getAccounts(customer.getCustomerId());
        return i >= 0 && i < list.size() ? list.get(i) : null;
    }

    private void refreshAll() {
        accountBox.removeAllItems();
        for (Account a : bank.getAccounts(customer.getCustomerId()))
            accountBox.addItem(a.getAccountNumber() + " (" + a.getAccountType() + ")");
        updateBalance();
        history.setText(String.join("\n", bank.getHistory(customer.getCustomerId())));
    }

    private void updateBalance() {
        Account a = selected();
        balance.setText(a == null ? "Balance: ₹0.00" : "Balance: ₹" + a.getBalance().toPlainString());
    }

    private void createAccount() {
        String[] types = {"Savings","Current"};
        String type = (String) JOptionPane.showInputDialog(this, "Account type:",
                "Open Account", JOptionPane.PLAIN_MESSAGE, null, types, types[0]);
        if (type == null) return;
        String amount = JOptionPane.showInputDialog(this, "Initial deposit:");
        try {
            bank.createAccount(customer.getCustomerId(), type, new BigDecimal(amount));
            refreshAll();
            JOptionPane.showMessageDialog(this, "Account created successfully.");
        } catch (Exception ex) { error(ex); }
    }

    private void money(String action) {
        Account a = selected();
        if (a == null) { error("Open an account first."); return; }
        String amount = JOptionPane.showInputDialog(this, action + " amount:");
        try {
            BigDecimal v = new BigDecimal(amount);
            if (action.equals("Deposit")) bank.deposit(a.getAccountNumber(), v);
            else bank.withdraw(a.getAccountNumber(), v);
            refreshAll();
        } catch (Exception ex) { error(ex); }
    }

    private void transfer() {
        Account from = selected();
        if (from == null) { error("Select an account."); return; }
        String to = JOptionPane.showInputDialog(this, "Recipient account number:");
        String amount = JOptionPane.showInputDialog(this, "Transfer amount:");
        try {
            bank.transfer(from.getAccountNumber(), to, new BigDecimal(amount));
            refreshAll();
            JOptionPane.showMessageDialog(this, "Transfer completed.");
        } catch (Exception ex) { error(ex); }
    }

    private void error(Exception ex) {
        error(ex.getMessage());
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this, message, "Operation Failed", JOptionPane.ERROR_MESSAGE);
    }
}
