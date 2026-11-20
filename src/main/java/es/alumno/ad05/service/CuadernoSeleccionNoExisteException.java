package es.alumno.ad05.service;

// EXCEPCIÓN DE DOMINIO: INDICA QUE FALTA EL CUADERNO O QUE YA NO EXISTE.
public class CuadernoSeleccionNoExisteException extends RuntimeException {

    // Crea el error que se muestra cuando no se ha seleccionado ningún cuaderno.
    public CuadernoSeleccionNoExisteException() {
        super("Debes seleccionar un cuaderno.");
    }

    // Crea el error que se muestra cuando el cuaderno seleccionado ya no existe.
    public CuadernoSeleccionNoExisteException(Long id) {
        super("El cuaderno seleccionado ya no existe (identificador " + id + ").");
    }
}
