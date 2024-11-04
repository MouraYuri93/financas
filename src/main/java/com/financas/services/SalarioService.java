package com.financas.services;

import com.financas.models.Salario;
import com.financas.repositories.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SalarioService {

    @Autowired
    private SalarioRepository salarioRepository;

    public Salario definirSalario(BigDecimal valor) {
        Salario salario = new Salario();
        salario.setValor(valor);
        return salarioRepository.save(salario);
    }

    public BigDecimal obterSalarioAtual() {
        return salarioRepository.findAll()
                .stream()
                .findFirst()
                .map(Salario::getValor)
                .orElse(BigDecimal.ZERO);
    }
}
