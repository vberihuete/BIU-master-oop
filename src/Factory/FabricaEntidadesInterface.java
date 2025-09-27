package Factory;

import Modelos.Producto.ProductoInterface;
import Modelos.Usuario.UsuarioInterface;

public interface FabricaEntidadesInterface {
    ProductoInterface crearProducto(String id, String nombre, Double precio, Integer cantidad, String formato, String url);
    ProductoInterface crearProducto(String id, String nombre, Double precio, Integer cantidad, Double peso, Double altura, Double ancho, Double profundidad);
    UsuarioInterface crearCliente(String id, String nombre, String email, String password);
    UsuarioInterface crearAdministrador(String id, String nombre, String email, String password);
}