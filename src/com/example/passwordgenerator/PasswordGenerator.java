package com.example.passwordgenerator;

import java.security.SecureRandom;

public class PasswordGenerator {
    private final int length;
    private final boolean includeUppercase;
    private final boolean includeDigits;
    private final boolean includeSpecial;
    private final boolean excludeSimilar;
    private final String excludeCharacters;

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_+=<>?/{}[]~";

    public PasswordGenerator(int length, boolean includeUppercase, boolean includeDigits,
                             boolean includeSpecial, boolean excludeSimilar, String excludeCharacters) {
        this.length = length;
        this.includeUppercase = includeUppercase;
        this.includeDigits = includeDigits;
        this.includeSpecial = includeSpecial;
        this.excludeSimilar = excludeSimilar;
        this.excludeCharacters = excludeCharacters;
    }

    public String generate() {
        StringBuilder characterPool = new StringBuilder(LOWERCASE);

        if (includeUppercase) characterPool.append(UPPERCASE);
        if (includeDigits) characterPool.append(DIGITS);
        if (includeSpecial) characterPool.append(SPECIAL);

        if (excludeSimilar) {
            String similarCharacters = "1lI0O";
            for (char ch : similarCharacters.toCharArray()) {
                int index;
                while ((index = characterPool.indexOf(String.valueOf(ch))) != -1) {
                    characterPool.deleteCharAt(index);
                }
            }
        }

        for (char ch : excludeCharacters.toCharArray()) {
            int index;
            while ((index = characterPool.indexOf(String.valueOf(ch))) != -1) {
                characterPool.deleteCharAt(index);
            }
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        return password.toString();
    }
}
