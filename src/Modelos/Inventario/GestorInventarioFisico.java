package Modelos.Inventario;

import Modelos.Producto.ProductoInterface;
import Modelos.Producto.ProductoFisico;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de GestorInventario para productos físicos.
 * Maneja el inventario de productos físicos con control de espacio y peso.
 */
public class GestorInventarioFisico extends GestorInventario {
    private List<ProductoInterface> productosFisicos;
    private Double capacidadMaximaPeso; // en kg
    private Double espacioDisponible; // en metros cúbicos
    private Double espacioOcupado;
    private Double pesoActual;
    
    public GestorInventarioFisico(String nombreInventario, String ubicacion, 
                                 Double capacidadMaximaPeso, Double espacioDisponible) {
        super(nombreInventario, ubicacion);
        this.capacidadMaximaPeso = capacidadMaximaPeso;
        this.espacioDisponible = espacioDisponible;
        this.espacioOcupado = 0.0;
        this.pesoActual = 0.0;
        this.productosFisicos = new ArrayList<>();
    }
    
    @Override
    public boolean añadirProducto(ProductoInterface producto) {
        if (producto instanceof ProductoFisico) {
            ProductoFisico productoFisico = (ProductoFisico) producto;
            
            // Calcular peso y espacio del producto
            Double pesoProducto = productoFisico.getPeso() * producto.getCantidad();
            Double espacioProducto = calcularEspacioProducto(productoFisico) * producto.getCantidad();
            
            // Verificar si hay espacio y peso suficiente
            if (pesoActual + pesoProducto <= capacidadMaximaPeso && 
                espacioOcupado + espacioProducto <= espacioDisponible) {
                
                productosFisicos.add(producto);
                pesoActual += pesoProducto;
                espacioOcupado += espacioProducto;
                
                System.out.println("Producto físico añadido: " + producto.getNombre() + 
                                 " - Peso total: " + pesoActual + "kg/" + capacidadMaximaPeso + "kg" +
                                 " - Espacio: " + espacioOcupado + "m³/" + espacioDisponible + "m³");
                return true;
            } else {
                System.out.println("Error: No hay suficiente espacio o capacidad de peso para " + 
                                 producto.getNombre());
                return false;
            }
        } else {
            System.out.println("Error: Solo se pueden añadir productos físicos a este inventario");
            return false;
        }
    }
    
    @Override
    public boolean eliminarProducto(String idProducto) {
        for (int i = 0; i < productosFisicos.size(); i++) {
            if (productosFisicos.get(i).getId().equals(idProducto)) {
                ProductoInterface producto = productosFisicos.remove(i);
                if (producto instanceof ProductoFisico) {
                    ProductoFisico productoFisico = (ProductoFisico) producto;
                    Double pesoProducto = productoFisico.getPeso() * producto.getCantidad();
                    Double espacioProducto = calcularEspacioProducto(productoFisico) * producto.getCantidad();
                    
                    pesoActual -= pesoProducto;
                    espacioOcupado -= espacioProducto;
                }
                System.out.println("Producto físico eliminado: " + producto.getNombre());
                return true;
            }
        }
        System.out.println("Producto físico no encontrado con ID: " + idProducto);
        return false;
    }
    
    @Override
    public boolean actualizarStock(String idProducto, Integer nuevaCantidad) {
        for (ProductoInterface producto : productosFisicos) {
            if (producto.getId().equals(idProducto)) {
                if (producto instanceof ProductoFisico) {
                    ProductoFisico productoFisico = (ProductoFisico) producto;
                    Integer cantidadAnterior = producto.getCantidad();
                    
                    // Calcular diferencia de peso y espacio
                    Double diferenciaPeso = productoFisico.getPeso() * (nuevaCantidad - cantidadAnterior);
                    Double diferenciaEspacio = calcularEspacioProducto(productoFisico) * (nuevaCantidad - cantidadAnterior);
                    
                    // Verificar si la nueva cantidad cabe en el almacén
                    if (pesoActual + diferenciaPeso <= capacidadMaximaPeso && 
                        espacioOcupado + diferenciaEspacio <= espacioDisponible) {
                        
                        producto.setCantidad(nuevaCantidad);
                        pesoActual += diferenciaPeso;
                        espacioOcupado += diferenciaEspacio;
                        
                        System.out.println("Stock actualizado para producto físico " + producto.getNombre() + 
                                         " a: " + nuevaCantidad + " unidades");
                        return true;
                    } else {
                        System.out.println("Error: No hay suficiente espacio para actualizar el stock de " + 
                                         producto.getNombre());
                        return false;
                    }
                }
            }
        }
        System.out.println("Producto físico no encontrado para actualizar stock: " + idProducto);
        return false;
    }
    
    @Override
    public List<ProductoInterface> obtenerProductos() {
        return new ArrayList<>(productosFisicos);
    }
    
    @Override
    public ProductoInterface buscarProducto(String idProducto) {
        for (ProductoInterface producto : productosFisicos) {
            if (producto.getId().equals(idProducto)) {
                return producto;
            }
        }
        return null;
    }
    
    /**
     * Método específico para productos físicos: calcular el espacio ocupado por un producto
     * @param productoFisico El producto físico
     * @return El espacio en metros cúbicos
     */
    private Double calcularEspacioProducto(ProductoFisico productoFisico) {
        return productoFisico.getAltura() * productoFisico.getAncho() * productoFisico.getProfundidad();
    }
    
    /**
     * Obtener información del estado del almacén
     * @return String con la información del estado
     */
    public String obtenerEstadoAlmacen() {
        return String.format("Almacén: %s - Peso: %.2f/%.2f kg - Espacio: %.2f/%.2f m³ - Productos: %d",
                           nombreInventario, pesoActual, capacidadMaximaPeso, 
                           espacioOcupado, espacioDisponible, productosFisicos.size());
    }
    
    /**
     * Verificar si hay espacio disponible para un nuevo producto
     * @param peso Peso del producto
     * @param espacio Espacio del producto
     * @return true si hay espacio disponible
     */
    public boolean verificarEspacioDisponible(Double peso, Double espacio) {
        return pesoActual + peso <= capacidadMaximaPeso && 
               espacioOcupado + espacio <= espacioDisponible;
    }
    
    // Getters y Setters
    public Double getCapacidadMaximaPeso() {
        return capacidadMaximaPeso;
    }
    
    public Double getEspacioDisponible() {
        return espacioDisponible;
    }
    
    public Double getEspacioOcupado() {
        return espacioOcupado;
    }
    
    public Double getPesoActual() {
        return pesoActual;
    }
}
