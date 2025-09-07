package com.banco.cliente.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.cliente.domain.repository.ClienteRepositoryInterface;
import com.banco.cliente.domain.service.ClienteDomainServiceInterface;
import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.exception.ClienteAlreadyExistsException;
import com.banco.cliente.exception.ClienteNotFoundException;
import com.banco.cliente.service.ClienteEventPublisher;

@Component
public class ActualizarClienteUseCase implements ActualizarClienteUseCaseInterface {

    private final ClienteRepositoryInterface clienteRepository;
    private final ClienteEventPublisher eventPublisher;
    private final ClienteDomainServiceInterface domainService;

    @Autowired
    public ActualizarClienteUseCase(ClienteRepositoryInterface clienteRepository, ClienteEventPublisher eventPublisher, ClienteDomainServiceInterface domainService) {
        this.clienteRepository = clienteRepository;
        this.eventPublisher = eventPublisher;
        this.domainService = domainService;
    }

    public ClienteDto ejecutar(Long id, ClienteDto clienteDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        if (!clienteExistente.getIdentificacion().equals(clienteDto.getIdentificacion()) &&
            clienteRepository.existsByIdentificacion(clienteDto.getIdentificacion())) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con identificación: " + clienteDto.getIdentificacion());
        }

        Cliente clienteActualizado = new Cliente();
        actualizarDatosCliente(clienteActualizado, clienteDto);
        
        domainService.validarClienteParaActualizacion(clienteExistente, clienteActualizado);
        
        actualizarDatosCliente(clienteExistente, clienteDto);
        clienteActualizado = clienteRepository.save(clienteExistente);
        
        eventPublisher.publicarClienteActualizado(clienteActualizado.getClienteId(), 
                clienteActualizado.getNombre(), clienteActualizado.getIdentificacion());
        
        return convertirEntidadADto(clienteActualizado);
    }

    private void actualizarDatosCliente(Cliente cliente, ClienteDto dto) {
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());
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