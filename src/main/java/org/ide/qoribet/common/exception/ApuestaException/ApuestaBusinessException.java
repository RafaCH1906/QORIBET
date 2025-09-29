package org.ide.qoribet.common.exception.ApuestaException;

// Excepción personalizada para errores de lógica de negocio relacionados con apuestas
public class ApuestaBusinessException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public ApuestaBusinessException(String message) {
        super(message);
    }
}
