package src.main.java.components.team7ContainerPortManagement.views.Account;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class AccountManager {

    private static final String ACCOUNT_FILE = "src/main/java/components/team7ContainerPortManagement/models/utils/account.txt";

    public static void register(String username, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE, true))) {
            writer.write(username + "," + password + "\n");
        }
    }

    public static Account login(String username, String password) throws IOException {
        List<Account> accounts = loadAccounts();
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public static List<Account> loadAccounts() throws IOException {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    Account account = new Account(parts[0], parts[1], parts[2]);
                    accounts.add(account);
                }
            }
        }
        return accounts;
    }

    // ... More methods to handle the assignment of ports to managers can be added here.
}
