package com.financas.controllers;

import com.financas.models.Categoria;
import com.financas.models.Despesa;
import com.financas.models.Mes;
import com.financas.services.CategoriaService;
import com.financas.services.DespesaService;
import com.financas.services.MesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MesService mesService;

    @GetMapping("/nova")
    public String novaDespesa(@RequestParam String categoria, @RequestParam String mes, Model model) {
        Optional<Categoria> categoriaObj = categoriaService.buscarPorNome(categoria);
        Optional<Mes> mesObj = mesService.buscarPorNome(mes);

        if (categoriaObj.isPresent() && mesObj.isPresent()) {
            Despesa despesa = new Despesa();
            despesa.setCategoria(categoriaObj.get());
            despesa.setMes(mesObj.get());

            model.addAttribute("despesa", despesa);
            model.addAttribute("categoriaSelect", categoriaObj.get().getId());
            return "nova-despesa";
        } else {
            model.addAttribute("erro", "Categoria ou mês não encontrados.");
            return "erro";
        }
    }

    @PostMapping("/despesa/incluir")
    public ResponseEntity<String> incluirDespesa(@RequestBody Despesa despesa) {
        try {
            despesaService.salvar(despesa);
            return ResponseEntity.ok("Despesa incluída com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao incluir despesa: " + e.getMessage());
        }
    }

    @GetMapping("/editar")
    public String editarDespesa(@RequestParam Long id, Model model) {
        Optional<Despesa> despesa = despesaService.buscarPorId(id);
        if (despesa.isPresent()) {
            model.addAttribute("despesa", despesa.get());
            model.addAttribute("categoriaSelect", despesa.get().getCategoria().getId());
            return "editar-despesa";
        } else {
            model.addAttribute("erro", "Despesa não encontrada.");
            return "erro";
        }
    }

    @PostMapping("/atualizar")
    public String atualizarDespesa(@ModelAttribute Despesa despesa) {
        despesaService.atualizar(despesa);
        return "redirect:/categoria?categoria=" + despesa.getCategoria().getNome() + "&mes=" + despesa.getMes().getNome();
    }

    @PostMapping("/remover")
    public String removerDespesa(@RequestParam Long id, Model model) {
        Optional<Despesa> despesa = despesaService.buscarPorId(id);
        if (despesa.isPresent()) {
            despesaService.remover(id);
            return "redirect:/categoria?categoria=" + despesa.get().getCategoria().getNome() + "&mes=" + despesa.get().getMes().getNome();
        } else {
            model.addAttribute("erro", "Despesa não encontrada.");
            return "erro";
        }
    }
}
