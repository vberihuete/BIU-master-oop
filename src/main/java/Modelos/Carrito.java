package Modelos;

import Modelos.Usuario.UsuarioCliente;
import Modelos.Producto.ProductoInterface;
import Excepciones.CarritoVacioExcepcion;
import Excepciones.ProductoNoEncontradoExcepcion;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private String id;
    private UsuarioCliente usuario;
    private List<ProductoInterface> productos;

    // Constructor
    public Carrito(String id, UsuarioCliente usuario) {
        this.id = id;
        this.usuario = usuario;
        this.productos = new ArrayList<>();
    }

    public Carrito(String id, UsuarioCliente usuario, List<ProductoInterface> productos) {
        this.id = id;
        this.usuario = usuario;
        this.productos = productos;
    }

    // Getters

    public String getId() {
        return id;
    }

    public UsuarioCliente getUsuario() {
        return usuario;
    }

    public List<ProductoInterface> getProductos() {
        return productos;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(UsuarioCliente usuario) {
        this.usuario = usuario;
    }

    public void setProductos(List<ProductoInterface> productos) {
        this.productos = productos;
    }

    /**
     * Agrega un producto al carrito.
     * @param producto El producto a agregar
     * @throws IllegalArgumentException Si el producto es null
     */
    public void agregarProducto(ProductoInterface producto) {
        if (producto == null) {
            throw new IllegalArgumentException("No se puede agregar un producto nulo al carrito");
        }
        productos.add(producto);
    }

    /**
     * Agrega múltiples productos al carrito.
     * @param productos Lista de productos a agregar
     * @throws IllegalArgumentException Si la lista es null o contiene productos nulos
     */
    public void agregarProductos(List<ProductoInterface> productos) {
        if (productos == null) {
            throw new IllegalArgumentException("No se puede agregar una lista nula de productos");
        }
        for (ProductoInterface producto : productos) {
            if (producto == null) {
                throw new IllegalArgumentException("No se puede agregar productos nulos al carrito");
            }
        }
        this.productos.addAll(productos);
    }

    /**
     * Elimina un producto del carrito.
     * @param producto El producto a eliminar
     * @throws ProductoNoEncontradoExcepcion Si el producto no está en el carrito
     */
    public void eliminarProducto(ProductoInterface producto) throws ProductoNoEncontradoExcepcion {
        if (producto == null) {
            throw new IllegalArgumentException("No se puede eliminar un producto nulo del carrito");
        }
        if (!productos.contains(producto)) {
            throw new ProductoNoEncontradoExcepcion(
                "El producto no se encuentra en el carrito", 
                producto.getId(), 
                "Carrito " + this.id
            );
        }
        productos.remove(producto);
    }

    /**
     * Elimina un producto del carrito por su ID.
     * @param idProducto ID del producto a eliminar
     * @throws ProductoNoEncontradoExcepcion Si el producto no está en el carrito
     */
    public void eliminarProductoPorId(String idProducto) throws ProductoNoEncontradoExcepcion {
        if (idProducto == null || idProducto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no puede ser nulo o vacío");
        }
        
        ProductoInterface productoEncontrado = null;
        for (ProductoInterface producto : productos) {
            if (idProducto.equals(producto.getId())) {
                productoEncontrado = producto;
                break;
            }
        }
        
        if (productoEncontrado == null) {
            throw new ProductoNoEncontradoExcepcion(
                "El producto con ID " + idProducto + " no se encuentra en el carrito", 
                idProducto, 
                "Carrito " + this.id
            );
        }
        
        productos.remove(productoEncontrado);
    }

    /**
     * Calcula el total del carrito.
     * @return El total del carrito
     * @throws CarritoVacioExcepcion Si el carrito está vacío
     */
    public Double calcularTotal() throws CarritoVacioExcepcion {
        if (productos.isEmpty()) {
            throw new CarritoVacioExcepcion(
                "No se puede calcular el total de un carrito vacío", 
                this.id, 
                this.usuario != null ? this.usuario.getId() : null
            );
        }
        
        return productos.stream()
                .mapToDouble(ProductoInterface::getPrecio)
                .sum();
    }

    /**
     * Obtiene la cantidad total de productos en el carrito.
     * @return Cantidad total de productos
     */
    public int obtenerCantidadTotalProductos() {
        return productos.size();
    }

    /**
     * Verifica si el carrito está vacío.
     * @return true si el carrito está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        return productos.isEmpty();
    }

    /**
     * Limpia todos los productos del carrito.
     */
    public void limpiar() {
        productos.clear();
    }

    /**
     * Verifica si un producto específico está en el carrito.
     * @param idProducto ID del producto a buscar
     * @return true si el producto está en el carrito, false en caso contrario
     */
    public boolean contieneProducto(String idProducto) {
        if (idProducto == null || idProducto.trim().isEmpty()) {
            return false;
        }
        
        return productos.stream()
                .anyMatch(producto -> idProducto.equals(producto.getId()));
    }

}
