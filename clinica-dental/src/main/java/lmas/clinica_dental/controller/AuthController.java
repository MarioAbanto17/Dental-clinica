package lmas.clinica_dental.controller;

import lmas.clinica_dental.dto.AuthResponse;
import lmas.clinica_dental.dto.LoginRequest;
import lmas.clinica_dental.dto.RegisterRequest;
import lmas.clinica_dental.entity.Paciente;
import lmas.clinica_dental.security.JwtTokenProvider;
import lmas.clinica_dental.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private IPacienteService pacienteService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    // RF-REG-001: Registro de paciente
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();
        
        // Validar que el email no exista
        if (pacienteService.existePorEmail(request.getEmail())) {
            response.put("error", "El email ya está registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // Validar que el documento no exista
        if (pacienteService.existePorNumeroDocumento(request.getNumeroDocumento())) {
            response.put("error", "El número de documento ya está registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // Crear nuevo paciente
        Paciente paciente = new Paciente();
        paciente.setNombres(request.getNombres());
        paciente.setApellidos(request.getApellidos());
        
        // Convertir String a Enum
        try {
            paciente.setTipoDocumento(Paciente.TipoDocumento.valueOf(request.getTipoDocumento()));
        } catch (Exception e) {
            paciente.setTipoDocumento(Paciente.TipoDocumento.DNI);
        }
        
        paciente.setNumeroDocumento(request.getNumeroDocumento());
        paciente.setEmail(request.getEmail());
        paciente.setPasswordHash(passwordEncoder.encode(request.getPassword())); // Encriptar contraseña
        paciente.setTelefono(request.getTelefono());
        paciente.setDireccion(request.getDireccion());
        paciente.setFechaRegistro(LocalDateTime.now());
        paciente.setEstado(Paciente.EstadoPaciente.ACTIVO);
        
        // Asignar rol (por defecto PACIENTE si no se especifica)
        try {
            if (request.getRol() != null && !request.getRol().isEmpty()) {
                paciente.setRol(Paciente.Rol.valueOf(request.getRol()));
            } else {
                paciente.setRol(Paciente.Rol.PACIENTE);
            }
        } catch (Exception e) {
            paciente.setRol(Paciente.Rol.PACIENTE);
        }
        
        Paciente pacienteGuardado = pacienteService.guardar(paciente);
        
        // Generar token con rol
        String token = tokenProvider.generarToken(
            pacienteGuardado.getEmail(), 
            pacienteGuardado.getRol().name()
        );
        
        AuthResponse authResponse = new AuthResponse(
            token,
            pacienteGuardado.getIdPaciente(),
            pacienteGuardado.getEmail(),
            pacienteGuardado.getNombres(),
            pacienteGuardado.getApellidos(),
            pacienteGuardado.getRol().name()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }
    
    // RF-REG-002: Login de paciente
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Map<String, String> response = new HashMap<>();
        
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorEmail(request.getEmail());
        
        if (pacienteOpt.isEmpty()) {
            response.put("error", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        Paciente paciente = pacienteOpt.get();
        
        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), paciente.getPasswordHash())) {
            response.put("error", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // Verificar que la cuenta esté activa
        if (paciente.getEstado() != Paciente.EstadoPaciente.ACTIVO) {
            response.put("error", "Cuenta inactiva o bloqueada");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        
        // Actualizar último acceso
        paciente.setUltimoAcceso(LocalDateTime.now());
        pacienteService.guardar(paciente);
        
        // Generar token con rol
        String token = tokenProvider.generarToken(
            paciente.getEmail(),
            paciente.getRol().name()
        );
        
        AuthResponse authResponse = new AuthResponse(
            token,
            paciente.getIdPaciente(),
            paciente.getEmail(),
            paciente.getNombres(),
            paciente.getApellidos(),
            paciente.getRol().name()
        );
        
        return ResponseEntity.ok(authResponse);
    }
    
    // Solicitar recuperación de contraseña (simular envío de email)
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, String> response = new HashMap<>();
        
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorEmail(email);
        
        if (pacienteOpt.isEmpty()) {
            // Por seguridad, no revelar si el email existe o no
            response.put("mensaje", "Si el email existe, recibirás instrucciones para recuperar tu contraseña");
            return ResponseEntity.ok(response);
        }
        
        Paciente paciente = pacienteOpt.get();
        
        // Generar token temporal (válido por 1 hora)
        String resetToken = tokenProvider.generarToken(email, "RESET");
        
        // En producción, aquí se enviaría un email con el link de reset
        // Para desarrollo, devolvemos el token
        response.put("mensaje", "Si el email existe, recibirás instrucciones para recuperar tu contraseña");
        response.put("resetToken", resetToken); // Solo para desarrollo
        response.put("idPaciente", paciente.getIdPaciente().toString());
        
        return ResponseEntity.ok(response);
    }
    
    // Resetear contraseña con token
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        Integer idPaciente = Integer.parseInt(request.get("idPaciente"));
        String newPassword = request.get("newPassword");
        
        Map<String, String> response = new HashMap<>();
        
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorId(idPaciente);
        
        if (pacienteOpt.isEmpty()) {
            response.put("error", "Paciente no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        Paciente paciente = pacienteOpt.get();
        paciente.setPasswordHash(passwordEncoder.encode(newPassword));
        pacienteService.guardar(paciente);
        
        response.put("mensaje", "Contraseña actualizada correctamente");
        return ResponseEntity.ok(response);
    }
    
    // Cambiar contraseña (usuario autenticado)
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        Integer idPaciente = Integer.parseInt(request.get("idPaciente"));
        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");
        
        Map<String, String> response = new HashMap<>();
        
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorId(idPaciente);
        
        if (pacienteOpt.isEmpty()) {
            response.put("error", "Paciente no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        Paciente paciente = pacienteOpt.get();
        
        // Verificar contraseña actual
        if (!passwordEncoder.matches(currentPassword, paciente.getPasswordHash())) {
            response.put("error", "Contraseña actual incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // Actualizar contraseña
        paciente.setPasswordHash(passwordEncoder.encode(newPassword));
        pacienteService.guardar(paciente);
        
        response.put("mensaje", "Contraseña cambiada correctamente");
        return ResponseEntity.ok(response);
    }
}
