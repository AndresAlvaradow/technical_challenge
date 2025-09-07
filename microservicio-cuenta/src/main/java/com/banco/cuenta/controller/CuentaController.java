package com.banco.cuenta.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.banco.cuenta.dto.CuentaDto;
import com.banco.cuenta.service.CuentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cuentas")
@CrossOrigin(origins = "*")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaDto> crearCuenta(@Valid @RequestBody CuentaDto cuentaDto) {
        CuentaDto cuentaCreada = cuentaService.crearCuenta(cuentaDto);
        return new ResponseEntity<>(cuentaCreada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDto> obtenerCuentaPorId(@PathVariable Long id) {
        CuentaDto cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping("/numero/{numeroCuenta}")
    public ResponseEntity<CuentaDto> obtenerCuentaPorNumero(@PathVariable String numeroCuenta) {
        CuentaDto cuenta = cuentaService.obtenerCuentaPorNumero(numeroCuenta);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaDto>> obtenerCuentasPorCliente(@PathVariable Long clienteId) {
        List<CuentaDto> cuentas = cuentaService.obtenerCuentasPorCliente(clienteId);
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping
    public ResponseEntity<List<CuentaDto>> obtenerTodasLasCuentas() {
        List<CuentaDto> cuentas = cuentaService.obtenerTodasLasCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDto> actualizarCuenta(@PathVariable Long id, 
                                                      @Valid @RequestBody CuentaDto cuentaDto) {
        CuentaDto cuentaActualizada = cuentaService.actualizarCuenta(id, cuentaDto);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Cuenta eliminada correctamente");
        return ResponseEntity.ok(response);
    }
}