package org.ide.qoribet.apuesta.service;

import org.ide.qoribet.apuesta.dto.ApuestaDTO;
import org.ide.qoribet.apuesta.entity.Apuesta;
import org.ide.qoribet.apuesta.repository.ApuestaRepository;
import org.ide.qoribet.usuario.repository.UsuarioRepository;
import org.ide.qoribet.usuario.entity.Usuario;
import org.ide.qoribet.mercado.repository.OpcionRepository;
import org.ide.qoribet.common.enums.apuesta.EstadoApuesta;
import org.ide.qoribet.common.exception.ApuestaException.ApuestaNotFoundException;
import org.ide.qoribet.common.exception.ApuestaException.ApuestaValidationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApuestaService {
    private final ApuestaRepository apuestaRepository;
    private final UsuarioRepository usuarioRepository;
    private final OpcionRepository opcionRepository;
    private final ApuestaAsyncService apuestaAsyncService;

    public ApuestaService(ApuestaRepository apuestaRepository,
                         UsuarioRepository usuarioRepository,
                         OpcionRepository opcionRepository,
                         ApuestaAsyncService apuestaAsyncService) {
        this.apuestaRepository = apuestaRepository;
        this.usuarioRepository = usuarioRepository;
        this.opcionRepository = opcionRepository;
        this.apuestaAsyncService = apuestaAsyncService;
    }

    public ApuestaDTO crearApuesta(ApuestaDTO dto) {
        validarApuesta(dto);
        Apuesta entity = toEntity(dto);
        entity.setFecha(LocalDateTime.now());
        entity.setEstado(EstadoApuesta.PENDIENTE);
        entity = apuestaRepository.save(entity);

        ApuestaDTO resultado = toDTO(entity);

        usuarioRepository.findById(dto.getUsuarioId())
            .ifPresent(usuario -> apuestaAsyncService.procesarApuestaCompleta(resultado, usuario.getCorreo()));

        return resultado;
    }

    public ApuestaDTO obtenerApuestaPorId(Long id) {
        Apuesta entity = apuestaRepository.findById(id)
                .orElseThrow(() -> new ApuestaNotFoundException("Apuesta no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<ApuestaDTO> listarApuestas() {
        return apuestaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ApuestaDTO> listarApuestasPorUsuario(Long usuarioId) {
        return apuestaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ApuestaDTO> listarApuestasPorUsuarioYEstado(Long usuarioId, String estado) {
        return apuestaRepository.findByUsuarioIdAndEstado(usuarioId, estado).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ApuestaDTO actualizarApuesta(Long id, ApuestaDTO dto) {
        validarApuesta(dto);
        Apuesta entity = apuestaRepository.findById(id)
                .orElseThrow(() -> new ApuestaNotFoundException("Apuesta no encontrada con id: " + id));

        // Solo actualizar campos que existen en la entidad
        entity.setMonto(dto.getMontoApostado() != null ? dto.getMontoApostado() : entity.getMonto());
        entity = apuestaRepository.save(entity);
        return toDTO(entity);
    }

    public ApuestaDTO resolverApuesta(Long id, EstadoApuesta nuevoEstado) {
        Apuesta entity = apuestaRepository.findById(id)
                .orElseThrow(() -> new ApuestaNotFoundException("Apuesta no encontrada con id: " + id));

        entity.setEstado(nuevoEstado);
        entity = apuestaRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarApuesta(Long id) {
        Apuesta entity = apuestaRepository.findById(id)
                .orElseThrow(() -> new ApuestaNotFoundException("Apuesta no encontrada con id: " + id));
        apuestaRepository.delete(entity);
    }

    private void validarApuesta(ApuestaDTO dto) {
        if (dto.getUsuarioId() == null)
            throw new ApuestaValidationException("El usuario es obligatorio");
        if (dto.getMontoApostado() == null || dto.getMontoApostado().compareTo(BigDecimal.ZERO) <= 0)
            throw new ApuestaValidationException("El monto apostado debe ser positivo");

        // Verificar que el usuario exista
        if (!usuarioRepository.existsById(dto.getUsuarioId())) {
            throw new ApuestaValidationException("El usuario no existe");
        }
    }

    private ApuestaDTO toDTO(Apuesta entity) {
        ApuestaDTO dto = new ApuestaDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setMontoApostado(entity.getMonto());
        dto.setEstado(entity.getEstado());
        dto.setCreadaEn(entity.getFecha());
        return dto;
    }

    private Apuesta toEntity(ApuestaDTO dto) {
        Apuesta entity = new Apuesta();
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setMonto(dto.getMontoApostado());

        // Si se especifica una opción en las selecciones, usar la primera
        if (dto.getSelecciones() != null && !dto.getSelecciones().isEmpty()) {
            Long opcionId = dto.getSelecciones().get(0).getOpcionId();
            if (opcionId != null) {
                entity.setOpcion(opcionRepository.findById(opcionId)
                        .orElseThrow(() -> new ApuestaValidationException("Opción no encontrada")));
            }
        }

        entity.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoApuesta.PENDIENTE);
        return entity;
    }
}
