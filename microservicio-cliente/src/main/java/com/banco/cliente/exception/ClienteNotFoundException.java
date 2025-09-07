package com.banco.cliente.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }

    public ClienteNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}