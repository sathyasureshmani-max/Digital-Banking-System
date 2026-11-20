package model;

import java.time.LocalDateTime;

public class Transaction {
    private final String id;
    private final String accountNumber;
    private final String type;
    private final String amount;
    private final String description;
    private final LocalDateTime time;

    public Transaction(String id, String accountNumber, String type, String amount, String description) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return time + " | " + type + " | " + amount + " | " + description;
    }
}
