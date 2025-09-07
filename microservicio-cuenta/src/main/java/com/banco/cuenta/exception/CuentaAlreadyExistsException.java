package com.banco.cuenta.exception;

public class CuentaAlreadyExistsException extends RuntimeException {

    public CuentaAlreadyExistsException(String mensaje) {
        super(mensaje);
    }

    public CuentaAlreadyExistsException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}