package co.edu.unbosque.TallerRendimiento.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.TallerRendimiento.model.TransInventario;

@Repository
public interface TransInventarioRepository extends JpaRepository<TransInventario, Integer> {
    


    @Query(value = "SELECT p.id_producto, p.nombre_producto, " +
                "CAST(SUM(ti.cantidad_trans_inventario) AS BIGINT) as unidades, " + 
                "SUM(ti.cantidad_trans_inventario * p.precio_producto) as ingresos " + 
                "FROM producto p JOIN trans_inventario ti ON p.id_producto = ti.id_producto " +
                "WHERE ti.tipo_trans_inventario = 'Venta' " +
                "AND ti.fecha_trans_inventario::DATE >= :startDate " +
                "GROUP BY p.id_producto, p.nombre_producto " +
                "ORDER BY unidades DESC ", nativeQuery = true)
    Page<Object[]> findTopSellingReportData(@Param("startDate") LocalDate startDate, Pageable pageable);

}
