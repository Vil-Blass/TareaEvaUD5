package es.alumno.ad05.controller;

import es.alumno.ad05.form.NotaForm;
import es.alumno.ad05.model.Nota;
import es.alumno.ad05.service.CuadernoSeleccionNoExisteException;
import es.alumno.ad05.service.CuadernoService;
import es.alumno.ad05.service.NotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notas")
// CONTROLADOR MVC: GESTIONA ALTA, EDICIÓN, LISTADO Y BORRADO DE NOTAS.
public class NotaController {

    private static final String FORM_OBJECT = "notaForm";
    private static final String FORM_VIEW = "notas/formulario";

    private final NotaService notaService;
    private final CuadernoService cuadernoService;

    // Recibe los servicios que gestionan las notas y los cuadernos asociados.
    public NotaController(NotaService notaService, CuadernoService cuadernoService) {
        this.notaService = notaService;
        this.cuadernoService = cuadernoService;
    }

    @GetMapping
    // Muestra el listado de notas.
    public String listar(Model model) {
        model.addAttribute("notas", notaService.listar());
        return "notas/lista";
    }

    @GetMapping("/nueva")
    // Prepara el formulario de alta y redirige si todavía no hay cuadernos.
    public String nueva(Model model, RedirectAttributes redirectAttributes) {
        if (cuadernoService.listar().isEmpty()) {
            redirectAttributes.addFlashAttribute("aviso",
                    "Crea primero un cuaderno para poder añadir notas.");
            return "redirect:/cuadernos/nuevo";
        }
        model.addAttribute(FORM_OBJECT, new NotaForm());
        prepararFormulario(model, "Nueva nota", "/notas");
        return FORM_VIEW;
    }

    @PostMapping
    // Recibe el formulario de alta y delega validación y guardado.
    public String crear(@Valid @ModelAttribute("notaForm") NotaForm form,
                        BindingResult bindingResult, Model model,
                        RedirectAttributes redirectAttributes) {
        // COMPARTE EL MISMO PROCESO DE VALIDACIÓN Y GUARDADO CON LA EDICIÓN.
        return guardar(null, form, bindingResult, model, redirectAttributes, "Nueva nota", "/notas");
    }

    @GetMapping("/{id}/editar")
    // Carga una nota existente y rellena el formulario para editarla.
    public String editar(@PathVariable long id, Model model) {
        // CARGA LA SELECCIÓN ACTUAL DEL CUADERNO ANTES DE MOSTRAR EL FORMULARIO.
        Nota nota = notaService.obtener(id);
        NotaForm form = new NotaForm();
        form.setTitulo(nota.getTitulo());
        form.setContenido(nota.getContenido());
        form.setCuadernoId(nota.getCuaderno().getId());
        model.addAttribute(FORM_OBJECT, form);
        prepararFormulario(model, "Editar nota", "/notas/" + id);
        return FORM_VIEW;
    }

    @PostMapping("/{id}")
    // Recibe los cambios de una nota existente y delega el guardado.
    public String actualizar(@PathVariable long id,
                             @Valid @ModelAttribute("notaForm") NotaForm form,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        // ACTUALIZA LA MISMA NOTA Y PERMITE CAMBIAR SU CUADERNO.
        return guardar(id, form, bindingResult, model, redirectAttributes,
                "Editar nota", "/notas/" + id);
    }

    @GetMapping("/{id}/eliminar")
    // Muestra los datos de la nota antes de confirmar su eliminación.
    public String confirmarEliminacion(@PathVariable long id, Model model) {
        model.addAttribute("nota", notaService.obtener(id));
        return "notas/confirmar-eliminar";
    }

    @PostMapping("/{id}/eliminar")
    // Elimina la nota tras la confirmación y redirige al listado.
    public String eliminar(@PathVariable long id, RedirectAttributes redirectAttributes) {
        // BORRA SOLO ESTA NOTA; NO AFECTA AL CUADERNO NI A OTRAS NOTAS.
        notaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Nota eliminada correctamente.");
        return "redirect:/notas";
    }

    // Valida y guarda una nota; si hay errores, vuelve a mostrar el formulario.
    private String guardar(Long id, NotaForm form, BindingResult bindingResult, Model model,
                           RedirectAttributes redirectAttributes, String titulo, String accion) {
        if (cuadernoService.listar().isEmpty()) {
            model.addAttribute("aviso", "Crea primero un cuaderno para poder guardar notas.");
            model.addAttribute("cuadernos", cuadernoService.listar());
            prepararFormulario(model, titulo, accion);
            return FORM_VIEW;
        }
        if (bindingResult.hasErrors()) {
            prepararFormulario(model, titulo, accion);
            return FORM_VIEW;
        }
        try {
            notaService.guardar(id, form.getTitulo(), form.getContenido(), form.getCuadernoId());
        } catch (CuadernoSeleccionNoExisteException exception) {
            String mensaje = exception.getMessage();
            if (mensaje == null) {
                mensaje = "El cuaderno seleccionado no existe.";
            }
            bindingResult.addError(new FieldError(FORM_OBJECT, "cuadernoId",
                    mensaje));
            prepararFormulario(model, titulo, accion);
            return FORM_VIEW;
        }
        redirectAttributes.addFlashAttribute("mensaje",
                id == null ? "Nota creada correctamente." : "Nota actualizada correctamente.");
        return "redirect:/notas";
    }

    // Añade al modelo los datos comunes que necesita el formulario de notas.
    private void prepararFormulario(Model model, String titulo, String accion) {
        model.addAttribute("cuadernos", cuadernoService.listar());
        model.addAttribute("tituloPagina", titulo);
        model.addAttribute("accion", accion);
    }
}
