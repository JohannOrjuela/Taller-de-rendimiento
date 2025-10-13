package co.edu.unbosque.TallerRendimiento.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.TallerRendimiento.model.Producto;
import co.edu.unbosque.TallerRendimiento.repository.ProductoRepository;

@Service
public class ReportService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Genera el reporte de productos más vendidos.
     * Este reporte utiliza una consulta pesada a la base de datos (requisito de Camila).
     */
    @Transactional(readOnly = true) 
    public List<Producto> obtenerProductosMasVendidos(LocalDate startDate) {
        
        // La fecha 'startDate' se recibe y se usa directamente, sin lógica de conversión.
        // Esto resuelve el problema que tenías en la consulta SQL.
        
        return productoRepository.findTopSellingProducts(startDate);
    }
}