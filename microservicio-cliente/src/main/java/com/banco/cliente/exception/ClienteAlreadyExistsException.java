package com.banco.cliente.exception;

public class ClienteAlreadyExistsException extends RuntimeException {

    public ClienteAlreadyExistsException(String mensaje) {
        super(mensaje);
    }

    public ClienteAlreadyExistsException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}