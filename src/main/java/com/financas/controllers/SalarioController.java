package com.financas.controllers;

import com.financas.services.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/salario")
public class SalarioController {

    @Autowired
    private SalarioService salarioService;

    @PostMapping
    public Map<String, Object> definirSalario(@RequestBody Map<String, Object> dados) {
        BigDecimal valor = new BigDecimal(dados.get("valor").toString());
        salarioService.definirSalario(valor);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Salário salvo com sucesso.");
        return response;
    }

    @GetMapping
    public BigDecimal obterSalario() {
        return salarioService.obterSalarioAtual();
    }
}
