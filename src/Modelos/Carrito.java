package Modelos;

import Modelos.Usuario.UsuarioCliente;
import Modelos.Producto.ProductoInterface;
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

    public void agregarProducto(ProductoInterface producto) {
        productos.add(producto);
    }

    public void eliminarProducto(ProductoInterface producto) {
        productos.remove(producto);
    }

}
