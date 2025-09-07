package com.banco.cliente.testdata;

import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;

/**
 * Test Data Builder para Cliente usando el patrón Object Mother
 * Proporciona métodos fluidos para construir datos de test válidos y consistentes
 */
public class ClienteTestDataBuilder {
    
    private Long clienteId;
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;

    private ClienteTestDataBuilder() {
        // Configuración por defecto con datos válidos
        this.clienteId = TestConstants.DEFAULT_CLIENTE_ID;
        this.nombre = TestConstants.DEFAULT_CLIENTE_NOMBRE;
        this.genero = TestConstants.DEFAULT_GENERO_MASCULINO;
        this.edad = TestConstants.DEFAULT_EDAD_VALIDA;
        this.identificacion = TestConstants.DEFAULT_IDENTIFICACION;
        this.direccion = TestConstants.DEFAULT_DIRECCION;
        this.telefono = TestConstants.DEFAULT_TELEFONO;
        this.contrasena = TestConstants.DEFAULT_CONTRASENA_SEGURA;
        this.estado = TestConstants.DEFAULT_ESTADO_ACTIVO;
    }

    /**
     * Crea un builder con datos por defecto válidos
     */
    public static ClienteTestDataBuilder aValidCliente() {
        return new ClienteTestDataBuilder();
    }

    /**
     * Crea un builder con datos específicos para José Lema
     */
    public static ClienteTestDataBuilder joseLema() {
        return new ClienteTestDataBuilder()
                .withNombre("José Lema")
                .withEdad(35)
                .withIdentificacion("1234567890")
                .withDireccion("Otavalo sn y principal")
                .withTelefono("098254785");
    }

    /**
     * Crea un builder con datos específicos para Marianela Montalvo
     */
    public static ClienteTestDataBuilder marianela() {
        return new ClienteTestDataBuilder()
                .withNombre("Marianela Montalvo")
                .withGenero(TestConstants.DEFAULT_GENERO_FEMENINO)
                .withEdad(28)
                .withIdentificacion("0987654321")
                .withDireccion("Amazonas y NNUU")
                .withTelefono("097548965");
    }

    /**
     * Crea un builder con datos inválidos para tests de validación
     */
    public static ClienteTestDataBuilder anInvalidCliente() {
        return new ClienteTestDataBuilder()
                .withNombre(null)
                .withEdad(-1)
                .withIdentificacion("")
                .withContrasena("123"); // Contraseña muy corta
    }

    /**
     * Crea un builder con cliente menor de edad
     */
    public static ClienteTestDataBuilder aMenorDeEdad() {
        return new ClienteTestDataBuilder()
                .withEdad(17)
                .withNombre("Cliente Menor");
    }

    // Métodos fluidos para personalización
    public ClienteTestDataBuilder withClienteId(Long clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public ClienteTestDataBuilder withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ClienteTestDataBuilder withGenero(String genero) {
        this.genero = genero;
        return this;
    }

    public ClienteTestDataBuilder withEdad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public ClienteTestDataBuilder withIdentificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public ClienteTestDataBuilder withDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public ClienteTestDataBuilder withTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public ClienteTestDataBuilder withContrasena(String contrasena) {
        this.contrasena = contrasena;
        return this;
    }

    public ClienteTestDataBuilder withEstado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    /**
     * Construye un ClienteDto
     */
    public ClienteDto buildDto() {
        ClienteDto dto = new ClienteDto();
        dto.setClienteId(clienteId);
        dto.setNombre(nombre);
        dto.setGenero(genero);
        dto.setEdad(edad);
        dto.setIdentificacion(identificacion);
        dto.setDireccion(direccion);
        dto.setTelefono(telefono);
        dto.setContrasena(contrasena);
        dto.setEstado(estado);
        return dto;
    }

    /**
     * Construye una entidad Cliente
     */
    public Cliente buildEntity() {
        Cliente cliente = new Cliente();
        cliente.setPersonaId(clienteId);
        cliente.setNombre(nombre);
        cliente.setGenero(genero);
        cliente.setEdad(edad);
        cliente.setIdentificacion(identificacion);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setContrasena(contrasena);
        cliente.setEstado(estado);
        return cliente;
    }
}