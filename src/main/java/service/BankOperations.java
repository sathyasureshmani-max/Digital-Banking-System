package service;

import exception.BankingException;
import model.Account;
import model.Customer;
import java.math.BigDecimal;
import java.util.List;

public interface BankOperations {
    Customer register(String name, String phone, String email, String pin, String ignored) throws BankingException;
    Customer login(String customerId, String pin) throws BankingException;
    Account createAccount(String customerId, String type, BigDecimal initialDeposit) throws BankingException;
    List<Account> getAccounts(String customerId);
    void deposit(String accountNumber, BigDecimal amount) throws BankingException;
    void withdraw(String accountNumber, BigDecimal amount) throws BankingException;
    void transfer(String from, String to, BigDecimal amount) throws BankingException;
    List<String> getHistory(String customerId);
}
