package com.financas.services;

import com.financas.models.Categoria;
import com.financas.models.Despesa;
import com.financas.models.Mes;
import com.financas.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        despesaRepository.save(despesa);
    }

    public BigDecimal calcularTotalDespesasPorMes(Mes mesObj) {
        List<Despesa> despesas = despesaRepository.findByMes(mesObj);
        BigDecimal total = BigDecimal.ZERO;

        for (Despesa despesa : despesas) {
            total = total.add(despesa.getValor());
        }

        return total;
    }

    public BigDecimal calcularTotalPorMes(Mes mesEncontrado) {
        return calcularTotalDespesasPorMes(mesEncontrado);
    }

    public List<Despesa> buscarPorMes(Mes mesEncontrado) {
        return despesaRepository.findByMes(mesEncontrado);
    }
}
