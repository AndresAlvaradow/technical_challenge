package com.banco.cliente.domain.valueobject;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Telefono {
    
    private String numero;
    
    protected Telefono() {
        // Constructor vacío para JPA
    }
    
    public Telefono(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de teléfono no puede ser null o vacío");
        }
        
        String numeroLimpio = numero.replaceAll("[\\s\\-\\(\\)\\+]", "");
        
        if (numeroLimpio.length() < 7 || numeroLimpio.length() > 15) {
            throw new IllegalArgumentException("El número de teléfono debe tener entre 7 y 15 dígitos");
        }
        
        if (!numeroLimpio.matches("\\d+")) {
            throw new IllegalArgumentException("El número de teléfono solo puede contener números");
        }
        
        this.numero = numeroLimpio;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public String getNumeroFormateado() {
        if (numero.length() == 10) {
            return String.format("(%s) %s-%s", 
                numero.substring(0, 3),
                numero.substring(3, 6),
                numero.substring(6));
        }
        return numero;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefono telefono = (Telefono) o;
        return Objects.equals(numero, telefono.numero);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
    
    @Override
    public String toString() {
        return numero;
    }
}