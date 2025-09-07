package com.banco.cuenta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReporteEstadoCuentaDto {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long clienteId;
    private String nombreCliente;
    private List<CuentaReporteDto> cuentas;

    public ReporteEstadoCuentaDto() {
    }

    public ReporteEstadoCuentaDto(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId,
                                  String nombreCliente, List<CuentaReporteDto> cuentas) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.cuentas = cuentas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<CuentaReporteDto> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaReporteDto> cuentas) {
        this.cuentas = cuentas;
    }

    public static class CuentaReporteDto {
        private String numeroCuenta;
        private String tipoCuenta;
        private BigDecimal saldoInicial;
        private BigDecimal saldoDisponible;
        private List<MovimientoReporteDto> movimientos;

        public CuentaReporteDto() {
        }

        public CuentaReporteDto(String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial,
                                BigDecimal saldoDisponible, List<MovimientoReporteDto> movimientos) {
            this.numeroCuenta = numeroCuenta;
            this.tipoCuenta = tipoCuenta;
            this.saldoInicial = saldoInicial;
            this.saldoDisponible = saldoDisponible;
            this.movimientos = movimientos;
        }

        public String getNumeroCuenta() {
            return numeroCuenta;
        }

        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public String getTipoCuenta() {
            return tipoCuenta;
        }

        public void setTipoCuenta(String tipoCuenta) {
            this.tipoCuenta = tipoCuenta;
        }

        public BigDecimal getSaldoInicial() {
            return saldoInicial;
        }

        public void setSaldoInicial(BigDecimal saldoInicial) {
            this.saldoInicial = saldoInicial;
        }

        public BigDecimal getSaldoDisponible() {
            return saldoDisponible;
        }

        public void setSaldoDisponible(BigDecimal saldoDisponible) {
            this.saldoDisponible = saldoDisponible;
        }

        public List<MovimientoReporteDto> getMovimientos() {
            return movimientos;
        }

        public void setMovimientos(List<MovimientoReporteDto> movimientos) {
            this.movimientos = movimientos;
        }
    }

    public static class MovimientoReporteDto {
        private LocalDate fecha;
        private String tipoMovimiento;
        private BigDecimal valor;
        private BigDecimal saldo;

        public MovimientoReporteDto() {
        }

        public MovimientoReporteDto(LocalDate fecha, String tipoMovimiento, BigDecimal valor, BigDecimal saldo) {
            this.fecha = fecha;
            this.tipoMovimiento = tipoMovimiento;
            this.valor = valor;
            this.saldo = saldo;
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
    }
}