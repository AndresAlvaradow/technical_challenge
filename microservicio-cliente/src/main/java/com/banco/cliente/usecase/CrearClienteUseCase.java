package com.banco.cliente.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.cliente.domain.repository.ClienteRepositoryInterface;
import com.banco.cliente.domain.service.ClienteDomainServiceInterface;
import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.exception.ClienteAlreadyExistsException;
import com.banco.cliente.service.ClienteEventPublisher;

@Component
public class CrearClienteUseCase implements CrearClienteUseCaseInterface {

    private final ClienteRepositoryInterface clienteRepository;
    private final ClienteEventPublisher eventPublisher;
    private final ClienteDomainServiceInterface domainService;

    @Autowired
    public CrearClienteUseCase(ClienteRepositoryInterface clienteRepository, ClienteEventPublisher eventPublisher, ClienteDomainServiceInterface domainService) {
        this.clienteRepository = clienteRepository;
        this.eventPublisher = eventPublisher;
        this.domainService = domainService;
    }

    public ClienteDto ejecutar(ClienteDto clienteDto) {
    
        if (clienteRepository.existsByIdentificacion(clienteDto.getIdentificacion())) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con identificación: " + clienteDto.getIdentificacion());
        }

        Cliente cliente = convertirDtoAEntidad(clienteDto);
        
        domainService.validarClienteParaCreacion(cliente);
        
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        eventPublisher.publicarClienteCreado(clienteGuardado.getClienteId(), 
                clienteGuardado.getNombre(), clienteGuardado.getIdentificacion());
        
        return convertirEntidadADto(clienteGuardado);
    }

    private Cliente convertirDtoAEntidad(ClienteDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());
        return cliente;
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