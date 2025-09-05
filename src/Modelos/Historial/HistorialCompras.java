package Modelos.Historial;

import Modelos.Usuario.UsuarioCliente;
import Modelos.Orden.Orden;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class HistorialCompras {
    private String id;
    private UsuarioCliente usuario;
    private List<Orden> ordenes;

    public HistorialCompras(String id, UsuarioCliente usuario, List<Orden> ordenes) {
        this.id = id;
        this.usuario = usuario;
        this.ordenes = ordenes;
    }

    public HistorialCompras(UsuarioCliente usuario) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.ordenes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public UsuarioCliente getUsuario() {
        return usuario;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(UsuarioCliente usuario) {
        this.usuario = usuario;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }
}
