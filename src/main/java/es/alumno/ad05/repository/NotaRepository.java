package es.alumno.ad05.repository;

import es.alumno.ad05.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    // CONSULTA JPQL PARA MOSTRAR LOS DATOS DESCRIPTIVOS DEL CUADERNO DE CADA NOTA.
    @Query("select n from Nota n join fetch n.cuaderno")
    List<Nota> findAllConCuaderno();

    @Query("select n from Nota n join fetch n.cuaderno where n.id = :id")
    Optional<Nota> findConCuadernoById(@Param("id") Long id);
}
