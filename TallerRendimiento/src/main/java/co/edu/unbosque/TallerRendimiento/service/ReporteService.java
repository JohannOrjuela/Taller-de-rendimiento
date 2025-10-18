package co.edu.unbosque.TallerRendimiento.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.TallerRendimiento.dto.ProductoReporteDTO;
import co.edu.unbosque.TallerRendimiento.repository.TransInventarioRepository;

@Service
public class ReporteService {

    private final TransInventarioRepository transInventarioRepository;

    public ReporteService(TransInventarioRepository transInventarioRepository) {
        this.transInventarioRepository = transInventarioRepository;
    }

    public Page<ProductoReporteDTO> getTopSellingProducts(LocalDate startDate, Pageable pageable) {
        
        Page<Object[]> reporteData = transInventarioRepository.findTopSellingReportData(startDate, pageable);

        return reporteData.map(this::mapToObjectArrayToDTO);
    }
    

    private ProductoReporteDTO mapToObjectArrayToDTO(Object[] row) {
        
        Long id = ((Integer) row[0]).longValue(); 
        String nombre = (String) row[1];
        Long unidades = (Long) row[2];          
        BigDecimal ingresos = (BigDecimal) row[3]; 

        return new ProductoReporteDTO(
            id, 
            nombre, 
            unidades, 
            ingresos
        );
    }
}