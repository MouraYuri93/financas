package com.financas.controllers;

import com.financas.models.Categoria;
import com.financas.models.CategoriaMes;
import com.financas.models.Despesa;
import com.financas.models.Mes;
import com.financas.services.CategoriaService;
import com.financas.services.DespesaService;
import com.financas.services.MesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MesService mesService;

    @Autowired
    private DespesaService despesaService;

    // Método para capitalizar palavras
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

    // Visualizar categoria e despesas associadas
    @GetMapping
    public String visualizarCategoria(
            @RequestParam String categoria,
            @RequestParam String mes,
            @RequestParam int ano,
            Model model
    ) {
        model.addAttribute("mes", mes);
        model.addAttribute("ano", ano);

        Optional<Categoria> categoriaObj = categoriaService.buscarPorNome(categoria);
        Optional<Mes> mesObj = mesService.buscarPorNomeEAno(mes, ano);

        if (categoriaObj.isPresent() && mesObj.isPresent()) {
            List<Despesa> despesas = despesaService.buscarPorCategoriaEMes(categoriaObj.get(), mesObj.get());
            model.addAttribute("categoria", capitalize(categoriaObj.get().getNome()));
            model.addAttribute("despesas", despesas);

            return "despesa"; // Redireciona para o template "despesa.html"
        } else {
            model.addAttribute("erro", "Categoria ou mês não encontrados.");
            return "erro";
        }
    }

    // Incluir nova categoria e associar à tabela categoria_mes
    @PostMapping("/adicionar")
    @ResponseBody
    public ResponseEntity<String> adicionarCategoria(@RequestBody Map<String, String> dados) {
        try {
            String nome = dados.get("nome");
            String mes = dados.get("mes");
            int ano = Integer.parseInt(dados.get("ano"));

            // Cria e salva a nova categoria
            Categoria novaCategoria = new Categoria();
            novaCategoria.setNome(nome);
            novaCategoria.setMes(mes);
            novaCategoria.setAno(ano);

            Categoria categoriaSalva = categoriaService.salvar(novaCategoria);

            // Associa a nova categoria ao mês e ano na tabela categoria_mes
            categoriaService.associarCategoriaMes(categoriaSalva.getId(), mes, ano);

            return ResponseEntity.ok("Categoria adicionada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao adicionar categoria: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    @ResponseBody
    public List<Categoria> listarCategorias(@RequestParam String mes, @RequestParam int ano) {
        System.out.println("Mês: " + mes + " | Ano: " + ano); // Log para debug
        List<Categoria> categorias = categoriaService.buscarPorMesEAno(mes, ano);
        categorias.forEach(c -> System.out.println("Categoria encontrada: " + c.getNome())); // Debug
        return categorias;
    }

    // Atualizar uma categoria
    @PutMapping("/atualizar")
    @ResponseBody
    public ResponseEntity<String> atualizarCategoria(@RequestBody Categoria categoria) {
        categoriaService.atualizar(categoria);
        return ResponseEntity.ok("Categoria atualizada com sucesso!");
    }

    // Deletar uma categoria
    @DeleteMapping("/deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> deletarCategoria(@PathVariable Long id) {
        categoriaService.remover(id);
        return ResponseEntity.ok("Categoria deletada com sucesso!");
    }
}