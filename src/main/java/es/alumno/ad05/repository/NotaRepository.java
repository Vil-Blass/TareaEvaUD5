package es.alumno.ad05.repository;

import es.alumno.ad05.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    // Devuelve todas las notas cargando también sus cuadernos en la misma consulta.
    @Query("select n from Nota n join fetch n.cuaderno")
    List<Nota> findAllConCuaderno();

    // Busca una nota por ID y carga en la misma consulta el cuaderno asociado.
    @Query("select n from Nota n join fetch n.cuaderno where n.id = :id")
    Optional<Nota> findConCuadernoById(@Param("id") Long id);
}
