package es.alumno.ad05.controller;

import es.alumno.ad05.form.CuadernoForm;
import es.alumno.ad05.model.Cuaderno;
import es.alumno.ad05.service.CuadernoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cuadernos")
// CONTROLADOR MVC: RECIBE LAS PETICIONES WEB Y ENTREGA LAS VISTAS THYMELEAF.
public class CuadernoController {

    private final CuadernoService cuadernoService;

    public CuadernoController(CuadernoService cuadernoService) {
        this.cuadernoService = cuadernoService;
    }

    @GetMapping
    public String listar(Model model) {
        // LISTA TODOS LOS CUADERNOS Y SUS NOTAS PARA LA CONSULTA.
        model.addAttribute("cuadernos", cuadernoService.listarConNotas());
        return "cuadernos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cuadernoForm", new CuadernoForm());
        model.addAttribute("tituloPagina", "Nuevo cuaderno");
        model.addAttribute("accion", "/cuadernos");
        return "cuadernos/formulario";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("cuadernoForm") CuadernoForm form,
                        BindingResult bindingResult, Model model,
                        RedirectAttributes redirectAttributes) {
        // VALIDA ANTES DE GUARDAR; SI HAY ERRORES CONSERVA EL FORMULARIO.
        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloPagina", "Nuevo cuaderno");
            model.addAttribute("accion", "/cuadernos");
            return "cuadernos/formulario";
        }
        cuadernoService.guardar(null, form.getNombre(), form.getDescripcion());
        redirectAttributes.addFlashAttribute("mensaje", "Cuaderno creado correctamente.");
        return "redirect:/cuadernos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable long id, Model model) {
        // CARGA LOS DATOS ACTUALES EN EL MISMO FORMULARIO.
        Cuaderno cuaderno = cuadernoService.obtener(id);
        CuadernoForm form = new CuadernoForm();
        form.setNombre(cuaderno.getNombre());
        form.setDescripcion(cuaderno.getDescripcion());
        model.addAttribute("cuadernoForm", form);
        model.addAttribute("tituloPagina", "Editar cuaderno");
        model.addAttribute("accion", "/cuadernos/" + id);
        return "cuadernos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable long id,
                             @Valid @ModelAttribute("cuadernoForm") CuadernoForm form,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes redirectAttributes) {
        // ACTUALIZA EL ID EXISTENTE Y NO CREA UNA NUEVA FILA.
        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloPagina", "Editar cuaderno");
            model.addAttribute("accion", "/cuadernos/" + id);
            return "cuadernos/formulario";
        }
        cuadernoService.guardar(id, form.getNombre(), form.getDescripcion());
        redirectAttributes.addFlashAttribute("mensaje", "Cuaderno actualizado correctamente.");
        return "redirect:/cuadernos";
    }

    @GetMapping("/{id}/eliminar")
    public String confirmarEliminacion(@PathVariable long id, Model model) {
        Cuaderno cuaderno = cuadernoService.obtenerConNotas(id);
        model.addAttribute("cuaderno", cuaderno);
        return "cuadernos/confirmar-eliminar";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable long id, RedirectAttributes redirectAttributes) {
        // EJECUTA LA ELIMINACIÓN SOLO DESDE POST DENTRO DE UNA TRANSACCIÓN.
        cuadernoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Cuaderno y sus notas eliminados correctamente.");
        return "redirect:/cuadernos";
    }
}
