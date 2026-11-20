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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getCuadernoId() {
        return cuadernoId;
    }

    public void setCuadernoId(Long cuadernoId) {
        this.cuadernoId = cuadernoId;
    }
}
