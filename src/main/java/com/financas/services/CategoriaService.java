package com.financas.services;

import com.financas.models.Categoria;
import com.financas.models.Mes;
import com.financas.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

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

}
