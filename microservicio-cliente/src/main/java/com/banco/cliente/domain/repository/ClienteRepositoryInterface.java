package com.banco.cliente.domain.repository;

import java.util.List;
import java.util.Optional;

import com.banco.cliente.entity.Cliente;

/**
 * Interfaz de dominio para el repositorio de clientes
 * Define los métodos de persistencia desde la perspectiva del dominio
 * Esta interfaz implementa el principio de Dependency Inversion
 */
public interface ClienteRepositoryInterface {
    
    Cliente save(Cliente cliente);
    
    Optional<Cliente> findById(Long id);
    
    Optional<Cliente> findByIdentificacion(String identificacion);
    
    Optional<Cliente> findActiveClienteByIdentificacion(String identificacion);
    
    boolean existsByIdentificacion(String identificacion);
    
    List<Cliente> findAll();
    
    List<Cliente> findAllActive();
    
    List<Cliente> findByEstado(Boolean estado);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}