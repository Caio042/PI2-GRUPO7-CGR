package br.com.projetointegradorgr3.estoqueglp.domain.service;

import br.com.projetointegradorgr3.estoqueglp.api.dto.RelatorioDto;
import br.com.projetointegradorgr3.estoqueglp.api.dto.RelatorioDto.RelatorioPorProduto;
import br.com.projetointegradorgr3.estoqueglp.domain.exception.NotFoundException;
import br.com.projetointegradorgr3.estoqueglp.domain.exception.UnprocessableEntityException;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Transacao;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Usuario;
import br.com.projetointegradorgr3.estoqueglp.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    private final UsuarioService usuarioService;
    private static final String RECURSO = "Transação";

    public TransacaoService(TransacaoRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public Transacao cadastrar(Transacao transacao) {
        String username = usuarioService.usuarioLogado();
        Usuario usuario = usuarioService.loadUserByUsername(username);
        transacao.setUsuario(usuario);
        transacao.setData(LocalDateTime.now());

        List<Transacao> transacoesAnteriores = buscar(transacao.getProduto());

        int estoqueAnterior = 0;

        if (!transacoesAnteriores.isEmpty()) {
            estoqueAnterior = transacoesAnteriores.getLast().getEstoqueAposTransacao();
        }

        transacao.setEstoqueAposTransacao(estoqueAnterior + transacao.calcularQuantidadeDaTransacao());

        if (transacao.getEstoqueAposTransacao() < 0) {
            throw new UnprocessableEntityException("Valor do estoque não pode ser negativo");
        }


        transacao = repository.save(transacao);

        return transacao;
    }

    public Transacao buscarPorId(Integer id) {
        String username = usuarioService.usuarioLogado();
        Transacao transacaoSemCalculo = repository.findByIdAndUsuarioUsername(id, username).orElseThrow(() -> new NotFoundException(RECURSO, id));
        List<Transacao> transacoes = buscar(transacaoSemCalculo.getProduto());

        return transacoes.stream().filter(transacao -> transacao.getId().equals(transacaoSemCalculo.getId())).findAny().orElseThrow(() -> new IllegalStateException("Problemas ao calcular estoque"));
    }

    public List<Transacao> buscar(String nomeProduto) {
        String username = usuarioService.usuarioLogado();
        List<Transacao> transacoes;
        if (nomeProduto != null) {
            transacoes = repository.findAllByProdutoAndUsuarioUsername(nomeProduto, username);
        } else {
            transacoes = repository.findAllByUsuarioUsername(username);
        }
        preencherQuantidadeEmEstoqueAposOperacao(transacoes);
        return transacoes;
    }

    public RelatorioDto gerarRelatorio(String nomeProduto) {
        String username = usuarioService.usuarioLogado();
        List<Transacao> transacoes;
        if (nomeProduto != null) {
            transacoes = repository.findAllByProdutoAndUsuarioUsername(nomeProduto, username);
        } else {
            transacoes = repository.findAllByUsuarioUsername(username);
        }

        return preencherQuantidadeEmEstoqueAposOperacao(transacoes);
    }

    public int buscarQuantidadeEmEstoque(String produto) {
        List<Transacao> transacoes = buscar(produto);
        if (transacoes.isEmpty()) {
            return 0;
        }

        return transacoes.getLast().getEstoqueAposTransacao();
    }

    public RelatorioDto preencherQuantidadeEmEstoqueAposOperacao(List<Transacao> transacoes) {
        Map<String, List<Transacao>> transacoesAgrupadas = transacoes.stream()
                .collect(Collectors.groupingBy(Transacao::getProduto));

        List<RelatorioPorProduto> porProdutos = new ArrayList<>();

        for (Map.Entry<String, List<Transacao>> entrySet : transacoesAgrupadas.entrySet()) {
            entrySet.getValue().sort(Comparator.comparing(Transacao::getData));

            int estoque = 0;
            BigDecimal totalCompra = BigDecimal.ZERO;
            BigDecimal totalVenda = BigDecimal.ZERO;
            for (Transacao transacao : entrySet.getValue()) {
                estoque += transacao.calcularQuantidadeDaTransacao();
                transacao.setEstoqueAposTransacao(estoque);
                totalCompra = totalCompra.add(transacao.getValorCompra());
                totalVenda = totalVenda.add(transacao.getValorVenda());
            }

            var relatorioPorProduto = new RelatorioPorProduto(entrySet.getKey(), estoque, totalVenda, totalCompra);

            porProdutos.add(relatorioPorProduto);
        }

        BigDecimal geralCompras = porProdutos.stream().map(RelatorioPorProduto::totalCompras).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal geralVendas = porProdutos.stream().map(RelatorioPorProduto::totalVendas).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RelatorioDto(geralVendas, geralCompras, porProdutos);
    }
}
