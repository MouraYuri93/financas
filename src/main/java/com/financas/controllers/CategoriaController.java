package com.financas.controllers;

import com.financas.models.Categoria;
import com.financas.models.Despesa;
import com.financas.models.Mes;
import com.financas.services.CategoriaService;
import com.financas.services.DespesaService;
import com.financas.services.MesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MesService mesService;

    @Autowired
    private DespesaService despesaService;

    public String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String[] words = text.split("_");
        StringBuilder capitalizedText = new StringBuilder();
        for (String word : words) {
            capitalizedText.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return capitalizedText.toString().trim();
    }

    @GetMapping("/categoria")
    public String visualizarCategoria(
            @RequestParam String categoria,
            @RequestParam String mes,
            @RequestParam int ano,  // Certificando-se de que 'ano' também é passado na URL
            Model model
    ) {
        Optional<Categoria> categoriaObj = categoriaService.buscarPorNome(categoria);
        Optional<Mes> mesObj = mesService.buscarPorNomeEAno(mes, ano);  // Alteração aqui

        if (categoriaObj.isPresent() && mesObj.isPresent()) {
            List<Despesa> despesas = despesaService.buscarPorCategoriaEMes(categoriaObj.get(), mesObj.get());
            model.addAttribute("categoria", capitalize(categoriaObj.get().getNome()));
            model.addAttribute("mes", capitalize(mes));
            model.addAttribute("ano", ano);  // Passando o ano para a view
            model.addAttribute("despesas", despesas);
        } else {
            model.addAttribute("erro", "Categoria ou mês não encontrados.");
            return "erro";
        }

        return "categoria";
    }

        @PostMapping("/categoria/incluir")
    public ResponseEntity<Void> incluirCategoria(@RequestBody Categoria categoria) {
        categoriaService.salvar(categoria); // Assumindo que você tem um método para salvar a categoria
        return ResponseEntity.ok().build();
    }
}
