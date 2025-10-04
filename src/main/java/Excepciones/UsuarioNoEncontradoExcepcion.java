package Excepciones;

/**
 * Excepción personalizada para manejar errores cuando un usuario no se encuentra.
 * Se lanza cuando se intenta acceder a un usuario que no existe en el sistema.
 */
public class UsuarioNoEncontradoExcepcion extends Exception {
    private String idUsuario;
    private String email;
    
    /**
     * Constructor básico con mensaje de error.
     * @param mensaje Mensaje descriptivo del error
     */
    public UsuarioNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa del error.
     * @param mensaje Mensaje descriptivo del error
     * @param causa La causa que originó esta excepción
     */
    public UsuarioNoEncontradoExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    /**
     * Constructor con ID del usuario no encontrado.
     * @param mensaje Mensaje descriptivo del error
     * @param idUsuario ID del usuario que no se encontró
     */
    public UsuarioNoEncontradoExcepcion(String mensaje, String idUsuario) {
        super(mensaje);
        this.idUsuario = idUsuario;
    }
    
    /**
     * Constructor completo con ID del usuario y email.
     * @param mensaje Mensaje descriptivo del error
     * @param idUsuario ID del usuario que no se encontró
     * @param email Email del usuario que no se encontró
     */
    public UsuarioNoEncontradoExcepcion(String mensaje, String idUsuario, String email) {
        super(mensaje);
        this.idUsuario = idUsuario;
        this.email = email;
    }
    
    /**
     * Obtiene el ID del usuario no encontrado.
     * @return ID del usuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }
    
    /**
     * Obtiene el email del usuario no encontrado.
     * @return Email del usuario
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Obtiene un mensaje detallado del error incluyendo los valores específicos.
     * @return Mensaje detallado del error
     */
    @Override
    public String getMessage() {
        if (idUsuario != null) {
            String mensajeBase = super.getMessage();
            if (email != null) {
                return String.format("%s - Usuario: %s, Email: %s", 
                                   mensajeBase, idUsuario, email);
            } else {
                return String.format("%s - Usuario: %s", mensajeBase, idUsuario);
            }
        }
        return super.getMessage();
    }
}
