package com.banco.cliente.usecase;

import com.banco.cliente.dto.ClienteDto;

public interface ActualizarClienteUseCaseInterface {
    
    ClienteDto ejecutar(Long id, ClienteDto clienteDto);
}