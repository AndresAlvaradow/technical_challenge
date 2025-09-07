package com.banco.cliente.controller;

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

import com.banco.cliente.application.service.ClienteApplicationService;
import com.banco.cliente.dto.ClienteDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteApplicationService clienteApplicationService;

    @Autowired
    public ClienteController(ClienteApplicationService clienteApplicationService) {
        this.clienteApplicationService = clienteApplicationService;
    }

    @PostMapping
    public ResponseEntity<ClienteDto> crearCliente(@Valid @RequestBody ClienteDto clienteDto) {
        ClienteDto clienteCreado = clienteApplicationService.crearCliente(clienteDto);
        return new ResponseEntity<>(clienteCreado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id) {
        ClienteDto cliente = clienteApplicationService.obtenerClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<ClienteDto> obtenerClientePorIdentificacion(@PathVariable String identificacion) {
        ClienteDto cliente = clienteApplicationService.obtenerClientePorIdentificacion(identificacion);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> obtenerTodosLosClientes() {
        List<ClienteDto> clientes = clienteApplicationService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable Long id, 
                                                        @Valid @RequestBody ClienteDto clienteDto) {
        ClienteDto clienteActualizado = clienteApplicationService.actualizarCliente(id, clienteDto);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCliente(@PathVariable Long id) {
        clienteApplicationService.eliminarCliente(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Cliente eliminado correctamente");
        return ResponseEntity.ok(response);
    }
}