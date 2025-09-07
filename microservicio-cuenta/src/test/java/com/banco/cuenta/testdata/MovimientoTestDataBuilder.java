package com.banco.cuenta.testdata;

import com.banco.cuenta.dto.MovimientoDto;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Test Data Builder para Movimiento usando el patrón Object Mother
 * Proporciona métodos fluidos para construir datos de test válidos y consistentes
 */
public class MovimientoTestDataBuilder {
    
    private Long movimientoId;
    private LocalDate fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Long cuentaId;
    private Cuenta cuenta;

    private MovimientoTestDataBuilder() {
        // Configuración por defecto con datos válidos
        this.movimientoId = TestConstants.DEFAULT_MOVIMIENTO_ID;
        this.fecha = LocalDate.now();
        this.tipoMovimiento = TestConstants.TIPO_MOVIMIENTO_DEPOSITO;
        this.valor = new BigDecimal(TestConstants.VALOR_DEPOSITO_DEFAULT);
        this.saldo = new BigDecimal(TestConstants.SALDO_INICIAL_DEFAULT);
        this.cuentaId = TestConstants.DEFAULT_CUENTA_ID;
    }

    /**
     * Crea un builder con datos por defecto válidos
     */
    public static MovimientoTestDataBuilder aValidMovimiento() {
        return new MovimientoTestDataBuilder();
    }

    /**
     * Depósito de 600.00 para cuenta de José Lema
     */
    public static MovimientoTestDataBuilder depositoJoseLema() {
        return new MovimientoTestDataBuilder()
                .withTipoMovimiento(TestConstants.TIPO_MOVIMIENTO_DEPOSITO)
                .withValor(new BigDecimal(TestConstants.VALOR_DEPOSITO_DEFAULT))
                .withSaldo(new BigDecimal("2600.00"))
                .withCuentaId(TestConstants.DEFAULT_CUENTA_ID);
    }

    /**
     * Retiro de 575.00 para cuenta de José Lema
     */
    public static MovimientoTestDataBuilder retiroJoseLema() {
        return new MovimientoTestDataBuilder()
                .withTipoMovimiento(TestConstants.TIPO_MOVIMIENTO_RETIRO)
                .withValor(new BigDecimal(TestConstants.VALOR_RETIRO_DEFAULT))
                .withSaldo(new BigDecimal("1425.00"))
                .withCuentaId(TestConstants.DEFAULT_CUENTA_ID);
    }

    /**
     * Retiro alto que causará saldo insuficiente
     */
    public static MovimientoTestDataBuilder retiroConSaldoInsuficiente() {
        return new MovimientoTestDataBuilder()
                .withTipoMovimiento(TestConstants.TIPO_MOVIMIENTO_RETIRO)
                .withValor(new BigDecimal(TestConstants.VALOR_RETIRO_ALTO))
                .withCuentaId(TestConstants.DEFAULT_CUENTA_ID);
    }

    /**
     * Movimiento para cuenta inexistente en tests de validación
     */
    public static MovimientoTestDataBuilder movimientoCuentaInexistente() {
        return new MovimientoTestDataBuilder()
                .withCuentaId(TestConstants.NONEXISTENT_CUENTA_ID);
    }

    // Métodos fluidos para personalización
    public MovimientoTestDataBuilder withMovimientoId(Long movimientoId) {
        this.movimientoId = movimientoId;
        return this;
    }

    public MovimientoTestDataBuilder withFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public MovimientoTestDataBuilder withTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return this;
    }

    public MovimientoTestDataBuilder withValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public MovimientoTestDataBuilder withSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public MovimientoTestDataBuilder withCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
        return this;
    }

    public MovimientoTestDataBuilder withCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
        return this;
    }

    /**
     * Construye un MovimientoDto
     */
    public MovimientoDto buildDto() {
        MovimientoDto dto = new MovimientoDto();
        dto.setMovimientoId(movimientoId);
        dto.setFecha(fecha);
        dto.setTipoMovimiento(tipoMovimiento);
        dto.setValor(valor);
        dto.setSaldo(saldo);
        dto.setCuentaId(cuentaId);
        return dto;
    }

    /**
     * Construye una entidad Movimiento
     */
    public Movimiento buildEntity() {
        Movimiento movimiento = new Movimiento();
        movimiento.setMovimientoId(movimientoId);
        movimiento.setFecha(fecha);
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setValor(valor);
        movimiento.setSaldo(saldo);
        movimiento.setCuenta(cuenta);
        return movimiento;
    }
}