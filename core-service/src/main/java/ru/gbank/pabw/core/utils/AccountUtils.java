package ru.gbank.pabw.core.utils;

import lombok.NonNull;

/**
 * Утилитный класс для работы со счетами
 */
public class AccountUtils {


    private static final int ACCOUNT_NUMBER_LENGTH = 9;

    /**
     * Генерация номера счета
     */
    @NonNull
    public static String generateAccountNumber(@NonNull final String accountNumber) {

        String newAccountNumberWithoutType = "00000000" + (Long.parseLong(accountNumber.substring(1)) + 1);

        return accountNumber.charAt(0) + newAccountNumberWithoutType
                .substring(newAccountNumberWithoutType.length() - ACCOUNT_NUMBER_LENGTH);

    }
}
