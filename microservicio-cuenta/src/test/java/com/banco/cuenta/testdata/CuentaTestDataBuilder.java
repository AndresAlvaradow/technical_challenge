package com.banco.cuenta.testdata;

import com.banco.cuenta.dto.CuentaDto;
import com.banco.cuenta.entity.Cuenta;

import java.math.BigDecimal;

/**
 * Test Data Builder para Cuenta usando el patrón Object Mother
 * Proporciona métodos fluidos para construir datos de test válidos y consistentes
 */
public class CuentaTestDataBuilder {
    
    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Boolean estado;
    private Long clienteId;

    private CuentaTestDataBuilder() {
        // Configuración por defecto con datos válidos
        this.cuentaId = TestConstants.DEFAULT_CUENTA_ID;
        this.numeroCuenta = TestConstants.DEFAULT_NUMERO_CUENTA;
        this.tipoCuenta = TestConstants.TIPO_CUENTA_AHORRO;
        this.saldoInicial = new BigDecimal(TestConstants.SALDO_INICIAL_DEFAULT);
        this.saldoDisponible = new BigDecimal(TestConstants.SALDO_INICIAL_DEFAULT);
        this.estado = TestConstants.DEFAULT_ESTADO_ACTIVO;
        this.clienteId = TestConstants.DEFAULT_CLIENTE_ID;
    }

    /**
     * Crea un builder con datos por defecto válidos
     */
    public static CuentaTestDataBuilder aValidCuenta() {
        return new CuentaTestDataBuilder();
    }

    /**
     * Cuenta de ahorros de José Lema con saldo alto
     */
    public static CuentaTestDataBuilder cuentaAhorrosJoseLema() {
        return new CuentaTestDataBuilder()
                .withNumeroCuenta("478758")
                .withTipoCuenta(TestConstants.TIPO_CUENTA_AHORRO)
                .withSaldoInicial(new BigDecimal("2000.00"))
                .withSaldoDisponible(new BigDecimal("2000.00"))
                .withClienteId(TestConstants.DEFAULT_CLIENTE_ID);
    }

    /**
     * Cuenta corriente de Marianela con saldo bajo
     */
    public static CuentaTestDataBuilder cuentaCorrienteMarianela() {
        return new CuentaTestDataBuilder()
                .withCuentaId(TestConstants.SECOND_CUENTA_ID)
                .withNumeroCuenta("225487")
                .withTipoCuenta(TestConstants.TIPO_CUENTA_CORRIENTE)
                .withSaldoInicial(new BigDecimal("100.00"))
                .withSaldoDisponible(new BigDecimal("100.00"))
                .withClienteId(TestConstants.SECOND_CLIENTE_ID);
    }

    /**
     * Cuenta con saldo insuficiente para tests de retiro
     */
    public static CuentaTestDataBuilder cuentaConSaldoBajo() {
        return new CuentaTestDataBuilder()
                .withSaldoInicial(new BigDecimal(TestConstants.SALDO_BAJO))
                .withSaldoDisponible(new BigDecimal(TestConstants.SALDO_BAJO));
    }

    /**
     * Cuenta inactiva para tests de validación
     */
    public static CuentaTestDataBuilder cuentaInactiva() {
        return new CuentaTestDataBuilder()
                .withEstado(TestConstants.ESTADO_INACTIVO);
    }

    // Métodos fluidos para personalización
    public CuentaTestDataBuilder withCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
        return this;
    }

    public CuentaTestDataBuilder withNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        return this;
    }

    public CuentaTestDataBuilder withTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public CuentaTestDataBuilder withSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
        return this;
    }

    public CuentaTestDataBuilder withSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
        return this;
    }

    public CuentaTestDataBuilder withEstado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public CuentaTestDataBuilder withClienteId(Long clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    /**
     * Construye un CuentaDto
     */
    public CuentaDto buildDto() {
        CuentaDto dto = new CuentaDto();
        dto.setCuentaId(cuentaId);
        dto.setNumeroCuenta(numeroCuenta);
        dto.setTipoCuenta(tipoCuenta);
        dto.setSaldoInicial(saldoInicial);
        dto.setSaldoDisponible(saldoDisponible);
        dto.setEstado(estado);
        dto.setClienteId(clienteId);
        return dto;
    }

    /**
     * Construye una entidad Cuenta
     */
    public Cuenta buildEntity() {
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(cuentaId);
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setSaldoInicial(saldoInicial);
        cuenta.setSaldoDisponible(saldoDisponible);
        cuenta.setEstado(estado);
        cuenta.setClienteId(clienteId);
        return cuenta;
    }
}