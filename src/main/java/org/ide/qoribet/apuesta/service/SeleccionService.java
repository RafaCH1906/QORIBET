package org.ide.qoribet.apuesta.service;

import org.ide.qoribet.apuesta.dto.SeleccionDTO;
import org.ide.qoribet.apuesta.entity.Seleccion;
import org.ide.qoribet.apuesta.repository.SeleccionRepository;
import org.ide.qoribet.apuesta.repository.ApuestaRepository;
import org.ide.qoribet.mercado.repository.OpcionRepository;
import org.ide.qoribet.common.exception.ApuestaException.SeleccionNotFoundException;
import org.ide.qoribet.common.exception.ApuestaException.SeleccionValidationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeleccionService {
    private final SeleccionRepository seleccionRepository;
    private final ApuestaRepository apuestaRepository;
    private final OpcionRepository opcionRepository;

    public SeleccionService(SeleccionRepository seleccionRepository, ApuestaRepository apuestaRepository, OpcionRepository opcionRepository) {
        this.seleccionRepository = seleccionRepository;
        this.apuestaRepository = apuestaRepository;
        this.opcionRepository = opcionRepository;
    }

    public SeleccionDTO crearSeleccion(SeleccionDTO dto) {
        validarSeleccion(dto);
        Seleccion entity = toEntity(dto);
        entity = seleccionRepository.save(entity);
        return toDTO(entity);
    }

    public SeleccionDTO obtenerSeleccionPorId(Long id) {
        Seleccion entity = seleccionRepository.findById(id)
                .orElseThrow(() -> new SeleccionNotFoundException("Selección no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<SeleccionDTO> listarSelecciones() {
        return seleccionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SeleccionDTO> listarSeleccionesPorApuesta(Long apuestaId) {
        return seleccionRepository.findByApuestaId(apuestaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SeleccionDTO> listarSeleccionesPorOpcion(Long opcionId) {
        return seleccionRepository.findByOpcionId(opcionId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SeleccionDTO actualizarSeleccion(Long id, SeleccionDTO dto) {
        validarSeleccion(dto);
        Seleccion entity = seleccionRepository.findById(id)
                .orElseThrow(() -> new SeleccionNotFoundException("Selección no encontrada con id: " + id));

        // Solo actualizar campos que existen en la entidad
        entity.setMonto(dto.getCuotaTomada() != null ? dto.getCuotaTomada() : entity.getMonto());
        entity = seleccionRepository.save(entity);
        return toDTO(entity);
    }

    public SeleccionDTO resolverSeleccion(Long id, String resultado) {
        Seleccion entity = seleccionRepository.findById(id)
                .orElseThrow(() -> new SeleccionNotFoundException("Selección no encontrada con id: " + id));

        try {
            org.ide.qoribet.common.enums.seleccion.EstadoSeleccion.valueOf(resultado); // Validar resultado
        } catch (Exception e) {
            throw new SeleccionValidationException("Resultado inválido: " + resultado);
        }

        entity.setEstado(org.ide.qoribet.common.enums.seleccion.EstadoSeleccion.valueOf(resultado));
        entity = seleccionRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarSeleccion(Long id) {
        Seleccion entity = seleccionRepository.findById(id)
                .orElseThrow(() -> new SeleccionNotFoundException("Selección no encontrada con id: " + id));
        seleccionRepository.delete(entity);
    }

    private void validarSeleccion(SeleccionDTO dto) {
        if (dto.getApuestaId() == null)
            throw new SeleccionValidationException("La apuesta es obligatoria");
        if (dto.getOpcionId() == null)
            throw new SeleccionValidationException("La opción es obligatoria");
        if (dto.getCuotaTomada() == null || dto.getCuotaTomada().compareTo(BigDecimal.ONE) < 0)
            throw new SeleccionValidationException("La cuota tomada debe ser al menos 1.00");

        // Verificar que la apuesta y la opción existan
        if (!apuestaRepository.existsById(dto.getApuestaId())) {
            throw new SeleccionValidationException("La apuesta no existe");
        }
        if (!opcionRepository.existsById(dto.getOpcionId())) {
            throw new SeleccionValidationException("La opción no existe");
        }
    }

    private SeleccionDTO toDTO(Seleccion entity) {
        SeleccionDTO dto = new SeleccionDTO();
        dto.setId(entity.getId());
        dto.setApuestaId(entity.getApuesta() != null ? entity.getApuesta().getId() : null);
        dto.setOpcionId(entity.getOpcion() != null ? entity.getOpcion().getId() : null);
        dto.setCuotaTomada(entity.getMonto());
        dto.setResultado(entity.getEstado().name());
        // partidoId y fechaResultado no existen en la entidad
        return dto;
    }

    private Seleccion toEntity(SeleccionDTO dto) {
        Seleccion entity = new Seleccion();
        entity.setApuesta(apuestaRepository.findById(dto.getApuestaId())
                .orElseThrow(() -> new SeleccionValidationException("Apuesta no encontrada")));
        entity.setOpcion(opcionRepository.findById(dto.getOpcionId())
                .orElseThrow(() -> new SeleccionValidationException("Opción no encontrada")));
        entity.setMonto(dto.getCuotaTomada());

        // Establecer estado por defecto si no se especifica
        if (dto.getResultado() != null && !dto.getResultado().isBlank()) {
            try {
                entity.setEstado(org.ide.qoribet.common.enums.seleccion.EstadoSeleccion.valueOf(dto.getResultado()));
            } catch (Exception e) {
                entity.setEstado(org.ide.qoribet.common.enums.seleccion.EstadoSeleccion.PENDIENTE);
            }
        } else {
            entity.setEstado(org.ide.qoribet.common.enums.seleccion.EstadoSeleccion.PENDIENTE);
        }

        return entity;
    }
}
