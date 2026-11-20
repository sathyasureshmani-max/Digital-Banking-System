package model;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    public CurrentAccount(String number, String customerId, BigDecimal balance) {
        super(number, customerId, balance);
    }
    @Override public String getAccountType() { return "Current"; }
}
