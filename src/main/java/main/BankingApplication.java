package main;

import gui.LoginFrame;
import service.BankingService;
import utility.AppTheme;

import javax.swing.*;

public class BankingApplication {
    public static void main(String[] args) {
        AppTheme.install();
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginFrame(new BankingService()).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to start banking system: " + e.getMessage(),
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
