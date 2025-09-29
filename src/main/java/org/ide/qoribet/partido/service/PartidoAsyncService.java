package org.ide.qoribet.partido.service;

import org.ide.qoribet.apuesta.service.ApuestaAsyncService;
import org.ide.qoribet.common.service.NotificacionAsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class PartidoAsyncService {

    private static final Logger logger = Logger.getLogger(PartidoAsyncService.class.getName());
    private final ApuestaAsyncService apuestaAsyncService;
    private final NotificacionAsyncService notificacionService;

    public PartidoAsyncService(ApuestaAsyncService apuestaAsyncService,
                              NotificacionAsyncService notificacionService) {
        this.apuestaAsyncService = apuestaAsyncService;
        this.notificacionService = notificacionService;
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> procesarFinalizacionPartido(Long partidoId, String resultado) {
        try {
            logger.info("Iniciando procesamiento asíncrono para partido: " + partidoId);

            // 1. Calcular pagos para los ganadores (operación pesada)
            apuestaAsyncService.calcularPagosGanadores(partidoId);

            // 2. Actualizar estadísticas del partido
            actualizarEstadisticasPartido(partidoId);

            // 3. Procesar todas las apuestas relacionadas
            procesarApuestasDelPartido(partidoId, resultado);

            // 4. Enviar notificaciones masivas a usuarios afectados
            enviarNotificacionesFinPartido(partidoId, resultado);

            logger.info("Procesamiento asíncrono completado para partido: " + partidoId);

        } catch (Exception e) {
            logger.severe("Error procesando finalización de partido: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> actualizarCuotasEnVivo(Long partidoId) {
        try {
            // Simular actualización de cuotas en tiempo real
            Thread.sleep(500);
            logger.info("Cuotas actualizadas en vivo para partido: " + partidoId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.severe("Error actualizando cuotas: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }

    private void actualizarEstadisticasPartido(Long partidoId) throws InterruptedException {
        Thread.sleep(2000);
        logger.info("Estadísticas del partido actualizadas: " + partidoId);
    }

    private void procesarApuestasDelPartido(Long partidoId, String resultado) throws InterruptedException {
        Thread.sleep(3000);
        logger.info("Apuestas procesadas para partido " + partidoId + " con resultado: " + resultado);
    }

    private void enviarNotificacionesFinPartido(Long partidoId, String resultado) {
        // Enviar notificaciones a múltiples usuarios de forma asíncrona
        notificacionService.enviarNotificacionPush(1L, "Partido " + partidoId + " finalizado: " + resultado);
        notificacionService.enviarNotificacionPush(2L, "¡Revisa el resultado de tus apuestas!");
    }
}
