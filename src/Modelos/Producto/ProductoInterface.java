package Modelos.Producto;

public interface ProductoInterface {
    public String getId();
    public String getNombre();
    public Double getPrecio();
    public Integer getCantidad();
    public void setId(String id);
    public void setNombre(String nombre);
    public void setPrecio(Double precio);
    public void setCantidad(Integer cantidad);
}