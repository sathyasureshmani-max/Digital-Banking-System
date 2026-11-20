# Digital Banking System

A professional Java Swing desktop application for basic banking operations.

## Features
- Customer registration and login
- Savings and Current accounts
- Deposit and withdrawal
- Fund transfer
- Balance enquiry
- Transaction history
- File-based persistent storage
- Simple professional Swing interface
- Input validation and error messages

## Requirements
- Java JDK 17 or later
- VS Code with Java Extension Pack (recommended)

## Run in VS Code

### Windows
Double-click `run.bat`, or use the terminal:

```powershell
javac -d out (Get-ChildItem -Recurse -Path src/main/java -Filter *.java).FullName
java -cp out main.BankingApplication
```

The application stores data in the `data` folder.

## Demo login
Register a new customer from the application. The same credentials can then be used to log in.
