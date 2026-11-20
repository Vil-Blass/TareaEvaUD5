package es.alumno.ad05.service;

// EXCEPCIÓN DE DOMINIO: INDICA QUE NO EXISTE UNA ENTIDAD PARA EL ID SOLICITADO.
public class RecursoNoEncontradoException extends RuntimeException {

    // Crea el error indicando qué recurso falta y el identificador buscado.
    public RecursoNoEncontradoException(String recurso, Long id) {
        super(recurso + " con identificador " + id + " no existe.");
    }
}
