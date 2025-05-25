package br.com.projetointegradorgr3.estoqueglp.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "controle_estoque")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "qtd_entrada")
    private int entradas;

    @Column(name = "qtd_vendas")
    private int vendas;

    @Column(name = "data_transacao")
    private LocalDateTime data;

    @Column(name = "valor_compra")
    private BigDecimal valorCompra = BigDecimal.ZERO;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @Column(name = "nome_fornecedor")
    private String nomeFornecedor;

    @Column(name = "nome_produto")
    private String produto;

    @Transient
    private int estoqueAposTransacao;

    @Transient
    private BigDecimal valorAposTransacao;

    public int calcularQuantidadeDaTransacao() {
        return entradas - vendas;
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public int getVendas() {
        return vendas;
    }

    public void setVendas(int vendas) {
        this.vendas = vendas;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getEstoqueAposTransacao() {
        return estoqueAposTransacao;
    }

    public void setEstoqueAposTransacao(int estoqueAposTransacao) {
        this.estoqueAposTransacao = estoqueAposTransacao;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }
}
