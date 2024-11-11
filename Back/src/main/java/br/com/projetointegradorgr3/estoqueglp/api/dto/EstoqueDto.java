package br.com.projetointegradorgr3.estoqueglp.api.dto;

import br.com.projetointegradorgr3.estoqueglp.domain.model.Transacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EstoqueDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Integer id,

        @NotBlank
        String produto,

        String fornecedor,

        @JsonProperty("quantidade_entrada")
        @PositiveOrZero
        Integer entrada,

        @JsonProperty("quantidade_saida")
        @PositiveOrZero
        Integer saida,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty("estoque_apos_operacao")
        Integer estoque,

        @JsonProperty("valor_compra")
        @PositiveOrZero
        BigDecimal valorCompra,

        @JsonProperty("valor_venda")
        @PositiveOrZero
        BigDecimal valorVenda,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty("data_transacao")
        LocalDateTime data
) {

    public Transacao converter() {
        Transacao transacao = new Transacao();
        transacao.setProduto(produto);
        transacao.setEntradas(entrada == null ? 0 : entrada);
        transacao.setVendas(saida == null ? 0 : saida);
        transacao.setValorCompra(valorCompra == null ? BigDecimal.ZERO : valorCompra);
        transacao.setValorVenda(valorVenda == null ? BigDecimal.ZERO : valorVenda);
        transacao.setNomeFornecedor(fornecedor);

        return transacao;
    }

    public EstoqueDto(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getProduto(),
                transacao.getNomeFornecedor(),
                transacao.getEntradas(),
                transacao.getVendas(),
                transacao.getEstoqueAposTransacao(),
                transacao.getValorCompra(),
                transacao.getValorVenda(),
                transacao.getData()
        );
    }
}
