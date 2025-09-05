package Modelos.Orden;

import Modelos.Producto.ProductoInterface;

public class OrdenDetalle {
    private String id;
    private Orden orden;
    private ProductoInterface producto;
    private Integer cantidad;

    public OrdenDetalle(String id, Orden orden, ProductoInterface producto, Integer cantidad) {
        this.id = id;
        this.orden = orden;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public Orden getOrden() {
        return orden;
    }

    public ProductoInterface getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public void setProducto(ProductoInterface producto) {
        this.producto = producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
