package co.edu.unbosque.TallerRendimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.TallerRendimiento.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Listo. JPA te proporciona todos los métodos CRUD básicos.
}