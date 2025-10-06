package org.ide.qoribet.mercado.service;

import org.ide.qoribet.mercado.dto.OpcionDTO;
import org.ide.qoribet.mercado.entity.Opcion;
import org.ide.qoribet.mercado.entity.Mercado;
import org.ide.qoribet.mercado.repository.OpcionRepository;
import org.ide.qoribet.mercado.repository.MercadoRepository;
import org.ide.qoribet.common.enums.mercado.NombreOpcion;
import org.ide.qoribet.common.exception.MercadoException.MercadoNotFoundException;
import org.ide.qoribet.common.exception.MercadoException.MercadoValidationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpcionService {
    private final OpcionRepository opcionRepository;
    private final MercadoRepository mercadoRepository;

    public OpcionService(OpcionRepository opcionRepository, MercadoRepository mercadoRepository) {
        this.opcionRepository = opcionRepository;
        this.mercadoRepository = mercadoRepository;
    }

    public OpcionDTO crearOpcion(OpcionDTO dto) {
        validarOpcion(dto);
        Opcion entity = toEntity(dto);
        entity = opcionRepository.save(entity);
        return toDTO(entity);
    }

    public OpcionDTO obtenerOpcionPorId(Long id) {
        Opcion entity = opcionRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Opción no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<OpcionDTO> listarOpciones() {
        return opcionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OpcionDTO actualizarOpcion(Long id, OpcionDTO dto) {
        validarOpcion(dto);
        Opcion entity = opcionRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Opción no encontrada con id: " + id));
        entity.setNombre(NombreOpcion.valueOf(dto.getNombre()));
        entity.setDescripcion(dto.getNombre()); // Usar nombre como descripción por ahora
        entity.setMercado(mercadoRepository.findById(dto.getMercadoId().longValue())
                .orElseThrow(() -> new MercadoValidationException("Mercado no encontrado")));
        entity = opcionRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarOpcion(Long id) {
        Opcion entity = opcionRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Opción no encontrada con id: " + id));
        opcionRepository.delete(entity);
    }

    private void validarOpcion(OpcionDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank())
            throw new MercadoValidationException("El nombre de la opción es obligatorio");
        try {
            NombreOpcion.valueOf(dto.getNombre());
        } catch (Exception e) {
            throw new MercadoValidationException("Nombre de opción inválido");
        }
        if (dto.getMercadoId() == null)
            throw new MercadoValidationException("El mercado es obligatorio");
    }

    private OpcionDTO toDTO(Opcion entity) {
        OpcionDTO dto = new OpcionDTO();
        dto.setId(entity.getId() != null ? entity.getId().intValue() : null);
        dto.setNombre(entity.getNombre().name());
        dto.setMercadoId(entity.getMercado() != null ? entity.getMercado().getId().intValue() : null);
        dto.setActiva(true); // Por defecto activa
        return dto;
    }

    private Opcion toEntity(OpcionDTO dto) {
        Opcion entity = new Opcion();
        entity.setNombre(NombreOpcion.valueOf(dto.getNombre()));
        entity.setDescripcion(dto.getNombre()); // Usar nombre como descripción
        entity.setMercado(mercadoRepository.findById(dto.getMercadoId().longValue())
                .orElseThrow(() -> new MercadoValidationException("Mercado no encontrado")));
        return entity;
    }
}
