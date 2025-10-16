package co.edu.unbosque.TallerRendimiento.service;

import co.edu.unbosque.TallerRendimiento.dto.ProductoReporteDTO;
import co.edu.unbosque.TallerRendimiento.repository.TransInventarioRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final TransInventarioRepository transInventarioRepository;

    public ReporteService(TransInventarioRepository transInventarioRepository) {
        this.transInventarioRepository = transInventarioRepository;
    }

    public List<ProductoReporteDTO> getTopSellingProducts(LocalDate startDate) {
        
        List<Object[]> reporteData = transInventarioRepository.findTopSellingReportData(startDate);

        return reporteData.stream()
            .map(this::mapToObjectArrayToDTO)
            .collect(Collectors.toList());
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