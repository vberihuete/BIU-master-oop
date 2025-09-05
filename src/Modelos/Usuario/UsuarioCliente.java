package Modelos.Usuario;

import Modelos.Historial.HistorialCompras;

public class UsuarioCliente implements UsuarioInterface {
    private String id;
    private String nombre;
    private String email;
    private String password;
    private UsuarioPreferencias preferencias;
    private HistorialCompras historialCompras;

    // Constructor
    public UsuarioCliente(
            String id,
            String nombre,
            String email,
            String password,
            UsuarioPreferencias preferencias,
            HistorialCompras historialCompras
    ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.preferencias = preferencias;
        this.historialCompras = historialCompras;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UsuarioPreferencias getPreferencias() {
        return preferencias;
    }

    public HistorialCompras getHistorialCompras() {
        return historialCompras;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPreferencias(UsuarioPreferencias preferencias) {
        this.preferencias = preferencias;
    }

    public void setHistorialCompras(HistorialCompras historialCompras) {
        this.historialCompras = historialCompras;
    }
}
