package Modelos.Producto;

public interface ProductoInterface {
    String getId();
    String getNombre();
    Double getPrecio();
    Integer getCantidad();
    void setId(String id);
    void setNombre(String nombre);
    void setPrecio(Double precio);
    void setCantidad(Integer cantidad);
}