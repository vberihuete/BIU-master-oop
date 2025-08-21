package Modelos;

public class Producto {
    private String id;
    private String titulo;
    private String descripcion;
    private Double precio;
    
    // Constructor
    public Producto() {
    }
    
    public Producto(String id, String titulo, String descripcion, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
