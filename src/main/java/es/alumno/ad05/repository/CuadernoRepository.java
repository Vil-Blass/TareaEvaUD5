package es.alumno.ad05.repository;

import es.alumno.ad05.model.Cuaderno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuadernoRepository extends JpaRepository<Cuaderno, Long> {

    // Devuelve todos los cuadernos cargando sus notas asociadas.
    @Query("select distinct c from Cuaderno c left join fetch c.notas")
    List<Cuaderno> findAllConNotas();

    // Busca por ID un cuaderno y carga sus notas, aunque no tenga ninguna.
    @Query("select distinct c from Cuaderno c left join fetch c.notas where c.id = :id")
    Optional<Cuaderno> findConNotasById(@Param("id") Long id);
}
