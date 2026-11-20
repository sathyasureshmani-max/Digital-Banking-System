
package utility;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public final class AppTheme {

    public static final Color BACKGROUND = new Color(241, 230, 214);
    public static final Color CARD = new Color(248, 242, 233);
    public static final Color NAVY = new Color(42, 65, 88);
    public static final Color GOLD = new Color(181, 139, 80);
    public static final Color TEXT = new Color(48, 55, 64);
    public static final Color WHITE = Color.WHITE;

    private AppTheme() {}

    public static void install() {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception ignored) {}

        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Label.font",
                new Font("Segoe UI", Font.PLAIN, 14));

        UIManager.put("Button.font",
                new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Button.background", NAVY);
        UIManager.put("Button.foreground", WHITE);
        UIManager.put("Button.focusPainted", false);
        UIManager.put("Button.opaque", true);
        UIManager.put("Button.contentAreaFilled", true);

        UIManager.put("TextField.background", WHITE);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.font",
                new Font("Segoe UI", Font.PLAIN, 14));

        UIManager.put("PasswordField.background", WHITE);
        UIManager.put("PasswordField.foreground", TEXT);

        UIManager.put("Table.background", CARD);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.selectionBackground", GOLD);
        UIManager.put("Table.selectionForeground", WHITE);

        UIManager.put("TableHeader.background", NAVY);
        UIManager.put("TableHeader.foreground", WHITE);
    }

    // Apply this to Login, Deposit, Transfer and Create Account buttons
    public static void styleButton(JButton button) {
        button.setBackground(NAVY);
        button.setForeground(WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 22, 12, 22));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Apply this to Register and secondary buttons
    public static void styleGoldButton(JButton button) {
        button.setBackground(GOLD);
        button.setForeground(NAVY);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 22, 12, 22));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
