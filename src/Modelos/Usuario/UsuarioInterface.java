package Modelos.Usuario;

public interface UsuarioInterface {
    public String getId();
    public String getNombre();
    public String getEmail();
    public String getPassword();
    public void setId(String id);
    public void setNombre(String nombre);
    public void setEmail(String email);
    public void setPassword(String password);
}