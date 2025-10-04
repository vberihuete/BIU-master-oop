package Modelos.Inventario;

import Modelos.Producto.ProductoFisico;
import Modelos.Producto.ProductoDigital;
import Excepciones.InventarioInsuficienteExcepcion;
import Excepciones.ProductoNoEncontradoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Pruebas unitarias para la clase GestorInventarioFisico.
 * Cubre casos de uso comunes, escenarios de error y manejo de excepciones.
 */
@DisplayName("Pruebas para la clase GestorInventarioFisico")
class GestorInventarioFisicoTest {

    private GestorInventarioFisico gestor;
    private ProductoFisico productoFisico;
    private ProductoFisico productoGrande;
    private ProductoDigital productoDigital;

    @BeforeEach
    void setUp() {
        // Crear gestor de inventario físico con capacidad limitada
        gestor = new GestorInventarioFisico(
            "Almacén Principal", 
            "Santo Domingo, RD", 
            100.0,  // 100 kg de capacidad máxima
            50.0    // 50 m³ de espacio disponible
        );
        
        // Producto físico normal
        productoFisico = new ProductoFisico(
            "FIS001", "Laptop", 999.99, 5, 
            2.5, 0.3, 0.4, 0.05  // peso: 2.5kg, dimensiones: 0.3x0.4x0.05m
        );
        
        // Producto muy grande que excede la capacidad
        productoGrande = new ProductoFisico(
            "FIS002", "Refrigerador", 899.99, 1,
            500.0, 2.0, 1.0, 0.8  // peso: 500kg, dimensiones: 2x1x0.8m
        );
        
        // Producto digital (no debería poder agregarse)
        productoDigital = new ProductoDigital(
            "DIG001", "Software", 299.99, 10, "EXE", "https://download.com"
        );
    }

    @Nested
    @DisplayName("Creación y configuración inicial")
    class CreacionGestor {
        
        @Test
        @DisplayName("Debería crear un gestor de inventario físico correctamente")
        void testCrearGestorInventario() {
            assertThat(gestor).isNotNull();
            assertThat(gestor.getNombreInventario()).isEqualTo("Almacén Principal");
            assertThat(gestor.getUbicacion()).isEqualTo("Santo Domingo, RD");
            assertThat(gestor.getCapacidadMaximaPeso()).isEqualTo(100.0);
            assertThat(gestor.getEspacioDisponible()).isEqualTo(50.0);
            assertThat(gestor.getPesoActual()).isZero();
            assertThat(gestor.getEspacioOcupado()).isZero();
            assertThat(gestor.obtenerProductos()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Operaciones de productos")
    class OperacionesProductos {
        
        @Test
        @DisplayName("Debería agregar un producto físico correctamente")
        void testAgregarProductoFisico() throws InventarioInsuficienteExcepcion {
            boolean resultado = gestor.añadirProducto(productoFisico);
            
            assertThat(resultado).isTrue();
            assertThat(gestor.obtenerProductos()).hasSize(1);
            assertThat(gestor.obtenerProductos()).contains(productoFisico);
            
            // Verificar que se actualizó el peso y espacio
            double pesoEsperado = productoFisico.getPeso() * productoFisico.getCantidad();
            double espacioEsperado = productoFisico.getAltura() * productoFisico.getAncho() * productoFisico.getProfundidad() * productoFisico.getCantidad();
            
            assertThat(gestor.getPesoActual()).isCloseTo(pesoEsperado, within(0.01));
            assertThat(gestor.getEspacioOcupado()).isCloseTo(espacioEsperado, within(0.01));
        }

        @Test
        @DisplayName("Debería lanzar excepción al agregar producto nulo")
        void testAgregarProductoNulo() {
            assertThatThrownBy(() -> gestor.añadirProducto(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se puede añadir un producto nulo al inventario");
        }

        @Test
        @DisplayName("Debería lanzar excepción al agregar producto digital")
        void testAgregarProductoDigital() {
            assertThatThrownBy(() -> gestor.añadirProducto(productoDigital))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Solo se pueden añadir productos físicos a este inventario");
        }

        @Test
        @DisplayName("Debería lanzar excepción al agregar producto que excede la capacidad de peso")
        void testAgregarProductoQueExcedePeso() {
            assertThatThrownBy(() -> gestor.añadirProducto(productoGrande))
                .isInstanceOf(InventarioInsuficienteExcepcion.class)
                .hasMessageContaining("No hay suficiente capacidad de peso")
                .satisfies(exception -> {
                    InventarioInsuficienteExcepcion ex = (InventarioInsuficienteExcepcion) exception;
                    assertThat(ex.getIdProducto()).isEqualTo("FIS002");
                    assertThat(ex.getStockDisponible()).isEqualTo(100);
                    assertThat(ex.getStockRequerido()).isEqualTo(500);
                });
        }
    }

    @Nested
    @DisplayName("Eliminación de productos")
    class EliminacionProductos {
        
        @Test
        @DisplayName("Debería eliminar un producto correctamente")
        void testEliminarProducto() throws InventarioInsuficienteExcepcion, ProductoNoEncontradoExcepcion {
            gestor.añadirProducto(productoFisico);
            double pesoInicial = gestor.getPesoActual();
            double espacioInicial = gestor.getEspacioOcupado();
            
            boolean resultado = gestor.eliminarProducto("FIS001");
            
            assertThat(resultado).isTrue();
            assertThat(gestor.obtenerProductos()).isEmpty();
            assertThat(gestor.getPesoActual()).isZero();
            assertThat(gestor.getEspacioOcupado()).isZero();
        }

        @Test
        @DisplayName("Debería lanzar excepción al eliminar producto con ID nulo")
        void testEliminarProductoConIdNulo() {
            assertThatThrownBy(() -> gestor.eliminarProducto(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El ID del producto no puede ser nulo o vacío");
        }

        @Test
        @DisplayName("Debería lanzar excepción al eliminar producto que no existe")
        void testEliminarProductoNoExiste() {
            assertThatThrownBy(() -> gestor.eliminarProducto("NOEXISTE"))
                .isInstanceOf(ProductoNoEncontradoExcepcion.class)
                .hasMessageContaining("El producto físico con ID NOEXISTE no se encuentra en el inventario")
                .satisfies(exception -> {
                    ProductoNoEncontradoExcepcion ex = (ProductoNoEncontradoExcepcion) exception;
                    assertThat(ex.getIdProducto()).isEqualTo("NOEXISTE");
                    assertThat(ex.getUbicacion()).isEqualTo("Almacén Principal");
                });
        }
    }

    @Nested
    @DisplayName("Actualización de stock")
    class ActualizacionStock {
        
        @Test
        @DisplayName("Debería actualizar el stock de un producto correctamente")
        void testActualizarStock() throws InventarioInsuficienteExcepcion, ProductoNoEncontradoExcepcion {
            gestor.añadirProducto(productoFisico);
            double pesoInicial = gestor.getPesoActual();
            
            boolean resultado = gestor.actualizarStock("FIS001", 10);
            
            assertThat(resultado).isTrue();
            ProductoFisico productoActualizado = (ProductoFisico) gestor.buscarProducto("FIS001");
            assertThat(productoActualizado.getCantidad()).isEqualTo(10);
            
            // Verificar que se actualizó el peso
            double pesoEsperado = productoFisico.getPeso() * 10;
            assertThat(gestor.getPesoActual()).isCloseTo(pesoEsperado, within(0.01));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -5, -100})
        @DisplayName("Debería lanzar excepción al actualizar stock con cantidad negativa")
        void testActualizarStockConCantidadNegativa(int cantidadNegativa) {
            assertThatThrownBy(() -> gestor.actualizarStock("FIS001", cantidadNegativa))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La nueva cantidad debe ser un número positivo");
        }

        @Test
        @DisplayName("Debería lanzar excepción al actualizar stock de producto que no existe")
        void testActualizarStockProductoNoExiste() {
            assertThatThrownBy(() -> gestor.actualizarStock("NOEXISTE", 10))
                .isInstanceOf(ProductoNoEncontradoExcepcion.class)
                .hasMessageContaining("El producto físico con ID NOEXISTE no se encuentra en el inventario");
        }
    }

    @Nested
    @DisplayName("Búsqueda de productos")
    class BusquedaProductos {
        
        @Test
        @DisplayName("Debería buscar un producto correctamente")
        void testBuscarProducto() throws InventarioInsuficienteExcepcion {
            gestor.añadirProducto(productoFisico);
            
            ProductoFisico productoEncontrado = (ProductoFisico) gestor.buscarProducto("FIS001");
            
            assertThat(productoEncontrado).isNotNull();
            assertThat(productoEncontrado.getId()).isEqualTo("FIS001");
            assertThat(productoEncontrado.getNombre()).isEqualTo("Laptop");
        }

        @Test
        @DisplayName("Debería retornar null al buscar producto que no existe")
        void testBuscarProductoNoExiste() {
            ProductoFisico productoEncontrado = (ProductoFisico) gestor.buscarProducto("NOEXISTE");
            
            assertThat(productoEncontrado).isNull();
        }
    }

    @Nested
    @DisplayName("Estado del almacén")
    class EstadoAlmacen {
        
        @Test
        @DisplayName("Debería obtener el estado del almacén correctamente")
        void testObtenerEstadoAlmacen() throws InventarioInsuficienteExcepcion {
            gestor.añadirProducto(productoFisico);
            
            String estado = gestor.obtenerEstadoAlmacen();
            
            assertThat(estado)
                .contains("Almacén Principal")
                .contains("Peso:")
                .contains("Espacio:")
                .contains("Productos: 1");
        }

        @Test
        @DisplayName("Debería verificar espacio disponible correctamente")
        void testVerificarEspacioDisponible() {
            // Verificar espacio para producto que cabe
            assertThat(gestor.verificarEspacioDisponible(10.0, 5.0)).isTrue();
            
            // Verificar espacio para producto que no cabe
            assertThat(gestor.verificarEspacioDisponible(200.0, 100.0)).isFalse();
        }
    }

    @Nested
    @DisplayName("Pruebas parametrizadas")
    class PruebasParametrizadas {
        
        @ParameterizedTest
        @CsvSource({
            "10.0, 5.0, true",
            "50.0, 25.0, true", 
            "200.0, 100.0, false",
            "150.0, 10.0, false"
        })
        @DisplayName("Debería verificar espacio disponible con diferentes valores")
        void testVerificarEspacioDisponibleParametrizado(double peso, double espacio, boolean esperado) {
            assertThat(gestor.verificarEspacioDisponible(peso, espacio)).isEqualTo(esperado);
        }
    }
}
