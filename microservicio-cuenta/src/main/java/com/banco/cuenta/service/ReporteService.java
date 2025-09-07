package com.banco.cuenta.service;

import com.banco.cuenta.dto.ReporteEstadoCuentaDto;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    @Autowired
    public ReporteService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public ReporteEstadoCuentaDto generarReporteEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        
        if (cuentas.isEmpty()) {
            throw new RuntimeException("Cliente no tiene cuentas registradas");
        }

        List<Movimiento> movimientos = movimientoRepository.findMovimientosByClienteAndFechaRange(
                clienteId, fechaInicio, fechaFin);

        Map<Long, List<Movimiento>> movimientosPorCuenta = movimientos.stream()
                .collect(Collectors.groupingBy(m -> m.getCuenta().getCuentaId()));

        List<ReporteEstadoCuentaDto.CuentaReporteDto> cuentasReporte = cuentas.stream()
                .map(cuenta -> {
                    List<Movimiento> movimientosCuenta = movimientosPorCuenta.getOrDefault(
                            cuenta.getCuentaId(), List.of());
                    
                    List<ReporteEstadoCuentaDto.MovimientoReporteDto> movimientosDto = 
                            movimientosCuenta.stream()
                                    .map(m -> new ReporteEstadoCuentaDto.MovimientoReporteDto(
                                            m.getFecha(), m.getTipoMovimiento(), m.getValor(), m.getSaldo()))
                                    .collect(Collectors.toList());

                    return new ReporteEstadoCuentaDto.CuentaReporteDto(
                            cuenta.getNumeroCuenta(), cuenta.getTipoCuenta(),
                            cuenta.getSaldoInicial(), cuenta.getSaldoDisponible(),
                            movimientosDto);
                })
                .collect(Collectors.toList());

        return new ReporteEstadoCuentaDto(fechaInicio, fechaFin, clienteId, 
                "Cliente " + clienteId, cuentasReporte);
    }
}