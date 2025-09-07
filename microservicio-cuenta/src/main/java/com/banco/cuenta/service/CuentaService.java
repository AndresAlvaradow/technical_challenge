package com.banco.cuenta.service;

import com.banco.cuenta.dto.CuentaDto;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.exception.CuentaAlreadyExistsException;
import com.banco.cuenta.exception.CuentaNotFoundException;
import com.banco.cuenta.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public CuentaDto crearCuenta(CuentaDto cuentaDto) {
        if (cuentaRepository.existsByNumeroCuenta(cuentaDto.getNumeroCuenta())) {
            throw new CuentaAlreadyExistsException("Ya existe una cuenta con número: " + cuentaDto.getNumeroCuenta());
        }

        Cuenta cuenta = convertirDtoAEntidad(cuentaDto);
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        return convertirEntidadADto(cuentaGuardada);
    }

    @Transactional(readOnly = true)
    public CuentaDto obtenerCuentaPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con ID: " + id));
        return convertirEntidadADto(cuenta);
    }

    @Transactional(readOnly = true)
    public CuentaDto obtenerCuentaPorNumero(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con número: " + numeroCuenta));
        return convertirEntidadADto(cuenta);
    }

    @Transactional(readOnly = true)
    public List<CuentaDto> obtenerCuentasPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId).stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CuentaDto> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll().stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    public CuentaDto actualizarCuenta(Long id, CuentaDto cuentaDto) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con ID: " + id));

        if (!cuentaExistente.getNumeroCuenta().equals(cuentaDto.getNumeroCuenta()) &&
            cuentaRepository.existsByNumeroCuenta(cuentaDto.getNumeroCuenta())) {
            throw new CuentaAlreadyExistsException("Ya existe una cuenta con número: " + cuentaDto.getNumeroCuenta());
        }

        actualizarDatosCuenta(cuentaExistente, cuentaDto);
        Cuenta cuentaActualizada = cuentaRepository.save(cuentaExistente);
        return convertirEntidadADto(cuentaActualizada);
    }

    public void eliminarCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNotFoundException("Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    private Cuenta convertirDtoAEntidad(CuentaDto dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setSaldoDisponible(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());
        return cuenta;
    }

    private CuentaDto convertirEntidadADto(Cuenta cuenta) {
        CuentaDto dto = new CuentaDto();
        dto.setCuentaId(cuenta.getCuentaId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setSaldoDisponible(cuenta.getSaldoDisponible());
        dto.setEstado(cuenta.getEstado());
        dto.setClienteId(cuenta.getClienteId());
        return dto;
    }

    private void actualizarDatosCuenta(Cuenta cuenta, CuentaDto dto) {
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());
    }
}