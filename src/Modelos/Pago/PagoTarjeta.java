package Modelos.Pago;

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
    private String numeroTarjeta;
    private String cvv;
    private String fechaVencimiento;
    private String nombreTitular;
    private EstadoPago estado;
    
    public PagoTarjeta(String numeroTarjeta, String cvv, String fechaVencimiento, String nombreTitular) {
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
        this.nombreTitular = nombreTitular;
        this.estado = EstadoPago.PENDIENTE;
    }
    
    @Override
    public boolean iniciarPago(Double monto, String moneda, String datosPago) {
        try {
            // Validar datos de la tarjeta
            if (!validarDatosTarjeta()) {
                this.estado = EstadoPago.ERROR;
                System.out.println("Error: Datos de tarjeta inválidos");
                return false;
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
        } catch (Exception e) {
            this.estado = EstadoPago.ERROR;
            System.out.println("Error al iniciar pago con tarjeta: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean verificarPago(String idTransaccion) {
        if (!this.idTransaccion.equals(idTransaccion)) {
            System.out.println("Error: ID de transacción no coincide");
            return false;
        }
        
        if (this.estado != EstadoPago.INICIADO) {
            System.out.println("Error: El pago no está en estado iniciado");
            return false;
        }
        
        // Simular verificación con el banco
        boolean verificado = verificarConBanco();
        
        if (verificado) {
            this.estado = EstadoPago.VERIFICADO;
            System.out.println("Pago verificado exitosamente con el banco");
            return true;
        } else {
            this.estado = EstadoPago.ERROR;
            System.out.println("Error: El banco rechazó la transacción");
            return false;
        }
    }
    
    @Override
    public boolean confirmarPago(String idTransaccion) {
        if (!this.idTransaccion.equals(idTransaccion)) {
            System.out.println("Error: ID de transacción no coincide");
            return false;
        }
        
        if (this.estado != EstadoPago.VERIFICADO) {
            System.out.println("Error: El pago debe estar verificado antes de confirmar");
            return false;
        }
        
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
            System.out.println("Error al procesar el pago final");
            return false;
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
