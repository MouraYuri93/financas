package com.financas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoriaController {

    @GetMapping("/categoria")
    public String visualizarCategoria(
            @RequestParam String categoria,
            @RequestParam String mes,
            Model model
    ) {
        model.addAttribute("categoria", categoria);
        model.addAttribute("mes", mes);
        // Aqui, você pode adicionar a lógica para buscar as despesas da categoria e mês especificados
        return "categoria"; // Retorna o template 'categoria.html'
    }
}