package org.ide.qoribet.partido.service;

import org.ide.qoribet.partido.dto.LigaDTO;
import org.ide.qoribet.partido.entity.Liga;
import org.ide.qoribet.partido.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.ide.qoribet.common.exception.PartidoException.LigaNotFoundException;
import org.ide.qoribet.common.exception.PartidoException.LigaValidationException;

@Service
public class LigaService {
    private final LigaRepository ligaRepository;

    public LigaService(LigaRepository ligaRepository) {
        this.ligaRepository = ligaRepository;
    }

    public LigaDTO crearLiga(LigaDTO dto) {
        validarLiga(dto);
        Liga entity = toEntity(dto);
        entity = ligaRepository.save(entity);
        return toDTO(entity);
    }

    public LigaDTO obtenerLigaPorId(Long id) {
        Liga entity = ligaRepository.findById(id)
                .orElseThrow(() -> new LigaNotFoundException("Liga no encontrada con id: " + id));
        return toDTO(entity);
    }

    public List<LigaDTO> listarLigas() {
        return ligaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LigaDTO actualizarLiga(Long id, LigaDTO dto) {
        validarLiga(dto);
        Liga entity = ligaRepository.findById(id)
                .orElseThrow(() -> new LigaNotFoundException("Liga no encontrada con id: " + id));
        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        entity.setCategoria(dto.getCategoria());
        entity = ligaRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarLiga(Long id) {
        Liga entity = ligaRepository.findById(id)
                .orElseThrow(() -> new LigaNotFoundException("Liga no encontrada con id: " + id));
        ligaRepository.delete(entity);
    }

    private LigaDTO toDTO(Liga entity) {
        LigaDTO dto = new LigaDTO();
        dto.setId(entity.getId() != null ? entity.getId().intValue() : null);
        dto.setNombre(entity.getNombre());
        dto.setPais(entity.getPais());
        dto.setCategoria(entity.getCategoria());
        // imagenUri no existe en la entidad
        return dto;
    }

    private Liga toEntity(LigaDTO dto) {
        Liga entity = new Liga();
        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        entity.setCategoria(dto.getCategoria());
        return entity;
    }

    private void validarLiga(LigaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank())
            throw new LigaValidationException("El nombre de la liga es obligatorio");
        if (dto.getPais() == null || dto.getPais().isBlank())
            throw new LigaValidationException("El país de la liga es obligatorio");
        if (dto.getCategoria() == null || dto.getCategoria().isBlank())
            throw new LigaValidationException("La categoría de la liga es obligatoria");
    }
}
