package co.edu.unbosque.TallerRendimiento.service;

import co.edu.unbosque.TallerRendimiento.dto.ComentarioDTO;
import co.edu.unbosque.TallerRendimiento.dto.DetalleProductoDTO;
import co.edu.unbosque.TallerRendimiento.dto.ProductoDTO;
import co.edu.unbosque.TallerRendimiento.model.Categoria;
import co.edu.unbosque.TallerRendimiento.model.Comentario;
import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.model.Subcategoria;
import co.edu.unbosque.TallerRendimiento.repository.ProductoRepository;
import co.edu.unbosque.TallerRendimiento.repository.ComentarioRepository;
import co.edu.unbosque.TallerRendimiento.repository.CalificacionRepository;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ComentarioRepository comentarioRepository;
    private final CalificacionRepository calificacionRepository;

    public ProductoService(ProductoRepository productoRepository, 
                           ComentarioRepository comentarioRepository, 
                           CalificacionRepository calificacionRepository) {
        this.productoRepository = productoRepository;
        this.comentarioRepository = comentarioRepository;
        this.calificacionRepository = calificacionRepository;
    }

    public List<ProductoDTO> searchProducts(String query, String category, BigDecimal minPrice) {
        List<Producto> productos = productoRepository.searchProducts(query, category, minPrice);

        return productos.stream()
                .map(this::mapToProductoDTO)
                .collect(Collectors.toList());
    }

    public DetalleProductoDTO getProductDetails(Integer id) {
        
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado."));

        Double promedio = calificacionRepository.findAverageByProductoId(id);
        Long conteo = calificacionRepository.countByIdProducto(id);
        
        List<Comentario> comentarios = comentarioRepository.findByProductoIdProducto(id); 
        return mapToDetalleProductoDTO(producto, promedio, conteo, comentarios); 
    }
    
    public List<ProductoDTO> getLowStockProducts(Integer threshold) {
        
        List<Producto> lowStock = productoRepository.findByCantidadProductoLessThan(threshold);
        
        return lowStock.stream()
                .map(this::mapToProductoDTO)
                .collect(Collectors.toList());
    }


   private ProductoDTO mapToProductoDTO(Producto p) {
       
        Subcategoria sub = p.getSubcategoria();
        return new ProductoDTO(
            p.getIdProducto(), 
            p.getNombreProducto(), 
            p.getDescripcionProducto(), 
            p.getPrecioProducto(), 
            p.getCantidadProducto(), 
            sub != null ? sub.getIdSubcategoria() : null,    
            sub != null ? sub.getNombreSubcategoria() : null 
        );
    }



    private ComentarioDTO mapToComentarioDTO(Comentario c) {

        return new ComentarioDTO(
            c.getContenidoComentario(), 
            c.getFechaComentario()
        );
    }
    private List<ComentarioDTO> mapToComentarioDTOList(List<Comentario> comentarios) {
        return comentarios.stream()
            .map(this::mapToComentarioDTO)
            .collect(Collectors.toList());
    }


    private DetalleProductoDTO mapToDetalleProductoDTO(Producto p, Double avg, Long count, List<Comentario> comentarios) {

        List<ComentarioDTO> comentariosDTO = mapToComentarioDTOList(comentarios);
        
        Subcategoria subcategoria = p.getSubcategoria();
        String nombreSubcategoria = subcategoria != null ? subcategoria.getNombreSubcategoria() : null;
        
        Categoria categoria = (subcategoria != null) ? subcategoria.getCategoria() : null;
        String nombreCategoria = (categoria != null) ? categoria.getNombreCategoria() : null;
        
        
        return new DetalleProductoDTO(
            p.getIdProducto().longValue(),             
            p.getNombreProducto(), 
            p.getDescripcionProducto(), 
            p.getPrecioProducto().doubleValue(),       
            p.getCantidadProducto(),                   
            
            nombreSubcategoria,
            nombreCategoria,
            
            avg != null ? avg : 0.0,                   
            count != null ? count : 0L,                
            comentariosDTO                             
        );
    }
}