package com.financas.repositories;

import com.financas.models.Despesa;
import com.financas.models.Categoria;
import com.financas.models.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    // Busca todas as despesas de uma determinada categoria
    List<Despesa> findByCategoria(Categoria categoria);

    // Busca todas as despesas de um determinado mês
    List<Despesa> findByMes(Mes mes);

    // Busca todas as despesas de uma categoria e mês específicos
    List<Despesa> findByCategoriaAndMes(Categoria categoria, Mes mes);
}
