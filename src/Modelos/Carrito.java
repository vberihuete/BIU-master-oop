package Modelos;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private String id;
    private Usuario usuario;
    private List<Producto> productos;

    // Constructor
    public Carrito(String id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.productos = new ArrayList<>();
    }

    public Carrito(String id, Usuario usuario, List<Producto> productos) {
        this.id = id;
        this.usuario = usuario;
        this.productos = productos;
    }

    // Getters

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

}
