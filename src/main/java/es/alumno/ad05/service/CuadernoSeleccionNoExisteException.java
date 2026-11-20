package es.alumno.ad05.service;

// EXCEPCIÓN DE DOMINIO: INDICA QUE FALTA EL CUADERNO O QUE YA NO EXISTE.
public class CuadernoSeleccionNoExisteException extends RuntimeException {

    public CuadernoSeleccionNoExisteException() {
        super("Debes seleccionar un cuaderno.");
    }

    public CuadernoSeleccionNoExisteException(Long id) {
        super("El cuaderno seleccionado ya no existe (identificador " + id + ").");
    }
}
