package com.banco.cliente.domain.valueobject;

import java.util.Objects;

public class Identificacion {
    
    private String valor;
    
    private Identificacion() {
        // Constructor privado para JPA 
    }

    public Identificacion(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede ser null o vacía");
        }
        
        if (valor.length() < 8 || valor.length() > 20) {
            throw new IllegalArgumentException("La identificación debe tener entre 8 y 20 caracteres");
        }
        
        if (!valor.matches("\\d+")) {
            throw new IllegalArgumentException("La identificación solo puede contener números");
        }
        
        this.valor = valor.trim();
    }
    
    public String getValor() {
        return valor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identificacion that = (Identificacion) o;
        return Objects.equals(valor, that.valor);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
    
    @Override
    public String toString() {
        return valor;
    }
}