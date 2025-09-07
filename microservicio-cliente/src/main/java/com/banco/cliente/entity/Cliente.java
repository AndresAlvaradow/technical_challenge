package com.banco.cliente.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Cliente extends Persona {

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @NotNull(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String genero, Integer edad, String identificacion, 
                   String direccion, String telefono, String contrasena, Boolean estado) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return super.getPersonaId();
    }
}