package co.edu.unbosque.TallerRendimiento.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class Controller {

    @GetMapping ("/hola")
    public String holaMundo() {
        return "Hola Mundo!";
    }

    @GetMapping("/holanombre/{nombre}/{edad}")
    public String holaMundoNombre(@PathVariable String nombre, @PathVariable String edad){
        return "Hola ! " + nombre + " ¿" +edad +"?";
    }
}
