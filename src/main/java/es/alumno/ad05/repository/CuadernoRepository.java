package es.alumno.ad05.repository;

import es.alumno.ad05.model.Cuaderno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuadernoRepository extends JpaRepository<Cuaderno, Long> {

    // CONSULTA JPQL PARA LISTAR CUADERNOS YA CON SUS NOTAS CARGADAS.
    @Query("select distinct c from Cuaderno c left join fetch c.notas")
    List<Cuaderno> findAllConNotas();

    // CONSULTA JPQL PARA OBTENER UN CUADERNO Y TODAS SUS NOTAS POR ID.
    @Query("select distinct c from Cuaderno c left join fetch c.notas where c.id = :id")
    Optional<Cuaderno> findConNotasById(@Param("id") Long id);
}
