package com.banco.cuenta.repository;

import com.banco.cuenta.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaCuentaIdOrderByFechaDesc(Long cuentaId);

    List<Movimiento> findByCuentaNumeroCuentaOrderByFechaDesc(String numeroCuenta);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.clienteId = :clienteId " +
           "AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fecha DESC")
    List<Movimiento> findMovimientosByClienteAndFechaRange(
            @Param("clienteId") Long clienteId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.cuentaId = :cuentaId " +
           "AND m.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY m.fecha DESC")
    List<Movimiento> findMovimientosByCuentaAndFechaRange(
            @Param("cuentaId") Long cuentaId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}