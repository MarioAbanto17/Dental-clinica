-- Creación de la Base de Datos
CREATE DATABASE IF NOT EXISTS db_clinica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_clinica;

-- =============================================
-- 1. MÓDULO DE USUARIOS Y PACIENTES (RF-REG-001 al RF-REG-005)
-- =============================================
CREATE TABLE pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    tipo_documento ENUM('DNI', 'CE', 'PASAPORTE') DEFAULT 'DNI',
    numero_documento VARCHAR(20) UNIQUE NOT NULL, -- RF-REG-001: Validación documento
    email VARCHAR(150) UNIQUE NOT NULL,           -- RF-REG-001: Email único
    telefono VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,          -- RF-REG-001: Contraseña segura
    direccion VARCHAR(255),
    foto_perfil VARCHAR(255),                     -- RF-PAC-007
    contacto_emergencia_nombre VARCHAR(100),
    contacto_emergencia_tel VARCHAR(20),
    token_recuperacion VARCHAR(100),              -- RF-REG-003: Recuperación
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME,                       -- RF-REG-002: Registro acceso
    estado ENUM('ACTIVO', 'BLOQUEADO', 'ELIMINADO') DEFAULT 'ACTIVO', -- RF-WEB-011
    rol ENUM('PACIENTE', 'RECEPCION', 'ADMINISTRADOR') DEFAULT 'PACIENTE' NOT NULL
);

-- =============================================
-- 2. MÓDULO DE PERFIL MÉDICO (RF-PAC-010)
-- =============================================
CREATE TABLE historial_medico (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    grupo_sanguineo VARCHAR(10),
    alergias TEXT,                -- RF-PAC-010: Registro alergias
    condiciones_preexistentes TEXT,
    medicamentos_actuales TEXT,
    antecedentes_familiares TEXT,
    seguro_medico VARCHAR(100),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE
);

-- =============================================
-- 3. MÓDULO DE GESTIÓN DE CITAS (RF-CIT-012 al RF-CIT-019)
-- =============================================
CREATE TABLE doctores (
    id_doctor INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL, -- RF-CIT-012: Filtro especialidad
    horario_atencion VARCHAR(100),
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);

CREATE TABLE citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_doctor INT,                             -- Puede ser NULL si es asignación automática (RF-CIT-013)
    fecha_hora DATETIME NOT NULL,              -- RF-CIT-012: Calendario
    tipo_consulta ENUM('PRIMERA_VEZ', 'CONTROL', 'EMERGENCIA') NOT NULL, -- RF-CIT-013
    motivo_consulta TEXT,
    duracion_estimada_min INT DEFAULT 30,
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'COMPLETADA', 'CANCELADA', 'REPROGRAMADA') DEFAULT 'PENDIENTE',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente),
    FOREIGN KEY (id_doctor) REFERENCES doctores(id_doctor)
);

CREATE TABLE valoraciones_citas ( -- RF-CIT-019: Valoración post-cita
    id_valoracion INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT NOT NULL,
    calificacion TINYINT CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    es_anonimo BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_cita) REFERENCES citas(id_cita)
);

-- =============================================
-- 4. MÓDULO DE PAGOS Y FACTURACIÓN (RF-PAG-032 al RF-PAG-036)
-- =============================================
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_cita INT, -- Opcional, un pago puede ser de una cita específica o abono general
    monto DECIMAL(10, 2) NOT NULL,
    metodo_pago ENUM('TARJETA', 'YAPE', 'PLIN', 'EFECTIVO') NOT NULL, -- RF-PAG-034
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    comprobante_url VARCHAR(255),              -- RF-PAG-033: Descarga PDF
    estado ENUM('PENDIENTE', 'PAGADO', 'FALLIDO') DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente),
    FOREIGN KEY (id_cita) REFERENCES citas(id_cita)
);

CREATE TABLE facturacion_datos ( -- RF-PAG-036: Factura electrónica
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    id_pago INT NOT NULL,
    ruc VARCHAR(11) NOT NULL,
    razon_social VARCHAR(150) NOT NULL,
    direccion_fiscal VARCHAR(255),
    estado_sunat ENUM('PENDIENTE', 'ENVIADO', 'ACEPTADO', 'RECHAZADO') DEFAULT 'PENDIENTE',
    pdf_url VARCHAR(255),
    FOREIGN KEY (id_pago) REFERENCES pagos(id_pago)
);

-- =============================================
-- 5. MÓDULO DE COMUNICACIÓN (RF-COM-037 al RF-COM-040)
-- =============================================
CREATE TABLE mensajes ( -- RF-COM-037: Mensajería
    id_mensaje INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    remitente ENUM('PACIENTE', 'CLINICA') NOT NULL,
    contenido TEXT NOT NULL,
    archivo_adjunto_url VARCHAR(255),
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

CREATE TABLE notificaciones ( -- RF-COM-039
    id_notificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    tipo ENUM('CITA', 'PAGO', 'MENSAJE', 'RESULTADO') NOT NULL,
    mensaje VARCHAR(255),
    leido BOOLEAN DEFAULT FALSE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

CREATE TABLE certificados ( -- RF-COM-040
    id_certificado INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    tipo ENUM('ASISTENCIA', 'APTITUD', 'REPOSO') NOT NULL,
    motivo VARCHAR(255),
    estado ENUM('SOLICITADO', 'EMITIDO', 'RECHAZADO') DEFAULT 'SOLICITADO',
    archivo_url VARCHAR(255),
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

-- =============================================
-- 6. MÓDULO DE BENEFICIOS (RF-BEN-042 al RF-BEN-045)
-- =============================================
CREATE TABLE programa_beneficios ( -- RF-BEN-042: Sistema de puntos
    id_beneficio INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    puntos_acumulados INT DEFAULT 0,
    nivel ENUM('BRONCE', 'PLATA', 'ORO') DEFAULT 'BRONCE',
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

CREATE TABLE historial_puntos (
    id_transaccion INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    cantidad INT NOT NULL, -- Puede ser positivo (ganar) o negativo (canjear)
    concepto VARCHAR(100),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

CREATE TABLE membresias_planes ( -- RF-BEN-045: Planes
    id_plan INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL, -- Ej: Plan Dental Plus
    precio_mensual DECIMAL(10, 2) NOT NULL,
    descripcion TEXT
);

CREATE TABLE suscripciones_pacientes (
    id_suscripcion INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_plan INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM('ACTIVO', 'VENCIDO', 'CANCELADO') DEFAULT 'ACTIVO',
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente),
    FOREIGN KEY (id_plan) REFERENCES membresias_planes(id_plan)
);
