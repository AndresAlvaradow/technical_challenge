package com.banco.cliente.repository;

import com.banco.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByIdentificacion(String identificacion);

    Optional<Cliente> findByIdentificacionAndEstado(String identificacion, Boolean estado);

    boolean existsByIdentificacion(String identificacion);

    @Query("SELECT c FROM Cliente c WHERE c.estado = :estado")
    List<Cliente> findByEstado(@Param("estado") Boolean estado);
}