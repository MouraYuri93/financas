package com.financas.services;

import com.financas.models.Mes;
import com.financas.models.Despesa;
import com.financas.repositories.MesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesService {

    @Autowired
    private MesRepository mesRepository;

    public List<Mes> listarTodos() {
        return mesRepository.findAll();
    }

    public Optional<Mes> buscarPorId(Long id) {
        return mesRepository.findById(id);
    }

    public Mes salvar(Mes mes) {
        return mesRepository.save(mes);
    }

    public void remover(Long id) {
        mesRepository.deleteById(id);
    }

    public Optional<Mes> buscarPorNome(String nome) {
        return mesRepository.findByNome(nome);
    }

    public Optional<Mes> buscarPorNomeEAno(String mesCapitalizado, int ano) {
        return mesRepository.findByNomeAndAno(mesCapitalizado, ano);
    }

    // Metodo para buscar todas as categorias associadas a um mês específico
    public List<Despesa> buscarDespesasPorMesEAno(String mes, int ano) {
        Optional<Mes> mesObj = buscarPorNomeEAno(mes, ano);
        return mesObj.map(Mes::getDespesas).orElse(List.of()); // Retorna as despesas ou uma lista vazia se não existir
    }
}
