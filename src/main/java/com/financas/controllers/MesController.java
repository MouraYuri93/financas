package com.financas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MesController {

    @GetMapping("/mes")
    public String visualizarMes(@RequestParam String mes, Model model) {
        // Capitaliza a primeira letra do mês
        String mesCapitalizado = mes.substring(0, 1).toUpperCase() + mes.substring(1);
        model.addAttribute("mes", mesCapitalizado);
        // Aqui, você pode adicionar a lógica para buscar as despesas totais de cada categoria no mês especificado
        return "mes"; // Retorna o template 'mes.html'
    }
}