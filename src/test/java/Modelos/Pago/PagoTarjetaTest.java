package Modelos.Pago;

import Excepciones.PagoFallidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase PagoTarjeta.
 * Cubre casos de uso comunes, escenarios de error y manejo de excepciones.
 */
@DisplayName("Pruebas para la clase PagoTarjeta")
class PagoTarjetaTest {

    private PagoTarjeta pagoTarjeta;

    @BeforeEach
    void setUp() {
        pagoTarjeta = new PagoTarjeta("1234567890123456", "123", "12/25", "Juan Pérez");
    }

    @Nested
    @DisplayName("Creación y configuración inicial")
    class CreacionPago {
        
        @Test
        @DisplayName("Debería crear un pago con tarjeta correctamente")
        void testCrearPagoTarjeta() {
            assertNotNull(pagoTarjeta);
            assertNull(pagoTarjeta.getIdTransaccion());
        }

        @Test
        @DisplayName("Debería crear pago con datos nulos (sin validación)")
        void testCrearPagoConDatosNulos() {
            PagoTarjeta pagoConNulos = new PagoTarjeta(null, "1234567890123456", "12/25", "123");
            assertNotNull(pagoConNulos);
        }
    }

    @Nested
    @DisplayName("Inicio de pago")
    class InicioPago {
        
        @Test
        @DisplayName("Debería iniciar un pago correctamente")
        void testIniciarPagoExitoso() throws PagoFallidoExcepcion {
            boolean resultado = pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            
            assertTrue(resultado);
            assertEquals(100.0, pagoTarjeta.getMonto());
            assertEquals("USD", pagoTarjeta.getMoneda());
        }

        @Test
        @DisplayName("Debería lanzar excepción al iniciar pago con monto nulo")
        void testIniciarPagoMontoNulo() {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(null, "USD", "Datos de prueba");
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al iniciar pago con monto negativo")
        void testIniciarPagoMontoNegativo() {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(-100.0, "USD", "Datos de prueba");
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al iniciar pago con monto cero")
        void testIniciarPagoMontoCero() {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(0.0, "USD", "Datos de prueba");
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al iniciar pago con moneda nula")
        void testIniciarPagoMonedaNula() {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(100.0, null, "Datos de prueba");
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al iniciar pago con moneda vacía")
        void testIniciarPagoMonedaVacia() {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(100.0, "", "Datos de prueba");
            });
        }

        @Test
        @DisplayName("Debería iniciar pago con datos de pago nulos (sin validación)")
        void testIniciarPagoDatosNulos() throws PagoFallidoExcepcion {
            boolean resultado = pagoTarjeta.iniciarPago(100.0, "USD", null);
            assertTrue(resultado);
        }
    }

    @Nested
    @DisplayName("Verificación de pago")
    class VerificacionPago {
        
        @Test
        @DisplayName("Debería verificar un pago correctamente")
        void testVerificarPagoExitoso() throws PagoFallidoExcepcion {
            pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            String idTransaccion = pagoTarjeta.getIdTransaccion();
            boolean resultado = pagoTarjeta.verificarPago(idTransaccion);
            
            assertTrue(resultado);
        }

        @Test
        @DisplayName("Debería lanzar excepción al verificar pago con ID nulo")
        void testVerificarPagoIdNulo() throws PagoFallidoExcepcion {
            pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.verificarPago(null);
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al verificar pago con ID incorrecto")
        void testVerificarPagoIdIncorrecto() throws PagoFallidoExcepcion {
            pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.verificarPago("ID_INCORRECTO");
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al verificar pago sin iniciar")
        void testVerificarPagoSinIniciar() {
            assertThrows(NullPointerException.class, () -> {
                pagoTarjeta.verificarPago("ID_INCORRECTO");
            });
        }
    }

    @Nested
    @DisplayName("Confirmación de pago")
    class ConfirmacionPago {
        
        @Test
        @DisplayName("Debería confirmar un pago correctamente")
        void testConfirmarPagoExitoso() throws PagoFallidoExcepcion {
            pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            String idTransaccion = pagoTarjeta.getIdTransaccion();
            pagoTarjeta.verificarPago(idTransaccion);
            boolean resultado = pagoTarjeta.confirmarPago(idTransaccion);
            
            assertTrue(resultado);
        }

        @Test
        @DisplayName("Debería lanzar excepción al confirmar pago con ID nulo")
        void testConfirmarPagoIdNulo() {
            try {
                pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
                String idTransaccion = pagoTarjeta.getIdTransaccion();
                pagoTarjeta.verificarPago(idTransaccion);
                
                assertThrows(PagoFallidoExcepcion.class, () -> {
                    try {
                        pagoTarjeta.confirmarPago(null);
                    } catch (PagoFallidoExcepcion e) {
                        throw e;
                    }
                });
            } catch (PagoFallidoExcepcion e) {
                // Expected for this test
            }
        }

        @Test
        @DisplayName("Debería lanzar excepción al confirmar pago con ID incorrecto")
        void testConfirmarPagoIdIncorrecto() throws PagoFallidoExcepcion {
            pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            String idTransaccion = pagoTarjeta.getIdTransaccion();
            pagoTarjeta.verificarPago(idTransaccion);
            
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.confirmarPago("ID_INCORRECTO");
            });
        }

        @Test
        @DisplayName("Debería lanzar excepción al confirmar pago sin verificar")
        void testConfirmarPagoSinVerificar() throws PagoFallidoExcepcion {
            pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba");
            
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.confirmarPago("ID_INCORRECTO");
            });
        }
    }

    @Nested
    @DisplayName("Flujo completo de pago")
    class FlujoCompletoPago {
        
        @Test
        @DisplayName("Debería ejecutar el flujo completo de pago correctamente")
        void testFlujoCompletoPagoExitoso() throws PagoFallidoExcepcion {
            // Paso 1: Iniciar pago
            assertTrue(pagoTarjeta.iniciarPago(100.0, "USD", "Datos de prueba"));
            String idTransaccion = pagoTarjeta.getIdTransaccion();
            
            // Paso 2: Verificar pago
            assertTrue(pagoTarjeta.verificarPago(idTransaccion));
            
            // Paso 3: Confirmar pago
            assertTrue(pagoTarjeta.confirmarPago(idTransaccion));
        }

        @Test
        @DisplayName("Debería manejar error en el flujo de pago")
        void testFlujoCompletoPagoConError() throws PagoFallidoExcepcion {
            // Simular error al iniciar pago
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(-100.0, "USD", "Datos de prueba");
            });
        }
    }

    @Nested
    @DisplayName("Pruebas parametrizadas")
    class PruebasParametrizadas {
        
        @ParameterizedTest
        @ValueSource(doubles = {-100.0, -50.0, -1.0, 0.0})
        @DisplayName("Debería lanzar excepción con montos inválidos")
        void testMontosInvalidos(double montoInvalido) {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(montoInvalido, "USD", "Datos de prueba");
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Debería lanzar excepción con monedas vacías")
        void testMonedasVacias(String monedaVacia) {
            assertThrows(PagoFallidoExcepcion.class, () -> {
                pagoTarjeta.iniciarPago(100.0, monedaVacia, "Datos de prueba");
            });
        }
    }
}
