package model;

import java.math.BigDecimal;

public abstract class Account {
    protected final String accountNumber;
    protected final String customerId;
    protected BigDecimal balance;

    protected Account(String accountNumber, String customerId, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public abstract String getAccountType();
}
