package org.ide.qoribet.common.exception.PartidoException;

// Excepción personalizada para errores de lógica de negocio relacionados con partidos
public class PartidoBusinessException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public PartidoBusinessException(String message) {
        super(message);
    }
}
