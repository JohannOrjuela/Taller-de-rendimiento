package co.edu.unbosque.TallerRendimiento.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.model.TransInventario;
import co.edu.unbosque.TallerRendimiento.model.Usuario;
import co.edu.unbosque.TallerRendimiento.repository.ProductoRepository;
import co.edu.unbosque.TallerRendimiento.repository.TransInventarioRepository;
import co.edu.unbosque.TallerRendimiento.repository.UsuarioRepository;

@Service
public class ProductService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TransInventarioRepository transInventarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca productos basándose en filtros de búsqueda, categoría y precio.
     * Es un método bloqueante que accede directamente a la BD.
     */
    public Page<Producto> buscarProductos(String query, String category, BigDecimal minPrice, Pageable pageable) {
        return productoRepository.searchProducts(query, category, minPrice, pageable);
    }

    

    /**
     * Obtiene los detalles de un producto por su ID.
     */
    public Optional<Producto> obtenerDetallesProducto(Integer id) {
        // En la línea base, la obtención de detalles cargará todas las relaciones (Comentarios, Calificaciones)
        // por defecto (Lazy loading con acceso dentro de una transacción o Eager loading).
        return productoRepository.findById(id);
    }

    /**
     * Actualiza el stock de un producto y registra la transacción de inventario.
     * Esta es la lógica crítica para Óscar en bodega (debe reflejar el cambio en < 400ms).
     */
    @Transactional
    public Optional<Producto> actualizarStock(Integer productId, Integer quantityChange, String description, Integer userId) {
        Optional<Producto> productOpt = productoRepository.findById(productId);
        Optional<Usuario> userOpt = usuarioRepository.findById(userId);

        if (productOpt.isEmpty() || userOpt.isEmpty()) {
            return Optional.empty();
        }

        Producto product = productOpt.get();
        Usuario user = userOpt.get();

        // 1. Actualizar la cantidad del producto (DB Write - Bloqueante)
        product.setCantidadProducto(product.getCantidadProducto() + quantityChange);
        Producto updatedProduct = productoRepository.save(product);

        // 2. Registrar la transacción de inventario (DB Write - Bloqueante)
        String tipo = quantityChange > 0 ? "Entrada" : "Ajuste";
        TransInventario trans = new TransInventario(
            tipo,
            java.time.LocalDate.now(),
            Math.abs(quantityChange),
            description,
            product,
            user
        );
        transInventarioRepository.save(trans);

        return Optional.of(updatedProduct);
    }

    /**
     * Obtiene los productos con bajo stock.
     */
    public List<Producto> obtenerProductosBajoStock(Integer threshold) {
        return productoRepository.findByCantidadProductoLessThan(threshold);
    }
}