package es.alumno.ad05.service;

import es.alumno.ad05.model.Cuaderno;
import es.alumno.ad05.model.Nota;
import es.alumno.ad05.repository.CuadernoRepository;
import es.alumno.ad05.repository.NotaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// SERVICIO: VALIDA EL CUADERNO SELECCIONADO Y COORDINA LAS OPERACIONES DE NOTAS.
public class NotaService {

    private final NotaRepository notaRepository;
    private final CuadernoRepository cuadernoRepository;

    public NotaService(NotaRepository notaRepository, CuadernoRepository cuadernoRepository) {
        this.notaRepository = notaRepository;
        this.cuadernoRepository = cuadernoRepository;
    }

    @Transactional(readOnly = true)
    public List<Nota> listar() {
        return notaRepository.findAllConCuaderno();
    }

    @Transactional(readOnly = true)
    @NonNull
    public Nota obtener(long id) {
        return exigirNota(id);
    }

    @NonNull
    private Nota exigirNota(long id) {
        Nota nota = notaRepository.findConCuadernoById(id).orElse(null);
        if (nota == null) {
            throw new RecursoNoEncontradoException("La nota", id);
        }
        return nota;
    }

    @Transactional
    public Nota guardar(Long id, String titulo, String contenido, Long cuadernoId) {
        // COMPRUEBA EL CUADERNO ANTES DE CREAR O ACTUALIZAR LA NOTA.
        if (cuadernoId == null) {
            throw new CuadernoSeleccionNoExisteException();
        }
        Cuaderno cuaderno = cuadernoRepository.findById(cuadernoId)
                .orElseThrow(() -> new CuadernoSeleccionNoExisteException(cuadernoId));
        Nota nota = id == null ? new Nota() : exigirNota(id.longValue());
        nota.setTitulo(titulo);
        nota.setContenido(contenido);
        nota.setCuaderno(cuaderno);
        return notaRepository.save(nota);
    }

    @Transactional
    public void eliminar(long id) {
        // ELIMINA SOLO LA NOTA SEÑALADA Y CONSERVA SU CUADERNO.
        notaRepository.delete(exigirNota(id));
    }
}
