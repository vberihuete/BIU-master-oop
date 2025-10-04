package Excepciones;

/**
 * Excepción personalizada para manejar errores en procesos de pago.
 * Se lanza cuando un proceso de pago falla por diversas razones como
 * fondos insuficientes, tarjeta declinada, datos inválidos, etc.
 */
public class PagoFallidoExcepcion extends Exception {
    private String idTransaccion;
    private String metodoPago;
    private Double monto;
    private String codigoError;
    private String razonFallo;
    
    /**
     * Constructor básico con mensaje de error.
     * @param mensaje Mensaje descriptivo del error
     */
    public PagoFallidoExcepcion(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa del error.
     * @param mensaje Mensaje descriptivo del error
     * @param causa La causa que originó esta excepción
     */
    public PagoFallidoExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    /**
     * Constructor completo con detalles específicos del pago fallido.
     * @param mensaje Mensaje descriptivo del error
     * @param idTransaccion ID de la transacción fallida
     * @param metodoPago Método de pago utilizado
     * @param monto Monto del pago fallido
     * @param codigoError Código de error específico del procesador de pagos
     * @param razonFallo Razón específica del fallo
     */
    public PagoFallidoExcepcion(String mensaje, String idTransaccion, String metodoPago, 
                               Double monto, String codigoError, String razonFallo) {
        super(mensaje);
        this.idTransaccion = idTransaccion;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.codigoError = codigoError;
        this.razonFallo = razonFallo;
    }
    
    /**
     * Obtiene el ID de la transacción fallida.
     * @return ID de la transacción
     */
    public String getIdTransaccion() {
        return idTransaccion;
    }
    
    /**
     * Obtiene el método de pago utilizado.
     * @return Método de pago
     */
    public String getMetodoPago() {
        return metodoPago;
    }
    
    /**
     * Obtiene el monto del pago fallido.
     * @return Monto del pago
     */
    public Double getMonto() {
        return monto;
    }
    
    /**
     * Obtiene el código de error del procesador de pagos.
     * @return Código de error
     */
    public String getCodigoError() {
        return codigoError;
    }
    
    /**
     * Obtiene la razón específica del fallo.
     * @return Razón del fallo
     */
    public String getRazonFallo() {
        return razonFallo;
    }
    
    /**
     * Obtiene un mensaje detallado del error incluyendo los valores específicos.
     * @return Mensaje detallado del error
     */
    @Override
    public String getMessage() {
        if (idTransaccion != null && metodoPago != null && monto != null) {
            return String.format("%s - Transacción: %s, Método: %s, Monto: %.2f, Código: %s, Razón: %s", 
                               super.getMessage(), idTransaccion, metodoPago, monto, 
                               codigoError != null ? codigoError : "N/A", 
                               razonFallo != null ? razonFallo : "N/A");
        }
        return super.getMessage();
    }
}
