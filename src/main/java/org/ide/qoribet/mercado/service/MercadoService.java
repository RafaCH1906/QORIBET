package org.ide.qoribet.mercado.service;

import org.ide.qoribet.mercado.dto.MercadoDTO;
import org.ide.qoribet.mercado.entity.Mercado;
import org.ide.qoribet.mercado.repository.MercadoRepository;
import org.ide.qoribet.partido.repository.PartidoRepository;
import org.ide.qoribet.common.enums.mercado.TipoMercado;
import org.ide.qoribet.common.exception.MercadoException.MercadoNotFoundException;
import org.ide.qoribet.common.exception.MercadoException.MercadoValidationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MercadoService {
    private final MercadoRepository mercadoRepository;
    private final PartidoRepository partidoRepository;

    public MercadoService(MercadoRepository mercadoRepository, PartidoRepository partidoRepository) {
        this.mercadoRepository = mercadoRepository;
        this.partidoRepository = partidoRepository;
    }

    public MercadoDTO crearMercado(MercadoDTO dto) {
        validarMercado(dto);
        Mercado entity = toEntity(dto);
        entity = mercadoRepository.save(entity);
        return toDTO(entity);
    }

    public MercadoDTO obtenerMercadoPorId(Long id) {
        Mercado entity = mercadoRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Mercado no encontrado con id: " + id));
        return toDTO(entity);
    }

    public List<MercadoDTO> listarMercados() {
        return mercadoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MercadoDTO actualizarMercado(Long id, MercadoDTO dto) {
        validarMercado(dto);
        Mercado entity = mercadoRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Mercado no encontrado con id: " + id));
        entity.setTipo(dto.getTipo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setActivo(dto.isActivo());
        entity.setPartido(partidoRepository.findById(dto.getPartidoId())
                .orElseThrow(() -> new MercadoValidationException("Partido no encontrado")));
        entity = mercadoRepository.save(entity);
        return toDTO(entity);
    }

    public void eliminarMercado(Long id) {
        Mercado entity = mercadoRepository.findById(id)
                .orElseThrow(() -> new MercadoNotFoundException("Mercado no encontrado con id: " + id));
        mercadoRepository.delete(entity);
    }

    private void validarMercado(MercadoDTO dto) {
        if (dto.getTipo() == null)
            throw new MercadoValidationException("El tipo de mercado es obligatorio");
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank())
            throw new MercadoValidationException("La descripción del mercado es obligatoria");
        if (dto.getPartidoId() == null)
            throw new MercadoValidationException("El partido es obligatorio");
    }

    private MercadoDTO toDTO(Mercado entity) {
        MercadoDTO dto = new MercadoDTO();
        dto.setId(entity.getId());
        dto.setPartidoId(entity.getPartido() != null ? entity.getPartido().getId() : null);
        dto.setTipo(entity.getTipo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.isActivo());
        return dto;
    }

    private Mercado toEntity(MercadoDTO dto) {
        Mercado entity = new Mercado();
        entity.setTipo(dto.getTipo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setActivo(dto.isActivo());
        entity.setPartido(partidoRepository.findById(dto.getPartidoId())
                .orElseThrow(() -> new MercadoValidationException("Partido no encontrado")));
        return entity;
    }
}
