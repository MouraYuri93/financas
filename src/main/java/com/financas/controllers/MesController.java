package com.financas.controllers;

import com.financas.models.Mes;
import com.financas.models.Despesa;
import com.financas.services.CategoriaService;
import com.financas.services.DespesaService;
import com.financas.services.MesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.financas.models.Categoria;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class MesController {

    @Autowired
    private MesService mesService;

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/mes")
    public String visualizarMes(@RequestParam String mes, @RequestParam int ano, Model model) {
        String mesCapitalizado = mes.substring(0, 1).toUpperCase() + mes.substring(1);

        Optional<Mes> mesObj = mesService.buscarPorNomeEAno(mesCapitalizado, ano);

        if (mesObj.isPresent()) {
            Mes mesEncontrado = mesObj.get();
            BigDecimal totalMes = despesaService.calcularTotalPorMes(mesEncontrado);
            List<Despesa> despesas = despesaService.buscarPorMes(mesEncontrado);
            List<Categoria> categorias = categoriaService.listarTodas(); // Busca todas as categorias

            model.addAttribute("mes", mesCapitalizado);
            model.addAttribute("totalMes", totalMes);
            model.addAttribute("ano", ano);
            model.addAttribute("despesas", despesas);
            model.addAttribute("categorias", categorias); // Adiciona as categorias ao modelo
        } else {
            model.addAttribute("mes", mesCapitalizado);
            model.addAttribute("totalMes", BigDecimal.ZERO);
            model.addAttribute("ano", ano);
            model.addAttribute("despesas", List.of());
            model.addAttribute("categorias", List.of());
        }

        return "mes"; // Retorna a view mes.html
    }
}
