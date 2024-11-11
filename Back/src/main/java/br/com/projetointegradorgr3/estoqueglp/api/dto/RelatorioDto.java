package br.com.projetointegradorgr3.estoqueglp.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioDto(

        @JsonProperty("total_em_vendas")
        BigDecimal totalVendas,

        @JsonProperty("total_em_compras")
        BigDecimal totalCompras,

        List<RelatorioPorProduto> porProdutos
) {

    public record RelatorioPorProduto(
            String produto,

            Integer quantidade,

            @JsonProperty("total_em_vendas")
            BigDecimal totalVendas,

            @JsonProperty("total_em_compras")
            BigDecimal totalCompras
    ) {

    }
}
