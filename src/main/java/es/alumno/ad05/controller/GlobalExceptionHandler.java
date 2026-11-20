package es.alumno.ad05.controller;

import es.alumno.ad05.service.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
// MANEJADOR GLOBAL: CONVIERTE LOS ID NO EXISTENTES EN UNA PÁGINA COMPRENSIBLE.
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String recursoNoEncontrado(RecursoNoEncontradoException exception, Model model) {
        model.addAttribute("mensajeError", exception.getMessage());
        return "error/404";
    }
}
