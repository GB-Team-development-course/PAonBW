package ru.gbank.pabw.model.response;

import lombok.Value;

@Value
public class Response<T> {

    int code;

    boolean success;

    T data;
}
