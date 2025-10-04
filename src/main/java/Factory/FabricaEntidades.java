package Factory;

import Modelos.Producto.ProductoInterface;
import Modelos.Usuario.UsuarioInterface;
import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoFisico;
import Modelos.Usuario.UsuarioCliente;
import Modelos.Usuario.UsuarioAdministrador;

public class FabricaEntidades implements FabricaEntidadesInterface {
    @Override
    public ProductoInterface crearProducto(String id, String nombre, Double precio, Integer cantidad, String formato, String url) {
        return new ProductoDigital(id, nombre, precio, cantidad, formato, url);
    }

    @Override
    public ProductoInterface crearProducto(String id, String nombre, Double precio, Integer cantidad, Double peso, Double altura, Double ancho, Double profundidad) {
        return new ProductoFisico(id, nombre, precio, cantidad, peso, altura, ancho, profundidad);
    }
    @Override
    public UsuarioInterface crearCliente(String id, String nombre, String email, String password) {
        return new UsuarioCliente(id, nombre, email, password, null, null);
    }
    @Override
    public UsuarioInterface crearAdministrador(String id, String nombre, String email, String password) {
        return new UsuarioAdministrador(id, nombre, email, password);
    }
}
