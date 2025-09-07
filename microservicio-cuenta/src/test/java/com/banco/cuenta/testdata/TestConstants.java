package com.banco.cuenta.testdata;

/**
 * Constantes para tests - Elimina magic strings y magic numbers
 * Centraliza todos los valores hardcodeados para fácil mantenimiento
 */
public final class TestConstants {

    // Prevenir instanciación
    private TestConstants() {}

    // ==========================================
    // CONSTANTES DE CLIENTE
    // ==========================================
    public static final Long DEFAULT_CLIENTE_ID = 1L;
    public static final Long SECOND_CLIENTE_ID = 2L;
    public static final Long NONEXISTENT_CLIENTE_ID = 999L;

    public static final String DEFAULT_CLIENTE_NOMBRE = "Cliente Test";
    public static final String CLIENTE_NOMBRE_ACTUALIZADO = "Cliente Test Actualizado";
    
    // Géneros
    public static final String DEFAULT_GENERO_MASCULINO = "Masculino";
    public static final String DEFAULT_GENERO_FEMENINO = "Femenino";

    // Edades
    public static final Integer DEFAULT_EDAD_VALIDA = 25;
    public static final Integer EDAD_MENOR = 17;
    public static final Integer EDAD_MAYOR = 65;

    // Identificaciones
    public static final String DEFAULT_IDENTIFICACION = "1234567890";
    public static final String SECOND_IDENTIFICACION = "0987654321";
    public static final String THIRD_IDENTIFICACION = "1122334455";

    // Direcciones
    public static final String DEFAULT_DIRECCION = "Dirección Test 123";
    public static final String DIRECCION_JOSE_LEMA = "Otavalo sn y principal";
    public static final String DIRECCION_MARIANELA = "Amazonas y NNUU";

    // Teléfonos
    public static final String DEFAULT_TELEFONO = "0987654321";
    public static final String TELEFONO_JOSE_LEMA = "098254785";
    public static final String TELEFONO_MARIANELA = "097548965";

    // Contraseñas
    public static final String DEFAULT_CONTRASENA_SEGURA = "TestPass123";
    public static final String CONTRASENA_INSEGURA = "123";
    public static final String CONTRASENA_SIN_MAYUSCULA = "testpass123";
    public static final String CONTRASENA_SIN_NUMERO = "TestPassword";

    // Estados
    public static final Boolean DEFAULT_ESTADO_ACTIVO = true;
    public static final Boolean ESTADO_INACTIVO = false;

    // ==========================================
    // CONSTANTES DE HTTP/REST
    // ==========================================
    public static final String CLIENTES_BASE_URL = "/clientes";
    public static final String CLIENTES_BY_ID_URL = "/clientes/{id}";
    public static final String CLIENTES_BY_IDENTIFICACION_URL = "/clientes/identificacion/{identificacion}";

    // ==========================================
    // CONSTANTES DE CUENTA (para microservicio-cuenta)
    // ==========================================
    public static final Long DEFAULT_CUENTA_ID = 1L;
    public static final Long SECOND_CUENTA_ID = 2L;
    public static final Long NONEXISTENT_CUENTA_ID = 999L;
    public static final String DEFAULT_NUMERO_CUENTA = "478758";
    public static final String SEGUNDO_NUMERO_CUENTA = "225487";
    
    public static final String TIPO_CUENTA_AHORRO = "Ahorro";
    public static final String TIPO_CUENTA_CORRIENTE = "Corriente";
    
    public static final String SALDO_INICIAL_DEFAULT = "2000.00";
    public static final String SALDO_BAJO = "100.00";
    public static final String SALDO_ALTO = "5000.00";

    // ==========================================
    // CONSTANTES DE MOVIMIENTO
    // ==========================================
    public static final Long DEFAULT_MOVIMIENTO_ID = 1L;
    public static final String TIPO_MOVIMIENTO_DEPOSITO = "Deposito";
    public static final String TIPO_MOVIMIENTO_RETIRO = "Retiro";
    
    public static final String VALOR_DEPOSITO_DEFAULT = "600.00";
    public static final String VALOR_RETIRO_DEFAULT = "-575.00";
    public static final String VALOR_RETIRO_ALTO = "-200.00";

    // ==========================================
    // MENSAJES DE ERROR ESPERADOS
    // ==========================================
    public static final String ERROR_CLIENTE_NO_ENCONTRADO = "Cliente no encontrado con ID: ";
    public static final String ERROR_CLIENTE_YA_EXISTE = "Ya existe un cliente con identificación: ";
    public static final String ERROR_SALDO_INSUFICIENTE = "Saldo no disponible";
    public static final String ERROR_CUENTA_NO_ENCONTRADA = "Cuenta no encontrada con ID: ";
    public static final String ERROR_EDAD_MENOR = "El cliente debe ser mayor de edad (18 años)";
    public static final String ERROR_CONTRASENA_CORTA = "La contraseña debe tener al menos 4 caracteres";

    // ==========================================
    // CONSTANTES DE VALIDACIÓN
    // ==========================================
    public static final int MIN_EDAD_VALIDA = 18;
    public static final int MIN_LONGITUD_IDENTIFICACION = 8;
    public static final int MAX_LONGITUD_IDENTIFICACION = 20;
    public static final int MIN_LONGITUD_CONTRASENA = 8;

    // ==========================================
    // CONSTANTES DE TEST COLLECTIONS
    // ==========================================
    public static final int EXPECTED_LIST_SIZE_TWO = 2;
    public static final int EXPECTED_LIST_SIZE_EMPTY = 0;
    public static final int FIRST_ELEMENT_INDEX = 0;
    public static final int SECOND_ELEMENT_INDEX = 1;

    // ==========================================
    // CONSTANTES DE RABBITMQ (si se usan en tests)
    // ==========================================
    public static final String CLIENTE_QUEUE_NAME = "cliente.queue";
    public static final String CLIENTE_EXCHANGE_NAME = "cliente.exchange";
    public static final String CLIENTE_ROUTING_KEY = "cliente.created";
    
    public static final String EVENT_TYPE_CLIENTE_CREATED = "CLIENTE_CREATED";
    public static final String EVENT_TYPE_CLIENTE_UPDATED = "CLIENTE_UPDATED";
    public static final String EVENT_TYPE_CLIENTE_DELETED = "CLIENTE_DELETED";
}