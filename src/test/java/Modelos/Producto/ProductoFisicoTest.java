package Modelos.Producto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Pruebas unitarias para la clase ProductoFisico.
 * Cubre casos de uso comunes, escenarios de error y validaciones.
 */
@DisplayName("Pruebas para la clase ProductoFisico")
class ProductoFisicoTest {

    private ProductoFisico productoFisico;

    @BeforeEach
    void setUp() {
        productoFisico = new ProductoFisico(
            "FIS001", "Laptop", 999.99, 5, 
            2.5, 0.3, 0.4, 0.05  // peso: 2.5kg, dimensiones: altura=0.3, ancho=0.4, profundidad=0.05
        );
    }

    @Nested
    @DisplayName("Creación y configuración inicial")
    class CreacionProducto {
        
        @Test
        @DisplayName("Debería crear un producto físico correctamente")
        void testCrearProductoFisico() {
            assertNotNull(productoFisico);
            assertEquals("FIS001", productoFisico.getId());
            assertEquals("Laptop", productoFisico.getNombre());
            assertEquals(999.99, productoFisico.getPrecio(), 0.01);
            assertEquals(5, productoFisico.getCantidad());
            assertEquals(2.5, productoFisico.getPeso(), 0.01);
            assertEquals(0.4, productoFisico.getAncho(), 0.01);
            assertEquals(0.3, productoFisico.getAltura(), 0.01);
            assertEquals(0.05, productoFisico.getProfundidad(), 0.01);
        }
    }

    @Nested
    @DisplayName("Operaciones de cantidad")
    class OperacionesCantidad {
        
        @Test
        @DisplayName("Debería actualizar la cantidad correctamente")
        void testActualizarCantidad() {
            productoFisico.setCantidad(10);
            assertEquals(10, productoFisico.getCantidad());
        }
    }

    @Nested
    @DisplayName("Operaciones de peso")
    class OperacionesPeso {
        
        @Test
        @DisplayName("Debería actualizar el peso correctamente")
        void testActualizarPeso() {
            productoFisico.setPeso(3.0);
            assertEquals(3.0, productoFisico.getPeso(), 0.01);
        }
    }

    @Nested
    @DisplayName("Operaciones de dimensiones")
    class OperacionesDimensiones {
        
        @Test
        @DisplayName("Debería actualizar las dimensiones correctamente")
        void testActualizarDimensiones() {
            productoFisico.setAncho(0.5);
            productoFisico.setAltura(0.6);
            productoFisico.setProfundidad(0.1);
            
            assertEquals(0.5, productoFisico.getAncho(), 0.01);
            assertEquals(0.6, productoFisico.getAltura(), 0.01);
            assertEquals(0.1, productoFisico.getProfundidad(), 0.01);
        }
    }

    @Nested
    @DisplayName("Operaciones de precio")
    class OperacionesPrecio {
        
        @Test
        @DisplayName("Debería actualizar el precio correctamente")
        void testActualizarPrecio() {
            productoFisico.setPrecio(1299.99);
            assertEquals(1299.99, productoFisico.getPrecio(), 0.01);
        }
    }
}
