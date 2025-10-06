package org.ide.qoribet.usuario.service;

import org.ide.qoribet.usuario.dto.UsuarioDTO;
import org.ide.qoribet.usuario.entity.Usuario;
import org.ide.qoribet.usuario.repository.UsuarioRepository;
import org.ide.qoribet.common.exception.UsuarioException.UsuarioNotFoundException;
import org.ide.qoribet.common.exception.UsuarioException.UsuarioValidationException;
import org.ide.qoribet.common.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO, String password) {
        validarUsuario(usuarioDTO);
        if (usuarioRepository.findByUsername(usuarioDTO.getUsername()).isPresent()) {
            throw new UsuarioValidationException("El nombre de usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setPassword_hash(passwordEncoder.encode(password));
        // Establecer valores por defecto para campos obligatorios de la entidad
        usuario.setRol(org.ide.qoribet.common.enums.usuario.Rol.JUGADOR); // Valor por defecto
        usuario.setEstado(org.ide.qoribet.common.enums.usuario.EstadoUsuario.ACTIVO); // Valor por defecto
        usuario.setFechaRegistro(java.time.LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public String login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        if (!passwordEncoder.matches(password, usuario.getPassword_hash())) {
            throw new UsuarioValidationException("Contraseña incorrecta");
        }
        return jwtTokenProvider.generateToken(username);
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
        return toDTO(usuario);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        validarUsuario(usuarioDTO);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setCorreo(usuarioDTO.getCorreo());
        // No actualizamos rol ni estado ya que no están en el DTO
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
        usuarioRepository.delete(usuario);
    }

    private void validarUsuario(UsuarioDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().isBlank())
            throw new UsuarioValidationException("El nombre de usuario es obligatorio");
        if (dto.getCorreo() == null || dto.getCorreo().isBlank())
            throw new UsuarioValidationException("El correo es obligatorio");
        // Validación de email básica
        if (!dto.getCorreo().contains("@"))
            throw new UsuarioValidationException("El correo debe ser válido");
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setCorreo(usuario.getCorreo());
        // Mapear estado (enum) a activo (boolean)
        dto.setActivo(usuario.getEstado() == org.ide.qoribet.common.enums.usuario.EstadoUsuario.ACTIVO);
        return dto;
    }
}
