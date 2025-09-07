package com.banco.cliente.service;

import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.repository.ClienteRepository;
import com.banco.cliente.usecase.ActualizarClienteUseCaseInterface;
import com.banco.cliente.usecase.CrearClienteUseCaseInterface;
import com.banco.cliente.usecase.EliminarClienteUseCaseInterface;
import com.banco.cliente.usecase.ObtenerClienteUseCaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService implements ClienteServiceInterface {

    private final ClienteRepository clienteRepository;
    private final CrearClienteUseCaseInterface crearClienteUseCase;
    private final ObtenerClienteUseCaseInterface obtenerClienteUseCase;
    private final ActualizarClienteUseCaseInterface actualizarClienteUseCase;
    private final EliminarClienteUseCaseInterface eliminarClienteUseCase;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,
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

    public ClienteDto crearCliente(ClienteDto clienteDto) {
        return crearClienteUseCase.ejecutar(clienteDto);
    }

    @Transactional(readOnly = true)
    public ClienteDto obtenerClientePorId(Long id) {
        return obtenerClienteUseCase.ejecutarPorId(id);
    }

    @Transactional(readOnly = true)
    public ClienteDto obtenerClientePorIdentificacion(String identificacion) {
        return obtenerClienteUseCase.ejecutarPorIdentificacion(identificacion);
    }

    @Transactional(readOnly = true)
    public List<ClienteDto> obtenerTodosLosClientes() {
        return clienteRepository.findAll().stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDto) {
        return actualizarClienteUseCase.ejecutar(id, clienteDto);
    }

    public void eliminarCliente(Long id) {
        eliminarClienteUseCase.ejecutar(id);
    }

    private ClienteDto convertirEntidadADto(Cliente cliente) {
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
    }
}