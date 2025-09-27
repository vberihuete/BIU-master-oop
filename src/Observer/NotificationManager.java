package Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Gestor de notificaciones simplificado que implementa el patrón Observer.
 * Permite registrar closures (funciones) para eventos específicos.
 */
public class NotificationManager {
    
    private static NotificationManager instancia;
    private Map<TipoEvento, List<Consumer<Object>>> observadores;
    private boolean notificacionesActivas;
    
    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private NotificationManager() {
        this.observadores = new HashMap<>();
        this.notificacionesActivas = true;
    }
    
    /**
     * Obtiene la instancia única del NotificationManager (Singleton).
     * @return La instancia del NotificationManager
     */
    public static synchronized NotificationManager getInstancia() {
        if (instancia == null) {
            instancia = new NotificationManager();
        }
        return instancia;
    }
    
    /**
     * Registra una función (closure) para ser ejecutada cuando ocurra un evento específico.
     * @param evento El tipo de evento a observar
     * @param callback La función a ejecutar cuando ocurra el evento
     */
    public void registrarObserver(TipoEvento evento, Consumer<Object> callback) {
        if (callback == null) {
            return;
        }
        
        observadores.computeIfAbsent(evento, _ -> new ArrayList<>()).add(callback);
        System.out.println("✓ Observador registrado para evento: " + evento.getCodigo());
    }
    
    /**
     * Notifica a todos los observadores registrados para un evento específico.
     * @param evento El evento que se ha producido
     * @param datos Datos adicionales relacionados con el evento
     */
    public void notificarEvento(TipoEvento evento, Object datos) {
        if (!notificacionesActivas) {
            return;
        }
        
        List<Consumer<Object>> callbacks = observadores.get(evento);
        if (callbacks == null || callbacks.isEmpty()) {
            return;
        }
        
        System.out.println("\n🔔 NOTIFICACIÓN: " + evento.getDescripcion());
        System.out.println("📊 Datos: " + (datos != null ? datos.toString() : "Sin datos"));
        System.out.println("👥 Ejecutando " + callbacks.size() + " observadores...");
        
        for (Consumer<Object> callback : callbacks) {
            try {
                callback.accept(datos);
            } catch (Exception e) {
                System.err.println("❌ Error ejecutando observador: " + e.getMessage());
            }
        }
        System.out.println("✅ Notificación completada\n");
    }
    
    /**
     * Notifica un evento sin datos adicionales.
     * @param evento El evento que se ha producido
     */
    public void notificarEvento(TipoEvento evento) {
        notificarEvento(evento, null);
    }
    
    /**
     * Activa o desactiva las notificaciones.
     * @param activas true para activar, false para desactivar
     */
    public void setNotificacionesActivas(boolean activas) {
        this.notificacionesActivas = activas;
        String estado = activas ? "activadas" : "desactivadas";
        System.out.println("🔧 Notificaciones " + estado);
    }
    
    /**
     * Verifica si las notificaciones están activas.
     * @return true si están activas, false en caso contrario
     */
    public boolean isNotificacionesActivas() {
        return notificacionesActivas;
    }
    
    /**
     * Obtiene el número total de observadores registrados.
     * @return Número total de observadores
     */
    public int getNumeroObservers() {
        return observadores.values().stream().mapToInt(List::size).sum();
    }
    
    /**
     * Obtiene el número de observadores para un evento específico.
     * @param evento El evento
     * @return Número de observadores para ese evento
     */
    public int getNumeroObservers(TipoEvento evento) {
        List<Consumer<Object>> callbacks = observadores.get(evento);
        return callbacks != null ? callbacks.size() : 0;
    }
    
    /**
     * Limpia todos los observadores registrados.
     */
    public void limpiarObservers() {
        int cantidad = getNumeroObservers();
        observadores.clear();
        System.out.println("🧹 Se eliminaron " + cantidad + " observadores");
    }
    
    /**
     * Limpia los observadores para un evento específico.
     * @param evento El evento
     */
    public void limpiarObservers(TipoEvento evento) {
        List<Consumer<Object>> callbacks = observadores.remove(evento);
        int cantidad = callbacks != null ? callbacks.size() : 0;
        System.out.println("🧹 Se eliminaron " + cantidad + " observadores para " + evento.getCodigo());
    }
    
    /**
     * Obtiene información del estado del sistema de notificaciones.
     * @return String con información del estado
     */
    public String obtenerEstadoSistema() {
        StringBuilder estado = new StringBuilder();
        estado.append("📋 ESTADO DEL SISTEMA DE NOTIFICACIONES\n");
        estado.append("=====================================\n");
        estado.append("Estado: ").append(notificacionesActivas ? "🟢 Activo" : "🔴 Inactivo").append("\n");
        estado.append("Total de observadores: ").append(getNumeroObservers()).append("\n");
        
        if (!observadores.isEmpty()) {
            estado.append("\nObservadores por evento:\n");
            observadores.forEach((evento, callbacks) -> 
                estado.append("  - ").append(evento.getCodigo()).append(": ").append(callbacks.size()).append(" observadores\n"));
        }
        
        return estado.toString();
    }
}
