package Modelos.Inventario;

import Modelos.Producto.ProductoInterface;
import Modelos.Producto.ProductoFisico;
import Excepciones.InventarioInsuficienteExcepcion;
import Excepciones.ProductoNoEncontradoExcepcion;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta de GestorInventario para productos físicos.
 * Maneja el inventario de productos físicos con control de espacio y peso.
 */
public class GestorInventarioFisico extends GestorInventario {
    private final List<ProductoInterface> productosFisicos;
    private final Double capacidadMaximaPeso; // en kg
    private final Double espacioDisponible; // en metros cúbicos
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
    public boolean añadirProducto(ProductoInterface producto) throws InventarioInsuficienteExcepcion {
        if (producto == null) {
            throw new IllegalArgumentException("No se puede añadir un producto nulo al inventario");
        }
        
        if (!(producto instanceof ProductoFisico productoFisico)) {
            throw new IllegalArgumentException("Solo se pueden añadir productos físicos a este inventario");
        }

        // Calcular peso y espacio del producto
        Double pesoProducto = productoFisico.getPeso() * producto.getCantidad();
        Double espacioProducto = calcularEspacioProducto(productoFisico) * producto.getCantidad();
        
        // Verificar si hay espacio y peso suficiente
        if (pesoActual + pesoProducto > capacidadMaximaPeso) {
            throw new InventarioInsuficienteExcepcion(
                "No hay suficiente capacidad de peso en el almacén para el producto " + producto.getNombre(),
                producto.getId(),
                (int)(capacidadMaximaPeso - pesoActual),
                (int)Math.ceil(pesoProducto)
            );
        }
        
        if (espacioOcupado + espacioProducto > espacioDisponible) {
            throw new InventarioInsuficienteExcepcion(
                "No hay suficiente espacio en el almacén para el producto " + producto.getNombre(),
                producto.getId(),
                (int)(espacioDisponible - espacioOcupado),
                (int)Math.ceil(espacioProducto)
            );
        }
        
        productosFisicos.add(producto);
        pesoActual += pesoProducto;
        espacioOcupado += espacioProducto;
        
        System.out.println("Producto físico añadido: " + producto.getNombre() + 
                         " - Peso total: " + pesoActual + "kg/" + capacidadMaximaPeso + "kg" +
                         " - Espacio: " + espacioOcupado + "m³/" + espacioDisponible + "m³");
        return true;
    }
    
    @Override
    public boolean eliminarProducto(String idProducto) throws ProductoNoEncontradoExcepcion {
        if (idProducto == null || idProducto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no puede ser nulo o vacío");
        }
        
        for (int i = 0; i < productosFisicos.size(); i++) {
            if (productosFisicos.get(i).getId().equals(idProducto)) {
                ProductoInterface producto = productosFisicos.remove(i);
                if (producto instanceof ProductoFisico productoFisico) {
                    Double pesoProducto = productoFisico.getPeso() * producto.getCantidad();
                    Double espacioProducto = calcularEspacioProducto(productoFisico) * producto.getCantidad();
                    
                    pesoActual -= pesoProducto;
                    espacioOcupado -= espacioProducto;
                }
                System.out.println("Producto físico eliminado: " + producto.getNombre());
                return true;
            }
        }
        
        throw new ProductoNoEncontradoExcepcion(
            "El producto físico con ID " + idProducto + " no se encuentra en el inventario",
            idProducto,
            this.nombreInventario
        );
    }
    
    @Override
    public boolean actualizarStock(String idProducto, Integer nuevaCantidad) throws ProductoNoEncontradoExcepcion, InventarioInsuficienteExcepcion {
        if (idProducto == null || idProducto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no puede ser nulo o vacío");
        }
        
        if (nuevaCantidad == null || nuevaCantidad < 0) {
            throw new IllegalArgumentException("La nueva cantidad debe ser un número positivo");
        }
        
        for (ProductoInterface producto : productosFisicos) {
            if (producto.getId().equals(idProducto)) {
                if (producto instanceof ProductoFisico productoFisico) {
                    Integer cantidadAnterior = producto.getCantidad();
                    
                    // Calcular diferencia de peso y espacio
                    Double diferenciaPeso = productoFisico.getPeso() * (nuevaCantidad - cantidadAnterior);
                    Double diferenciaEspacio = calcularEspacioProducto(productoFisico) * (nuevaCantidad - cantidadAnterior);
                    
                    // Verificar si la nueva cantidad cabe en el almacén
                    if (pesoActual + diferenciaPeso > capacidadMaximaPeso) {
                        throw new InventarioInsuficienteExcepcion(
                            "No hay suficiente capacidad de peso para actualizar el stock del producto " + producto.getNombre(),
                            producto.getId(),
                            (int)(capacidadMaximaPeso - pesoActual),
                            (int)Math.ceil(diferenciaPeso)
                        );
                    }
                    
                    if (espacioOcupado + diferenciaEspacio > espacioDisponible) {
                        throw new InventarioInsuficienteExcepcion(
                            "No hay suficiente espacio para actualizar el stock del producto " + producto.getNombre(),
                            producto.getId(),
                            (int)(espacioDisponible - espacioOcupado),
                            (int)Math.ceil(diferenciaEspacio)
                        );
                    }
                    
                    producto.setCantidad(nuevaCantidad);
                    pesoActual += diferenciaPeso;
                    espacioOcupado += diferenciaEspacio;
                    
                    System.out.println("Stock actualizado para producto físico " + producto.getNombre() + 
                                     " a: " + nuevaCantidad + " unidades");
                    return true;
                }
            }
        }
        
        throw new ProductoNoEncontradoExcepcion(
            "El producto físico con ID " + idProducto + " no se encuentra en el inventario para actualizar stock",
            idProducto,
            this.nombreInventario
        );
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
