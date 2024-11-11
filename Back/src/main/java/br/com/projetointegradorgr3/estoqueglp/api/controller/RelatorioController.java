package br.com.projetointegradorgr3.estoqueglp.api.controller;

import br.com.projetointegradorgr3.estoqueglp.api.dto.RelatorioDto;
import br.com.projetointegradorgr3.estoqueglp.domain.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final TransacaoService transacaoService;

    public RelatorioController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    @Operation(summary = "Gerar relatório de compra e venda",
            description = "Buscar todas as transações do usuário; calcula o estoque, a entrada e a saída. Pode ser filtrado pelo id do produto")
    public RelatorioDto gerarRelatorio(@RequestParam(value = "produto", required = false) String produto) {
        return transacaoService.gerarRelatorio(produto);
    }
}
