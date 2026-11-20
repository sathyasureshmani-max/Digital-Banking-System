package model;

public class Customer {
    private final String customerId;
    private final String name;
    private final String phone;
    private final String email;
    private final String pin;

    public Customer(String customerId, String name, String phone, String email, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pin = pin;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPin() { return pin; }
}
