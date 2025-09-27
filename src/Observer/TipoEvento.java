package Observer;

/**
 * Enum que define los tipos de eventos que pueden ocurrir en el sistema.
 * Estos eventos son los que pueden ser observados por los diferentes componentes.
 */
public enum TipoEvento {
    
    // Eventos de Orden
    ORDEN_CREADA("ORDEN_CREADA", "Se ha creado una nueva orden"),
    ORDEN_PAGADA("ORDEN_PAGADA", "Una orden ha sido pagada"),
    ORDEN_CANCELADA("ORDEN_CANCELADA", "Una orden ha sido cancelada"),
    ORDEN_ENVIADA("ORDEN_ENVIADA", "Una orden ha sido enviada"),
    ORDEN_ENTREGADA("ORDEN_ENTREGADA", "Una orden ha sido entregada"),
    
    // Eventos de Inventario
    STOCK_BAJO("STOCK_BAJO", "El stock de un producto está bajo"),
    STOCK_AGOTADO("STOCK_AGOTADO", "El stock de un producto se ha agotado"),
    PRODUCTO_AGREGADO("PRODUCTO_AGREGADO", "Se ha agregado un nuevo producto"),
    PRODUCTO_ELIMINADO("PRODUCTO_ELIMINADO", "Se ha eliminado un producto"),
    STOCK_ACTUALIZADO("STOCK_ACTUALIZADO", "Se ha actualizado el stock de un producto"),
    
    // Eventos de Pago
    PAGO_INICIADO("PAGO_INICIADO", "Se ha iniciado un proceso de pago"),
    PAGO_EXITOSO("PAGO_EXITOSO", "Un pago se ha completado exitosamente"),
    PAGO_FALLIDO("PAGO_FALLIDO", "Un pago ha fallado"),
    
    // Eventos de Usuario
    USUARIO_REGISTRADO("USUARIO_REGISTRADO", "Un nuevo usuario se ha registrado"),
    USUARIO_LOGIN("USUARIO_LOGIN", "Un usuario ha iniciado sesión"),
    USUARIO_LOGOUT("USUARIO_LOGOUT", "Un usuario ha cerrado sesión"),
    
    // Eventos del Sistema
    SISTEMA_ERROR("SISTEMA_ERROR", "Se ha producido un error en el sistema"),
    SISTEMA_MANTENIMIENTO("SISTEMA_MANTENIMIENTO", "El sistema entrará en mantenimiento");
    
    private final String codigo;
    private final String descripcion;
    
    TipoEvento(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString() {
        return codigo + ": " + descripcion;
    }
}
