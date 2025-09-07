package com.banco.cliente.entity;

import com.banco.cliente.domain.valueobject.Telefono;
import com.banco.cliente.infrastructure.converter.TelefonoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Long personaId;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El género es obligatorio")
    @Column(name = "genero", nullable = false)
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @PositiveOrZero(message = "La edad debe ser positiva")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank(message = "La identificación es obligatoria")
    @Column(name = "identificacion", nullable = false, unique = true)
    private String identificacion;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono", nullable = false)
    @Convert(converter = TelefonoConverter.class)
    private Telefono telefono;

    public Persona() {
    }

    public Persona(String nombre, String genero, Integer edad, String identificacion, String direccion, String telefono) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = new Telefono(telefono);
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono != null ? telefono.getNumero() : null;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono != null ? new Telefono(telefono) : null;
    }

    public Telefono getTelefonoVO() {
        return telefono;
    }
}