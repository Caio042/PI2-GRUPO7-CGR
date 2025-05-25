package br.com.projetointegradorgr3.estoqueglp.domain.service;

import br.com.projetointegradorgr3.estoqueglp.api.dto.RelatorioDto;
import br.com.projetointegradorgr3.estoqueglp.domain.exception.UnprocessableEntityException;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Transacao;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Usuario;
import br.com.projetointegradorgr3.estoqueglp.domain.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Testes de transações financeiras")
class TransacaoServiceTest {

    private final TransacaoRepository repository = mock(TransacaoRepository.class);
    private final UsuarioService usuarioService = mock(UsuarioService.class);
    private final TransacaoService service = new TransacaoService(repository, usuarioService);
    
    private final String NOME_PRODUTO = "botijão de 10 litros";
    private final String USUARIO_LOGADO = "email@email.com";
    private final String PASSWORD = "1234";

    @Test
    @DisplayName("Cenário 1: Consulta correta do estoque após compra e venda")
    void buscar_deveRetornar95EmEstoque_dadoQueUmaCompraCom100EUmaVendaCom5() {

        Transacao compra = new Transacao();

        compra.setData(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        compra.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        compra.setNomeFornecedor("juca gas");
        compra.setValorVenda(BigDecimal.ZERO);
        compra.setValorCompra(BigDecimal.valueOf(70));
        compra.setVendas(0);
        compra.setEntradas(100);
        compra.setProduto(NOME_PRODUTO);

        Transacao venda = new Transacao();

        venda.setData(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        venda.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        venda.setNomeFornecedor("juca gas");
        venda.setValorVenda(BigDecimal.valueOf(120));
        venda.setValorCompra(BigDecimal.ZERO);
        venda.setVendas(5);
        venda.setEntradas(0);
        venda.setProduto(NOME_PRODUTO);

        when(usuarioService.usuarioLogado()).thenReturn(USUARIO_LOGADO);
        when(repository.findAllByUsuarioUsername(USUARIO_LOGADO)).thenReturn(List.of(compra, venda));


        assertThat(service.buscar(null))
                .hasSize(2)
                .last()
                .returns(95, Transacao::getEstoqueAposTransacao);
    }

    @Test
    @DisplayName("Cenário 4: Venda não permitida por ultrapassar o estoque")
    void cadastrar_deveDispararException_dadoEstoqueNegativo() {
        Transacao compra = new Transacao();

        compra.setData(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        compra.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        compra.setNomeFornecedor("juca gas");
        compra.setValorVenda(BigDecimal.ZERO);
        compra.setValorCompra(BigDecimal.valueOf(70));
        compra.setVendas(0);
        compra.setEntradas(100);
        compra.setProduto(NOME_PRODUTO);

        Transacao venda = new Transacao();

        venda.setData(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
        venda.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        venda.setNomeFornecedor("juca gas");
        venda.setValorVenda(BigDecimal.valueOf(120));
        venda.setValorCompra(BigDecimal.ZERO);
        venda.setVendas(5);
        venda.setEntradas(0);
        venda.setProduto(NOME_PRODUTO);

        when(usuarioService.usuarioLogado()).thenReturn(USUARIO_LOGADO);
        when(repository.findAllByProdutoAndUsuarioUsername(NOME_PRODUTO, USUARIO_LOGADO)).thenReturn(List.of(compra, venda));

        Transacao transacaoInvalida = new Transacao();

        transacaoInvalida.setData(LocalDateTime.of(2025, 1, 2, 3, 0, 0));
        transacaoInvalida.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        transacaoInvalida.setNomeFornecedor("juca gas");
        transacaoInvalida.setValorVenda(BigDecimal.valueOf(120));
        transacaoInvalida.setValorCompra(BigDecimal.ZERO);
        transacaoInvalida.setVendas(500);
        transacaoInvalida.setEntradas(0);
        transacaoInvalida.setProduto(NOME_PRODUTO);

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> service.cadastrar(transacaoInvalida))
                .withMessage("Valor do estoque não pode ser negativo");
    }

    @Test
    @DisplayName("Cenário 2: Venda permitida dentro do limite de estoque")
    void cadastrar_devePermitirCadastro_dadoQueEstoqueNaoFicaraNegativo() {
        Transacao compra = new Transacao();

        compra.setData(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        compra.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        compra.setNomeFornecedor("juca gas");
        compra.setValorVenda(BigDecimal.ZERO);
        compra.setValorCompra(BigDecimal.valueOf(70));
        compra.setVendas(0);
        compra.setEntradas(100);
        compra.setProduto(NOME_PRODUTO);

        Transacao venda = new Transacao();

        venda.setData(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
        venda.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        venda.setNomeFornecedor("juca gas");
        venda.setValorVenda(BigDecimal.valueOf(120));
        venda.setValorCompra(BigDecimal.ZERO);
        venda.setVendas(5);
        venda.setEntradas(0);
        venda.setProduto(NOME_PRODUTO);

        when(usuarioService.usuarioLogado()).thenReturn(USUARIO_LOGADO);
        when(repository.findAllByProdutoAndUsuarioUsername(NOME_PRODUTO, USUARIO_LOGADO)).thenReturn(List.of(compra, venda));

        Transacao transacaoValida = new Transacao();

        transacaoValida.setData(LocalDateTime.of(2025, 1, 2, 3, 0, 0));
        transacaoValida.setUsuario(new Usuario(USUARIO_LOGADO, "1234"));
        transacaoValida.setNomeFornecedor("juca gas");
        transacaoValida.setValorVenda(BigDecimal.valueOf(120));
        transacaoValida.setValorCompra(BigDecimal.ZERO);
        transacaoValida.setVendas(50);
        transacaoValida.setEntradas(0);
        transacaoValida.setProduto(NOME_PRODUTO);

        when(repository.save(transacaoValida)).thenReturn(transacaoValida);

        assertThatCode(() -> service.cadastrar(transacaoValida))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Cenário 3: Geração de relatório financeiro após compra e venda.")
    void gerarRelatorio_deveCalcularOTotalDeComprasEVendas() {
        Usuario usuario = new Usuario(USUARIO_LOGADO, PASSWORD);

        Transacao compra = new Transacao();

        compra.setData(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        compra.setUsuario(usuario);
        compra.setNomeFornecedor("juca gas");
        compra.setValorCompra(BigDecimal.valueOf(70));
        compra.setEntradas(100);
        compra.setProduto(NOME_PRODUTO);

        Transacao venda = new Transacao();

        venda.setData(LocalDateTime.of(2025, 1, 2, 0, 0, 0));
        venda.setUsuario(usuario);
        venda.setNomeFornecedor("juca gas");
        venda.setValorVenda(BigDecimal.valueOf(120));
        venda.setVendas(5);
        venda.setProduto(NOME_PRODUTO);

        when(usuarioService.usuarioLogado()).thenReturn(usuario.getUsername());
        when(repository.findAllByProdutoAndUsuarioUsername(NOME_PRODUTO, usuario.getUsername())).thenReturn(List.of(compra, venda));

        assertThat(service.gerarRelatorio(NOME_PRODUTO))
                .returns(BigDecimal.valueOf(7000), RelatorioDto::totalCompras)
                .returns(BigDecimal.valueOf(600), RelatorioDto::totalVendas)
                .returns(BigDecimal.valueOf(-6400), relatorio -> relatorio.totalVendas().subtract(relatorio.totalCompras()));
    }
}
