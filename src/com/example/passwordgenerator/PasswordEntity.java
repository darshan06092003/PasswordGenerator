package com.example.passwordgenerator;

public class PasswordEntity {
    private int id;
    private String password;
    private int length;
    private boolean uppercase;
    private boolean digits;
    private boolean special;
    private String timestamp;

    // Constructor
    public PasswordEntity(String password, int length, boolean uppercase, boolean digits, boolean special) {
        this.password = password;
        this.length = length;
        this.uppercase = uppercase;
        this.digits = digits;
        this.special = special;
    }

    // Getters and setters
    // (Generate using IntelliJ or write manually)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public boolean isUppercase() { return uppercase; }
    public void setUppercase(boolean uppercase) { this.uppercase = uppercase; }

    public boolean isDigits() { return digits; }
    public void setDigits(boolean digits) { this.digits = digits; }

    public boolean isSpecial() { return special; }
    public void setSpecial(boolean special) { this.special = special; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
