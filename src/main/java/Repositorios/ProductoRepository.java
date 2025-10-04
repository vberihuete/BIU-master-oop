package Repositorios;

import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoFisico;
import Modelos.Producto.ProductoInterface;
import Repositorios.ProductoRepositoryInterface;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductoRepository implements ProductoRepositoryInterface {
    @Override
    public ProductoInterface obtenerProducto(String id) {
        // TODO: Implementar
        return new ProductoDigital(id, "producto 1", 1234.2, 200, "jpg", null);
    }

    @Override
    public List<ProductoInterface> obtenerProductos() {
        // TODO: Implementar con data real conectada a la base de datos
        
        return new ArrayList<>(Arrays.asList(
            new ProductoDigital("1", "producto 1", 1234.2, 200, "jpg", null),
            new ProductoDigital("2", "producto 2", 444.2, 100, "zip", null),
            new ProductoFisico("3", "producto 3", 1234.2, 200, 100.2, 100.2, 100.2, 100.2)
        ));
    }

    @Override
    public List<ProductoInterface> obtenerProductosPorPrecio(Double precio) {
        // TODO: Implementar
        return new ArrayList<>(Arrays.asList(
            new ProductoDigital("1", "producto 1", 1234.2, 200, "jpg", null),
            new ProductoDigital("2", "producto 2", 444.2, 100, "zip", null),
            new ProductoFisico("3", "producto 3", 1234.2, 200, 100.2, 100.2, 100.2, 100.2)
        ));
    }
    
    
    @Override
    public List<ProductoInterface> obtenerProductosPorNombre(String nombre) {
        // TODO: Implementar con data real conectada a la base de datos
        return new ArrayList<>(Arrays.asList(
            new ProductoDigital("1", "producto 1", 1234.2, 200, "jpg", null),
            new ProductoDigital("2", "producto 2", 444.2, 100, "zip", null),
            new ProductoFisico("3", "producto 3", 1234.2, 200, 100.2, 100.2, 100.2, 100.2)
        ));
    }

    @Override
    public void agregarProducto(ProductoDigital producto) {
        // TODO: Implementar con data real conectada a la base de datos
        System.out.println("Agregar producto digital: " + producto);
    }

    @Override
    public void agregarProducto(ProductoFisico producto) {
        // TODO: Implementar
        System.out.println("Agregar producto fisico: " + producto);
    }

    @Override
    public void eliminarProducto(String id) {
        // TODO: Implementar
        System.out.println("Eliminar producto: " + id);
    }

    @Override
    public void actualizarProducto(ProductoDigital producto) {
        // TODO: Implementar
        System.out.println("Actualizar producto digital: " + producto);
    }

    @Override
    public void actualizarProducto(ProductoFisico producto) {
        // TODO: Implementar
        System.out.println("Actualizar producto fisico: " + producto);
    }
}
