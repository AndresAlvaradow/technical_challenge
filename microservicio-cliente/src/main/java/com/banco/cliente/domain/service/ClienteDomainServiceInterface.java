package com.banco.cliente.domain.service;

import com.banco.cliente.entity.Cliente;

public interface ClienteDomainServiceInterface {
    
    void validarClienteParaCreacion(Cliente cliente);
    
    void validarClienteParaActualizacion(Cliente clienteExistente, Cliente clienteActualizado);
    
    boolean esClienteActivo(Cliente cliente);
    
    
    String generarNumeroCliente(Long clienteId);
}