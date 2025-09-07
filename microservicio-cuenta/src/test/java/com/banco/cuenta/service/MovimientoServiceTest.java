package com.banco.cuenta.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banco.cuenta.dto.MovimientoDto;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;
import com.banco.cuenta.exception.CuentaNotFoundException;
import com.banco.cuenta.exception.SaldoInsuficienteException;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import com.banco.cuenta.testdata.CuentaTestDataBuilder;
import com.banco.cuenta.testdata.MovimientoTestDataBuilder;
import com.banco.cuenta.testdata.TestConstants;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    private MovimientoService movimientoService;

    @BeforeEach
    void setUp() {
        movimientoService = new MovimientoService(movimientoRepository, cuentaRepository);
    }

    @Test
    void registrarMovimiento_DepositoValido_DeberiaCrearMovimiento() {
        Cuenta cuenta = CuentaTestDataBuilder.cuentaAhorrosJoseLema().buildEntity();
        
        MovimientoDto movimientoDto = MovimientoTestDataBuilder.depositoJoseLema().buildDto();

        Movimiento movimientoGuardado = MovimientoTestDataBuilder.depositoJoseLema()
                .withCuenta(cuenta)
                .buildEntity();

        when(cuentaRepository.findById(TestConstants.DEFAULT_CUENTA_ID)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimientoGuardado);

        MovimientoDto resultado = movimientoService.registrarMovimiento(movimientoDto);

        assertNotNull(resultado);
        assertEquals(TestConstants.TIPO_MOVIMIENTO_DEPOSITO, resultado.getTipoMovimiento());
        assertEquals(new BigDecimal(TestConstants.VALOR_DEPOSITO_DEFAULT), resultado.getValor());
        assertEquals(new BigDecimal("2600.00"), resultado.getSaldo());

        verify(cuentaRepository).findById(TestConstants.DEFAULT_CUENTA_ID);
        verify(cuentaRepository).save(cuenta);
        verify(movimientoRepository).save(any(Movimiento.class));
        assertEquals(new BigDecimal("2600.00"), cuenta.getSaldoDisponible());
    }

    @Test
    void registrarMovimiento_RetiroConSaldoSuficiente_DeberiaCrearMovimiento() {
        Cuenta cuenta = CuentaTestDataBuilder.cuentaAhorrosJoseLema().buildEntity();
        
        MovimientoDto movimientoDto = MovimientoTestDataBuilder.retiroJoseLema().buildDto();

        Movimiento movimientoGuardado = MovimientoTestDataBuilder.retiroJoseLema()
                .withCuenta(cuenta)
                .buildEntity();

        when(cuentaRepository.findById(TestConstants.DEFAULT_CUENTA_ID)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimientoGuardado);

        MovimientoDto resultado = movimientoService.registrarMovimiento(movimientoDto);

        assertNotNull(resultado);
        assertEquals(TestConstants.TIPO_MOVIMIENTO_RETIRO, resultado.getTipoMovimiento());
        assertEquals(new BigDecimal(TestConstants.VALOR_RETIRO_DEFAULT), resultado.getValor());
        assertEquals(new BigDecimal("1425.00"), resultado.getSaldo());

        verify(cuentaRepository).save(cuenta);
        assertEquals(new BigDecimal("1425.00"), cuenta.getSaldoDisponible());
    }

    @Test
    void registrarMovimiento_RetiroSinSaldoSuficiente_DeberiaLanzarExcepcion() {
        Cuenta cuenta = CuentaTestDataBuilder.cuentaConSaldoBajo().buildEntity();
        
        MovimientoDto movimientoDto = MovimientoTestDataBuilder.retiroConSaldoInsuficiente().buildDto();

        when(cuentaRepository.findById(TestConstants.DEFAULT_CUENTA_ID)).thenReturn(Optional.of(cuenta));

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            movimientoService.registrarMovimiento(movimientoDto);
        });

        assertEquals(TestConstants.ERROR_SALDO_INSUFICIENTE, exception.getMessage());
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }

    @Test
    void registrarMovimiento_CuentaInexistente_DeberiaLanzarExcepcion() {
        MovimientoDto movimientoDto = MovimientoTestDataBuilder.movimientoCuentaInexistente().buildDto();

        when(cuentaRepository.findById(TestConstants.NONEXISTENT_CUENTA_ID)).thenReturn(Optional.empty());

        assertThrows(CuentaNotFoundException.class, () -> {
            movimientoService.registrarMovimiento(movimientoDto);
        });

        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }
}