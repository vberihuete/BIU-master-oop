import java.util.ArrayList;
import java.util.List;
import Modelos.*;
import Modelos.Producto.ProductoInterface;
import Modelos.Usuario.UsuarioCliente;
import Modelos.Usuario.UsuarioPreferencias;
import Modelos.Historial.HistorialCompras;
import Modelos.Producto.ProductoDigital;
import Modelos.Producto.ProductoFisico;
import Repositorios.ProductoRepository;
import Repositorios.ProductoRepositoryInterface;

public class Main {
    public static void main(String[] args) {
        UsuarioCliente usuario = new UsuarioCliente("id_usuario_1", "Juan", "juan@gmail.com", "123456", new UsuarioPreferencias(), null);
        usuario.setHistorialCompras(new HistorialCompras(usuario));
        Carrito carrito = new Carrito("id_carrito_1", usuario);

        ProductoRepositoryInterface productoRepository = new ProductoRepository();
        List<ProductoInterface> productos = productoRepository.obtenerProductos();

        carrito.agregarProductos(productos);
        
        for (ProductoInterface p : carrito.getProductos()) {
            System.out.println("Id: " + p.getId());
            System.out.println("Titulo: " + p.getNombre());
            System.out.println("Cantidad: " + p.getCantidad());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println();
        }
        
    }
}