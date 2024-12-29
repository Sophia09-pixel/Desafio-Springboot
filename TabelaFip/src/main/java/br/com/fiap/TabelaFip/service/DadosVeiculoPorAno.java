package br.com.fiap.TabelaFip.service;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosVeiculoPorAno(@JsonAlias("codigo") String codigo,
                                 @JsonAlias("nome") String nome) {
}
