package Modelos.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioPreferencias {

    public enum TipoUsuarioPreferencias {
        MENU_FAVORITOS,
        HORARIO,
        TEMA
    }

    private Map<TipoUsuarioPreferencias, String> preferencias;

    public UsuarioPreferencias() {
        this.preferencias = new HashMap<>();
    }

    public Map<TipoUsuarioPreferencias, String> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Map<TipoUsuarioPreferencias, String> preferencias) {
        this.preferencias = preferencias;
    }
}