package com.banco.cuenta.service;

import com.banco.cuenta.dto.MovimientoDto;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;
import com.banco.cuenta.exception.CuentaNotFoundException;
import com.banco.cuenta.exception.SaldoInsuficienteException;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public MovimientoDto registrarMovimiento(MovimientoDto movimientoDto) {
        Cuenta cuenta = cuentaRepository.findById(movimientoDto.getCuentaId())
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con ID: " + movimientoDto.getCuentaId()));

        BigDecimal nuevoSaldo = calcularNuevoSaldo(cuenta.getSaldoDisponible(), movimientoDto.getValor());
        
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDate.now());
        movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
        movimiento.setValor(movimientoDto.getValor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
        return convertirEntidadADto(movimientoGuardado);
    }

    @Transactional(readOnly = true)
    public MovimientoDto obtenerMovimientoPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        return convertirEntidadADto(movimiento);
    }

    @Transactional(readOnly = true)
    public List<MovimientoDto> obtenerMovimientosPorCuenta(Long cuentaId) {
        return movimientoRepository.findByCuentaCuentaIdOrderByFechaDesc(cuentaId).stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovimientoDto> obtenerMovimientosPorNumeroCuenta(String numeroCuenta) {
        return movimientoRepository.findByCuentaNumeroCuentaOrderByFechaDesc(numeroCuenta).stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovimientoDto> obtenerTodosLosMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovimientoDto> obtenerMovimientosPorClienteYFechas(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        return movimientoRepository.findMovimientosByClienteAndFechaRange(clienteId, fechaInicio, fechaFin).stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    public MovimientoDto actualizarMovimiento(Long id, MovimientoDto movimientoDto) {
        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));

        movimientoExistente.setTipoMovimiento(movimientoDto.getTipoMovimiento());
        movimientoExistente.setValor(movimientoDto.getValor());

        Movimiento movimientoActualizado = movimientoRepository.save(movimientoExistente);
        return convertirEntidadADto(movimientoActualizado);
    }

    public void eliminarMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new RuntimeException("Movimiento no encontrado con ID: " + id);
        }
        movimientoRepository.deleteById(id);
    }

    private BigDecimal calcularNuevoSaldo(BigDecimal saldoActual, BigDecimal valorMovimiento) {
        return saldoActual.add(valorMovimiento);
    }

    private MovimientoDto convertirEntidadADto(Movimiento movimiento) {
        MovimientoDto dto = new MovimientoDto();
        dto.setMovimientoId(movimiento.getMovimientoId());
        dto.setFecha(movimiento.getFecha());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        dto.setValor(movimiento.getValor());
        dto.setSaldo(movimiento.getSaldo());
        dto.setCuentaId(movimiento.getCuenta().getCuentaId());
        dto.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        return dto;
    }
}