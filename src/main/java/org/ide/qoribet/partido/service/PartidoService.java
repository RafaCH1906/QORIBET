package org.ide.qoribet.partido.service;

import org.ide.qoribet.partido.dto.PartidoDTO;
import org.ide.qoribet.partido.entity.Partido;
import org.ide.qoribet.partido.repository.PartidoRepository;
import org.ide.qoribet.partido.repository.EquipoRepository;
import org.ide.qoribet.partido.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.ide.qoribet.common.exception.PartidoException.PartidoNotFoundException;
import org.ide.qoribet.common.exception.PartidoException.PartidoValidationException;

@Service
public class PartidoService {
    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;
    private final LigaRepository ligaRepository;

    public PartidoService(PartidoRepository partidoRepository, EquipoRepository equipoRepository, LigaRepository ligaRepository) {
        this.partidoRepository = partidoRepository;
        this.equipoRepository = equipoRepository;
        this.ligaRepository = ligaRepository;
    }

    public PartidoDTO crearPartido(PartidoDTO dto) {
        validarPartido(dto);
        Partido entity = toEntity(dto);
        entity = partidoRepository.save(entity);
        return toDTO(entity);
    }

    public PartidoDTO obtenerPartidoPorId(Long id) {
        Partido entity = partidoRepository.findById(id)
                .orElseThrow(() -> new PartidoNotFoundException("Partido no encontrado con id: " + id));
        return toDTO(entity);
    }

    public List<PartidoDTO> listarPartidos() {
        return partidoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PartidoDTO actualizarPartido(Long id, PartidoDTO dto) {
        validarPartido(dto);
        Partido entity = partidoRepository.findById(id)
                .orElseThrow(() -> new PartidoNotFoundException("Partido no encontrado con id: " + id));
        entity.setFecha(dto.getFecha());
        entity.setGolesLocal(dto.getGolesLocal());
        entity.setGolesVisitante(dto.getGolesVisitante());
        entity.setEstado(dto.getEstado());
        entity.setEquipoLocal(equipoRepository.findById(dto.getEquipoLocalId()).orElseThrow(() -> new PartidoValidationException("Equipo local no encontrado")));
        entity.setEquipoVisitante(equipoRepository.findById(dto.getEquipoVisitanteId()).orElseThrow(() -> new PartidoValidationException("Equipo visitante no encontrado")));
        entity.setLiga(ligaRepository.findById(dto.getLigaId()).orElseThrow(() -> new PartidoValidationException("Liga no encontrada")));
        entity = partidoRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarPartido(Long id) {
        Partido entity = partidoRepository.findById(id)
                .orElseThrow(() -> new PartidoNotFoundException("Partido no encontrado con id: " + id));
        partidoRepository.delete(entity);
    }

    private void validarPartido(PartidoDTO dto) {
        if (dto.getLigaId() == null)
            throw new PartidoValidationException("La liga es obligatoria");
        if (dto.getEquipoLocalId() == null || dto.getEquipoVisitanteId() == null)
            throw new PartidoValidationException("Los equipos son obligatorios");
        if (dto.getFecha() == null)
            throw new PartidoValidationException("La fecha es obligatoria");
        if (dto.getEstado() == null || dto.getEstado().isBlank())
            throw new PartidoValidationException("El estado del partido es obligatorio");
    }

    private PartidoDTO toDTO(Partido entity) {
        PartidoDTO dto = new PartidoDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setEquipoLocalId(entity.getEquipoLocal() != null ? entity.getEquipoLocal().getId() : null);
        dto.setEquipoVisitanteId(entity.getEquipoVisitante() != null ? entity.getEquipoVisitante().getId() : null);
        dto.setLigaId(entity.getLiga() != null ? entity.getLiga().getId() : null);
        dto.setGolesLocal(entity.getGolesLocal());
        dto.setGolesVisitante(entity.getGolesVisitante());
        dto.setEstado(entity.getEstado());
        return dto;
    }

    private Partido toEntity(PartidoDTO dto) {
        Partido entity = new Partido();
        entity.setFecha(dto.getFecha());
        entity.setGolesLocal(dto.getGolesLocal());
        entity.setGolesVisitante(dto.getGolesVisitante());
        entity.setEstado(dto.getEstado());
        entity.setEquipoLocal(equipoRepository.findById(dto.getEquipoLocalId()).orElseThrow(() -> new PartidoValidationException("Equipo local no encontrado")));
        entity.setEquipoVisitante(equipoRepository.findById(dto.getEquipoVisitanteId()).orElseThrow(() -> new PartidoValidationException("Equipo visitante no encontrado")));
        entity.setLiga(ligaRepository.findById(dto.getLigaId()).orElseThrow(() -> new PartidoValidationException("Liga no encontrada")));
        return entity;
    }
}
