package es.alumno.ad05.service;

import es.alumno.ad05.model.Cuaderno;
import es.alumno.ad05.repository.CuadernoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// SERVICIO: CONTIENE LAS REGLAS DE NEGOCIO Y LAS OPERACIONES TRANSACCIONALES DE CUADERNOS.
public class CuadernoService {

    private final CuadernoRepository cuadernoRepository;

    public CuadernoService(CuadernoRepository cuadernoRepository) {
        this.cuadernoRepository = cuadernoRepository;
    }

    @Transactional(readOnly = true)
    public List<Cuaderno> listar() {
        return cuadernoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cuaderno> listarConNotas() {
        return cuadernoRepository.findAllConNotas();
    }

    @Transactional(readOnly = true)
    @NonNull
    public Cuaderno obtener(long id) {
        return exigirCuaderno(id);
    }

    @Transactional(readOnly = true)
    @NonNull
    public Cuaderno obtenerConNotas(long id) {
        Cuaderno cuaderno = cuadernoRepository.findConNotasById(id).orElse(null);
        if (cuaderno == null) {
            throw new RecursoNoEncontradoException("El cuaderno", id);
        }
        return cuaderno;
    }

    @NonNull
    private Cuaderno exigirCuaderno(long id) {
        Cuaderno cuaderno = cuadernoRepository.findById(id).orElse(null);
        if (cuaderno == null) {
            throw new RecursoNoEncontradoException("El cuaderno", id);
        }
        return cuaderno;
    }

    @Transactional
    public Cuaderno guardar(Long id, String nombre, String descripcion) {
        // SI EL ID ES NULO CREA; SI EXISTE ACTUALIZA EL MISMO CUADERNO.
        Cuaderno cuaderno = id == null ? new Cuaderno() : exigirCuaderno(id.longValue());
        cuaderno.setNombre(nombre);
        cuaderno.setDescripcion(descripcion);
        return cuadernoRepository.save(cuaderno);
    }

    @Transactional
    public void eliminar(long id) {
        // ELIMINAR EL CUADERNO PROPAGA CASCADE ALL A SUS NOTAS.
        cuadernoRepository.delete(exigirCuaderno(id));
    }
}
