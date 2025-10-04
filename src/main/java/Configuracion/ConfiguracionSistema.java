package Configuracion;

public class ConfiguracionSistema {
    private static ConfiguracionSistema instancia;
    private String databaseUrl;
    private String cdnURL;
    private ApplicationTheme theme;

    // Constructor privado para evitar instanciación externa
    private ConfiguracionSistema() {}

    // Método estático para obtener la instancia única (Singleton)
    public static ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSistema();
        }
        return instancia;
    }

    // Getter y Setter para databaseUrl
    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    // Getter y Setter para cdnURL
    public String getCdnURL() {
        return cdnURL;
    }

    public void setCdnURL(String cdnURL) {
        this.cdnURL = cdnURL;
    }

    // Getter y Setter para theme
    public ApplicationTheme getTheme() {
        return theme;
    }

    public void setTheme(ApplicationTheme theme) {
        this.theme = theme;
    }
}
