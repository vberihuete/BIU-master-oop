package Modelos.Inventario;

import Modelos.Producto.ProductoInterface;
import java.util.List;

/**
 * Clase abstracta para la gestión de inventario.
 * Define métodos abstractos que deben ser implementados por clases concretas
 * para diferentes tipos de inventario (digital vs. físico).
 */
public abstract class GestorInventario {
    protected String nombreInventario;
    protected String ubicacion;
    
    public GestorInventario(String nombreInventario, String ubicacion) {
        this.nombreInventario = nombreInventario;
        this.ubicacion = ubicacion;
    }
    
    /**
     * Método abstracto para añadir un producto al inventario.
     * @param producto El producto a añadir
     * @return true si se añadió exitosamente, false en caso contrario
     */
    public abstract boolean añadirProducto(ProductoInterface producto);
    
    /**
     * Método abstracto para eliminar un producto del inventario.
     * @param idProducto El ID del producto a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public abstract boolean eliminarProducto(String idProducto);
    
    /**
     * Método abstracto para actualizar el stock de un producto.
     * @param idProducto El ID del producto
     * @param nuevaCantidad La nueva cantidad en stock
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public abstract boolean actualizarStock(String idProducto, Integer nuevaCantidad);
    
    /**
     * Método concreto para obtener el nombre del inventario.
     * @return El nombre del inventario
     */
    public String getNombreInventario() {
        return nombreInventario;
    }
    
    /**
     * Método concreto para obtener la ubicación del inventario.
     * @return La ubicación del inventario
     */
    public String getUbicacion() {
        return ubicacion;
    }
    
    /**
     * Método concreto para establecer el nombre del inventario.
     * @param nombreInventario El nuevo nombre del inventario
     */
    public void setNombreInventario(String nombreInventario) {
        this.nombreInventario = nombreInventario;
    }
    
    /**
     * Método concreto para establecer la ubicación del inventario.
     * @param ubicacion La nueva ubicación del inventario
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    /**
     * Método abstracto para obtener todos los productos del inventario.
     * @return Lista de productos en el inventario
     */
    public abstract List<ProductoInterface> obtenerProductos();
    
    /**
     * Método abstracto para buscar un producto por ID.
     * @param idProducto El ID del producto a buscar
     * @return El producto encontrado o null si no existe
     */
    public abstract ProductoInterface buscarProducto(String idProducto);
}
