package com.financas.services;

import com.financas.models.Categoria;
import com.financas.models.Despesa;
import com.financas.models.Mes;
import com.financas.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public List<Despesa> listarTodas() {
        return despesaRepository.findAll();
    }

    public Optional<Despesa> buscarPorId(Long id) {
        return despesaRepository.findById(id);
    }

    public List<Despesa> buscarPorCategoria(Categoria categoria) {
        return despesaRepository.findByCategoria(categoria);
    }

    public List<Despesa> buscarPorMes(Mes mes) {
        return despesaRepository.findByMes(mes);
    }

    public List<Despesa> buscarPorCategoriaEMes(Categoria categoria, Mes mes) {
        return despesaRepository.findByCategoriaAndMes(categoria, mes);
    }

    public Despesa salvar(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public void remover(Long id) {
        despesaRepository.deleteById(id);
    }

    public void atualizar(Despesa despesa) {

    }
}
