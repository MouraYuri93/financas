package com.financas.repositories;

import com.financas.models.Categoria;
import com.financas.models.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Busca uma categoria pelo nome
    Optional<Categoria> findByNome(String nome);

    // Busca uma categoria pelo ID
    Optional<Categoria> findById(Long id);

    // Busca categorias pelo nome e pelo mês
    List<Categoria> findByNomeAndMes(String nome, Mes mes);

    // Busca categoria por nome e ano
    Optional<Categoria> findByNomeAndAno(String nome, int ano);
}
