package model;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    public SavingsAccount(String number, String customerId, BigDecimal balance) {
        super(number, customerId, balance);
    }
    @Override public String getAccountType() { return "Savings"; }
}
