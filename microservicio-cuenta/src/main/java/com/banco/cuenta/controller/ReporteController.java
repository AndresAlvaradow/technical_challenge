package com.banco.cuenta.controller;

import com.banco.cuenta.dto.ReporteEstadoCuentaDto;
import com.banco.cuenta.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<ReporteEstadoCuentaDto> generarReporteEstadoCuenta(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        ReporteEstadoCuentaDto reporte = reporteService.generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}