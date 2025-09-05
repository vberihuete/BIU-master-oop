package Modelos.Orden;

import Modelos.Usuario.UsuarioCliente;
import java.util.Date;

public class Orden {
    private String id;
    private UsuarioCliente usuario;
    private Date fecha;
    private Double total;

    public Orden(String id, UsuarioCliente usuario, Date fecha, Double total) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
    }

    public String getId() {
        return id;
    }
    
    public UsuarioCliente getUsuario() {
        return usuario;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public Double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setUsuario(UsuarioCliente usuario) {
        this.usuario = usuario;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
}
