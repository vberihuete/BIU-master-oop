package Repositorios;

import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoFisico;
import Modelos.Producto.ProductoInterface;
import java.util.List;

public interface ProductoRepositoryInterface {
    ProductoInterface obtenerProducto(String id);
    List<ProductoInterface> obtenerProductos();
    List<ProductoInterface> obtenerProductosPorPrecio(Double precio);
    List<ProductoInterface> obtenerProductosPorNombre(String nombre);
    void agregarProducto(ProductoDigital producto);
    void agregarProducto(ProductoFisico producto);
    void eliminarProducto(String id);
    void actualizarProducto(ProductoDigital producto);
    void actualizarProducto(ProductoFisico producto);
}
