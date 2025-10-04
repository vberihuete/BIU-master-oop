package Modelos.Pago;

import java.util.Date;
import java.util.UUID;

/**
 * Implementación del proceso de pago para PayPal.
 * Maneja pagos realizados a través de la plataforma PayPal.
 */
public class PagoPayPal implements ProcesoPago {
    private String idTransaccion;
    private Double monto;
    private String moneda;
    private Date fechaPago;
    private final String emailPayPal;
    private EstadoPago estado;
    private String tokenAcceso;
    
    public PagoPayPal(String emailPayPal) {
        this.emailPayPal = emailPayPal;
        this.estado = EstadoPago.PENDIENTE;
    }
    
    @Override
    public boolean iniciarPago(Double monto, String moneda, String datosPago) {
        try {
            // Validar email de PayPal
            if (!validarEmailPayPal()) {
                this.estado = EstadoPago.ERROR;
                System.out.println("Error: Email de PayPal inválido");
                return false;
            }
            
            this.monto = monto;
            this.moneda = moneda;
            this.idTransaccion = generarIdTransaccion();
            this.fechaPago = new Date();
            this.estado = EstadoPago.INICIADO;
            
            // Simular obtención de token de acceso de PayPal
            this.tokenAcceso = generarTokenAcceso();
            
            System.out.println("Pago con PayPal iniciado:");
            System.out.println("  - ID Transacción: " + idTransaccion);
            System.out.println("  - Monto: " + monto + " " + moneda);
            System.out.println("  - Email PayPal: " + emailPayPal);
            System.out.println("  - Token de acceso obtenido");
            
            return true;
        } catch (Exception e) {
            this.estado = EstadoPago.ERROR;
            System.out.println("Error al iniciar pago con PayPal: " + e.getMessage());
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
        
        // Simular verificación con PayPal API
        boolean verificado = verificarConPayPal();
        
        if (verificado) {
            this.estado = EstadoPago.VERIFICADO;
            System.out.println("Pago verificado exitosamente con PayPal");
            System.out.println("  - Saldo disponible verificado");
            System.out.println("  - Cuenta PayPal activa y válida");
            return true;
        } else {
            this.estado = EstadoPago.ERROR;
            System.out.println("Error: PayPal rechazó la transacción");
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
        
        // Simular procesamiento final con PayPal
        boolean procesado = procesarPagoFinalPayPal();
        
        if (procesado) {
            this.estado = EstadoPago.CONFIRMADO;
            System.out.println("Pago confirmado exitosamente con PayPal");
            System.out.println("  - Monto transferido: " + monto + " " + moneda);
            System.out.println("  - Fecha: " + fechaPago);
            System.out.println("  - Comisión PayPal aplicada");
            return true;
        } else {
            this.estado = EstadoPago.ERROR;
            System.out.println("Error al procesar el pago final con PayPal");
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
        System.out.println("Pago con PayPal cancelado exitosamente");
        return true;
    }
    
    @Override
    public String obtenerDetallesMetodoPago() {
        return "Pago con PayPal - " + emailPayPal;
    }
    
    /**
     * Valida el formato del email de PayPal
     * @return true si el email es válido
     */
    private boolean validarEmailPayPal() {
        return emailPayPal != null && 
               emailPayPal.contains("@") && 
               emailPayPal.contains(".") &&
               emailPayPal.length() > 5;
    }
    
    /**
     * Simula la verificación con la API de PayPal
     * @return true si PayPal aprueba la transacción
     */
    private boolean verificarConPayPal() {
        // Simulación: 97% de éxito en la verificación con PayPal
        return Math.random() > 0.03;
    }
    
    /**
     * Simula el procesamiento final del pago con PayPal
     * @return true si el pago se procesa exitosamente
     */
    private boolean procesarPagoFinalPayPal() {
        // Simulación: 99% de éxito en el procesamiento con PayPal
        return Math.random() > 0.01;
    }
    
    /**
     * Genera un ID único para la transacción
     * @return El ID de la transacción
     */
    private String generarIdTransaccion() {
        return "PP_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Genera un token de acceso simulado para PayPal
     * @return El token de acceso
     */
    private String generarTokenAcceso() {
        return "TOKEN_" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }
    
    /**
     * Calcula la comisión de PayPal (simulada)
     * @return El monto de la comisión
     */
    public Double calcularComisionPayPal() {
        // PayPal cobra aproximadamente 2.9% + $0.30 USD
        Double comisionPorcentaje = monto * 0.029;
        Double comisionFija = 0.30;
        return comisionPorcentaje + comisionFija;
    }
    
    /**
     * Obtiene el monto total incluyendo comisiones
     * @return El monto total
     */
    public Double getMontoTotal() {
        return monto + calcularComisionPayPal();
    }
    
    // Getters específicos de PayPal
    public String getEmailPayPal() {
        return emailPayPal;
    }
    
    public String getTokenAcceso() {
        return tokenAcceso;
    }
}
