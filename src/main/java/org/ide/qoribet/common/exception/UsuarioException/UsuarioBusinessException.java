package org.ide.qoribet.common.exception.UsuarioException;

// Excepción personalizada para errores de lógica de negocio relacionados con usuarios
public class UsuarioBusinessException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public UsuarioBusinessException(String message) {
        super(message);
    }
}
