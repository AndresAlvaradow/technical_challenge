package com.banco.cliente.service;

import com.banco.cliente.dto.ClienteDto;

import java.util.List;

public interface ClienteServiceInterface {
    
    ClienteDto crearCliente(ClienteDto clienteDto);
    
    ClienteDto obtenerClientePorId(Long id);
    
    ClienteDto obtenerClientePorIdentificacion(String identificacion);
    
    List<ClienteDto> obtenerTodosLosClientes();
    
    ClienteDto actualizarCliente(Long id, ClienteDto clienteDto);
    
    void eliminarCliente(Long id);
}