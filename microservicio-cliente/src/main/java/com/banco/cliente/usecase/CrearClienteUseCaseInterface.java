package com.banco.cliente.usecase;

import com.banco.cliente.dto.ClienteDto;

public interface CrearClienteUseCaseInterface {
    ClienteDto ejecutar(ClienteDto clienteDto);
}