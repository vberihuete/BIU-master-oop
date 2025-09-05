package Modelos.Producto;

public class ProductoDigital implements ProductoInterface {
    private String id;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String formato;
    private String url;

    public ProductoDigital(String id, String nombre, Double precio, Integer cantidad, String formato, String url) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.formato = formato;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Double getPrecio() {
        return precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getFormato() {
        return formato;
    }

    public String getUrl() {
        return url;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
