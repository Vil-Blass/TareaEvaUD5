package es.alumno.ad05.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "notas")
// ENTIDAD DEPENDIENTE: CADA NOTA GUARDA UNA REFERENCIA A SU CUADERNO.
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 120, message = "El título no puede superar los 120 caracteres.")
    @Column(nullable = false, length = 120)
    private String titulo;

    @NotBlank(message = "El contenido es obligatorio.")
    @Size(max = 2000, message = "El contenido no puede superar los 2000 caracteres.")
    @Column(nullable = false, length = 2000)
    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuaderno_id", nullable = false)
    // MANY-TO-ONE: PERMITE CAMBIAR UNA NOTA DE CUADERNO EN LA EDICIÓN.
    private Cuaderno cuaderno;

    // Constructor vacío que JPA necesita para materializar la entidad.
    public Nota() {
    }

    // Devuelve el identificador asignado a la nota.
    public Long getId() {
        return id;
    }

    // Devuelve el título de la nota.
    public String getTitulo() {
        return titulo;
    }

    // Cambia el título de la nota.
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Devuelve el contenido de la nota.
    public String getContenido() {
        return contenido;
    }

    // Cambia el contenido de la nota.
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    // Devuelve el cuaderno al que pertenece la nota.
    public Cuaderno getCuaderno() {
        return cuaderno;
    }

    // Asigna un cuaderno y mantiene sincronizadas ambas listas de la relación.
    public void setCuaderno(Cuaderno cuaderno) {
        // MANTIENE SINCRONIZADOS LOS DOS EXTREMOS DE LA RELACIÓN EN MEMORIA.
        if (this.cuaderno == cuaderno) {
            return;
        }
        if (this.cuaderno != null) {
            this.cuaderno.getNotas().remove(this);
        }
        this.cuaderno = cuaderno;
        if (cuaderno != null && !cuaderno.getNotas().contains(this)) {
            cuaderno.getNotas().add(this);
        }
    }
}
