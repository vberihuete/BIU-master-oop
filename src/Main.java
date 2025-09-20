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

public class Main {
    public static void main(String[] args) {
// Demostración de gestión de inventario con clases abstractas
        demostrarGestionInventario();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demostración de procesos de pago con interfaces
        demostrarProcesosPago();
        
        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }
    
    /**
     * Demuestra el uso de clases abstractas para gestión de inventario
     */
    private static void demostrarGestionInventario() {
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
        ProductoDigital productoDigital1 = new ProductoDigital(
            "DIG001", "Software Premium", 299.99, 100, "EXE", "https://download.com/software.exe"
        );
        
        ProductoDigital productoDigital2 = new ProductoDigital(
            "DIG002", "Curso Online", 149.99, 50, "PDF", "https://academy.com/curso.pdf"
        );
        
        ProductoFisico productoFisico1 = new ProductoFisico(
            "FIS001", "Laptop Gaming", 1599.99, 25, 2.5, 0.3, 0.4, 0.05
        );
        
        ProductoFisico productoFisico2 = new ProductoFisico(
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
    }
}