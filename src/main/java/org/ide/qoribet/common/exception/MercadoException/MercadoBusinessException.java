package org.ide.qoribet.common.exception.MercadoException;

// Excepción personalizada para errores de lógica de negocio relacionados con mercados
public class MercadoBusinessException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public MercadoBusinessException(String message) {
        super(message);
    }
}
