package com.financas.repositories;

import com.financas.models.Mes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MesRepository extends JpaRepository<Mes, Long> {
    Optional<Mes> findByNome(String nome);

    Optional<Mes> findByNomeAndAno(String nome, int ano); // Novo metodo para buscar por nome e ano
}
