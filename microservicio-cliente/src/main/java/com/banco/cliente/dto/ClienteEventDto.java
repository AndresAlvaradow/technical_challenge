package com.banco.cliente.dto;

import java.time.LocalDateTime;

public class ClienteEventDto {

    private Long clienteId;
    private String nombre;
    private String identificacion;
    private String eventType;
    private LocalDateTime timestamp;

    public ClienteEventDto() {
        this.timestamp = LocalDateTime.now();
    }

    public ClienteEventDto(Long clienteId, String nombre, String identificacion, String eventType) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}