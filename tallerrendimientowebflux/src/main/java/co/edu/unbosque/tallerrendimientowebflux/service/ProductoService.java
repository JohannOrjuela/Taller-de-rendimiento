package co.edu.unbosque.tallerrendimientowebflux.service;

import co.edu.unbosque.tallerrendimientowebflux.repository.CalificacionReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.ComentarioReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.ProductoReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.SubcategoriaReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.UsuarioReactiveRepository;

import co.edu.unbosque.tallerrendimientowebflux.model.Producto;
import co.edu.unbosque.tallerrendimientowebflux.model.Subcategoria;
import co.edu.unbosque.tallerrendimientowebflux.model.Comentario;
import co.edu.unbosque.tallerrendimientowebflux.model.Usuario;

import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.dto.DetalleProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.dto.ComentarioDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoReactiveRepository productoRepository;
    private final CalificacionReactiveRepository calificacionRepository;
    private final ComentarioReactiveRepository comentarioRepository;
    private final SubcategoriaReactiveRepository subcategoriaRepository; 
    private final UsuarioReactiveRepository usuarioRepository; 

    public ProductoService(ProductoReactiveRepository productoRepository,
                           CalificacionReactiveRepository calificacionRepository,
                           ComentarioReactiveRepository comentarioRepository,
                           SubcategoriaReactiveRepository subcategoriaRepository,
                           UsuarioReactiveRepository usuarioRepository) {
        this.productoRepository = productoRepository;
        this.calificacionRepository = calificacionRepository;
        this.comentarioRepository = comentarioRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // -----------------------------------------------------------------
    // 1. ENDPOINT GET: BÚSQUEDA GENERAL (/search)
    // -----------------------------------------------------------------
    public Flux<ProductoDTO> searchProducts(String query, String category, Double minPrice) {
        BigDecimal price = (minPrice != null) ? BigDecimal.valueOf(minPrice) : null;
        
        return productoRepository.searchProducts(query, category, price)
                                 .map(this::toProductoDTO);
    }
    
    // -----------------------------------------------------------------
    // 2. ENDPOINT GET: DETALLES POR ID (CONCURRENCIA CRÍTICA)
    // -----------------------------------------------------------------
    public Mono<DetalleProductoDTO> obtenerDetallesPorId(Integer id) {
        
        Mono<Producto> productoMono = productoRepository.findById(id); 

        Mono<Double> promedioCalificacionMono = calificacionRepository.findAverageByProductoId(id).defaultIfEmpty(0.0);
        Mono<Long> totalComentariosMono = calificacionRepository.countByIdProducto(id).defaultIfEmpty(0L);
        
        Mono<List<ComentarioDTO>> comentariosMonoList = comentarioRepository.findByIdProducto(id)
                .flatMapSequential(this::toComentarioDTO)
                .collectList();
        
        // La consulta a Subcategoria depende del resultado de Producto
        Mono<Subcategoria> subcategoriaMono = productoMono
                .flatMap(p -> subcategoriaRepository.findById(p.getIdSubcategoria())); 
        
        // La consulta a Categoria depende del resultado de Subcategoria
        Mono<String> categoriaNombreMono = subcategoriaMono
                .flatMap(s -> subcategoriaRepository.findCategoriaNombreById(s.getIdCategoria()))
                .defaultIfEmpty("N/A");

        // ******* ORQUESTACIÓN CONCURRENTE CON MONO.ZIP *******
        return Mono.zip(
            productoMono, promedioCalificacionMono, totalComentariosMono, 
            comentariosMonoList, subcategoriaMono, categoriaNombreMono
        ).map(tuple -> {
            Producto p = tuple.getT1();
            Double avg = tuple.getT2();
            Long count = tuple.getT3();
            List<ComentarioDTO> comentarios = tuple.getT4();
            Subcategoria sub = tuple.getT5();
            String catName = tuple.getT6();

            // Usando los Getters de tu modelo (p.getIdProducto(), etc.)
            return new DetalleProductoDTO(
                p.getIdProducto().longValue(), 
                p.getNombreProducto(), 
                p.getDescripcionProducto(), 
                p.getPrecioProducto().doubleValue(),
                p.getCantidadProducto(), 
                sub.getNombreSubcategoria(),
                catName,
                avg, 
                count,
                comentarios
            );
        });
    }

    // -----------------------------------------------------------------
    // 3. ENDPOINT GET: INVENTARIO BAJO STOCK (/low-stock)
    // -----------------------------------------------------------------
    public Flux<ProductoDTO> findByCantidadProductoLessThan(Integer threshold) {
        return productoRepository.findByCantidadProductoLessThan(threshold)
                                 .map(this::toProductoDTO);
    }

    // -----------------------------------------------------------------
    // MAPPERS PRIVADOS Y LÓGICA AUXILIAR
    // -----------------------------------------------------------------

    private ProductoDTO toProductoDTO(Producto entity) {
        return new ProductoDTO(
            entity.getIdProducto().longValue(), 
            entity.getNombreProducto(), 
            entity.getDescripcionProducto(),
            entity.getPrecioProducto().doubleValue(), 
            entity.getCantidadProducto(), 
            entity.getIdSubcategoria(), 
            null
        );
    }

    private Mono<ComentarioDTO> toComentarioDTO(Comentario comentario) {
        Mono<String> usuarioNombreMono = usuarioRepository.findById(comentario.getIdUsuario())
            .map(Usuario::getNombreUsuario)
            .defaultIfEmpty("Anónimo"); 

        return usuarioNombreMono.map(nombre -> new ComentarioDTO(
            comentario.getContenidoComentario(),
            comentario.getFechaComentario(),
            nombre
        ));
    }

    public Mono<ProductoDTO> crearProducto(ProductoDTO dto) {
        // CORRECCIÓN: Ahora usa el constructor de 5 argumentos (sin ID) que se debe añadir al modelo.
        Producto productoParaGuardar = new Producto(
            dto.getNombre(), 
            BigDecimal.valueOf(dto.getPrecio()), 
            dto.getDescripcion(), 
            dto.getInventario(), 
            dto.getIdSubcategoria()
        );
        
        return productoRepository.save(productoParaGuardar) 
                                 .map(this::toProductoDTO); 
    }
}