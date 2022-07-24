package ru.gb.core.response;

import lombok.NonNull;
import org.springframework.lang.Nullable;
import ru.gb.core.enums.ResponseCode;
import ru.gb.core.util.TypeUtils;


/**
 * Класс для создания ответов для вызывающих сервисов
 */
public class ResponseFactory {

    /**
     * Успешный ответ
     */
    @NonNull
    public static <T> Response<T> successResponse(@NonNull final ResponseCode responseCode, @Nullable final T body) {
        return new Response<>(
                responseCode.getCode(),
                true,
                body);
    }

    /**
     * Ответ с текстовой ошибкой
     */
    @NonNull
    public static <T> Response<T> errorResponse(@NonNull final ResponseCode responseCode, @Nullable final String errorMessage
    ) {
        return new Response<>(
                responseCode.getCode(),
                false,
                errorMessage != null
                        ? TypeUtils.cast(errorMessage)
                        : null
        );
    }
}
