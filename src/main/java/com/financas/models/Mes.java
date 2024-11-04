package com.financas.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Mes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false) // Adiciona a coluna ano
    private int ano;

    @OneToMany(mappedBy = "mes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Despesa> despesas;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() { // Getter para ano
        return ano;
    }

    public void setAno(int ano) { // Setter para ano
        this.ano = ano;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }
}
