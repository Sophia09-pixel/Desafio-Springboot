package br.com.fiap.TabelaFip.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculoPorId(@JsonAlias("codigo") Integer codigo,
                                @JsonAlias("nome") String nome) {
}
