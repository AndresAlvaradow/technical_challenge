# Technical Challenge - Sistema Bancario con Microservicios

## Descripción

Sistema bancario implementado con arquitectura de microservicios usando Spring Boot.

**Ubicación del proyecto**: `/technical_challenge/`

## Arquitectura

### Microservicios

1. **Microservicio Cliente** (Puerto 8081)
   - Gestión de personas y clientes
   - Patrón Repository implementado
   - Comunicación asíncrona con RabbitMQ

2. **Microservicio Cuenta** (Puerto 8082)
   - Gestión de cuentas y movimientos
   - Patrón Repository implementado
   - Validaciones de saldo y reportes

### Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (Patrón Repository)
- **PostgreSQL** (Producción)
- **H2 Database** (Desarrollo/Testing)
- **RabbitMQ** (Comunicación asíncrona)
- **Docker & Docker Compose**
- **JUnit 5 & Mockito** (Testing)
- **Maven** (Gestión de dependencias)

## Funcionalidades Implementadas

### ✅ F1: CRUDs Completos
- **Endpoint /clientes**: CRUD completo para clientes
- **Endpoint /cuentas**: CRUD completo para cuentas
- **Endpoint /movimientos**: CRUD completo para movimientos

### ✅ F2: Registro de Movimientos
- Actualización automática de saldo disponible
- Soporte para valores positivos (depósitos) y negativos (retiros)
- Registro completo de transacciones

### ✅ F3: Validación de Saldo
- Excepción personalizada: `SaldoInsuficienteException`
- Mensaje: "Saldo no disponible"
- Validación en tiempo real antes de procesar movimientos

### ✅ F4: Reportes
- **Endpoint /reportes**: Estado de cuenta por rango de fechas y cliente
- Formato JSON con cuentas asociadas y detalles de movimientos
- Parámetros: `clienteId`, `fechaInicio`, `fechaFin`

### ✅ F5: Pruebas Unitarias
- Pruebas unitarias para la entidad Cliente implementadas
- Cobertura de casos positivos y negativos
- Uso de Mockito para simulación de dependencias

### ✅ F6: Pruebas de Integración
- Pruebas de integración para controladores
- Testing completo de endpoints con MockMvc
- Configuración de perfil de testing

### ✅ F7: Despliegue en Contenedores
- Dockerfiles para ambos microservicios
- Docker Compose con PostgreSQL y RabbitMQ
- Configuración de red y dependencias

## Estructura del Proyecto

```
java/
└── technical_challenge/
    ├── microservicio-cliente/
    │   ├── src/main/java/com/banco/cliente/
    │   │   ├── controller/     # Controladores REST
    │   │   ├── service/        # Lógica de negocio
    │   │   ├── repository/     # Patrón Repository
    │   │   ├── entity/         # Entidades JPA
    │   │   ├── dto/            # Data Transfer Objects
    │   │   ├── exception/      # Manejo de excepciones
    │   │   └── config/         # Configuraciones
    │   └── src/test/           # Pruebas unitarias e integración
    ├── microservicio-cuenta/
    │   ├── src/main/java/com/banco/cuenta/
    │   │   ├── controller/     # Controladores REST
    │   │   ├── service/        # Lógica de negocio
    │   │   ├── repository/     # Patrón Repository
    │   │   ├── entity/         # Entidades JPA
    │   │   ├── dto/            # Data Transfer Objects
    │   │   ├── exception/      # Manejo de excepciones
    │   │   └── config/         # Configuraciones
    │   └── src/test/           # Pruebas unitarias e integración
    ├── docker-compose.yml      # Orquestación de contenedores
    ├── BaseDatos.sql          # Script de base de datos
    ├── Banco_Microservicios.postman_collection.json
    └── README.md              # Documentación del proyecto
```

## Instalación y Ejecución

### Prerrequisitos

- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### Opción 1: Ejecución con Docker (Recomendada)

```bash
# Clonar o descargar el proyecto
cd java/technical_challenge/

# Ejecutar con Docker Compose
docker-compose up --build

# Los servicios estarán disponibles en:
# - Microservicio Cliente: http://localhost:8081
# - Microservicio Cuenta: http://localhost:8082
# - PostgreSQL: localhost:5432
# - RabbitMQ Management: http://localhost:15672
```

### Opción 2: Ejecución Local

```bash
# Terminal 1 - Microservicio Cliente
cd microservicio-cliente/
mvn spring-boot:run

# Terminal 2 - Microservicio Cuenta
cd microservicio-cuenta/
mvn spring-boot:run
```

## Testing

### Ejecutar Pruebas Unitarias

```bash
# Microservicio Cliente
cd microservicio-cliente/
mvn test

# Microservicio Cuenta
cd microservicio-cuenta/
mvn test
```

### Usar Colección Postman

1. Importar archivo `Banco_Microservicios.postman_collection.json` en Postman
2. Las variables de entorno están preconfiguradas:
   - `base_url_cliente`: http://localhost:8081
   - `base_url_cuenta`: http://localhost:8082
3. Ejecutar los casos de uso en orden secuencial

## Casos de Uso Implementados

### 1. Creación de Clientes
- Jose Lema (ID: 1234567890)
- Marianela Montalvo (ID: 0987654321)
- Juan Osorio (ID: 1122334455)

### 2. Creación de Cuentas
- Cuenta Ahorro 478758 - Jose Lema ($2000)
- Cuenta Corriente 225487 - Marianela Montalvo ($100)
- Cuenta Ahorros 495878 - Juan Osorio ($0)

### 3. Movimientos de Ejemplo
- Retiro de $575 de cuenta 478758
- Depósito de $600 en cuenta 225487
- Depósito de $150 en cuenta 495878

## Patrones de Diseño Implementados

### Repository Pattern
- Abstracción de acceso a datos
- Interfaces que extienden `JpaRepository`
- Queries personalizadas para funcionalidades específicas

### Service Layer Pattern
- Separación de lógica de negocio
- Transacciones declarativas con `@Transactional`
- Conversión entre DTOs y entidades

### Exception Handling
- Manejo global de excepciones con `@RestControllerAdvice`
- Excepciones personalizadas por dominio
- Respuestas consistentes de error

### Event-Driven Architecture
- Comunicación asíncrona entre microservicios
- Publisher/Subscriber pattern con RabbitMQ
- Eventos de dominio para cambios en clientes

## Consideraciones de Arquitectura Senior

### Rendimiento
- Índices de base de datos optimizados
- Pool de conexiones configurado
- Queries eficientes con JPA

### Escalabilidad
- Arquitectura de microservicios independientes
- Comunicación asíncrona no bloqueante
- Containerización para despliegue escalable

### Resiliencia
- Manejo robusto de excepciones
- Health checks para contenedores
- Retry logic en comunicación entre servicios

## Base de Datos

El script `BaseDatos.sql` incluye:
- Esquema completo de tablas
- Índices para optimización
- Datos de prueba
- Vistas para reportes
- Triggers para timestamps

## Monitoreo

### Endpoints de Salud
- Cliente: http://localhost:8081/actuator/health
- Cuenta: http://localhost:8082/actuator/health

### Consolas de Administración
- H2 Console: http://localhost:8081/h2-console (desarrollo)
- RabbitMQ Management: http://localhost:15672 (guest/guest)

## Contacto

Desarrollado para prueba técnica de perfil Senior - Sistema Bancario con Microservicios.

Para consultas técnicas, revisar la documentación de cada microservicio y las pruebas implementadas.