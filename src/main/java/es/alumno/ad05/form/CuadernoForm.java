package es.alumno.ad05.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// OBJETO DEL FORMULARIO: SEPARA LOS DATOS INTRODUCIDOS POR EL USUARIO DE LA ENTIDAD JPA.
public class CuadernoForm {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String descripcion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
