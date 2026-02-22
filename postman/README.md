# 📬 Colección Postman - Sistema Clínica Dental

## 📋 Archivos Generados

1. **ClinicaDental_Environment.postman_environment.json** - Variables de entorno
2. **ClinicaDental_Collection.postman_collection.json** - Colección completa de endpoints

---

## 🚀 Cómo Importar en Postman

### 1. Importar el Environment

1. Abre Postman
2. Click en **"Import"** (esquina superior izquierda)
3. Arrastra o selecciona: `ClinicaDental_Environment.postman_environment.json`
4. Click en **"Import"**
5. En la esquina superior derecha, selecciona el environment: **"Clínica Dental - Local Dev"**

### 2. Importar la Colección

1. Click en **"Import"** nuevamente
2. Arrastra o selecciona: `ClinicaDental_Collection.postman_collection.json`
3. Click en **"Import"**
4. La colección aparecerá en el panel izquierdo: **"Clínica Dental - Prueba E2E Completa"**

---

## ⚙️ Configuración Inicial

### Pre-requisitos

1. **Backend corriendo** en `http://localhost:8080`
2. **Base de datos** con los scripts ejecutados:
   - `database.sql` - Estructura de tablas
   - `data.sql` - Datos de prueba

### Variables del Environment

El environment incluye estas variables (se llenan automáticamente):

```
baseUrl = http://localhost:8080/api

# Usuarios de prueba
adminEmail = admin@clinica.com
adminPassword = 123456
pacienteEmail = juan@gmail.com
pacientePassword = 123456

# Tokens (se guardan automáticamente al hacer login)
adminToken = [se llena automáticamente]
pacienteToken = [se llena automáticamente]

# IDs (se guardan automáticamente al crear recursos)
pacienteId, doctorId, citaId, pagoId, etc...
```

---

## 🎯 Cómo Ejecutar las Pruebas

### Opción 1: Ejecutar Request Individual

1. Expande la colección en el panel izquierdo
2. Navega a cualquier request (ej: `01.1 Login Admin`)
3. Click en **"Send"**
4. Verifica la respuesta

### Opción 2: Ejecutar Toda la Colección (E2E)

1. Click derecho en la colección **"Clínica Dental - Prueba E2E Completa"**
2. Selecciona **"Run collection"**
3. Se abrirá el **Collection Runner**
4. Verifica que el environment esté seleccionado: **"Clínica Dental - Local Dev"**
5. Click en **"Run Clínica Dental..."**
6. Observa los resultados en tiempo real ✅

---

## 📂 Estructura de la Colección

La colección incluye **14 módulos** con **88 endpoints**:

### 01 - AUTENTICACIÓN (4 requests)
- Login Admin
- Login Paciente  
- Registro Nuevo Paciente
- Recuperación de Password

### 02 - PACIENTES (4 requests)
- Listar Pacientes
- Obtener por ID
- Actualizar Paciente
- Perfil Completo

### 03 - DOCTORES (4 requests)
- Listar Doctores
- Crear Doctor
- Buscar por Especialidad
- Actualizar Doctor

### 04 - CITAS (7 requests)
- Listar Citas
- Crear Cita
- Ver Disponibilidad
- Confirmar Cita
- **Completar Cita (suma puntos automáticamente)** ⭐
- Cancelar Cita
- Citas por Paciente

### 05 - PAGOS (5 requests)
- Listar Pagos
- Crear Pago
- **Procesar Pago (suma puntos automáticamente)** ⭐
- Pagos por Paciente
- Filtrar por Estado

### 06 - MENSAJERÍA (5 requests)
- Listar Mensajes
- Enviar Mensaje (Paciente → Clínica)
- Responder Mensaje (Clínica → Paciente)
- Marcar como Leído
- Mensajes No Leídos

### 07 - NOTIFICACIONES (5 requests)
- Listar Notificaciones
- Crear Notificación
- Notificaciones No Leídas
- Marcar como Leída
- Marcar Todas como Leídas

### 08 - BENEFICIOS Y PUNTOS (3 requests)
- Obtener Beneficios del Paciente
- Historial de Puntos
- Canjear Puntos

### 09 - HISTORIAL MÉDICO (2 requests)
- Obtener Historial Médico
- Crear/Actualizar Historial

### 10 - CERTIFICADOS (3 requests)
- Listar Certificados
- Solicitar Certificado
- Aprobar/Emitir Certificado

### 11 - VALORACIONES (2 requests)
- Crear Valoración de Cita
- Obtener Valoraciones

### 12 - PLANES Y SUSCRIPCIONES (4 requests)
- Listar Planes
- Crear Plan
- Crear Suscripción
- Suscripción Activa del Paciente

### 13 - FACTURACIÓN (2 requests)
- Solicitar Factura
- Obtener Facturas del Paciente

### 14 - VALIDACIÓN FINAL (2 requests)
- Health Check del Sistema
- Resumen de Datos Creados

---

## ✨ Características Especiales

### 🔄 Variables Automáticas

Los scripts de test automáticamente:
- Guardan el **token JWT** al hacer login
- Guardan los **IDs** de recursos creados (pacientes, citas, pagos, etc.)
- Los usan en requests posteriores

Ejemplo:
```javascript
// En "01.1 Login Admin", el script automáticamente ejecuta:
pm.environment.set('adminToken', jsonData.token);

// Luego en "02.1 Listar Pacientes", se usa:
Authorization: Bearer {{adminToken}}
```

### ✅ Tests Incluidos

Cada request incluye validaciones automáticas:
```javascript
pm.test("Login Admin exitoso", function () {
    pm.response.to.have.status(200);
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('token');
    console.log('✅ Token Admin guardado');
});
```

### 📊 Resumen Final

Al ejecutar toda la colección, obtendrás un resumen completo:
```
============================================================
🎉 PRUEBA E2E COMPLETADA - CLÍNICA DENTAL
============================================================
📊 RESUMEN DE DATOS CREADOS:
   • Paciente ID: 1
   • Doctor ID: 2
   • Cita ID: 5
   • Pago ID: 3
   • Mensaje ID: 8
   • Beneficio ID: 1

💰 Total Pagado: S/ 250.00
⭐ Puntos Acumulados: 22
📅 Total Citas: 3
============================================================
✅ TODOS LOS MÓDULOS FUNCIONANDO CORRECTAMENTE
============================================================
```

---

## 🎯 Flujo de Prueba Recomendado

### Para Desarrollo:
1. Ejecuta solo el módulo que estás trabajando
2. Usa los requests individuales para debug

### Para Testing Completo:
1. Ejecuta toda la colección con Collection Runner
2. Verifica que todos los tests pasen ✅
3. Revisa el resumen final

### Para Demostración:
1. Ejecuta requests específicos en orden lógico:
   - Login → Crear Cita → Procesar Pago → Ver Puntos

---

## 🔐 Roles y Permisos

La colección incluye 3 tipos de usuarios:

| Usuario | Email | Password | Rol |
|---------|-------|----------|-----|
| Admin | admin@clinica.com | 123456 | ADMINISTRADOR |
| Paciente | juan@gmail.com | 123456 | PACIENTE |
| Recepción | recepcion@clinica.com | 123456 | RECEPCION |

**Tokens separados** para cada rol:
- `{{adminToken}}` - Acceso completo
- `{{pacienteToken}}` - Solo sus datos
- `{{recepcionToken}}` - Gestión operativa

---

## 🐛 Troubleshooting

### Error: "Could not get response"
- ✅ Verifica que el backend esté corriendo en `localhost:8080`
- ✅ Revisa la consola del backend por errores

### Error 401 Unauthorized
- ✅ Ejecuta primero el request de login correspondiente
- ✅ Verifica que el token se guardó: mira las variables del environment

### Error 404 Not Found
- ✅ Verifica que la URL base sea correcta: `{{baseUrl}}/api`
- ✅ Revisa que el endpoint exista en tu controller

### IDs vacíos en requests
- ✅ Ejecuta primero los requests que crean esos recursos
- ✅ Ejemplo: antes de "04.2 Crear Cita" ejecuta "03.1 Listar Doctores"

---

## 📝 Notas Adicionales

### Endpoints que NO requieren autenticación:
- `POST /api/auth/login`
- `POST /api/auth/register`
- `POST /api/auth/recuperar-password`

### Endpoints con lógica especial:
- `PATCH /api/citas/{id}/completar` - Suma puntos por puntualidad
- `PATCH /api/pagos/{id}/procesar` - Suma puntos según monto pagado
- `POST /api/beneficios/canjear` - Resta puntos del paciente

### Sistema de Puntos:
- **Cita completada**: +10 puntos
- **Puntualidad**: +20 puntos bonus
- **Pago**: +5 puntos por cada S/100

---

## 🎉 ¡Listo para usar!

Ahora puedes:
1. ✅ Importar ambos archivos en Postman
2. ✅ Ejecutar la colección completa
3. ✅ Ver todos los módulos funcionando
4. ✅ Usar como referencia para el frontend

---

**Última actualización**: 16 de febrero de 2026  
**Versión**: 1.0.0  
**Total de endpoints**: 88
