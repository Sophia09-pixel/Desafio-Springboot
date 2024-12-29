package br.com.fiap.TabelaFip.principal;

import br.com.fiap.TabelaFip.service.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner entrada = new Scanner(System.in);
    private ConsumoApi api = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private ConverteDadosVeiculoId conversorVeiculoId = new ConverteDadosVeiculoId();
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private final String APIKEY = "/marcas";
    private final String BARRA = "/";

    public void exibirMenu(){

      System.out.println("Digite qual veiculo voce gostaria de pesquisar -> (carros/motos/caminhoes)");
      var veiculo = entrada.nextLine();

      while(!veiculo.equals("carros") || veiculo.equals("motos") || veiculo.equals("caminhoes")){
          System.out.println("Opção inválida! -> (carros/motos/caminhoes)");
          veiculo = entrada.nextLine();
      }
      var json = api.obterDados(ENDERECO + veiculo + APIKEY);

      List<DadosVeiculo> dadosVeiculos = conversor.obterDados(json, new TypeReference<List<DadosVeiculo>>() {});

      System.out.println(json);
      dadosVeiculos.forEach(System.out::println);

      System.out.println("Agora digite o código do veiculo:");
      int idVeiculo = entrada.nextInt();

      json = api.obterDados(ENDERECO + veiculo + APIKEY + BARRA + idVeiculo + "/modelos");

      System.out.println(json);

      RetornoModelos retornoModelos = conversor.obterDados(json, new TypeReference<RetornoModelos>() {});
      List<DadosVeiculoPorId> dadosVeiculoPorIds = retornoModelos.modelos();
      dadosVeiculoPorIds.forEach(System.out::println);

        System.out.println("Digite o código do modelo do veículo para listar avaliações de todos os anos:");
        var codVeiculo = entrada.nextInt(); // Código do modelo escolhido

        json = api.obterDados(ENDERECO + veiculo + APIKEY + "/" + idVeiculo + "/modelos/" + codVeiculo + "/anos");
        System.out.println("Anos disponíveis: " + json);

        List<DadosVeiculoPorAno> dadosPorAno = conversor.obterDados(json, new TypeReference<List<DadosVeiculoPorAno>>() {});
        dadosPorAno.forEach(System.out::println);


        List<DadosCompletosVeiculo> avaliacoesPorAno = new ArrayList<>();


        for (DadosVeiculoPorAno anoDisponivel : dadosPorAno) {
            String ano = anoDisponivel.codigo(); // Obtém o código do ano atual

            json = api.obterDados(ENDERECO + veiculo + APIKEY + "/" + idVeiculo + "/modelos/" + codVeiculo + "/anos/" + ano);
            System.out.println("Detalhes para o ano " + ano + ": " + json);

            DadosCompletosVeiculo dadosCompletos = conversor.obterDados(json, new TypeReference<DadosCompletosVeiculo>() {});
            avaliacoesPorAno.add(dadosCompletos);
        }


        System.out.println("Avaliações completas para o modelo e anos disponíveis:");
        avaliacoesPorAno.forEach(System.out::println);

      System.out.println("Utilizando Streams (plus)");
      System.out.println("Codigos dos veiculos em ordem decrescente");
      dadosVeiculoPorIds.stream().sorted(Comparator.comparing(DadosVeiculoPorId::codigo).reversed()).forEach(System.out::println);

      System.out.println("Maior código: ");
      dadosVeiculoPorIds.stream().max(Comparator.comparing(DadosVeiculoPorId::codigo)).ifPresent(System.out::println);

      System.out.println("Menos código: ");
      dadosVeiculoPorIds.stream().min(Comparator.comparing(DadosVeiculoPorId::codigo)).ifPresent(System.out::println);
    }
}
