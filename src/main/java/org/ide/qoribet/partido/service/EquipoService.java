package org.ide.qoribet.partido.service;

import org.ide.qoribet.partido.dto.EquipoDTO;
import org.ide.qoribet.partido.entity.Equipo;
import org.ide.qoribet.partido.repository.EquipoRepository;
import org.ide.qoribet.common.exception.PartidoException.EquipoNotFoundException;
import org.ide.qoribet.common.exception.PartidoException.EquipoValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public EquipoDTO crearEquipo(EquipoDTO dto) {
        validarEquipo(dto);
        Equipo entity = toEntity(dto);
        entity = equipoRepository.save(entity);
        return toDTO(entity);
    }

    public EquipoDTO obtenerEquipoPorId(Long id) {
        Equipo entity = equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado con id: " + id));
        return toDTO(entity);
    }

    public List<EquipoDTO> listarEquipos() {
        return equipoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EquipoDTO actualizarEquipo(Long id, EquipoDTO dto) {
        validarEquipo(dto);
        Equipo entity = equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado con id: " + id));
        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        // estadio e imagenUri no existen en la entidad
        entity = equipoRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarEquipo(Long id) {
        Equipo entity = equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado con id: " + id));
        equipoRepository.delete(entity);
    }

    private void validarEquipo(EquipoDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank())
            throw new EquipoValidationException("El nombre del equipo es obligatorio");
        if (dto.getPais() == null || dto.getPais().isBlank())
            throw new EquipoValidationException("El país del equipo es obligatorio");
    }

    private EquipoDTO toDTO(Equipo entity) {
        EquipoDTO dto = new EquipoDTO();
        dto.setId(entity.getId() != null ? entity.getId().intValue() : null);
        dto.setNombre(entity.getNombre());
        dto.setPais(entity.getPais());
        // estadio e imagenUri no existen en la entidad
        return dto;
    }

    private Equipo toEntity(EquipoDTO dto) {
        Equipo entity = new Equipo();
        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        return entity;
    }
}
