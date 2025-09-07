package com.banco.cuenta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimientoDto {

    private Long movimientoId;
    private LocalDate fecha;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;

    @NotNull(message = "El valor es obligatorio")
    private BigDecimal valor;

    private BigDecimal saldo;

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long cuentaId;

    private String numeroCuenta;

    public MovimientoDto() {
    }

    public MovimientoDto(Long movimientoId, LocalDate fecha, String tipoMovimiento, BigDecimal valor,
                         BigDecimal saldo, Long cuentaId, String numeroCuenta) {
        this.movimientoId = movimientoId;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
        this.cuentaId = cuentaId;
        this.numeroCuenta = numeroCuenta;
    }

    public Long getMovimientoId() {
        return movimientoId;
    }

    public void setMovimientoId(Long movimientoId) {
        this.movimientoId = movimientoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}