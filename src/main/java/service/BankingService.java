package service;

import exception.*;
import model.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class BankingService implements BankOperations {
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private final List<String> history = new ArrayList<>();
    private int customerSeq = 1001;
    private long accountSeq = 7000000001L;
    private final Path dataDir = Paths.get("data");

    public BankingService() {
        load();
    }

    @Override
    public Customer register(String name, String phone, String email, String pin, String ignored) throws BankingException {
        if (name == null || name.isBlank() || pin == null || pin.length() < 4)
            throw new BankingException("Enter a name and a PIN of at least 4 digits.");
        String id = "C" + customerSeq++;
        Customer c = new Customer(id, name.trim(), phone == null ? "" : phone.trim(),
                email == null ? "" : email.trim(), pin);
        customers.put(id, c);
        save();
        return c;
    }

    @Override
    public Customer login(String customerId, String pin) throws BankingException {
        Customer c = customers.get(customerId.trim());
        if (c == null || !c.getPin().equals(pin))
            throw new BankingException("Invalid Customer ID or PIN.");
        return c;
    }

    @Override
    public Account createAccount(String customerId, String type, BigDecimal initialDeposit) throws BankingException {
        if (!customers.containsKey(customerId)) throw new BankingException("Customer not found.");
        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) throw new BankingException("Initial deposit cannot be negative.");
        String number = String.valueOf(accountSeq++);
        Account a = type.equalsIgnoreCase("Current")
                ? new CurrentAccount(number, customerId, initialDeposit)
                : new SavingsAccount(number, customerId, initialDeposit);
        accounts.put(number, a);
        record(number, "ACCOUNT", initialDeposit, "Account created");
        save();
        return a;
    }

    @Override
    public List<Account> getAccounts(String customerId) {
        List<Account> result = new ArrayList<>();
        for (Account a : accounts.values())
            if (a.getCustomerId().equals(customerId)) result.add(a);
        return result;
    }

    private Account find(String number) throws AccountNotFoundException {
        Account a = accounts.get(number);
        if (a == null) throw new AccountNotFoundException("Account not found.");
        return a;
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) throws BankingException {
        validateAmount(amount);
        Account a = find(accountNumber);
        a.setBalance(a.getBalance().add(amount));
        record(accountNumber, "DEPOSIT", amount, "Cash deposit");
        save();
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount) throws BankingException {
        validateAmount(amount);
        Account a = find(accountNumber);
        if (a.getBalance().compareTo(amount) < 0) throw new InsufficientBalanceException("Insufficient balance.");
        a.setBalance(a.getBalance().subtract(amount));
        record(accountNumber, "WITHDRAW", amount, "Cash withdrawal");
        save();
    }

    @Override
    public void transfer(String from, String to, BigDecimal amount) throws BankingException {
        validateAmount(amount);
        Account source = find(from);
        Account target = find(to);
        if (source.getBalance().compareTo(amount) < 0) throw new InsufficientBalanceException("Insufficient balance.");
        source.setBalance(source.getBalance().subtract(amount));
        target.setBalance(target.getBalance().add(amount));
        record(from, "TRANSFER", amount, "Transfer to " + to);
        record(to, "TRANSFER", amount, "Transfer from " + from);
        save();
    }

    private void validateAmount(BigDecimal amount) throws BankingException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankingException("Amount must be greater than zero.");
    }

    private void record(String account, String type, BigDecimal amount, String description) {
        history.add(LocalDateTime.now() + " | " + account + " | " + type + " | " + amount + " | " + description);
    }

    @Override
    public List<String> getHistory(String customerId) {
        Set<String> owned = new HashSet<>();
        for (Account a : getAccounts(customerId)) owned.add(a.getAccountNumber());
        List<String> result = new ArrayList<>();
        for (String h : history) {
            for (String acc : owned) if (h.contains("| " + acc + " |")) { result.add(h); break; }
        }
        return result;
    }

    private void save() {
        try {
            Files.createDirectories(dataDir);
            try (PrintWriter p = new PrintWriter(Files.newBufferedWriter(dataDir.resolve("customers.txt")))) {
                for (Customer c : customers.values())
                    p.println(String.join("|", c.getCustomerId(), c.getName(), c.getPhone(), c.getEmail(), c.getPin()));
            }
            try (PrintWriter p = new PrintWriter(Files.newBufferedWriter(dataDir.resolve("accounts.txt")))) {
                for (Account a : accounts.values())
                    p.println(String.join("|", a.getAccountNumber(), a.getCustomerId(), a.getAccountType(), a.getBalance().toPlainString()));
            }
            Files.write(dataDir.resolve("transactions.txt"), history);
        } catch (IOException e) {
            throw new RuntimeException("Could not save banking data.", e);
        }
    }

    private void load() {
        try {
            Path cfile = dataDir.resolve("customers.txt");
            if (Files.exists(cfile)) for (String s : Files.readAllLines(cfile)) {
                if (s.isBlank()) continue;
                String[] x = s.split("\\|", -1);
                if (x.length >= 5) {
                    customers.put(x[0], new Customer(x[0],x[1],x[2],x[3],x[4]));
                    customerSeq = Math.max(customerSeq, Integer.parseInt(x[0].substring(1)) + 1);
                }
            }
            Path afile = dataDir.resolve("accounts.txt");
            if (Files.exists(afile)) for (String s : Files.readAllLines(afile)) {
                if (s.isBlank()) continue;
                String[] x = s.split("\\|", -1);
                if (x.length >= 4) {
                    BigDecimal b = new BigDecimal(x[3]);
                    Account a = x[2].equalsIgnoreCase("Current")
                            ? new CurrentAccount(x[0],x[1],b) : new SavingsAccount(x[0],x[1],b);
                    accounts.put(x[0], a);
                    accountSeq = Math.max(accountSeq, Long.parseLong(x[0]) + 1);
                }
            }
            Path tfile = dataDir.resolve("transactions.txt");
            if (Files.exists(tfile)) history.addAll(Files.readAllLines(tfile));
        } catch (Exception ignored) {}
    }
}
