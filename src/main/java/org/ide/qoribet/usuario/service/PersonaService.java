package org.ide.qoribet.usuario.service;

import org.ide.qoribet.usuario.dto.PersonaDTO;
import org.ide.qoribet.usuario.entity.Persona;
import org.ide.qoribet.usuario.repository.PersonaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;
import org.ide.qoribet.common.exception.PersonaException.PersonaNotFoundException;
import org.ide.qoribet.common.exception.PersonaException.PersonaValidationException;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public PersonaDTO crearPersona(PersonaDTO personaDTO) {
        validarCamposObligatorios(personaDTO);
        if (personaRepository.findByNumeroDocumento(personaDTO.getNumeroDocumento()).isPresent()) {
            throw new PersonaValidationException("Ya existe una persona con ese número de documento");
        }
        Persona persona = toEntity(personaDTO);
        persona = personaRepository.save(persona);
        return toDTO(persona);
    }

    public PersonaDTO obtenerPersonaPorId(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con id: " + id));
        return toDTO(persona);
    }

    public List<PersonaDTO> listarPersonas() {
        return personaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PersonaDTO actualizarPersona(Long id, PersonaDTO personaDTO) {
        validarCamposObligatorios(personaDTO);
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con id: " + id));
        if (!Objects.equals(persona.getNumero_documento(), personaDTO.getNumeroDocumento()) &&
            personaRepository.findByNumeroDocumento(personaDTO.getNumeroDocumento()).isPresent()) {
            throw new PersonaValidationException("Ya existe una persona con ese número de documento");
        }
        persona.setPrimerNombre(personaDTO.getPrimerNombre());
        persona.setSegundoNombre(personaDTO.getSegundoNombre());
        persona.setPrimerApellido(personaDTO.getPrimerApellido());
        persona.setSegundoApellido(personaDTO.getSegundoApellido());
        persona.setTipo_documento(personaDTO.getTipoDocumento());
        persona.setNumero_documento(personaDTO.getNumeroDocumento());
        persona.setFechaNacimiento(personaDTO.getFechaNacimiento());
        persona.setDireccion(personaDTO.getDireccion());
        persona.setTelefono(personaDTO.getTelefono());
        persona = personaRepository.save(persona);
        return toDTO(persona);
    }

    public void eliminarPersona(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con id: " + id));
        personaRepository.delete(persona);
    }

    private void validarCamposObligatorios(PersonaDTO dto) {
        if (dto.getPrimerNombre() == null || dto.getPrimerNombre().isBlank())
            throw new PersonaValidationException("El primer nombre es obligatorio");
        if (dto.getPrimerApellido() == null || dto.getPrimerApellido().isBlank())
            throw new PersonaValidationException("El primer apellido es obligatorio");
        if (dto.getTipoDocumento() == null || dto.getTipoDocumento().isBlank())
            throw new PersonaValidationException("El tipo de documento es obligatorio");
        if (dto.getNumeroDocumento() == null || dto.getNumeroDocumento().isBlank())
            throw new PersonaValidationException("El número de documento es obligatorio");
        if (dto.getFechaNacimiento() == null)
            throw new PersonaValidationException("La fecha de nacimiento es obligatoria");
    }

    private PersonaDTO toDTO(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId() != null ? persona.getId().intValue() : null);
        dto.setPrimerNombre(persona.getPrimerNombre());
        dto.setSegundoNombre(persona.getSegundoNombre());
        dto.setPrimerApellido(persona.getPrimerApellido());
        dto.setSegundoApellido(persona.getSegundoApellido());
        dto.setTipoDocumento(persona.getTipo_documento());
        dto.setNumeroDocumento(persona.getNumero_documento());
        dto.setFechaNacimiento(persona.getFechaNacimiento());
        dto.setDireccion(persona.getDireccion());
        dto.setTelefono(persona.getTelefono());
        return dto;
    }

    private Persona toEntity(PersonaDTO dto) {
        Persona persona = new Persona();
        persona.setPrimerNombre(dto.getPrimerNombre());
        persona.setSegundoNombre(dto.getSegundoNombre());
        persona.setPrimerApellido(dto.getPrimerApellido());
        persona.setSegundoApellido(dto.getSegundoApellido());
        persona.setTipo_documento(dto.getTipoDocumento());
        persona.setNumero_documento(dto.getNumeroDocumento());
        persona.setFechaNacimiento(dto.getFechaNacimiento());
        persona.setDireccion(dto.getDireccion());
        persona.setTelefono(dto.getTelefono());
        return persona;
    }
}
