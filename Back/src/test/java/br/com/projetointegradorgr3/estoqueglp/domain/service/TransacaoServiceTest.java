package br.com.projetointegradorgr3.estoqueglp.domain.service;

import br.com.projetointegradorgr3.estoqueglp.domain.model.Transacao;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Usuario;
import br.com.projetointegradorgr3.estoqueglp.domain.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TransacaoServiceTest {

    TransacaoRepository repository = mock(TransacaoRepository.class);
    UsuarioService usuarioService = mock(UsuarioService.class);
    TransacaoService service = new TransacaoService(repository, usuarioService);

    @Test
    void buscar() {

        Transacao transacao = new Transacao();

        transacao.setData(LocalDateTime.now());
        transacao.setUsuario(new Usuario("mock", "mock"));
        transacao.setNomeFornecedor("mock");
        transacao.setValorVenda(BigDecimal.ZERO);
        transacao.setValorCompra(BigDecimal.ZERO);
        transacao.setVendas(1);
        transacao.setEntradas(1);
        transacao.setProduto("mock");

        when(usuarioService.usuarioLogado()).thenReturn("mock");
        when(repository.findAllByUsuarioUsername("mock")).thenReturn(List.of(transacao));


        assertThat(service.buscar(null))
                .hasSize(1);
    }
}