package Excepciones;

/**
 * Excepción personalizada para manejar errores cuando se intenta realizar
 * operaciones en un carrito de compras que está vacío.
 */
public class CarritoVacioExcepcion extends Exception {
    private String idCarrito;
    private String idUsuario;
    
    /**
     * Constructor básico con mensaje de error.
     * @param mensaje Mensaje descriptivo del error
     */
    public CarritoVacioExcepcion(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa del error.
     * @param mensaje Mensaje descriptivo del error
     * @param causa La causa que originó esta excepción
     */
    public CarritoVacioExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    /**
     * Constructor con ID del carrito vacío.
     * @param mensaje Mensaje descriptivo del error
     * @param idCarrito ID del carrito vacío
     */
    public CarritoVacioExcepcion(String mensaje, String idCarrito) {
        super(mensaje);
        this.idCarrito = idCarrito;
    }
    
    /**
     * Constructor completo con ID del carrito y usuario.
     * @param mensaje Mensaje descriptivo del error
     * @param idCarrito ID del carrito vacío
     * @param idUsuario ID del usuario propietario del carrito
     */
    public CarritoVacioExcepcion(String mensaje, String idCarrito, String idUsuario) {
        super(mensaje);
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
    }
    
    /**
     * Obtiene el ID del carrito vacío.
     * @return ID del carrito
     */
    public String getIdCarrito() {
        return idCarrito;
    }
    
    /**
     * Obtiene el ID del usuario propietario del carrito.
     * @return ID del usuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }
    
    /**
     * Obtiene un mensaje detallado del error incluyendo los valores específicos.
     * @return Mensaje detallado del error
     */
    @Override
    public String getMessage() {
        if (idCarrito != null) {
            String mensajeBase = super.getMessage();
            if (idUsuario != null) {
                return String.format("%s - Carrito: %s, Usuario: %s", 
                                   mensajeBase, idCarrito, idUsuario);
            } else {
                return String.format("%s - Carrito: %s", mensajeBase, idCarrito);
            }
        }
        return super.getMessage();
    }
}
