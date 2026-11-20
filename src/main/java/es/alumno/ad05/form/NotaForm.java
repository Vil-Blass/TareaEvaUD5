package es.alumno.ad05.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// OBJETO DEL FORMULARIO: GUARDA LOS TEXTOS Y EL ID DEL CUADERNO SELECCIONADO.
public class NotaForm {

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 120, message = "El título no puede superar los 120 caracteres.")
    private String titulo;

    @NotBlank(message = "El contenido es obligatorio.")
    @Size(max = 2000, message = "El contenido no puede superar los 2000 caracteres.")
    private String contenido;

    @NotNull(message = "Selecciona un cuaderno.")
    private Long cuadernoId;

    // Devuelve el título recibido desde el formulario web.
    public String getTitulo() {
        return titulo;
    }

    // Guarda el título enviado en el formulario.
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Devuelve el contenido recibido desde el formulario web.
    public String getContenido() {
        return contenido;
    }

    // Guarda el contenido enviado en el formulario.
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    // Devuelve el identificador del cuaderno seleccionado.
    public Long getCuadernoId() {
        return cuadernoId;
    }

    // Guarda el identificador del cuaderno seleccionado en el formulario.
    public void setCuadernoId(Long cuadernoId) {
        this.cuadernoId = cuadernoId;
    }
}
