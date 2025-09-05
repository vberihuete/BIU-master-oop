import java.util.ArrayList;
import java.util.List;
import Modelos.*;
import Modelos.Producto.ProductoInterface;
import Modelos.Usuario.UsuarioCliente;
import Modelos.Usuario.UsuarioPreferencias;
import Modelos.Historial.HistorialCompras;
import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoFisico;

public class Main {
    public static void main(String[] args) {
        UsuarioCliente usuario = new UsuarioCliente("id_usuario_1", "Juan", "juan@gmail.com", "123456", new UsuarioPreferencias(), null);
        usuario.setHistorialCompras(new HistorialCompras(usuario));
        Carrito carrito = new Carrito("id_carrito_1", usuario);

        ProductoInterface p1 = new ProductoDigital("1", "producto 1", 1234.2, 200, "jpg", null);
        ProductoInterface p2 = new ProductoDigital("2", "producto 2", 444.2, 100, "zip", null);
        ProductoInterface p3 = new ProductoFisico("3", "producto 3", 1234.2, 200, 100.2, 100.2, 100.2, 100.2);

        carrito.agregarProducto(p1);
        carrito.agregarProducto(p2);
        carrito.agregarProducto(p3);
        
        for (ProductoInterface p : carrito.getProductos()) {
            System.out.println("Id: " + p.getId());
            System.out.println("Titulo: " + p.getNombre());
            System.out.println("Cantidad: " + p.getCantidad());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println();
        }
        
    }
}