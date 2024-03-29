package br.com.intelligym.utils;

public class StringUtils {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$");
    }

    public static boolean isValidCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.matches(cpf.charAt(0) + "{11}")) return false;

        int[] numbers = new int[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += numbers[i] * (10 - i);
        }

        int firstCheckDigit = 11 - (sum % 11);
        firstCheckDigit = firstCheckDigit >= 10 ? 0 : firstCheckDigit;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += numbers[i] * (11 - i);
        }

        int secondCheckDigit = 11 - (sum % 11);
        secondCheckDigit = secondCheckDigit >= 10 ? 0 : secondCheckDigit;

        return firstCheckDigit == numbers[9] && secondCheckDigit == numbers[10];
    }
}
