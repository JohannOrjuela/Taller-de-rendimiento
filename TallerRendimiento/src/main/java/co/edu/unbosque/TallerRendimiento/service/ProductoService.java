package co.edu.unbosque.TallerRendimiento.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.TallerRendimiento.dto.ComentarioDTO;
import co.edu.unbosque.TallerRendimiento.dto.DetalleProductoDTO;
import co.edu.unbosque.TallerRendimiento.dto.ProductoDTO;
import co.edu.unbosque.TallerRendimiento.model.Categoria;
import co.edu.unbosque.TallerRendimiento.model.Comentario;
import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.model.Subcategoria;
import co.edu.unbosque.TallerRendimiento.model.TransInventario;
import co.edu.unbosque.TallerRendimiento.model.Usuario;
import co.edu.unbosque.TallerRendimiento.repository.CalificacionRepository;
import co.edu.unbosque.TallerRendimiento.repository.ComentarioRepository;
import co.edu.unbosque.TallerRendimiento.repository.ProductoRepository;
import co.edu.unbosque.TallerRendimiento.repository.TransInventarioRepository;
import co.edu.unbosque.TallerRendimiento.repository.UsuarioRepository;
import jakarta.transaction.Transactional;



@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ComentarioRepository comentarioRepository;
    private final CalificacionRepository calificacionRepository;
    private final TransInventarioRepository transInventarioRepository;
    private final UsuarioRepository usuarioRepository;

    public ProductoService(ProductoRepository productoRepository, 
                           ComentarioRepository comentarioRepository, 
                           CalificacionRepository calificacionRepository,
                           TransInventarioRepository transInventarioRepository,
                           UsuarioRepository usuarioRepository) {
        this.productoRepository = productoRepository;
        this.comentarioRepository = comentarioRepository;
        this.calificacionRepository = calificacionRepository;
        this.transInventarioRepository = transInventarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<ProductoDTO> searchProducts(String query, String category, BigDecimal minPrice, Pageable pageable) { 
        
        Page<Producto> productosPage = productoRepository.searchProducts(
            query, category, minPrice, pageable 
        );

        // Retorna la página mapeada
        return productosPage.map(this::mapToProductoDTO);
    }

    public DetalleProductoDTO getProductDetails(Integer id) {
        
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado."));

        Double promedio = calificacionRepository.findAverageByProductoId(id);
        Long conteo = calificacionRepository.countByIdProducto(id);
        
        List<Comentario> comentarios = comentarioRepository.findByProductoIdProducto(id); 
        return mapToDetalleProductoDTO(producto, promedio, conteo, comentarios); 
    }
    
    public Page<ProductoDTO> getLowStockProducts(Integer threshold, Pageable pageable) { 
        
        Page<Producto> lowStock = productoRepository.findByCantidadProductoLessThan(threshold, pageable);
        
        // Retorna la página mapeada
        return lowStock.map(this::mapToProductoDTO);
    }


   @Transactional 
    public void actualizarStockPorRecepcionUnitaria(Integer idProducto, Integer cantidadRecibida, Integer idUsuarioLogueado) {
        
        Usuario usuario = usuarioRepository.findById(idUsuarioLogueado)
                            .orElseThrow(() -> new IllegalArgumentException("Error de auditoría: Usuario no encontrado.")); 
        
        int filasActualizadas = productoRepository.sumarStockPorIdProducto(
            idProducto, 
            cantidadRecibida
        );
        
        if (filasActualizadas == 0) {
            throw new RuntimeException("Producto no encontrado con ID: " + idProducto); 
        }

        Producto productoRef = productoRepository.getReferenceById(idProducto); 
        
        TransInventario trans = new TransInventario();
        trans.setProducto(productoRef);
        trans.setUsuario(usuario);
        trans.setTipoTransInventario("Entrada");
        trans.setCantidadTransInventario(cantidadRecibida); 
        trans.setFechaTransInventario(LocalDate.now());
        trans.setDescripcionTransInventario("Recepción unitaria de mercancía (vía URL).");
        
        transInventarioRepository.save(trans); 
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