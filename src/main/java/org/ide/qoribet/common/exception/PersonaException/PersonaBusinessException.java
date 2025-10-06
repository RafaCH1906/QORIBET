package org.ide.qoribet.common.exception.PersonaException;

// Excepción personalizada para errores de lógica de negocio relacionados con personas
public class PersonaBusinessException extends RuntimeException {
    public PersonaBusinessException(String message) {
        super(message);
    }
}
