package com.banco.cliente.usecase;

import com.banco.cliente.dto.ClienteDto;

public interface ObtenerClienteUseCaseInterface {
    
    ClienteDto ejecutarPorId(Long id);
    
    ClienteDto ejecutarPorIdentificacion(String identificacion);
}