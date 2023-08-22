package src.main.java.components.team7ContainerPortManagement.views.Account;

public class Account {
    private String username;
    private String password;
    private String type;
    private String assignedPort = "";  // empty if not assigned

    public String getType() {
        return type;
    }

    public Account(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    // ... Getter and Setter methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAssignedPort() {
        return assignedPort;
    }

    public void setAssignedPort(String assignedPort) {
        this.assignedPort = assignedPort;
    }
}
