package Excepciones;

/**
 * Excepción personalizada para manejar errores cuando un producto no se encuentra.
 * Se lanza cuando se intenta acceder a un producto que no existe en el sistema
 * o en el inventario.
 */
public class ProductoNoEncontradoExcepcion extends Exception {
    private String idProducto;
    private String ubicacion;
    
    /**
     * Constructor básico con mensaje de error.
     * @param mensaje Mensaje descriptivo del error
     */
    public ProductoNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa del error.
     * @param mensaje Mensaje descriptivo del error
     * @param causa La causa que originó esta excepción
     */
    public ProductoNoEncontradoExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    /**
     * Constructor con ID del producto no encontrado.
     * @param mensaje Mensaje descriptivo del error
     * @param idProducto ID del producto que no se encontró
     */
    public ProductoNoEncontradoExcepcion(String mensaje, String idProducto) {
        super(mensaje);
        this.idProducto = idProducto;
    }
    
    /**
     * Constructor completo con ID del producto y ubicación donde se buscó.
     * @param mensaje Mensaje descriptivo del error
     * @param idProducto ID del producto que no se encontró
     * @param ubicacion Ubicación donde se buscó el producto
     */
    public ProductoNoEncontradoExcepcion(String mensaje, String idProducto, String ubicacion) {
        super(mensaje);
        this.idProducto = idProducto;
        this.ubicacion = ubicacion;
    }
    
    /**
     * Obtiene el ID del producto no encontrado.
     * @return ID del producto
     */
    public String getIdProducto() {
        return idProducto;
    }
    
    /**
     * Obtiene la ubicación donde se buscó el producto.
     * @return Ubicación de búsqueda
     */
    public String getUbicacion() {
        return ubicacion;
    }
    
    /**
     * Obtiene un mensaje detallado del error incluyendo los valores específicos.
     * @return Mensaje detallado del error
     */
    @Override
    public String getMessage() {
        if (idProducto != null) {
            String mensajeBase = super.getMessage();
            if (ubicacion != null) {
                return String.format("%s - Producto: %s, Ubicación: %s", 
                                   mensajeBase, idProducto, ubicacion);
            } else {
                return String.format("%s - Producto: %s", mensajeBase, idProducto);
            }
        }
        return super.getMessage();
    }
}
