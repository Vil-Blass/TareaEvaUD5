package es.alumno.ad05.controller;

import es.alumno.ad05.service.CuadernoService;
import es.alumno.ad05.service.NotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// CONTROLADOR DE INICIO: PRESENTA LA PÁGINA PRINCIPAL Y LOS CONTADORES.
public class InicioController {

    private final CuadernoService cuadernoService;
    private final NotaService notaService;

    public InicioController(CuadernoService cuadernoService, NotaService notaService) {
        this.cuadernoService = cuadernoService;
        this.notaService = notaService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("numeroCuadernos", cuadernoService.listar().size());
        model.addAttribute("numeroNotas", notaService.listar().size());
        return "index";
    }
}
