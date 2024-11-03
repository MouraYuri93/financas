package com.financas.controllers;

import com.financas.models.Despesa;
import com.financas.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    // Método para criar uma nova despesa
    @GetMapping("/nova")
    public String novaDespesa(@RequestParam String categoria, @RequestParam String mes, Model model) {
        model.addAttribute("categoria", categoria);
        model.addAttribute("mes", mes);
        return "nova-despesa"; // Retorna o template 'nova-despesa.html'
    }

    // Método para salvar uma despesa
    @PostMapping("/salvar")
    public String salvarDespesa(@ModelAttribute Despesa despesa) {
        despesaService.salvar(despesa);
        return "redirect:/categoria?categoria=" + despesa.getCategoria() + "&mes=" + despesa.getMes();
    }

    // Método para editar uma despesa
    @GetMapping("/editar")
    public String editarDespesa(@RequestParam Long id, Model model) {
        Despesa despesa = despesaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        model.addAttribute("despesa", despesa);
        return "editar-despesa"; // Retorna o template 'editar-despesa.html'
    }

    // Método para atualizar uma despesa
    @PostMapping("/atualizar")
    public String atualizarDespesa(@ModelAttribute Despesa despesa) {
        despesaService.atualizar(despesa);
        return "redirect:/categoria?categoria=" + despesa.getCategoria() + "&mes=" + despesa.getMes();
    }

    // Método para remover uma despesa
    @PostMapping("/remover")
    public String removerDespesa(@RequestParam Long id) {
        Despesa despesa = despesaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        despesaService.remover(id);
        return "redirect:/categoria?categoria=" + despesa.getCategoria() + "&mes=" + despesa.getMes();
    }
}