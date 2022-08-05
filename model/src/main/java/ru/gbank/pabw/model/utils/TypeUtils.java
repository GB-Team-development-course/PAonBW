package ru.gbank.pabw.model.utils;

import lombok.NonNull;

@SuppressWarnings("unchecked")
public class TypeUtils {

    @NonNull
    public static <T> T cast(@NonNull final Object object) {
        return (T) object;
    }
}
