package br.com.fiap.TabelaFip.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RetornoModelos(
        @JsonAlias("modelos") List<DadosVeiculoPorId> modelos
) {
}
