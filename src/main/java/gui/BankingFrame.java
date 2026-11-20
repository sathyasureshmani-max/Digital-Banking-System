package gui;

import model.Customer;
import service.BankingService;

public class BankingFrame extends DashboardFrame {
    public BankingFrame(BankingService bank, Customer customer) {
        super(bank, customer);
    }
}
