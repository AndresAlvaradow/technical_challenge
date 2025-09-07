package com.banco.cliente.infrastructure.repository;

import com.banco.cliente.domain.repository.ClienteRepositoryInterface;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de infraestructura para el repositorio de clientes
 * Implementa la interfaz de dominio usando el repositorio JPA
 * Esta clase implementa el patrón Adapter para Clean Architecture
 */
@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryInterface {

    private final ClienteRepository jpaRepository;

    @Autowired
    public ClienteRepositoryAdapter(ClienteRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return jpaRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Cliente> findByIdentificacion(String identificacion) {
        return jpaRepository.findByIdentificacion(identificacion);
    }

    @Override
    public Optional<Cliente> findActiveClienteByIdentificacion(String identificacion) {
        return jpaRepository.findByIdentificacionAndEstado(identificacion, true);
    }

    @Override
    public boolean existsByIdentificacion(String identificacion) {
        return jpaRepository.existsByIdentificacion(identificacion);
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Cliente> findAllActive() {
        return jpaRepository.findByEstado(true);
    }

    @Override
    public List<Cliente> findByEstado(Boolean estado) {
        return jpaRepository.findByEstado(estado);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}