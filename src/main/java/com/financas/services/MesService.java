package com.financas.services;

import com.financas.models.Mes;
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
}
