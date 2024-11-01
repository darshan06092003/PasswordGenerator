package com.example.passwordgenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PasswordGeneratorUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Password Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLayout(new BorderLayout());

            // Title Label
            JLabel titleLabel = new JLabel("Secure Password Generator", JLabel.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            frame.add(titleLabel, BorderLayout.NORTH);

            // Main Panel with GridBagLayout for better alignment
            JPanel mainPanel = new JPanel(new GridBagLayout());
            frame.add(mainPanel, BorderLayout.CENTER);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Password Length
            gbc.gridx = 0;
            gbc.gridy = 0;
            mainPanel.add(new JLabel("Password Length:"), gbc);

            gbc.gridx = 1;
            JTextField lengthField = new JTextField(10);
            mainPanel.add(lengthField, gbc);

            // Options checkboxes
            gbc.gridx = 0;
            gbc.gridy = 1;
            JCheckBox uppercaseBox = new JCheckBox("Include Uppercase Letters");
            mainPanel.add(uppercaseBox, gbc);

            gbc.gridx = 1;
            JCheckBox digitsBox = new JCheckBox("Include Digits");
            mainPanel.add(digitsBox, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            JCheckBox specialBox = new JCheckBox("Include Special Characters");
            mainPanel.add(specialBox, gbc);

            gbc.gridx = 1;
            JCheckBox excludeSimilarBox = new JCheckBox("Exclude Similar Characters");
            mainPanel.add(excludeSimilarBox, gbc);

            // Exclude Characters
            gbc.gridx = 0;
            gbc.gridy = 3;
            mainPanel.add(new JLabel("Exclude Characters:"), gbc);

            gbc.gridx = 1;
            JTextField excludeField = new JTextField(10);
            mainPanel.add(excludeField, gbc);

            // Generate Password Button and Password Display Area
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton generateButton = new JButton("Generate Password");
            mainPanel.add(generateButton, gbc);

            gbc.gridy = 5;
            JTextArea passwordDisplay = new JTextArea(3, 20);
            passwordDisplay.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(passwordDisplay);
            mainPanel.add(scrollPane, gbc);

            // Password Strength label
            gbc.gridy = 6;
            JLabel strengthLabel = new JLabel("Password Strength:");
            mainPanel.add(strengthLabel, gbc);

            // Save Password Button
            gbc.gridy = 7;
            JButton saveButton = new JButton("Save Password");
            saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            saveButton.setBackground(new Color(70, 130, 180));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFocusPainted(false);
            saveButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
            saveButton.setEnabled(false); // Initially disabled until a password is generated
            mainPanel.add(saveButton, gbc);

            // View Password History Button
            gbc.gridy = 8;
            JButton viewHistoryButton = new JButton("View Password History");
            viewHistoryButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            viewHistoryButton.setBackground(new Color(70, 130, 180));
            viewHistoryButton.setForeground(Color.WHITE);
            viewHistoryButton.setFocusPainted(false);
            viewHistoryButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
            mainPanel.add(viewHistoryButton, gbc);

            // Generate Password button action
            generateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int length = Integer.parseInt(lengthField.getText().trim());
                        boolean includeUppercase = uppercaseBox.isSelected();
                        boolean includeDigits = digitsBox.isSelected();
                        boolean includeSpecial = specialBox.isSelected();
                        boolean excludeSimilar = excludeSimilarBox.isSelected();
                        String excludeChars = excludeField.getText();

                        PasswordGenerator generator = new PasswordGenerator(
                                length, includeUppercase, includeDigits, includeSpecial, excludeSimilar, excludeChars);

                        String password = generator.generate();
                        passwordDisplay.setText(password);
                        strengthLabel.setText("Password Strength: " + calculateStrength(password));

                        saveButton.setEnabled(true); // Enable save button after generating password
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid length.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Save Password button action
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PasswordEntity passwordEntity = new PasswordEntity(
                            passwordDisplay.getText(),
                            Integer.parseInt(lengthField.getText().trim()),
                            uppercaseBox.isSelected(),
                            digitsBox.isSelected(),
                            specialBox.isSelected()
                    );

                    PasswordDAO passwordDAO = new PasswordDAO();
                    passwordDAO.savePassword(passwordEntity);

                    JOptionPane.showMessageDialog(frame, "Password saved successfully!", "Save Password", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            // View Password History button action
            viewHistoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PasswordDAO passwordDAO = new PasswordDAO();
                    List<PasswordEntity> passwordHistory = passwordDAO.getAllPasswords();
                    StringBuilder historyText = new StringBuilder();

                    for (PasswordEntity password : passwordHistory) {
                        historyText.append("Password: ").append(password.getPassword()).append("\n")
                                .append("Length: ").append(password.getLength()).append("\n")
                                .append("Uppercase: ").append(password.isUppercase()).append("\n")
                                .append("Digits: ").append(password.isDigits()).append("\n")
                                .append("Special: ").append(password.isSpecial()).append("\n")
                                .append("Timestamp: ").append(password.getTimestamp()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(frame, historyText.toString(), "Password History", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            frame.setVisible(true);
        });
    }

    // Method to calculate password strength
    private static String calculateStrength(String password) {
        int strengthScore = 0;
        if (password.length() >= 12) strengthScore++;
        if (password.matches(".*[A-Z].*")) strengthScore++;
        if (password.matches(".*[0-9].*")) strengthScore++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) strengthScore++;

        switch (strengthScore) {
            case 4: return "Very Strong";
            case 3: return "Strong";
            case 2: return "Medium";
            default: return "Weak";
        }
    }
}
