package com.financas.repositories;

import com.financas.models.CategoriaMes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaMesRepository extends JpaRepository<CategoriaMes, Long> {

    // Busca por categoria, mês e ano
    List<CategoriaMes> findByMesAndAno(String mes, int ano);

}
