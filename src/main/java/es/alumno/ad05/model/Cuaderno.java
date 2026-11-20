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

    public Cuaderno() {
    }

    public Long getId() {
        return id;
    }

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

    public List<Nota> getNotas() {
        return notas;
    }
}
