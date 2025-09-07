package com.banco.cuenta.exception;

public class CuentaNotFoundException extends RuntimeException {

    public CuentaNotFoundException(String mensaje) {
        super(mensaje);
    }

    public CuentaNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}