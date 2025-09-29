package org.ide.qoribet.common.exception.SaldoException;

// Excepción personalizada para errores de lógica de negocio relacionados con saldos
public class SaldoBusinessException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public SaldoBusinessException(String message) {
        super(message);
    }
}
