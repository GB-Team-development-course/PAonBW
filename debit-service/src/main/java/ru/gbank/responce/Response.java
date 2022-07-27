package ru.gbank.responce;

import lombok.Value;

@Value
public class Response<T> {

    int code;

    boolean success;

    T data;
}
