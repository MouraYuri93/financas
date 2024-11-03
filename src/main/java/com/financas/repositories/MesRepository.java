package com.financas.repositories;

import com.financas.models.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesRepository extends JpaRepository<Mes, Long> {
    // Métodos de consulta específicos para Mes, se necessário
}
