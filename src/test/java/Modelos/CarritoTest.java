package Modelos;

import Modelos.Usuario.UsuarioCliente;
import Modelos.Usuario.UsuarioPreferencias;
import Modelos.Historial.HistorialCompras;
import Modelos.Producto.ProductoFisico;
import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoInterface;
import Excepciones.CarritoVacioExcepcion;
import Excepciones.ProductoNoEncontradoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas unitarias para la clase Carrito.
 * Cubre casos de uso comunes, escenarios de error y manejo de excepciones.
 */
@DisplayName("Pruebas para la clase Carrito")
class CarritoTest {

    private Carrito carrito;
    private UsuarioCliente usuario;
    private ProductoFisico productoFisico;
    private ProductoDigital productoDigital;

    @BeforeEach
    void setUp() {
        // Crear usuario cliente
        UsuarioPreferencias preferencias = new UsuarioPreferencias();
        usuario = new UsuarioCliente("USR001", "Juan Pérez", "juan@email.com", "password123", preferencias, null);
        // Crear historial después de tener el usuario
        HistorialCompras historial = new HistorialCompras(usuario);
        usuario.setHistorialCompras(historial);
        
        // Crear carrito
        carrito = new Carrito("CART001", usuario);
        
        // Crear productos
        productoFisico = new ProductoFisico(
            "FIS001", "Laptop", 999.99, 5, 
            2.5, 0.3, 0.4, 0.05  // peso: 2.5kg, dimensiones: 0.3x0.4x0.05m
        );
        
        productoDigital = new ProductoDigital(
            "DIG001", "Software", 299.99, 10, "EXE", "https://download.com"
        );
    }

    @Nested
    @DisplayName("Creación del carrito")
    class CreacionCarrito {
        
        @Test
        @DisplayName("Debería crear un carrito correctamente")
        void testCrearCarrito() {
            assertNotNull(carrito);
            assertEquals("CART001", carrito.getId());
            assertEquals(usuario, carrito.getUsuario());
            assertTrue(carrito.getProductos().isEmpty());
            assertTrue(carrito.estaVacio());
        }

        @Test
        @DisplayName("Debería crear un carrito con productos iniciales")
        void testCrearCarritoConProductos() {
            List<ProductoInterface> productosIniciales = new ArrayList<>();
            productosIniciales.add(productoFisico);
            productosIniciales.add(productoDigital);
            
            Carrito carritoConProductos = new Carrito("CART002", usuario, productosIniciales);
            
            assertNotNull(carritoConProductos);
            assertEquals("CART002", carritoConProductos.getId());
            assertEquals(usuario, carritoConProductos.getUsuario());
            assertEquals(2, carritoConProductos.getProductos().size());
            assertFalse(carritoConProductos.estaVacio());
        }
    }

    @Nested
    @DisplayName("Operaciones de productos")
    class OperacionesProductos {
        
        @Test
        @DisplayName("Debería agregar un producto al carrito")
        void testAgregarProducto() {
            carrito.agregarProducto(productoFisico);
            
            assertEquals(1, carrito.getProductos().size());
            assertTrue(carrito.getProductos().contains(productoFisico));
            assertFalse(carrito.estaVacio());
        }

        @Test
        @DisplayName("Debería agregar múltiples productos al carrito")
        void testAgregarProductos() {
            List<ProductoInterface> productos = new ArrayList<>();
            productos.add(productoFisico);
            productos.add(productoDigital);
            
            carrito.agregarProductos(productos);
            
            assertEquals(2, carrito.getProductos().size());
            assertTrue(carrito.getProductos().contains(productoFisico));
            assertTrue(carrito.getProductos().contains(productoDigital));
        }

        @Test
        @DisplayName("Debería eliminar un producto del carrito")
        void testEliminarProducto() throws ProductoNoEncontradoExcepcion {
            carrito.agregarProducto(productoFisico);
            carrito.agregarProducto(productoDigital);
            
            carrito.eliminarProducto(productoFisico);
            
            assertEquals(1, carrito.getProductos().size());
            assertFalse(carrito.getProductos().contains(productoFisico));
            assertTrue(carrito.getProductos().contains(productoDigital));
        }

        @Test
        @DisplayName("Debería eliminar un producto por ID")
        void testEliminarProductoPorId() throws ProductoNoEncontradoExcepcion {
            carrito.agregarProducto(productoFisico);
            carrito.agregarProducto(productoDigital);
            
            carrito.eliminarProductoPorId("FIS001");
            
            assertEquals(1, carrito.getProductos().size());
            assertFalse(carrito.getProductos().contains(productoFisico));
            assertTrue(carrito.getProductos().contains(productoDigital));
        }

        @Test
        @DisplayName("Debería lanzar excepción al eliminar producto no existente")
        void testEliminarProductoNoExistente() {
            carrito.agregarProducto(productoFisico);
            
            assertThrows(ProductoNoEncontradoExcepcion.class, () -> {
                carrito.eliminarProducto(productoDigital);
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al eliminar producto por ID no existente")
        void testEliminarProductoPorIdNoExistente() {
            carrito.agregarProducto(productoFisico);
            
            assertThrows(ProductoNoEncontradoExcepcion.class, () -> {
                carrito.eliminarProductoPorId("PRODUCTO_NO_EXISTENTE");
            });
        }
    }

    @Nested
    @DisplayName("Cálculos y operaciones")
    class CalculosOperaciones {
        
        @Test
        @DisplayName("Debería calcular el total correctamente")
        void testCalcularTotal() throws CarritoVacioExcepcion {
            carrito.agregarProducto(productoFisico);
            carrito.agregarProducto(productoDigital);
            
            Double total = carrito.calcularTotal();
            Double totalEsperado = 999.99 + 299.99; // solo suma los precios, no precio * cantidad
            
            assertEquals(totalEsperado, total, 0.01);
        }

        @Test
        @DisplayName("Debería lanzar excepción al calcular total de carrito vacío")
        void testCalcularTotalCarritoVacio() {
            assertThrows(CarritoVacioExcepcion.class, () -> {
                carrito.calcularTotal();
            });
        }

        @Test
        @DisplayName("Debería obtener la cantidad total de productos")
        void testObtenerCantidadTotalProductos() {
            carrito.agregarProducto(productoFisico);
            carrito.agregarProducto(productoDigital);
            
            int cantidadTotal = carrito.obtenerCantidadTotalProductos();
            int cantidadEsperada = 2; // número de productos diferentes, no suma de cantidades
            
            assertEquals(cantidadEsperada, cantidadTotal);
        }

        @Test
        @DisplayName("Debería verificar si contiene un producto")
        void testContieneProducto() {
            carrito.agregarProducto(productoFisico);
            
            assertTrue(carrito.contieneProducto("FIS001"));
            assertFalse(carrito.contieneProducto("DIG001"));
        }
    }

    @Nested
    @DisplayName("Operaciones de limpieza")
    class OperacionesLimpieza {
        
        @Test
        @DisplayName("Debería limpiar el carrito")
        void testLimpiarCarrito() {
            carrito.agregarProducto(productoFisico);
            carrito.agregarProducto(productoDigital);
            
            carrito.limpiar();
            
            assertTrue(carrito.getProductos().isEmpty());
            assertTrue(carrito.estaVacio());
        }

        @Test
        @DisplayName("Debería verificar si el carrito está vacío")
        void testEstaVacio() {
            assertTrue(carrito.estaVacio());
            
            carrito.agregarProducto(productoFisico);
            assertFalse(carrito.estaVacio());
        }
    }

    @Nested
    @DisplayName("Operaciones de configuración")
    class OperacionesConfiguracion {
        
        @Test
        @DisplayName("Debería establecer y obtener ID")
        void testSetGetId() {
            carrito.setId("CART_NUEVO");
            assertEquals("CART_NUEVO", carrito.getId());
        }

        @Test
        @DisplayName("Debería establecer y obtener usuario")
        void testSetGetUsuario() {
            UsuarioPreferencias nuevasPreferencias = new UsuarioPreferencias();
            UsuarioCliente nuevoUsuario = new UsuarioCliente("USR002", "María García", "maria@email.com", "password456", nuevasPreferencias, null);
            HistorialCompras nuevoHistorial = new HistorialCompras(nuevoUsuario);
            nuevoUsuario.setHistorialCompras(nuevoHistorial);
            
            carrito.setUsuario(nuevoUsuario);
            assertEquals(nuevoUsuario, carrito.getUsuario());
        }

        @Test
        @DisplayName("Debería establecer y obtener productos")
        void testSetGetProductos() {
            List<ProductoInterface> nuevosProductos = new ArrayList<>();
            nuevosProductos.add(productoFisico);
            nuevosProductos.add(productoDigital);
            
            carrito.setProductos(nuevosProductos);
            assertEquals(nuevosProductos, carrito.getProductos());
            assertEquals(2, carrito.getProductos().size());
        }
    }
}
