package Modelos.Pago;

import Excepciones.PagoFallidoExcepcion;
import java.util.Date;
import java.util.UUID;

/**
 * Implementación del proceso de pago para tarjetas de crédito/débito.
 * Maneja pagos realizados con tarjetas bancarias.
 */
public class PagoTarjeta implements ProcesoPago {
    private String idTransaccion;
    private Double monto;
    private String moneda;
    private Date fechaPago;
    private final String numeroTarjeta;
    private final String cvv;
    private final String fechaVencimiento;
    private final String nombreTitular;
    private EstadoPago estado;
    
    public PagoTarjeta(String numeroTarjeta, String cvv, String fechaVencimiento, String nombreTitular) {
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
        this.nombreTitular = nombreTitular;
        this.estado = EstadoPago.PENDIENTE;
    }
    
    @Override
    public boolean iniciarPago(Double monto, String moneda, String datosPago) throws PagoFallidoExcepcion {
        try {
            // Validar parámetros de entrada
            if (monto == null || monto <= 0) {
                throw new PagoFallidoExcepcion(
                    "El monto debe ser un valor positivo",
                    null, "Tarjeta", monto, "INVALID_AMOUNT", "Monto inválido"
                );
            }
            
            if (moneda == null || moneda.trim().isEmpty()) {
                throw new PagoFallidoExcepcion(
                    "La moneda no puede ser nula o vacía",
                    null, "Tarjeta", monto, "INVALID_CURRENCY", "Moneda inválida"
                );
            }
            
            // Validar datos de la tarjeta
            if (!validarDatosTarjeta()) {
                this.estado = EstadoPago.ERROR;
                throw new PagoFallidoExcepcion(
                    "Los datos de la tarjeta son inválidos",
                    null, "Tarjeta", monto, "INVALID_CARD_DATA", "Datos de tarjeta inválidos"
                );
            }
            
            this.monto = monto;
            this.moneda = moneda;
            this.idTransaccion = generarIdTransaccion();
            this.fechaPago = new Date();
            this.estado = EstadoPago.INICIADO;
            
            // Enmascarar número de tarjeta para seguridad
            String tarjetaEnmascarada = enmascararTarjeta(numeroTarjeta);
            
            System.out.println("Pago con tarjeta iniciado:");
            System.out.println("  - ID Transacción: " + idTransaccion);
            System.out.println("  - Monto: " + monto + " " + moneda);
            System.out.println("  - Tarjeta: " + tarjetaEnmascarada);
            System.out.println("  - Titular: " + nombreTitular);
            
            return true;
        } catch (PagoFallidoExcepcion e) {
            this.estado = EstadoPago.ERROR;
            throw e; // Re-lanzar la excepción personalizada
        } catch (Exception e) {
            this.estado = EstadoPago.ERROR;
            throw new PagoFallidoExcepcion(
                "Error inesperado al iniciar pago con tarjeta: " + e.getMessage(),
                this.idTransaccion, "Tarjeta", this.monto, "UNEXPECTED_ERROR", e.getMessage()
            );
        }
    }
    
    @Override
    public boolean verificarPago(String idTransaccion) throws PagoFallidoExcepcion {
        if (idTransaccion == null || idTransaccion.trim().isEmpty()) {
            throw new PagoFallidoExcepcion(
                "El ID de transacción no puede ser nulo o vacío",
                idTransaccion, "Tarjeta", this.monto, "INVALID_TRANSACTION_ID", "ID de transacción inválido"
            );
        }
        
        if (!this.idTransaccion.equals(idTransaccion)) {
            throw new PagoFallidoExcepcion(
                "El ID de transacción no coincide con el pago actual",
                idTransaccion, "Tarjeta", this.monto, "TRANSACTION_MISMATCH", "ID de transacción no coincide"
            );
        }
        
        if (this.estado != EstadoPago.INICIADO) {
            throw new PagoFallidoExcepcion(
                "El pago no está en estado iniciado para verificación",
                idTransaccion, "Tarjeta", this.monto, "INVALID_STATE", "Estado de pago inválido"
            );
        }
        
        try {
            // Simular verificación con el banco
            boolean verificado = verificarConBanco();
            
            if (verificado) {
                this.estado = EstadoPago.VERIFICADO;
                System.out.println("Pago verificado exitosamente con el banco");
                return true;
            } else {
                this.estado = EstadoPago.ERROR;
                throw new PagoFallidoExcepcion(
                    "El banco rechazó la transacción",
                    idTransaccion, "Tarjeta", this.monto, "BANK_REJECTED", "Transacción rechazada por el banco"
                );
            }
        } catch (PagoFallidoExcepcion e) {
            throw e; // Re-lanzar la excepción personalizada
        } catch (Exception e) {
            this.estado = EstadoPago.ERROR;
            throw new PagoFallidoExcepcion(
                "Error inesperado al verificar pago: " + e.getMessage(),
                idTransaccion, "Tarjeta", this.monto, "VERIFICATION_ERROR", e.getMessage()
            );
        }
    }
    
    @Override
    public boolean confirmarPago(String idTransaccion) throws PagoFallidoExcepcion {
        if (idTransaccion == null || idTransaccion.trim().isEmpty()) {
            throw new PagoFallidoExcepcion(
                "El ID de transacción no puede ser nulo o vacío",
                idTransaccion, "Tarjeta", this.monto, "INVALID_TRANSACTION_ID", "ID de transacción inválido"
            );
        }
        
        if (!this.idTransaccion.equals(idTransaccion)) {
            throw new PagoFallidoExcepcion(
                "El ID de transacción no coincide con el pago actual",
                idTransaccion, "Tarjeta", this.monto, "TRANSACTION_MISMATCH", "ID de transacción no coincide"
            );
        }
        
        if (this.estado != EstadoPago.VERIFICADO) {
            throw new PagoFallidoExcepcion(
                "El pago debe estar verificado antes de confirmar",
                idTransaccion, "Tarjeta", this.monto, "INVALID_STATE", "Estado de pago inválido para confirmación"
            );
        }
        
        try {
            // Simular procesamiento final del pago
            boolean procesado = procesarPagoFinal();
            
            if (procesado) {
                this.estado = EstadoPago.CONFIRMADO;
                System.out.println("Pago confirmado exitosamente");
                System.out.println("  - Monto cobrado: " + monto + " " + moneda);
                System.out.println("  - Fecha: " + fechaPago);
                return true;
            } else {
                this.estado = EstadoPago.ERROR;
                throw new PagoFallidoExcepcion(
                    "Error al procesar el pago final",
                    idTransaccion, "Tarjeta", this.monto, "PROCESSING_ERROR", "Error en el procesamiento final"
                );
            }
        } catch (PagoFallidoExcepcion e) {
            throw e; // Re-lanzar la excepción personalizada
        } catch (Exception e) {
            this.estado = EstadoPago.ERROR;
            throw new PagoFallidoExcepcion(
                "Error inesperado al confirmar pago: " + e.getMessage(),
                idTransaccion, "Tarjeta", this.monto, "CONFIRMATION_ERROR", e.getMessage()
            );
        }
    }
    
    @Override
    public String obtenerEstadoPago(String idTransaccion) {
        if (this.idTransaccion.equals(idTransaccion)) {
            return this.estado.toString();
        }
        return "NO_ENCONTRADO";
    }
    
    @Override
    public String getIdTransaccion() {
        return idTransaccion;
    }
    
    @Override
    public Double getMonto() {
        return monto;
    }
    
    @Override
    public String getMoneda() {
        return moneda;
    }
    
    @Override
    public Date getFechaPago() {
        return fechaPago;
    }
    
    @Override
    public boolean cancelarPago(String idTransaccion) {
        if (!this.idTransaccion.equals(idTransaccion)) {
            System.out.println("Error: ID de transacción no coincide");
            return false;
        }
        
        if (this.estado == EstadoPago.CONFIRMADO) {
            System.out.println("Error: No se puede cancelar un pago ya confirmado");
            return false;
        }
        
        this.estado = EstadoPago.CANCELADO;
        System.out.println("Pago cancelado exitosamente");
        return true;
    }
    
    @Override
    public String obtenerDetallesMetodoPago() {
        return "Pago con Tarjeta - " + enmascararTarjeta(numeroTarjeta) + 
               " - Titular: " + nombreTitular;
    }
    
    /**
     * Valida los datos básicos de la tarjeta
     * @return true si los datos son válidos
     */
    private boolean validarDatosTarjeta() {
        return numeroTarjeta != null && numeroTarjeta.length() >= 13 && numeroTarjeta.length() <= 19 &&
               cvv != null && cvv.length() >= 3 && cvv.length() <= 4 &&
               fechaVencimiento != null && !fechaVencimiento.isEmpty() &&
               nombreTitular != null && !nombreTitular.trim().isEmpty();
    }
    
    /**
     * Enmascara el número de tarjeta para seguridad
     * @param numeroTarjeta El número de tarjeta original
     * @return El número de tarjeta enmascarado
     */
    private String enmascararTarjeta(String numeroTarjeta) {
        if (numeroTarjeta.length() < 8) {
            return "****";
        }
        return numeroTarjeta.substring(0, 4) + "****" + numeroTarjeta.substring(numeroTarjeta.length() - 4);
    }
    
    /**
     * Simula la verificación con el banco
     * @return true si el banco aprueba la transacción
     */
    private boolean verificarConBanco() {
        // Simulación: 95% de éxito en la verificación
        return Math.random() > 0.05;
    }
    
    /**
     * Simula el procesamiento final del pago
     * @return true si el pago se procesa exitosamente
     */
    private boolean procesarPagoFinal() {
        // Simulación: 98% de éxito en el procesamiento
        return Math.random() > 0.02;
    }
    
    /**
     * Genera un ID único para la transacción
     * @return El ID de la transacción
     */
    private String generarIdTransaccion() {
        return "TXN_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    // Getters para datos específicos de tarjeta (sin incluir datos sensibles)
    public String getNombreTitular() {
        return nombreTitular;
    }
    
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
}
