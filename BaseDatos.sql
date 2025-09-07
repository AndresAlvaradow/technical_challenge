-- ===================================================================
-- Script de Base de Datos para Sistema Bancario Microservicios
-- Versión: 1.0
-- Fecha: 2023
-- ===================================================================

-- Crear base de datos (opcional, depende del entorno)
-- CREATE DATABASE bancodb;
-- USE bancodb;

-- ===================================================================
-- TABLAS DEL MICROSERVICIO CLIENTE
-- ===================================================================

-- Tabla Personas (Clase base)
CREATE TABLE personas (
    persona_id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20) NOT NULL,
    edad INTEGER NOT NULL CHECK (edad >= 0),
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Clientes (Hereda de Personas)
CREATE TABLE clientes (
    cliente_id BIGINT PRIMARY KEY REFERENCES personas(persona_id) ON DELETE CASCADE,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===================================================================
-- TABLAS DEL MICROSERVICIO CUENTA
-- ===================================================================

-- Tabla Cuentas
CREATE TABLE cuentas (
    cuenta_id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL CHECK (saldo_inicial >= 0),
    saldo_disponible DECIMAL(15,2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Movimientos
CREATE TABLE movimientos (
    movimiento_id BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id BIGINT NOT NULL REFERENCES cuentas(cuenta_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===================================================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ===================================================================

-- Índices para tabla personas
CREATE INDEX idx_personas_identificacion ON personas(identificacion);
CREATE INDEX idx_personas_nombre ON personas(nombre);

-- Índices para tabla clientes
CREATE INDEX idx_clientes_estado ON clientes(estado);

-- Índices para tabla cuentas
CREATE INDEX idx_cuentas_numero_cuenta ON cuentas(numero_cuenta);
CREATE INDEX idx_cuentas_cliente_id ON cuentas(cliente_id);
CREATE INDEX idx_cuentas_estado ON cuentas(estado);

-- Índices para tabla movimientos
CREATE INDEX idx_movimientos_cuenta_id ON movimientos(cuenta_id);
CREATE INDEX idx_movimientos_fecha ON movimientos(fecha);
CREATE INDEX idx_movimientos_tipo ON movimientos(tipo_movimiento);

-- ===================================================================
-- TRIGGERS PARA TIMESTAMPS
-- ===================================================================

-- Función para actualizar timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers para actualizar updated_at
CREATE TRIGGER update_personas_updated_at BEFORE UPDATE ON personas 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_clientes_updated_at BEFORE UPDATE ON clientes 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_cuentas_updated_at BEFORE UPDATE ON cuentas 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_movimientos_updated_at BEFORE UPDATE ON movimientos 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ===================================================================
-- DATOS DE PRUEBA (CASOS DE USO DEL EJERCICIO)
-- ===================================================================

-- Insertar usuarios según casos de uso
INSERT INTO personas (nombre, genero, edad, identificacion, direccion, telefono) VALUES
('Jose Lema', 'Masculino', 35, '1234567890', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', 28, '0987654321', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', 42, '1122334455', '13 junio y Equinoccial', '098874587');

-- Insertar clientes
INSERT INTO clientes (cliente_id, contrasena, estado) VALUES
((SELECT persona_id FROM personas WHERE identificacion = '1234567890'), '1234', TRUE),
((SELECT persona_id FROM personas WHERE identificacion = '0987654321'), '5678', TRUE),
((SELECT persona_id FROM personas WHERE identificacion = '1122334455'), '1245', TRUE);

-- Insertar cuentas según casos de uso
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, saldo_inicial, saldo_disponible, estado, cliente_id) VALUES
('478758', 'Ahorro', 2000.00, 2000.00, TRUE, (SELECT cliente_id FROM clientes c JOIN personas p ON c.cliente_id = p.persona_id WHERE p.identificacion = '1234567890')),
('225487', 'Corriente', 100.00, 100.00, TRUE, (SELECT cliente_id FROM clientes c JOIN personas p ON c.cliente_id = p.persona_id WHERE p.identificacion = '0987654321')),
('495878', 'Ahorros', 0.00, 0.00, TRUE, (SELECT cliente_id FROM clientes c JOIN personas p ON c.cliente_id = p.persona_id WHERE p.identificacion = '1122334455')),
('496825', 'Ahorros', 540.00, 540.00, TRUE, (SELECT cliente_id FROM clientes c JOIN personas p ON c.cliente_id = p.persona_id WHERE p.identificacion = '0987654321')),
('585545', 'Corriente', 1000.00, 1000.00, TRUE, (SELECT cliente_id FROM clientes c JOIN personas p ON c.cliente_id = p.persona_id WHERE p.identificacion = '1234567890'));

-- Insertar movimientos de ejemplo
INSERT INTO movimientos (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
('2022-02-10', 'Deposito de 600', 600.00, 700.00, (SELECT cuenta_id FROM cuentas WHERE numero_cuenta = '225487')),
('2022-02-08', 'Retiro de 540', -540.00, 0.00, (SELECT cuenta_id FROM cuentas WHERE numero_cuenta = '496825')),
('2022-02-10', 'Retiro de 575', -575.00, 1425.00, (SELECT cuenta_id FROM cuentas WHERE numero_cuenta = '478758')),
('2022-02-10', 'Deposito de 150', 150.00, 150.00, (SELECT cuenta_id FROM cuentas WHERE numero_cuenta = '495878'));

-- ===================================================================
-- VISTAS ÚTILES PARA REPORTES
-- ===================================================================

-- Vista para reporte completo de clientes con sus cuentas
CREATE VIEW vista_clientes_cuentas AS
SELECT 
    p.persona_id,
    p.nombre,
    p.identificacion,
    p.telefono,
    p.direccion,
    c.estado as cliente_estado,
    cu.cuenta_id,
    cu.numero_cuenta,
    cu.tipo_cuenta,
    cu.saldo_inicial,
    cu.saldo_disponible,
    cu.estado as cuenta_estado
FROM personas p
JOIN clientes c ON p.persona_id = c.cliente_id
LEFT JOIN cuentas cu ON c.cliente_id = cu.cliente_id;

-- Vista para reporte de movimientos con información de cuenta y cliente
CREATE VIEW vista_movimientos_completa AS
SELECT 
    m.movimiento_id,
    m.fecha,
    m.tipo_movimiento,
    m.valor,
    m.saldo,
    cu.numero_cuenta,
    cu.tipo_cuenta,
    p.nombre as cliente_nombre,
    p.identificacion as cliente_identificacion
FROM movimientos m
JOIN cuentas cu ON m.cuenta_id = cu.cuenta_id
JOIN clientes c ON cu.cliente_id = c.cliente_id
JOIN personas p ON c.cliente_id = p.persona_id
ORDER BY m.fecha DESC, m.movimiento_id DESC;

-- ===================================================================
-- COMENTARIOS DE TABLAS Y COLUMNAS
-- ===================================================================

COMMENT ON TABLE personas IS 'Tabla base para información personal';
COMMENT ON TABLE clientes IS 'Tabla de clientes que extiende personas';
COMMENT ON TABLE cuentas IS 'Tabla de cuentas bancarias';
COMMENT ON TABLE movimientos IS 'Tabla de movimientos/transacciones';

COMMENT ON COLUMN personas.identificacion IS 'Número de identificación único';
COMMENT ON COLUMN clientes.contrasena IS 'Contraseña encriptada del cliente';
COMMENT ON COLUMN cuentas.saldo_disponible IS 'Saldo actual disponible';
COMMENT ON COLUMN movimientos.valor IS 'Valor del movimiento (positivo=depósito, negativo=retiro)';

-- ===================================================================
-- PERMISOS Y SEGURIDAD (OPCIONAL)
-- ===================================================================

-- Crear usuarios para los microservicios (opcional)
-- CREATE USER cliente_service WITH PASSWORD 'cliente_pass';
-- CREATE USER cuenta_service WITH PASSWORD 'cuenta_pass';

-- Otorgar permisos específicos
-- GRANT SELECT, INSERT, UPDATE, DELETE ON personas, clientes TO cliente_service;
-- GRANT SELECT, INSERT, UPDATE, DELETE ON cuentas, movimientos TO cuenta_service;
-- GRANT USAGE ON ALL SEQUENCES IN SCHEMA public TO cliente_service, cuenta_service;

-- ===================================================================
-- SCRIPT COMPLETADO
-- ===================================================================

-- Verificar datos insertados
SELECT 'Personas creadas: ' || COUNT(*) FROM personas;
SELECT 'Clientes creados: ' || COUNT(*) FROM clientes;
SELECT 'Cuentas creadas: ' || COUNT(*) FROM cuentas;
SELECT 'Movimientos creados: ' || COUNT(*) FROM movimientos;