# INNOVATIVE AJ - Sistema de Gestión Clínica Dental

## INTRODUCCIÓN
INNOVATIVE AJ es una plataforma digital diseñada para empoderar a los pacientes, permitiéndoles gestionar de manera autónoma sus citas médicas, realizar pagos en línea y mantener una comunicación directa con la clínica odontológica.

Esta plataforma complementa el sistema administrativo interno, proporcionando a los pacientes acceso 24/7 a sus datos médicos y servicios, mejorando la experiencia del paciente y reduciendo la carga administrativa de la clínica.

## OBJETIVOS DEL PORTAL
- Facilitar el agendamiento de citas médicas de forma autónoma
- Permitir la gestión de pagos y visualización de deudas pendientes
- Mejorar la comunicación entre pacientes y la clínica
- Reducir llamadas telefónicas y carga administrativa
- Ofrecer recordatorios automáticos de citas y seguimientos

## REQUERIMIENTOS FUNCIONALES

### Actores del sistema:
- **Paciente** (Usuario Principal)
- **Recepción** (Gestión administrativa)
- **Administrador** de Clínica (Soporte y configuración)

---

## 1. MÓDULO DE REGISTRO Y AUTENTICACIÓN

### RF-REG-001: Registro de nuevo paciente
**Descripción:** El portal permite que nuevos pacientes se registren creando una cuenta de usuario.

**Criterios de Aceptación:**
- Formulario con datos personales básicos (nombre, apellidos, email, teléfono)
- Validación de email único en el sistema
- Validación de número de documento
- Creación de contraseña segura (mínimo 8 caracteres, mayúsculas, números)
- Email de bienvenida con instrucciones de uso

### RF-REG-002: Inicio de sesión de paciente
**Descripción:** El sistema permite a pacientes registrados acceder a su portal personal.

**Criterios de Aceptación:**
- Login mediante email/username y contraseña
- Validación de credenciales
- Generación de token de sesión (JWT)
- Registro de fecha/hora de último acceso
- Bloqueo temporal tras 5 intentos fallidos
- Redirección al dashboard del paciente

### RF-REG-003: Recuperación de contraseña
**Descripción:** El sistema permite recuperar contraseña olvidada mediante email.

**Criterios de Aceptación:**
- Formulario de solicitud con email registrado
- Validación de email existente en sistema
- Envío de correo con enlace de restablecimiento
- Token único válido por 30 minutos
- Formulario para crear nueva contraseña
- Confirmación de cambio exitoso

### RF-REG-005: Cierre de sesión
**Descripción:** El sistema permite al paciente cerrar sesión de forma segura.

**Criterios de Aceptación:**
- Botón de cerrar sesión visible en todo momento
- Invalidación de token de sesión actual
- Redirección a página de login
- Mensaje de confirmación de cierre exitoso

---

## 2. MÓDULO DE PERFIL DEL PACIENTE

### RF-PAC-007: Ver perfil personal
**Descripción:** El paciente puede visualizar toda su información personal registrada.

**Criterios de Aceptación:**
- Datos personales completos
- Foto de perfil
- Información de contacto
- Dirección registrada
- Contactos de emergencia
- Información médica básica (alergias, condiciones)

### RF-REG-008: Actualizar datos personales
**Descripción:** El paciente puede modificar su información personal.

**Criterios de Aceptación:**
- Edición de nombre y apellidos
- Cambio de email (requiere verificación)
- Actualización de teléfono
- Modificación de dirección
- Cambio de foto de perfil
- Actualización de contactos de emergencia
- Validación de datos antes de guardar
- Confirmación de cambios guardados
- Email de notificación de cambios importantes

### RF-WEB-009: Cambiar contraseña
**Descripción:** El paciente puede modificar su contraseña de acceso.

**Criterios de Aceptación:**
- Formulario de cambio de contraseña
- Validación de contraseña actual
- Validación de requisitos de nueva contraseña
- Confirmación de nueva contraseña
- Email de confirmación de cambio

### RF-PAC-010: Gestionar información médica
**Descripción:** El paciente puede actualizar datos relevantes para su atención médica.

**Criterios de Aceptación:**
- Registro de alergias a medicamentos
- Registro de condiciones médicas preexistentes
- Medicamentos que toma actualmente
- Antecedentes médicos familiares
- Grupo sanguíneo
- Información de seguro médico
- Historial de cirugías previas
- Restricciones o consideraciones especiales

### RF-WEB-011: Eliminar cuenta
**Descripción:** El paciente puede solicitar la eliminación permanente de su cuenta.

**Criterios de Aceptación:**
- Opción de eliminación en configuración
- Advertencia de pérdida de datos
- Confirmación mediante contraseña
- Email de confirmación de solicitud
- Notificación de eliminación completada

---

## 3. MÓDULO DE GESTIÓN DE CITAS

### RF-CIT-012: Ver calendario de disponibilidad
**Descripción:** El paciente puede visualizar horarios disponibles para agendar citas.

**Criterios de Aceptación:**
- Vista de calendario mensual
- Selección de especialidad odontológica
- Selección de doctor preferido (opcional)
- Visualización de horarios disponibles por día
- Filtro por doctor
- Filtro por especialidad
- Indicadores de disponibilidad (libre, ocupado)
- Información de duración aproximada de consulta

### RF-CIT-013: Agendar nueva cita
**Descripción:** El paciente puede reservar citas médicas de forma autónoma.

**Criterios de Aceptación:**
- Selección de tipo de consulta (Primera vez, Control, Emergencia)
- Selección de fecha disponible
- Selección de hora disponible
- Selección de doctor (o asignación automática)
- Campo de motivo/síntomas (opcional)
- Email de confirmación con detalles

### RF-CIT-014: Ver mis citas
**Descripción:** El paciente puede visualizar todas sus citas programadas.

**Criterios de Aceptación:**
- Lista de citas próximas
- Historial de citas pasadas
- Detalles de cada cita (fecha, hora, doctor, motivo)
- Estado de la cita (Confirmada, Pendiente, Completada, Cancelada)
- Tiempo restante hasta la cita
- Ubicación de la clínica con mapa
- Instrucciones especiales si las hay
- Opción de descarga de comprobante

### RF-CIT-015: Reprogramar cita
**Descripción:** El paciente puede cambiar fecha u hora de citas programadas.

**Criterios de Aceptación:**
- Acceso desde listado de citas
- Solo disponible para citas futuras (mínimo 24h antes)
- Visualización de nuevos horarios disponibles
- Selección de nueva fecha/hora
- Confirmación de reprogramación
- Email de notificación de cambio
- Actualización automática en el sistema
- Registro de historial de cambios

### RF-CIT-016: Cancelar cita
**Descripción:** El paciente puede anular citas programadas.

**Criterios de Aceptación:**
- Botón de cancelación en detalle de cita
- Solo disponible hasta 24 horas antes
- Solicitud de motivo de cancelación
- Confirmación de cancelación
- Email de confirmación
- Liberación automática del horario
- Registro en historial como cancelada

### RF-CIT-017: Recordatorios de citas
**Descripción:** El sistema envía recordatorios automáticos de citas próximas.

**Criterios de Aceptación:**
- Recordatorio por email 48 horas antes
- Recordatorio por WhatsApp (opcional)
- Confirmación de asistencia mediante link
- Historial de recordatorios enviados

### RF-CIT-019: Valoración post-cita
**Descripción:** El paciente puede calificar su experiencia después de la consulta.

**Criterios de Aceptación:**
- Solicitud automática 24h después de la cita
- Calificación de 1 a 5 estrellas
- Valoración del doctor
- Valoración de la atención recibida
- Comentarios opcionales
- Sugerencias de mejora
- Opción de mantener anónimo

---

## 6. MÓDULO DE PAGOS Y FACTURACIÓN

### RF-PAG-032: Ver estado de cuenta
**Descripción:** El paciente puede visualizar su situación financiera con la clínica.

**Criterios de Aceptación:**
- Saldo total pendiente
- Pagos realizados
- Tratamientos con deuda
- Próximos pagos programados
- Historial de transacciones
- Descarga de estado de cuenta en PDF

### RF-PAG-033: Ver comprobantes de pago
**Descripción:** El paciente puede acceder a todos sus recibos y facturas.

**Criterios de Aceptación:**
- Listado de comprobantes emitidos
- Tipo (Boleta, Factura, Recibo)
- Número de comprobante
- Fecha de emisión
- Monto pagado
- Método de pago utilizado
- Concepto/servicio
- Descarga en PDF
- Envío por email

### RF-PAG-034: Realizar pago en línea
**Descripción:** El paciente puede pagar sus deudas a través del portal.

**Criterios de Aceptación:**
- Selección de deuda a pagar
- Pago total o parcial
- Múltiples métodos de pago (tarjeta, yape, plin, etc.)
- Validación de transacción
- Confirmación de pago exitoso
- Email con comprobante
- Actualización automática de estado de cuenta

### RF-PAG-036: Solicitar factura electrónica
**Descripción:** El paciente puede solicitar factura para pagos realizados.

**Criterios de Aceptación:**
- Formulario con datos fiscales (RUC, razón social)
- Validación de RUC en SUNAT
- Selección de pago a facturar
- Generación de factura electrónica
- Envío a SUNAT (Perú)
- Descarga de PDF con código QR
- Envío por email
- Validación en portal de SUNAT

---

## 7. MÓDULO DE COMUNICACIÓN

### RF-COM-037: Mensajería con la clínica
**Descripción:** El paciente puede enviar mensajes directos a la clínica.

**Criterios de Aceptación:**
- Interfaz de chat/mensajería
- Envío de mensajes de texto
- Adjuntar archivos (imágenes, documentos)
- Límite de tamaño de archivos (10MB)
- Historial de conversaciones
- Notificación de mensaje nuevo
- Horario de atención indicado
- Tiempo estimado de respuesta
- Mensajes automáticos fuera de horario

### RF-COM-039: Notificaciones
**Descripción:** El paciente recibe alertas sobre eventos importantes.

**Criterios de Aceptación:**
- Centro de notificaciones en el portal
- Email para notificaciones importantes
- SMS para alertas críticas
- Configuración de preferencias
- Marcar como leída/no leída
- Historial de notificaciones
- Categorización (Citas, Pagos, Mensajes, Resultados)

### RF-COM-040: Solicitar certificados médicos
**Descripción:** El paciente puede solicitar documentos oficiales.

**Criterios de Aceptación:**
- Formulario de solicitud
- Tipo de certificado (Asistencia, Aptitud, Reposo)
- Motivo de solicitud
- Envío de solicitud a clínica
- Seguimiento de estado
- Notificación cuando esté listo
- Descarga de certificado
- Costo asociado (si aplica)

### RF-COM-041: Encuestas de satisfacción
**Descripción:** El paciente puede participar en encuestas de la clínica.

**Criterios de Aceptación:**
- Invitación a encuesta post-atención
- Formulario dinámico de preguntas
- Escalas de valoración
- Preguntas abiertas
- Envío anónimo opcional
- Agradecimiento por participación
- Incentivos por completar (descuentos, puntos)

---

## 8. MÓDULO DE PROGRAMA DE BENEFICIOS

### RF-BEN-042: Sistema de puntos
**Descripción:** El paciente acumula puntos por atenciones y puede canjearlos.

**Criterios de Aceptación:**
- Visualización de puntos acumulados
- Historial de puntos ganados
- Puntos por cada consulta/tratamiento
- Bonificaciones especiales
- Catálogo de recompensas
- Canje de puntos
- Fecha de expiración de puntos
- Términos y condiciones del programa

### RF-BEN-043: Cupones y descuentos
**Descripción:** El paciente puede acceder a promociones especiales.

**Criterios de Aceptación:**
- Sección de cupones disponibles
- Cupones personalizados
- Código de descuento
- Vigencia del cupón
- Restricciones de uso
- Aplicación automática al agendar/pagar
- Historial de cupones usados
- Notificación de nuevas promociones

### RF-BEN-045: Membresías y planes
**Descripción:** El paciente puede suscribirse a planes de atención mensual.

**Criterios de Aceptación:**
- Visualización de planes disponibles
- Comparativa de beneficios
- Precio mensual/anual
- Servicios incluidos
- Descuentos por plan
- Suscripción en línea
- Pago automático mensual
- Cancelación flexible
- Dashboard de membresía
- Uso de beneficios incluidos

---

## REQUERIMIENTOS NO FUNCIONALES

### 1. Seguridad y Privacidad
- **RNF-SEG-002 (Cifrado):** Toda comunicación entre el cliente y el servidor debe realizarse mediante el protocolo HTTPS con certificados SSL/TLS.
- **RNF-SEG-003 (Gestión de Identidad):** Las contraseñas en la base de datos deben estar encriptadas.
- **RNF-SEG-004 (Sesiones):** Las sesiones inactivas en el portal del paciente deben expirar automáticamente tras 30 minutos de inactividad.

### 2. Disponibilidad y Rendimiento
- **RNF-PER-001 (Tiempo de Respuesta):** Las consultas de disponibilidad de citas y carga de perfiles no deben exceder los 2 segundos bajo condiciones normales de red.
- **RNF-DIS-002 (Disponibilidad):** El portal debe garantizar un Uptime del 99.5% (24/7), considerando que el agendamiento es autónomo.
- **RNF-PER-003 (Carga Simultánea):** El sistema debe soportar al menos 50 usuarios concurrentes sin degradación del servicio.

### 3. Usabilidad (UX/UI)
- **RNF-USA-001 (Diseño Responsivo):** La interfaz debe ser Mobile-First, adaptándose perfectamente a smartphones, tablets y computadoras.
- **RNF-USA-002 (Accesibilidad):** El diseño debe seguir las pautas de accesibilidad WCAG 2.1, permitiendo una lectura clara y contraste adecuado para personas con dificultades visuales.
- **RNF-USA-003 (Idioma):** La interfaz debe estar completamente en español, utilizando terminología odontológica comprensible para el paciente.

### 4. Mantenibilidad y Escalabilidad
- **RNF-ESC-001 (Arquitectura):** El sistema debe estar desarrollado bajo una arquitectura de capas o microservicios para facilitar la adición de nuevos módulos (ej. Módulo de Laboratorio) en el futuro.
- **RNF-MAN-002 (Documentación):** El código fuente debe estar documentado siguiendo estándares de la industria y las APIs deben estar documentadas con Swagger/OpenAPI.

### 5. Interoperabilidad y Cumplimiento Legal
- **RNF-INT-001 (Pasarela de Pagos):** Integración obligatoria con proveedores de pago locales que soporten tarjetas y billeteras digitales (Yape/Plin).

---

## ESTADO DE IMPLEMENTACIÓN

Ver archivo: [ANALISIS_GAP.md](ANALISIS_GAP.md) para el análisis detallado de lo implementado vs lo faltante.
