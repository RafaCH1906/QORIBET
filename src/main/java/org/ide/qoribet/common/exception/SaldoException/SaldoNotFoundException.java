package org.ide.qoribet.common.exception.SaldoException;

// Excepción personalizada para cuando no se encuentra un saldo en el sistema
public class SaldoNotFoundException extends RuntimeException {
    // Constructor que recibe el mensaje de error
    public SaldoNotFoundException(String message) {
        super(message);
    }
}
