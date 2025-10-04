package Modelos.Usuario;

public interface UsuarioInterface {
    String getId();
    String getNombre();
    String getEmail();
    String getPassword();
    void setId(String id);
    void setNombre(String nombre);
    void setEmail(String email);
    void setPassword(String password);
}