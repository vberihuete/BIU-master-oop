package Modelos.Inventario;

import Modelos.Producto.ProductoInterface;
import Modelos.Producto.ProductoDigital;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de GestorInventario para productos digitales.
 * Maneja el inventario de productos digitales como archivos, software, etc.
 */
public class GestorInventarioDigital extends GestorInventario {
    private List<ProductoInterface> productosDigitales;
    private String servidorAlmacenamiento;
    
    public GestorInventarioDigital(String nombreInventario, String ubicacion, String servidorAlmacenamiento) {
        super(nombreInventario, ubicacion);
        this.servidorAlmacenamiento = servidorAlmacenamiento;
        this.productosDigitales = new ArrayList<>();
    }
    
    @Override
    public boolean añadirProducto(ProductoInterface producto) {
        if (producto instanceof ProductoDigital) {
            // Verificar que el producto digital tenga URL válida
            ProductoDigital productoDigital = (ProductoDigital) producto;
            if (productoDigital.getUrl() != null && !productoDigital.getUrl().isEmpty()) {
                productosDigitales.add(producto);
                System.out.println("Producto digital añadido: " + producto.getNombre() + 
                                 " al servidor: " + servidorAlmacenamiento);
                return true;
            } else {
                System.out.println("Error: El producto digital debe tener una URL válida");
                return false;
            }
        } else {
            System.out.println("Error: Solo se pueden añadir productos digitales a este inventario");
            return false;
        }
    }
    
    @Override
    public boolean eliminarProducto(String idProducto) {
        for (int i = 0; i < productosDigitales.size(); i++) {
            if (productosDigitales.get(i).getId().equals(idProducto)) {
                ProductoInterface productoEliminado = productosDigitales.remove(i);
                System.out.println("Producto digital eliminado: " + productoEliminado.getNombre());
                return true;
            }
        }
        System.out.println("Producto digital no encontrado con ID: " + idProducto);
        return false;
    }
    
    @Override
    public boolean actualizarStock(String idProducto, Integer nuevaCantidad) {
        for (ProductoInterface producto : productosDigitales) {
            if (producto.getId().equals(idProducto)) {
                producto.setCantidad(nuevaCantidad);
                System.out.println("Stock actualizado para producto digital " + producto.getNombre() + 
                                 " a: " + nuevaCantidad + " licencias");
                return true;
            }
        }
        System.out.println("Producto digital no encontrado para actualizar stock: " + idProducto);
        return false;
    }
    
    @Override
    public List<ProductoInterface> obtenerProductos() {
        return new ArrayList<>(productosDigitales);
    }
    
    @Override
    public ProductoInterface buscarProducto(String idProducto) {
        for (ProductoInterface producto : productosDigitales) {
            if (producto.getId().equals(idProducto)) {
                return producto;
            }
        }
        return null;
    }
    
    /**
     * Método específico para productos digitales: verificar disponibilidad de URL
     * @param idProducto El ID del producto a verificar
     * @return true si la URL está disponible, false en caso contrario
     */
    public boolean verificarDisponibilidadUrl(String idProducto) {
        ProductoInterface producto = buscarProducto(idProducto);
        if (producto instanceof ProductoDigital) {
            ProductoDigital productoDigital = (ProductoDigital) producto;
            // Simulación de verificación de URL
            boolean urlDisponible = productoDigital.getUrl() != null && 
                                  !productoDigital.getUrl().isEmpty() && 
                                  productoDigital.getCantidad() > 0;
            System.out.println("Verificación de URL para " + producto.getNombre() + 
                             ": " + (urlDisponible ? "Disponible" : "No disponible"));
            return urlDisponible;
        }
        return false;
    }
    
    /**
     * Obtener el servidor de almacenamiento
     * @return El servidor de almacenamiento
     */
    public String getServidorAlmacenamiento() {
        return servidorAlmacenamiento;
    }
    
    /**
     * Establecer el servidor de almacenamiento
     * @param servidorAlmacenamiento El nuevo servidor de almacenamiento
     */
    public void setServidorAlmacenamiento(String servidorAlmacenamiento) {
        this.servidorAlmacenamiento = servidorAlmacenamiento;
    }
}
