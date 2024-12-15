package com.financas.services;

import com.financas.models.Categoria;
import com.financas.models.CategoriaMes;
import com.financas.models.Mes;
import com.financas.repositories.CategoriaMesRepository;
import com.financas.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMesRepository categoriaMesRepository;

    // Retorna todas as categorias
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Busca por nome de categoria
    public Optional<Categoria> buscarPorNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    // Salva uma nova categoria
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Remove uma categoria pelo ID
    public void remover(Long id) {
        categoriaRepository.deleteById(id);
    }

    // Atualiza uma categoria existente
    public Categoria atualizar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Busca categorias por nome e mês
    public List<Categoria> buscarPorNomeEMes(String nome, Mes mes) {
        return categoriaRepository.findByNomeAndMes(nome, mes);
    }

    // Busca por nome e ano
    public Optional<Categoria> buscarPorNomeEano(String nome, int ano) {
        return categoriaRepository.findByNomeAndAno(nome, ano);
    }

    // Associa uma categoria a um mês e ano específico
    public CategoriaMes associarCategoriaMes(Long categoriaId, String mes, int ano) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        if (categoria.isPresent()) {
            CategoriaMes categoriaMes = new CategoriaMes();
            categoriaMes.setCategoria(categoria.get());
            categoriaMes.setMes(mes);
            categoriaMes.setAno(ano);
            return categoriaMesRepository.save(categoriaMes);
        }
        throw new IllegalArgumentException("Categoria não encontrada com ID: " + categoriaId);
    }

    // Busca todas as categorias associadas a um mês e ano específicos
    public List<Categoria> buscarPorMesEAno(String mes, int ano) {
        return categoriaRepository.findAll().stream()
                .filter(c -> c.getMes().equalsIgnoreCase(mes) && c.getAno() == ano)
                .toList();
    }

    // Busca uma categoria pelo ID
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }
}
