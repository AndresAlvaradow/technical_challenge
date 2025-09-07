package com.banco.cliente.application.service;

import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.repository.ClienteRepository;
import com.banco.cliente.service.ClienteServiceInterface;
import com.banco.cliente.usecase.ActualizarClienteUseCaseInterface;
import com.banco.cliente.usecase.CrearClienteUseCaseInterface;
import com.banco.cliente.usecase.EliminarClienteUseCaseInterface;
import com.banco.cliente.usecase.ObtenerClienteUseCaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Application Service - Capa de aplicación que coordina los casos de uso
 * Esta clase implementa los principios de Clean Architecture manteniendo
 * la lógica de aplicación separada del dominio
 */
@Service
@Transactional
public class ClienteApplicationService implements ClienteServiceInterface {

    private final ClienteRepository clienteRepository;
    private final CrearClienteUseCaseInterface crearClienteUseCase;
    private final ObtenerClienteUseCaseInterface obtenerClienteUseCase;
    private final ActualizarClienteUseCaseInterface actualizarClienteUseCase;
    private final EliminarClienteUseCaseInterface eliminarClienteUseCase;

    @Autowired
    public ClienteApplicationService(ClienteRepository clienteRepository,
                                   CrearClienteUseCaseInterface crearClienteUseCase,
                                   ObtenerClienteUseCaseInterface obtenerClienteUseCase,
                                   ActualizarClienteUseCaseInterface actualizarClienteUseCase,
                                   EliminarClienteUseCaseInterface eliminarClienteUseCase) {
        this.clienteRepository = clienteRepository;
        this.crearClienteUseCase = crearClienteUseCase;
        this.obtenerClienteUseCase = obtenerClienteUseCase;
        this.actualizarClienteUseCase = actualizarClienteUseCase;
        this.eliminarClienteUseCase = eliminarClienteUseCase;
    }

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDto) {
        return crearClienteUseCase.ejecutar(clienteDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDto obtenerClientePorId(Long id) {
        return obtenerClienteUseCase.ejecutarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDto obtenerClientePorIdentificacion(String identificacion) {
        return obtenerClienteUseCase.ejecutarPorIdentificacion(identificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDto> obtenerTodosLosClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> {
                    ClienteDto dto = new ClienteDto();
                    dto.setClienteId(cliente.getClienteId());
                    dto.setNombre(cliente.getNombre());
                    dto.setGenero(cliente.getGenero());
                    dto.setEdad(cliente.getEdad());
                    dto.setIdentificacion(cliente.getIdentificacion());
                    dto.setDireccion(cliente.getDireccion());
                    dto.setTelefono(cliente.getTelefono());
                    dto.setContrasena(cliente.getContrasena());
                    dto.setEstado(cliente.getEstado());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDto) {
        return actualizarClienteUseCase.ejecutar(id, clienteDto);
    }

    @Override
    public void eliminarCliente(Long id) {
        eliminarClienteUseCase.ejecutar(id);
    }
}