package com.financas.repositories;

import com.financas.models.Categoria;
import com.financas.models.Despesa;
import com.financas.models.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByMes(Mes mes);
    List<Despesa> findByCategoriaAndMes(Categoria categoria, Mes mes);
}
