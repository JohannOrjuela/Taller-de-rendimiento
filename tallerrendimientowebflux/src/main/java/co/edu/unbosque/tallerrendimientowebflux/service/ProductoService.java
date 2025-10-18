package co.edu.unbosque.tallerrendimientowebflux.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unbosque.tallerrendimientowebflux.dto.ComentarioDTO;
import co.edu.unbosque.tallerrendimientowebflux.dto.DetalleProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.dto.ProductoDTO;
import co.edu.unbosque.tallerrendimientowebflux.model.Comentario;
import co.edu.unbosque.tallerrendimientowebflux.model.Producto;
import co.edu.unbosque.tallerrendimientowebflux.model.Subcategoria;
import co.edu.unbosque.tallerrendimientowebflux.model.TransInventario;
import co.edu.unbosque.tallerrendimientowebflux.repository.CalificacionReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.ComentarioReactiveRepository; 
import co.edu.unbosque.tallerrendimientowebflux.repository.ProductoReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.SubcategoriaReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.TransInventarioReactiveRepository;
import co.edu.unbosque.tallerrendimientowebflux.repository.UsuarioReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class ProductoService {

    private final ProductoReactiveRepository productoRepository;
    private final CalificacionReactiveRepository calificacionRepository;
    private final ComentarioReactiveRepository comentarioRepository;
    private final SubcategoriaReactiveRepository subcategoriaRepository; 
    private final UsuarioReactiveRepository usuarioRepository;
    private final TransInventarioReactiveRepository transInventarioRepository;

    public ProductoService(ProductoReactiveRepository productoRepository,
                           CalificacionReactiveRepository calificacionRepository,
                           ComentarioReactiveRepository comentarioRepository,
                           SubcategoriaReactiveRepository subcategoriaRepository,
                           UsuarioReactiveRepository usuarioRepository,
                           TransInventarioReactiveRepository transInventarioRepository) {
        this.productoRepository = productoRepository;
        this.calificacionRepository = calificacionRepository;
        this.comentarioRepository = comentarioRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.transInventarioRepository = transInventarioRepository;
    }

    // -----------------------------------------------------------------
    // 1. ENDPOINT GET: BÚSQUEDA GENERAL (/search)
    // -----------------------------------------------------------------
    public Flux<ProductoDTO> searchProducts(String query, String category, Double minPrice) {
    
    BigDecimal minPriceBD = (minPrice != null) ? BigDecimal.valueOf(minPrice) : null;
    
    return productoRepository.searchProductsBase(query, category, minPriceBD).flatMap(producto -> {
            
        Integer idSub = producto.getIdSubcategoria();
        Mono<Subcategoria> subMono;
            if (idSub == null) {
                subMono = Mono.just(new Subcategoria(null, null, null)); 
            } else {
                subMono = subcategoriaRepository.findById(idSub).defaultIfEmpty(new Subcategoria(idSub, "N/A", null)); 
            }

            return Mono.zip(Mono.just(producto), subMono);

        }).map(this::mapTupleToProductoDTO);
}
    
    // -----------------------------------------------------------------
    // 2. ENDPOINT GET: DETALLES POR ID
    // -----------------------------------------------------------------
      public Mono<DetalleProductoDTO> obtenerDetallesPorId(Integer id) {

        Mono<Producto> productoMono = productoRepository.findById(id);
        Mono<Double> promedioCalificacionMono = calificacionRepository.findAverageByProductoId(id).defaultIfEmpty(0.0);
        Mono<Long> totalComentariosMono = calificacionRepository.countByIdProducto(id).defaultIfEmpty(0L);


        Mono<List<ComentarioDTO>> comentariosMonoList = comentarioRepository.findByIdProducto(id).map(this::toComentarioDTOAnonymized).collectList();

        Mono<Subcategoria> subcategoriaMono = productoMono.flatMap(p -> subcategoriaRepository.findById(p.getIdSubcategoria()));

        Mono<String> categoriaNombreMono = subcategoriaMono.flatMap(s -> subcategoriaRepository.findCategoriaNombreById(s.getIdCategoria())).defaultIfEmpty("N/A");


        return Mono.zip(
            productoMono, promedioCalificacionMono, totalComentariosMono, comentariosMonoList, subcategoriaMono, categoriaNombreMono)
            .map(tuple -> {

                Producto p = tuple.getT1();
                Double avg = tuple.getT2();
                Long count = tuple.getT3();
                List<ComentarioDTO> comentarios = tuple.getT4();
                Subcategoria sub = tuple.getT5();
                String catName = tuple.getT6();

                return new DetalleProductoDTO(

                    p.getIdProducto().longValue(), p.getNombreProducto(),  p.getDescripcionProducto(),p.getPrecioProducto().doubleValue(),
                    p.getCantidadProducto(),sub.getNombreSubcategoria(),catName,avg,count,comentarios
                );

            });

    }

    // -----------------------------------------------------------------
    // 3. ENDPOINT GET: INVENTARIO BAJO STOCK (/low-stock)
    // -----------------------------------------------------------------
    public Flux<ProductoDTO> findByCantidadProductoLessThan(Integer threshold) {
    
    return productoRepository.findLowStockProductsBase(threshold) 
        
        .flatMap(producto -> {
            
            Mono<Subcategoria> subMono = subcategoriaRepository.findById(producto.getIdSubcategoria()).defaultIfEmpty(
                new Subcategoria(producto.getIdSubcategoria(), "N/A", null)); 
            
            return Mono.zip(Mono.just(producto), subMono);
            
        }).map(this::mapTupleToProductoDTO);
}
public Mono<Void> actualizarStockPorRecepcionUnitaria(Integer idProducto, Integer cantidadRecibida, Integer idUsuarioLogueado) {
        // ... (El cuerpo del método está bien)
        // ... (Aquí se usa transInventarioRepository.save(trans).then())
        return usuarioRepository.findById(idUsuarioLogueado)
             .switchIfEmpty(Mono.error(new IllegalArgumentException("Error de auditoría: Usuario no encontrado.")))
             .flatMap(usuario -> 
                 productoRepository.sumarStockPorIdProducto(idProducto, cantidadRecibida)
                 .flatMap(filasActualizadas -> {
                     if (filasActualizadas == 0) {
                         return Mono.error(new RuntimeException("Producto no encontrado con ID: " + idProducto));
                     }
                     // 4. Crear el registro de TransInventario
                     TransInventario trans = new TransInventario();
                     trans.setIdProducto(idProducto);
                     trans.setIdUsuario(idUsuarioLogueado);
                     trans.setTipoTransInventario("Entrada");
                     trans.setCantidadTransInventario(cantidadRecibida); 
                     trans.setFechaTransInventario(LocalDate.now());
                     trans.setDescripcionTransInventario("Recepción unitaria de mercancía (vía WebFlux).");
                     
                     // 5. Guardar la transacción y completar el flujo
                     return transInventarioRepository.save(trans).then(); 
                 })
             );
    }
    // -----------------------------------------------------------------
    // MAPPERS PRIVADOS
    // -----------------------------------------------------------------

    private ProductoDTO mapTupleToProductoDTO(Tuple2<Producto, Subcategoria> tuple) {
        Producto producto = tuple.getT1();
        Subcategoria sub = tuple.getT2();
        
        return new ProductoDTO(
            producto.getIdProducto(),
            producto.getNombreProducto(), 
            producto.getDescripcionProducto(),
            producto.getPrecioProducto(),
            producto.getCantidadProducto(), 
            producto.getIdSubcategoria(), 
            sub.getNombreSubcategoria()
        );
    }
    
    private ComentarioDTO toComentarioDTOAnonymized(Comentario comentario) {
        return new ComentarioDTO(
            comentario.getContenidoComentario(),
            comentario.getFechaComentario()
        );
    }
    
}