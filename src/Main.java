import java.util.ArrayList;
import java.util.List;
import Modelos.*;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("id_usuario_1", "Juan", "juan@gmail.com", "123456");
        Carrito carrito = new Carrito("id_carrito_1", usuario);

        Producto p1 = new Producto("1", "Producto 1", "Descripción del producto 1", 10.00);
        Producto p2 = new Producto("2", "Producto 2", "Descripción del producto 2", 20.00);
        Producto p3 = new Producto("3", "Producto 3", "Descripción del producto 3", 30.00);

        carrito.agregarProducto(p1);
        carrito.agregarProducto(p2);
        carrito.agregarProducto(p3);
        
        for (Producto p : carrito.getProductos()) {
            System.out.println("Id: " + p.getId());
            System.out.println("Titulo: " + p.getTitulo());
            System.out.println("Descripción: " + p.getDescripcion());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println();
        }
        
    }
}