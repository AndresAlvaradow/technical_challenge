package com.banco.cuenta.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.cuenta.dto.MovimientoDto;
import com.banco.cuenta.service.MovimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Autowired
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoDto> registrarMovimiento(@Valid @RequestBody MovimientoDto movimientoDto) {
        MovimientoDto movimientoCreado = movimientoService.registrarMovimiento(movimientoDto);
        return new ResponseEntity<>(movimientoCreado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDto> obtenerMovimientoPorId(@PathVariable Long id) {
        MovimientoDto movimiento = movimientoService.obtenerMovimientoPorId(id);
        return ResponseEntity.ok(movimiento);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<MovimientoDto>> obtenerMovimientosPorCuenta(@PathVariable Long cuentaId) {
        List<MovimientoDto> movimientos = movimientoService.obtenerMovimientosPorCuenta(cuentaId);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<List<MovimientoDto>> obtenerMovimientosPorNumeroCuenta(@PathVariable String numeroCuenta) {
        List<MovimientoDto> movimientos = movimientoService.obtenerMovimientosPorNumeroCuenta(numeroCuenta);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MovimientoDto>> obtenerMovimientosPorClienteYFechas(
            @PathVariable Long clienteId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        List<MovimientoDto> movimientos = movimientoService.obtenerMovimientosPorClienteYFechas(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDto>> obtenerTodosLosMovimientos() {
        List<MovimientoDto> movimientos = movimientoService.obtenerTodosLosMovimientos();
        return ResponseEntity.ok(movimientos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDto> actualizarMovimiento(@PathVariable Long id, 
                                                              @Valid @RequestBody MovimientoDto movimientoDto) {
        MovimientoDto movimientoActualizado = movimientoService.actualizarMovimiento(id, movimientoDto);
        return ResponseEntity.ok(movimientoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Movimiento eliminado correctamente");
        return ResponseEntity.ok(response);
    }
}