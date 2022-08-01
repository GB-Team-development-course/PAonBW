package ru.gbank.pabw.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Коды возврата операций
 *
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    //todo продумать все необходимые ошибки и написать корректные коды

    TOKEN_VALIDATION_ERROR(101, "Ошибка проверки токена"),
    ACCESS_RIGHTS_VALIDATION_ERROR(102, "Ошибка проверки прав доступа"),
    ACCOUNT_OPERATION_COMPLETE(200, "Операция выполнена"),
    ACCOUNT_NOT_FOUND(201, "Счет не найден"),
    ACCOUNT_VALIDATION_ERROR(202, "Ошибка валидации входных данных"),
    ACCOUNT_AUTHENTICATION_ERROR(203, "Ошибка аутентификации"),
    ACCOUNT_CLOSED_ERROR(204, "Ошибка закрытия счёта"),
    ACCOUNT_BLOCK_ERROR(205, "Ошибка блокировки счёта"),
    TRANSFER_EXECUTION_ERROR(300, "Системная ошибка выполнения перевода"),
    FIELD_VALIDATION_ERROR(301, "Ошибка валидации полей распоряжения"),
    TRANSFER_DIRECTION_ERROR(302, "Ошибка направления перевода"),
    ACCOUNT_FOR_WITHDRAWAL_DISABLED(303, "Счет списания отсутствует, заблокирован или закрыт"),
    ACCOUNT_FOR_ENROLLMENT_DISABLED(304, "Счет зачисления отсутствует, заблокирован или закрыт"),
    INSUFFICIENT_BALANCE_TO_WITHDRAW(305, "Недостаточный баланс счета списания"),
    EQUALED_SOURCE_TARGET_ACCOUNTS(306, "Счета списания и зачисления должны отличаться"),
    INCORRECT_UUID(307, "Некорректный Уникальный Идентификатор Внешней Системы"),
    OPERATION_COMPLETE(333, "Операция выполнена");

    private final static Map<Integer, ResponseCode> LOOKUP_BY_ID = Stream
            .of(ResponseCode.values())
            .collect(Collectors.toMap(ResponseCode::getCode, Function.identity()));

    private final static EnumSet<ResponseCode> ONE_HUNDREDS_CODE = Stream
            .of(ResponseCode.values())
            .filter(responseCode -> responseCode.getCode() >= 100 && responseCode.getCode() < 200)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(ResponseCode.class)));

    private final static EnumSet<ResponseCode> TWO_HUNDREDS_CODE = Stream
            .of(ResponseCode.values())
            .filter(responseCode -> responseCode.getCode() >= 200 && responseCode.getCode() < 300)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(ResponseCode.class)));

    private final static EnumSet<ResponseCode> THREE_HUNDREDS_CODE = Stream
            .of(ResponseCode.values())
            .filter(responseCode -> responseCode.getCode() >= 300 && responseCode.getCode() < 400)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(ResponseCode.class)));

    private final int code;

    private final String description;

    @NonNull
    public static ResponseCode getById(final int responseCodeId) {
        return LOOKUP_BY_ID.get(responseCodeId);
    }

    @NonNull
    public static EnumSet<ResponseCode> get1xxCodes() {
        return ONE_HUNDREDS_CODE;
    }

    @NonNull
    public static EnumSet<ResponseCode> get2xxCodes() {
        return TWO_HUNDREDS_CODE;
    }

    @NonNull
    public static EnumSet<ResponseCode> get3xxCodes() {
        return THREE_HUNDREDS_CODE;
    }
}
