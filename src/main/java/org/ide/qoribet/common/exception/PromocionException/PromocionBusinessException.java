package org.ide.qoribet.common.exception.PromocionException;

// Excepción personalizada para errores de lógica de negocio relacionados con promociones
public class PromocionBusinessException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public PromocionBusinessException(String message) {
        super(message);
    }
}
