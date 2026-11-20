package es.alumno.ad05.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuadernos")
// ENTIDAD PRINCIPAL: REPRESENTA EL CUADERNO QUE AGRUPA VARIAS NOTAS.
public class Cuaderno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres.")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @OneToMany(mappedBy = "cuaderno", cascade = CascadeType.ALL)
    // CASCADE ALL: AL ELIMINAR EL CUADERNO, HIBERNATE ELIMINA TAMBIÉN SUS NOTAS.
    private List<Nota> notas = new ArrayList<>();

    // Constructor vacío requerido por JPA para crear instancias de la entidad.
    public Cuaderno() {
    }

    // Devuelve el identificador generado para este cuaderno.
    public Long getId() {
        return id;
    }

    // Devuelve el nombre del cuaderno.
    public String getNombre() {
        return nombre;
    }

    // Actualiza el nombre del cuaderno.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la descripción del cuaderno.
    public String getDescripcion() {
        return descripcion;
    }

    // Actualiza la descripción del cuaderno.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Devuelve la lista de notas asociadas a este cuaderno.
    public List<Nota> getNotas() {
        return notas;
    }
}
