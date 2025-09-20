package Modelos.Pago;

import java.util.Date;

/**
 * Interfaz que define el contrato para procesos de pago.
 * Especifica los métodos que deben implementar todas las clases
 * que representen diferentes métodos de pago.
 */
public interface ProcesoPago {
    
    /**
     * Inicia el proceso de pago con los datos proporcionados.
     * @param monto El monto a pagar
     * @param moneda La moneda del pago (ej: "USD", "EUR", "MXN")
     * @param datosPago Datos específicos del método de pago
     * @return true si el pago se inició correctamente, false en caso contrario
     */
    boolean iniciarPago(Double monto, String moneda, String datosPago);
    
    /**
     * Verifica el estado del pago y valida los datos.
     * @param idTransaccion El ID de la transacción a verificar
     * @return true si el pago es válido y puede proceder, false en caso contrario
     */
    boolean verificarPago(String idTransaccion);
    
    /**
     * Confirma y finaliza el proceso de pago.
     * @param idTransaccion El ID de la transacción a confirmar
     * @return true si el pago se confirmó exitosamente, false en caso contrario
     */
    boolean confirmarPago(String idTransaccion);
    
    /**
     * Obtiene el estado actual del pago.
     * @param idTransaccion El ID de la transacción
     * @return El estado del pago como String
     */
    String obtenerEstadoPago(String idTransaccion);
    
    /**
     * Obtiene el ID de la transacción generado durante el proceso.
     * @return El ID de la transacción
     */
    String getIdTransaccion();
    
    /**
     * Obtiene el monto del pago.
     * @return El monto del pago
     */
    Double getMonto();
    
    /**
     * Obtiene la moneda del pago.
     * @return La moneda del pago
     */
    String getMoneda();
    
    /**
     * Obtiene la fecha y hora del pago.
     * @return La fecha del pago
     */
    Date getFechaPago();
    
    /**
     * Cancela un pago en proceso.
     * @param idTransaccion El ID de la transacción a cancelar
     * @return true si el pago se canceló exitosamente, false en caso contrario
     */
    boolean cancelarPago(String idTransaccion);
    
    /**
     * Obtiene los detalles del método de pago utilizado.
     * @return Los detalles del método de pago
     */
    String obtenerDetallesMetodoPago();
}
