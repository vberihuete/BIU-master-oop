import java.util.ArrayList;
import java.util.List;

import Modelos.Producto.ProductoInterface;
import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoFisico;
import Modelos.Inventario.GestorInventario;
import Modelos.Inventario.GestorInventarioDigital;
import Modelos.Inventario.GestorInventarioFisico;
import Modelos.Pago.ProcesoPago;
import Modelos.Pago.PagoTarjeta;
import Modelos.Pago.PagoPayPal;
import Factory.FabricaEntidadesInterface;
import Factory.FabricaEntidades;
import Modelos.Usuario.UsuarioCliente;
import Configuracion.ConfiguracionSistema;
import Configuracion.ApplicationTheme;
import Observer.NotificationManager;
import Observer.TipoEvento;
import Excepciones.InventarioInsuficienteExcepcion;
import Excepciones.ProductoNoEncontradoExcepcion;
import Excepciones.PagoFallidoExcepcion;

public class Main {
    private static final FabricaEntidadesInterface fabricaEntidades = new FabricaEntidades();

    public static void main(String[] args) {
// Demostración de gestión de inventario con clases abstractas
        demostrarGestionInventario();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Demostración de procesos de pago con interfaces
        demostrarProcesosPago();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Demostración de creación de usuarios
        demostrarCreacionUsuarios();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Demostración de Configuración del sistema
        demostrarConfiguracionSistema();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Demostración del patrón Observer para notificaciones
        demostrarSistemaNotificaciones();

        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }

    /**
     * Demuestra el uso de clases abstractas para gestión de inventario
     */
    private static void demostrarGestionInventario() {
        try {
        System.out.println("1. GESTIÓN DE INVENTARIO - Clases Abstractas");
        System.out.println("=".repeat(50));

        // Crear gestores de inventario
        GestorInventario gestorDigital = new GestorInventarioDigital(
                "Inventario Digital", "Servidor Cloud", "cloud-server-01"
        );

        GestorInventario gestorFisico = new GestorInventarioFisico(
                "Almacén Principal", "Santo Domingo, Republica Dominicana", 1000.0, 500.0
        );

        // Crear productos de prueba
        ProductoDigital productoDigital1 = (ProductoDigital) fabricaEntidades.crearProducto(
                "DIG001", "Software Premium", 299.99, 100, "EXE", "https://download.com/software.exe"
        );

        ProductoDigital productoDigital2 = (ProductoDigital) fabricaEntidades.crearProducto(
                "DIG002", "Curso Online", 149.99, 50, "PDF", "https://academy.com/curso.pdf"
        );

        ProductoFisico productoFisico1 = (ProductoFisico) fabricaEntidades.crearProducto(
                "FIS001", "Laptop Gaming", 1599.99, 25, 2.5, 0.3, 0.4, 0.05
        );

        ProductoFisico productoFisico2 = (ProductoFisico) fabricaEntidades.crearProducto(
                "FIS002", "Mouse Inalámbrico", 49.99, 100, 0.1, 0.12, 0.06, 0.04
        );

        // Probar gestión de inventario digital
        System.out.println("\n--- Inventario Digital ---");
        gestorDigital.añadirProducto(productoDigital1);
        gestorDigital.añadirProducto(productoDigital2);
        gestorDigital.actualizarStock("DIG001", 150);

        // Verificar disponibilidad de URL (método específico de inventario digital)
        GestorInventarioDigital gestorDigitalCast = (GestorInventarioDigital) gestorDigital;
        gestorDigitalCast.verificarDisponibilidadUrl("DIG001");

        // Probar gestión de inventario físico
        System.out.println("\n--- Inventario Físico ---");
        gestorFisico.añadirProducto(productoFisico1);
        gestorFisico.añadirProducto(productoFisico2);
        gestorFisico.actualizarStock("FIS001", 30);

        // Mostrar estado del almacén (método específico de inventario físico)
        GestorInventarioFisico gestorFisicoCast = (GestorInventarioFisico) gestorFisico;
        System.out.println("Estado del almacén: " + gestorFisicoCast.obtenerEstadoAlmacen());

        // Intentar añadir producto que excede la capacidad
        ProductoFisico productoGrande = new ProductoFisico(
                "FIS003", "Refrigerador", 899.99, 1, 500.0, 2.0, 1.0, 0.8
        );
        gestorFisico.añadirProducto(productoGrande);

        // Listar productos en ambos inventarios
        System.out.println("\n--- Productos en Inventario Digital ---");
        for (ProductoInterface p : gestorDigital.obtenerProductos()) {
            System.out.println("- " + p.getNombre() + " (Stock: " + p.getCantidad() + ")");
        }

        System.out.println("\n--- Productos en Inventario Físico ---");
        for (ProductoInterface p : gestorFisico.obtenerProductos()) {
            System.out.println("- " + p.getNombre() + " (Stock: " + p.getCantidad() + ")");
        }
        } catch (InventarioInsuficienteExcepcion e) {
            System.out.println("Error de inventario: " + e.getMessage());
        } catch (ProductoNoEncontradoExcepcion e) {
            System.out.println("Error de producto: " + e.getMessage());
        }
    }

    /**
     * Demuestra el uso de interfaces para procesos de pago
     */
    private static void demostrarProcesosPago() {
        System.out.println("2. PROCESOS DE PAGO - Interfaces");
        System.out.println("=".repeat(50));

        // Crear diferentes métodos de pago
        ProcesoPago pagoTarjeta = new PagoTarjeta(
                "1234567890123456", "123", "12/25", "Juan Pérez"
        );

        ProcesoPago pagoPayPal = new PagoPayPal("usuario@paypal.com");

        // Probar pago con tarjeta
        System.out.println("\n--- Pago con Tarjeta ---");
        procesarPago(pagoTarjeta, 299.99, "USD");

        System.out.println("\n--- Pago con PayPal ---");
        procesarPago(pagoPayPal, 149.99, "USD");

        // Demostrar polimorfismo con lista de métodos de pago
        System.out.println("\n--- Demostración de Polimorfismo ---");
        List<ProcesoPago> metodosPago = new ArrayList<>();
        metodosPago.add(pagoTarjeta);
        metodosPago.add(pagoPayPal);

        for (ProcesoPago metodo : metodosPago) {
            System.out.println("Método: " + metodo.obtenerDetallesMetodoPago());
            System.out.println("Estado: " + metodo.obtenerEstadoPago(metodo.getIdTransaccion()));
            System.out.println();
        }
    }

    /**
     * Procesa un pago completo usando la interfaz ProcesoPago
     */
    private static void procesarPago(ProcesoPago procesoPago, Double monto, String moneda) {
        try {
        // Paso 1: Iniciar pago
        if (procesoPago.iniciarPago(monto, moneda, "Datos de prueba")) {
            System.out.println("✓ Pago iniciado exitosamente");

            // Paso 2: Verificar pago
            if (procesoPago.verificarPago(procesoPago.getIdTransaccion())) {
                System.out.println("✓ Pago verificado exitosamente");

                // Paso 3: Confirmar pago
                if (procesoPago.confirmarPago(procesoPago.getIdTransaccion())) {
                    System.out.println("✓ Pago confirmado exitosamente");
                    System.out.println("  - Transacción: " + procesoPago.getIdTransaccion());
                    System.out.println("  - Monto: " + procesoPago.getMonto() + " " + procesoPago.getMoneda());
                } else {
                    System.out.println("✗ Error al confirmar el pago");
                }
            } else {
                System.out.println("✗ Error al verificar el pago");
            }
        } else {
            System.out.println("✗ Error al iniciar el pago");
        }
        } catch (PagoFallidoExcepcion e) {
            System.out.println("✗ Error en el proceso de pago: " + e.getMessage());
        }
    }

    private static void demostrarCreacionUsuarios() {
        System.out.println("3. CREACIÓN DE USUARIOS - Interfaces");
        System.out.println("=".repeat(50));

        UsuarioCliente usuarioCliente = (UsuarioCliente) fabricaEntidades.crearCliente("CLI001", "Juan Pérez", "juan.perez@gmail.com", "123456");
        System.out.println("Usuario cliente creado: " + usuarioCliente.getNombre());
    }

    private static void demostrarConfiguracionSistema() {
        System.out.println("4. CONFIGURACIÓN DEL SISTEMA - Interfaces");
        System.out.println("=".repeat(50));

        ConfiguracionSistema configuracionSistema = ConfiguracionSistema.getInstancia();
        configuracionSistema.setDatabaseUrl("jdbc:mysql://localhost:3306/mydatabase");
        configuracionSistema.setCdnURL("https://cdn.com");
        configuracionSistema.setTheme(ApplicationTheme.LIGHT);

        System.out.println("Configuración del sistema:");
        System.out.println("Database URL: " + configuracionSistema.getDatabaseUrl());
        System.out.println("CDN URL: " + configuracionSistema.getCdnURL());
        System.out.println("Theme: " + configuracionSistema.getTheme());
    }

    /**
     * Demuestra el uso del patrón Observer para notificaciones
     */
    private static void demostrarSistemaNotificaciones() {
        System.out.println("5. SISTEMA DE NOTIFICACIONES - Patrón Observer");
        System.out.println("=".repeat(50));
        
        // Obtener la instancia del NotificationManager
        NotificationManager notificador = NotificationManager.getInstancia();
        
        // Configurar observadores usando closures (lambdas)
        configurarObservadores(notificador);
        
        // Simular diferentes eventos del sistema
        simularEventosSistema(notificador);
        
        // Mostrar estado del sistema
        System.out.println(notificador.obtenerEstadoSistema());
    }
    
    /**
     * Configura diferentes observadores para eventos específicos
     */
    private static void configurarObservadores(NotificationManager notificador) {
        System.out.println("\n--- Configurando Observadores ---");
        
        // Observador para eventos de orden
        notificador.registrarObserver(TipoEvento.ORDEN_CREADA, (datos) -> {
            System.out.println("🛒 [UI] Nueva orden creada - Mostrando notificación al usuario");
            if (datos != null) {
                System.out.println("   📋 Detalles: " + datos);
            }
        });
        
        notificador.registrarObserver(TipoEvento.ORDEN_PAGADA, (_) -> {
            System.out.println("💳 [PAGO] Orden pagada - Procesando pago");
            System.out.println("   ✅ Pago confirmado y procesado");
        });
        
        // Observador para eventos de inventario
        notificador.registrarObserver(TipoEvento.STOCK_BAJO, (_) -> {
            System.out.println("⚠️ [INVENTARIO] Stock bajo detectado");
            System.out.println("   📦 Generando orden de reabastecimiento");
        });
        
        notificador.registrarObserver(TipoEvento.STOCK_AGOTADO, (_) -> {
            System.out.println("🚨 [INVENTARIO] Producto agotado");
            System.out.println("   📞 Notificando al proveedor urgentemente");
        });
        
        // Observador para eventos de pago
        notificador.registrarObserver(TipoEvento.PAGO_EXITOSO, (_) -> {
            System.out.println("✅ [SISTEMA] Pago exitoso");
            System.out.println("   📧 Enviando confirmación por email");
        });
        
        notificador.registrarObserver(TipoEvento.PAGO_FALLIDO, (_) -> {
            System.out.println("❌ [SISTEMA] Pago fallido");
            System.out.println("   🔄 Reintentando proceso de pago");
        });
        
        // Observador para eventos de usuario
        notificador.registrarObserver(TipoEvento.USUARIO_REGISTRADO, (_) -> {
            System.out.println("👤 [USUARIO] Nuevo usuario registrado");
            System.out.println("   🎉 Enviando email de bienvenida");
        });
        
        // Observador múltiple para eventos de sistema
        notificador.registrarObserver(TipoEvento.SISTEMA_ERROR, (_) -> {
            System.out.println("🔴 [SISTEMA] Error detectado");
            System.out.println("   📝 Registrando en log de errores");
        });
        
        notificador.registrarObserver(TipoEvento.SISTEMA_MANTENIMIENTO, (_) -> {
            System.out.println("🔧 [SISTEMA] Mantenimiento programado");
            System.out.println("   📢 Notificando a todos los usuarios");
        });
    }
    
    /**
     * Simula diferentes eventos del sistema para demostrar las notificaciones
     */
    private static void simularEventosSistema(NotificationManager notificador) {
        System.out.println("\n--- Simulando Eventos del Sistema ---");
        
        // Simular creación de orden
        notificador.notificarEvento(TipoEvento.ORDEN_CREADA, "Orden #12345 - Cliente: Juan Pérez - Total: $299.99");
        
        // Simular pago exitoso
        notificador.notificarEvento(TipoEvento.PAGO_EXITOSO, "Transacción #TXN789 - Monto: $299.99");
        
        // Simular stock bajo
        notificador.notificarEvento(TipoEvento.STOCK_BAJO, "Producto: LAPTOP001 - Stock actual: 5 - Mínimo: 10");
        
        // Simular registro de usuario
        notificador.notificarEvento(TipoEvento.USUARIO_REGISTRADO, "Usuario: maria.garcia@email.com");
        
        // Simular error del sistema
        notificador.notificarEvento(TipoEvento.SISTEMA_ERROR, "Error de conexión a base de datos");
        
        // Simular stock agotado
        notificador.notificarEvento(TipoEvento.STOCK_AGOTADO, "Producto: MOUSE001 - Mouse Inalámbrico");
        
        // Simular pago fallido
        notificador.notificarEvento(TipoEvento.PAGO_FALLIDO, "Tarjeta declinada - Código: 51");
        
        // Simular mantenimiento del sistema
        notificador.notificarEvento(TipoEvento.SISTEMA_MANTENIMIENTO, "Mantenimiento programado para las 2:00 AM");
        
        // Demostrar evento sin datos
        notificador.notificarEvento(TipoEvento.ORDEN_ENVIADA);
    }
}