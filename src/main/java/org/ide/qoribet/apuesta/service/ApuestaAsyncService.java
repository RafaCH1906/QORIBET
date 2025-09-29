package org.ide.qoribet.apuesta.service;

import org.ide.qoribet.apuesta.dto.ApuestaDTO;
import org.ide.qoribet.common.service.NotificacionAsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class ApuestaAsyncService {

    private static final Logger logger = Logger.getLogger(ApuestaAsyncService.class.getName());
    private final NotificacionAsyncService notificacionService;

    public ApuestaAsyncService(NotificacionAsyncService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> procesarApuestaCompleta(ApuestaDTO apuesta, String emailUsuario) {
        try {
            // 1. Procesar validaciones complejas de la apuesta
            validarApuestaAsincrona(apuesta);

            // 2. Actualizar estadísticas del usuario
            actualizarEstadisticasUsuario(apuesta.getUsuarioId());

            // 3. Enviar notificaciones (estas también son asíncronas)
            notificacionService.enviarEmailApuestaCreada(emailUsuario,
                "Apuesta de $" + apuesta.getMontoApostado() + " creada exitosamente");

            notificacionService.enviarNotificacionPush(apuesta.getUsuarioId(),
                "Tu apuesta ha sido procesada correctamente");

            // 4. Procesar estadísticas globales
            procesarEstadisticasGlobales(apuesta);

            logger.info("Procesamiento asíncrono completo para apuesta ID: " + apuesta.getId());

        } catch (Exception e) {
            logger.severe("Error en procesamiento asíncrono de apuesta: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> calcularPagosGanadores(Long partidoId) {
        try {
            // Simular cálculo complejo de pagos para ganadores
            Thread.sleep(5000); // Operación que toma tiempo
            logger.info("Pagos calculados para partido ID: " + partidoId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.severe("Error calculando pagos: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    private void validarApuestaAsincrona(ApuestaDTO apuesta) throws InterruptedException {
        // Simular validaciones complejas que toman tiempo
        Thread.sleep(1000);
        logger.info("Validaciones complejas completadas para apuesta: " + apuesta.getId());
    }

    private void actualizarEstadisticasUsuario(Long usuarioId) throws InterruptedException {
        // Simular actualización de estadísticas
        Thread.sleep(1500);
        logger.info("Estadísticas actualizadas para usuario: " + usuarioId);
    }

    private void procesarEstadisticasGlobales(ApuestaDTO apuesta) throws InterruptedException {
        // Simular procesamiento de estadísticas globales
        Thread.sleep(2000);
        logger.info("Estadísticas globales procesadas");
    }
}
