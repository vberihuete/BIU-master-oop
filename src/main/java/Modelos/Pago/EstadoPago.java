package Modelos.Pago;

/**
 * Enum que define los estados posibles de un proceso de pago.
 * Este enum es compartido entre todas las implementaciones de ProcesoPago
 * para asegurar consistencia en los estados de pago.
 */
public enum EstadoPago {
    /**
     * Estado inicial cuando se crea el objeto de pago
     */
    PENDIENTE("PENDIENTE"),
    
    /**
     * Estado cuando el pago ha sido iniciado exitosamente
     */
    INICIADO("INICIADO"),
    
    /**
     * Estado cuando el pago ha sido verificado exitosamente
     */
    VERIFICADO("VERIFICADO"),
    
    /**
     * Estado cuando el pago ha sido confirmado y procesado exitosamente
     */
    CONFIRMADO("CONFIRMADO"),
    
    /**
     * Estado cuando el pago ha sido cancelado
     */
    CANCELADO("CANCELADO"),
    
    /**
     * Estado cuando ocurrió un error durante el proceso de pago
     */
    ERROR("ERROR");
    
    private final String descripcion;
    
    /**
     * Constructor del enum
     * @param descripcion La descripción del estado
     */
    EstadoPago(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene la descripción del estado
     * @return La descripción del estado
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Convierte el enum a String
     * @return La descripción del estado
     */
    @Override
    public String toString() {
        return descripcion;
    }
    
    /**
     * Verifica si el estado indica que el pago fue exitoso
     * @return true si el pago fue confirmado exitosamente
     */
    public boolean esExitoso() {
        return this == CONFIRMADO;
    }
    
    /**
     * Verifica si el estado indica que el pago falló
     * @return true si el pago está en estado de error o cancelado
     */
    public boolean esFallido() {
        return this == ERROR || this == CANCELADO;
    }
    
    /**
     * Verifica si el estado permite continuar con el siguiente paso del proceso
     * @return true si se puede proceder con el siguiente paso
     */
    public boolean permiteContinuar() {
        return this == INICIADO || this == VERIFICADO;
    }
}
