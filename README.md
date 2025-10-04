# Plataforma E-Commerce - Sistema de Gestión

## 🚀 Inicio Rápido

### Prerrequisitos
- **Java 17+** (recomendado Java 22)
- **Apache Maven 3.6+**

### Ejecución Rápida
```bash
# Clonar y navegar al proyecto
git clone <repository-url>
cd biu-master-oop

# Ejecutar todas las pruebas
mvn test

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="Main"

# Ver reporte de cobertura
mvn clean test jacoco:report
open target/site/jacoco/index.html
```

## Descripción del Proyecto

Este proyecto implementa una plataforma de comercio electrónico completa utilizando Java, con patrones de diseño orientados a objetos, manejo robusto de excepciones y pruebas unitarias exhaustivas. El proyecto utiliza **Apache Maven** como herramienta de construcción y gestión de dependencias.

## Características Principales

### 🏗️ Arquitectura y Patrones de Diseño
- **Factory Pattern**: Para la creación de entidades (productos, usuarios)
- **Observer Pattern**: Sistema de notificaciones en tiempo real
- **Abstract Classes**: Gestión de inventario diferenciada (físico vs digital)
- **Interfaces**: Contratos claros para productos, usuarios y procesos de pago
- **Singleton Pattern**: Configuración del sistema y gestión de notificaciones

### 🛡️ Manejo de Excepciones Personalizadas
Se implementaron excepciones específicas para diferentes escenarios de error:

#### `InventarioInsuficienteExcepcion`
- **Propósito**: Maneja errores cuando no hay suficiente stock o capacidad
- **Casos de uso**: Productos que exceden la capacidad del almacén, stock insuficiente
- **Información incluida**: ID del producto, stock disponible, stock requerido

#### `PagoFallidoExcepcion`
- **Propósito**: Maneja errores en procesos de pago
- **Casos de uso**: Tarjetas declinadas, fondos insuficientes, datos inválidos
- **Información incluida**: ID de transacción, método de pago, monto, código de error, razón del fallo

#### `ProductoNoEncontradoExcepcion`
- **Propósito**: Maneja errores cuando un producto no existe
- **Casos de uso**: Búsqueda de productos inexistentes, eliminación de productos no encontrados
- **Información incluida**: ID del producto, ubicación donde se buscó

#### `UsuarioNoEncontradoExcepcion`
- **Propósito**: Maneja errores cuando un usuario no existe
- **Casos de uso**: Búsqueda de usuarios inexistentes, autenticación fallida
- **Información incluida**: ID del usuario, email

#### `CarritoVacioExcepcion`
- **Propósito**: Maneja errores cuando se intenta realizar operaciones en un carrito vacío
- **Casos de uso**: Cálculo de totales en carritos vacíos, checkout sin productos
- **Información incluida**: ID del carrito, ID del usuario

### 🧪 Pruebas Unitarias Completas

#### Cobertura de Pruebas
- **CarritoTest**: 17 pruebas que cubren operaciones CRUD, validaciones y manejo de excepciones
- **GestorInventarioFisicoTest**: 21 pruebas que cubren gestión de inventario, validaciones de capacidad y manejo de errores
- **PagoTarjetaTest**: 27 pruebas que cubren flujo completo de pagos, validaciones y manejo de excepciones
- **ProductoFisicoTest**: 5 pruebas que cubren funcionalidad básica y validaciones
- **SimpleTest**: 2 pruebas básicas de funcionamiento
- **WorkingTest**: 3 pruebas de verificación del entorno Maven

#### Tipos de Pruebas Implementadas
1. **Pruebas de Funcionalidad Normal**: Casos de uso comunes y esperados
2. **Pruebas de Validación**: Verificación de parámetros de entrada
3. **Pruebas de Excepciones**: Manejo de errores y casos límite
4. **Pruebas de Integración**: Interacción entre componentes
5. **Pruebas de Estado**: Verificación de cambios de estado

### 📁 Estructura del Proyecto Maven

```
├── pom.xml                         # Configuración Maven
├── README.md                       # Documentación del proyecto
├── README-MAVEN.md                 # Guía específica de Maven
└── src/
    ├── main/java/                  # Código fuente principal
    │   ├── Excepciones/            # Excepciones personalizadas
    │   │   ├── CarritoVacioExcepcion.java
    │   │   ├── InventarioInsuficienteExcepcion.java
    │   │   ├── PagoFallidoExcepcion.java
    │   │   ├── ProductoNoEncontradoExcepcion.java
    │   │   └── UsuarioNoEncontradoExcepcion.java
    │   ├── Modelos/                # Modelos de dominio
    │   │   ├── Carrito.java        # ✅ Actualizado con manejo de excepciones
    │   │   ├── Inventario/
    │   │   │   ├── GestorInventario.java
    │   │   │   ├── GestorInventarioFisico.java  # ✅ Actualizado con manejo de excepciones
    │   │   │   └── GestorInventarioDigital.java
    │   │   ├── Pago/
    │   │   │   ├── PagoTarjeta.java        # ✅ Actualizado con manejo de excepciones
    │   │   │   ├── PagoPayPal.java
    │   │   │   └── ProcesoPago.java
    │   │   ├── Producto/
    │   │   │   ├── ProductoFisico.java
    │   │   │   ├── ProductoDigital.java
    │   │   │   └── ProductoInterface.java
    │   │   └── Usuario/
    │   │       ├── UsuarioCliente.java
    │   │       ├── UsuarioAdministrador.java
    │   │       └── UsuarioInterface.java
    │   └── Main.java               # Punto de entrada de la aplicación
    └── test/java/                  # Pruebas unitarias
        ├── Modelos/
        │   ├── CarritoTest.java    # ✅ 17 pruebas
        │   ├── Inventario/
        │   │   └── GestorInventarioFisicoTest.java  # ✅ 21 pruebas
        │   ├── Pago/
        │   │   └── PagoTarjetaTest.java  # ✅ 27 pruebas
        │   └── Producto/
        │       └── ProductoFisicoTest.java  # ✅ 5 pruebas
        ├── SimpleTest.java         # ✅ 2 pruebas básicas
        └── WorkingTest.java        # ✅ 3 pruebas de funcionamiento
```

### 🔧 Configuración Maven

El proyecto utiliza **Apache Maven** como herramienta de construcción y gestión de dependencias. La configuración se encuentra en el archivo `pom.xml`:

#### Dependencias Principales
- **JUnit 5**: Framework de pruebas unitarias
- **AssertJ**: Biblioteca de aserciones fluidas
- **JaCoCo**: Herramienta de cobertura de código
- **Maven Surefire**: Plugin para ejecución de pruebas
- **Maven Exec**: Plugin para ejecutar la aplicación

#### Características de la Configuración
- **Java 22**: Versión de compilación y ejecución
- **UTF-8**: Codificación de archivos
- **Cobertura de Código**: Reportes automáticos con JaCoCo
- **Ejecución de Pruebas**: Configuración optimizada para JUnit 5
- **Gestión de Dependencias**: Resolución automática de versiones

## Mejoras Implementadas

### 🔧 Manejo de Excepciones en Operaciones Críticas

#### Carrito de Compras
- **Validación de parámetros**: Verificación de productos nulos
- **Manejo de productos no encontrados**: Excepciones específicas al eliminar productos
- **Validación de carrito vacío**: Prevención de operaciones en carritos sin productos
- **Métodos adicionales**: `eliminarProductoPorId()`, `calcularTotal()`, `contieneProducto()`

#### Gestión de Inventario
- **Validación de capacidad**: Verificación de peso y espacio disponible
- **Manejo de productos no encontrados**: Excepciones al buscar/eliminar productos inexistentes
- **Validación de tipos**: Solo productos físicos en inventario físico
- **Actualización de stock**: Validación de capacidad antes de actualizar

#### Procesos de Pago
- **Validación de datos**: Verificación de montos, monedas y datos de tarjeta
- **Manejo de estados**: Validación del flujo de pago (iniciado → verificado → confirmado)
- **Códigos de error específicos**: Identificación clara de tipos de fallo
- **Información detallada**: Contexto completo del error para debugging

### 🧪 Cobertura de Pruebas

#### Casos de Uso Cubiertos
1. **Funcionalidad Normal**: Operaciones exitosas y flujos típicos
2. **Validaciones**: Parámetros nulos, vacíos o inválidos
3. **Casos Límite**: Valores extremos y condiciones de borde
4. **Manejo de Errores**: Excepciones y recuperación de errores
5. **Integración**: Interacción entre componentes

#### Métricas de Cobertura
- **Total de Pruebas**: 75 pruebas unitarias (100% exitosas)
- **Clases Cubiertas**: 6 clases principales
- **Métodos Probados**: 50+ métodos
- **Excepciones Probadas**: 5 tipos de excepciones personalizadas
- **Cobertura de Código**: Reportes automáticos con JaCoCo
- **Tiempo de Ejecución**: < 2 segundos para todas las pruebas

### 📊 Beneficios de las Mejoras

#### Robustez del Sistema
- **Recuperación Graceful**: El sistema maneja errores sin fallar completamente
- **Feedback Útil**: Mensajes de error informativos para usuarios y desarrolladores
- **Validación Temprana**: Detección de problemas antes de que causen fallos
- **Trazabilidad**: Información detallada para debugging y auditoría

#### Mantenibilidad
- **Código Limpio**: Separación clara de responsabilidades
- **Documentación**: Comentarios y JavaDoc completos
- **Pruebas Automatizadas**: Verificación continua de funcionalidad
- **Patrones Establecidos**: Uso consistente de patrones de diseño

#### Calidad del Software
- **Confiabilidad**: Manejo robusto de errores
- **Usabilidad**: Mensajes de error claros para usuarios
- **Escalabilidad**: Arquitectura preparada para crecimiento
- **Testabilidad**: Código fácil de probar y mantener

## Requisitos del Sistema

### Prerrequisitos
- **Java 17 o superior** (recomendado Java 22)
- **Apache Maven 3.6+** (recomendado Maven 3.9+)
- **IDE compatible** (IntelliJ IDEA, Eclipse, VS Code)

### Instalación de Maven

#### Windows
```bash
# Usando Chocolatey
choco install maven

# O descargar desde: https://maven.apache.org/download.cgi
# Agregar MAVEN_HOME al PATH
```

#### macOS
```bash
# Usando Homebrew
brew install maven

# Verificar instalación
mvn --version
```

#### Linux (Ubuntu/Debian)
```bash
# Instalar Maven
sudo apt update
sudo apt install maven

# Verificar instalación
mvn --version
```

#### Verificación de la Instalación
```bash
# Verificar Java
java --version

# Verificar Maven
mvn --version
```

## Cómo Ejecutar el Proyecto

### Estructura del Proyecto Maven
```
src/
├── main/java/                    # Código fuente principal
│   ├── Excepciones/             # Excepciones personalizadas
│   ├── Modelos/                 # Modelos de dominio
│   └── Main.java                # Punto de entrada
└── test/java/                   # Pruebas unitarias
    ├── Modelos/                 # Pruebas de modelos
    ├── SimpleTest.java          # Pruebas básicas
    └── WorkingTest.java         # Pruebas de funcionamiento
```

### Comandos Maven Principales

#### Compilación y Ejecución
```bash
# Compilar el proyecto
mvn compile

# Ejecutar la aplicación principal
mvn exec:java -Dexec.mainClass="Main"

# Compilar y ejecutar en un solo comando
mvn compile exec:java -Dexec.mainClass="Main"
```

#### Ejecución de Pruebas
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas específicas
mvn test -Dtest=CarritoTest
mvn test -Dtest=ProductoFisicoTest
mvn test -Dtest=PagoTarjetaTest

# Ejecutar con reporte de cobertura
mvn clean test jacoco:report

# Ver reporte de cobertura
open target/site/jacoco/index.html
```

#### Construcción del Proyecto
```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar pruebas y generar JAR
mvn clean package

# Instalar en repositorio local
mvn clean install

# Ejecutar con perfil de desarrollo
mvn clean test -Pdev
```

### Ejecución de Pruebas Específicas

#### Por Clase de Prueba
```bash
# Carrito
mvn test -Dtest=Modelos.CarritoTest

# Inventario
mvn test -Dtest=Modelos.Inventario.GestorInventarioFisicoTest

# Pago
mvn test -Dtest=Modelos.Pago.PagoTarjetaTest

# Producto
mvn test -Dtest=Modelos.Producto.ProductoFisicoTest
```

#### Por Patrón de Nombre
```bash
# Todas las pruebas de modelos
mvn test -Dtest="Modelos.*Test"

# Pruebas específicas
mvn test -Dtest="*Carrito*"
mvn test -Dtest="*Pago*"
```

### Reportes y Análisis

#### Generar Reportes de Cobertura
```bash
# Ejecutar pruebas con cobertura
mvn clean test jacoco:report

# Ver reporte en navegador
open target/site/jacoco/index.html
```

#### Información del Proyecto
```bash
# Ver información del proyecto
mvn help:describe -Dplugin=compiler

# Ver dependencias
mvn dependency:tree

# Ver configuración
mvn help:effective-pom
```

## Casos de Uso de las Excepciones

### Ejemplo: Manejo de Inventario Insuficiente
```java
try {
    gestor.añadirProducto(productoGrande);
} catch (InventarioInsuficienteExcepcion e) {
    System.out.println("Error: " + e.getMessage());
    System.out.println("Producto: " + e.getIdProducto());
    System.out.println("Capacidad disponible: " + e.getStockDisponible());
    System.out.println("Capacidad requerida: " + e.getStockRequerido());
}
```

### Ejemplo: Manejo de Pago Fallido
```java
try {
    pagoTarjeta.iniciarPago(monto, moneda, datos);
} catch (PagoFallidoExcepcion e) {
    System.out.println("Error: " + e.getMessage());
    System.out.println("Código de error: " + e.getCodigoError());
    System.out.println("Razón: " + e.getRazonFallo());
}
```

### Ejemplo: Manejo de Carrito Vacío
```java
try {
    Double total = carrito.calcularTotal();
} catch (CarritoVacioExcepcion e) {
    System.out.println("Error: " + e.getMessage());
    System.out.println("Carrito: " + e.getIdCarrito());
    System.out.println("Usuario: " + e.getIdUsuario());
}
```

## Conclusiones

Este proyecto demuestra la implementación de un sistema robusto de manejo de excepciones y pruebas unitarias en una plataforma de comercio electrónico. Las mejoras implementadas incluyen:

1. **Excepciones Personalizadas**: Manejo específico de errores del dominio
2. **Validaciones Robustas**: Verificación de parámetros y estados
3. **Pruebas Exhaustivas**: Cobertura completa de funcionalidad y casos de error
4. **Documentación Clara**: Código autodocumentado y bien comentado
5. **Arquitectura Sólida**: Patrones de diseño bien implementados

El sistema resultante es más confiable, mantenible y fácil de probar, proporcionando una base sólida para el desarrollo futuro de la plataforma de comercio electrónico.