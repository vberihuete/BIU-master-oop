package Excepciones;

/**
 * Excepción personalizada para manejar errores de inventario insuficiente.
 * Se lanza cuando se intenta realizar una operación que requiere más stock
 * del que está disponible en el inventario.
 */
public class InventarioInsuficienteExcepcion extends Exception {
    private String idProducto;
    private Integer stockDisponible;
    private Integer stockRequerido;
    
    /**
     * Constructor básico con mensaje de error.
     * @param mensaje Mensaje descriptivo del error
     */
    public InventarioInsuficienteExcepcion(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa del error.
     * @param mensaje Mensaje descriptivo del error
     * @param causa La causa que originó esta excepción
     */
    public InventarioInsuficienteExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    /**
     * Constructor completo con detalles específicos del inventario.
     * @param mensaje Mensaje descriptivo del error
     * @param idProducto ID del producto con stock insuficiente
     * @param stockDisponible Cantidad disponible en inventario
     * @param stockRequerido Cantidad requerida para la operación
     */
    public InventarioInsuficienteExcepcion(String mensaje, String idProducto, 
                                         Integer stockDisponible, Integer stockRequerido) {
        super(mensaje);
        this.idProducto = idProducto;
        this.stockDisponible = stockDisponible;
        this.stockRequerido = stockRequerido;
    }
    
    /**
     * Obtiene el ID del producto con stock insuficiente.
     * @return ID del producto
     */
    public String getIdProducto() {
        return idProducto;
    }
    
    /**
     * Obtiene la cantidad disponible en inventario.
     * @return Stock disponible
     */
    public Integer getStockDisponible() {
        return stockDisponible;
    }
    
    /**
     * Obtiene la cantidad requerida para la operación.
     * @return Stock requerido
     */
    public Integer getStockRequerido() {
        return stockRequerido;
    }
    
    /**
     * Obtiene un mensaje detallado del error incluyendo los valores específicos.
     * @return Mensaje detallado del error
     */
    @Override
    public String getMessage() {
        if (idProducto != null && stockDisponible != null && stockRequerido != null) {
            return String.format("%s - Producto: %s, Stock disponible: %d, Stock requerido: %d", 
                               super.getMessage(), idProducto, stockDisponible, stockRequerido);
        }
        return super.getMessage();
    }
}
