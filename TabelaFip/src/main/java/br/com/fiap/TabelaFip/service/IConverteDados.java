package br.com.fiap.TabelaFip.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface IConverteDados {
    <T> T obterDados(String json, TypeReference<T> typeReference);
}
