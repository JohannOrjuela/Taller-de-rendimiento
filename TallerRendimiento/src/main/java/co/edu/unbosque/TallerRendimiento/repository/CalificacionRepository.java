package co.edu.unbosque.TallerRendimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.TallerRendimiento.model.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {}
// ---
