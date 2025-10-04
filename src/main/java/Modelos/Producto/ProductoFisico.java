package Modelos.Producto;

public class ProductoFisico implements ProductoInterface {
    private String id;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private Double peso;
    private Double altura;
    private Double ancho;
    private Double profundidad;

    public ProductoFisico(String id, String nombre, Double precio, Integer cantidad, Double peso, Double altura, Double ancho, Double profundidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.peso = peso;
        this.altura = altura;
        this.ancho = ancho;
        this.profundidad = profundidad;
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
    
    public Double getPeso() {
        return peso;
    }
    
    public Double getAltura() {
        return altura;
    }
    
    public Double getAncho() {
        return ancho;
    }
    
    public Double getProfundidad() {
        return profundidad;
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
    
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
    public void setAltura(Double altura) {
        this.altura = altura;
    }
    
    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }
    
    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }
}
