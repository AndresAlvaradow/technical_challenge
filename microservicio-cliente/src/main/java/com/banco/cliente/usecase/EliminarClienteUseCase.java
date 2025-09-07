package com.banco.cliente.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.cliente.domain.repository.ClienteRepositoryInterface;
import com.banco.cliente.domain.service.ClienteDomainServiceInterface;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.exception.ClienteNotFoundException;
import com.banco.cliente.service.ClienteEventPublisher;

@Component
public class EliminarClienteUseCase implements EliminarClienteUseCaseInterface {

    private final ClienteRepositoryInterface clienteRepository;
    private final ClienteEventPublisher eventPublisher;

    @Autowired
    public EliminarClienteUseCase(ClienteRepositoryInterface clienteRepository, ClienteEventPublisher eventPublisher, ClienteDomainServiceInterface domainService) {
        this.clienteRepository = clienteRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void ejecutar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        
    eventPublisher.publicarClienteEliminado(cliente.getClienteId(), 
        cliente.getNombre(), cliente.getIdentificacion());
        
        clienteRepository.deleteById(id);
    }
}