package com.banco.cliente.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.banco.cliente.domain.valueobject.Identificacion;
import com.banco.cliente.entity.Cliente;

@Service
public class ClienteDomainService implements ClienteDomainServiceInterface {

    public void validarClienteParaCreacion(Cliente cliente) {
        validarEdadMinima(cliente.getEdad());
        validarEstadoInicial(cliente.getEstado());
        validarContrasenaSegura(cliente.getContrasena());
    }

    public void validarClienteParaActualizacion(Cliente clienteExistente, Cliente clienteActualizado) {
        
        if (!clienteExistente.getIdentificacion().equals(clienteActualizado.getIdentificacion())) {
       
            new Identificacion(clienteActualizado.getIdentificacion());
        }
        
        validarContrasenaSegura(clienteActualizado.getContrasena());
    }

    public boolean esClienteActivo(Cliente cliente) {
        return cliente.getEstado() != null && cliente.getEstado();
    }

    private void validarEdadMinima(Integer edad) {
        if (edad == null || edad < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad (18 años)");
        }
    }

    private void validarEstadoInicial(Boolean estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del cliente es obligatorio");
        }
    }

    private void validarContrasenaSegura(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        
        if (contrasena.length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
        }
    }

    @Override
    public String generarNumeroCliente(Long clienteId) {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%04d%02d%06d", 
            now.getYear() % 10000, 
            now.getMonthValue(), 
            clienteId);
    }
}